/*
This file is part of JOP, the Java Optimized Processor
see <http://www.jopdesign.com/>

Copyright (C) 2004,2005, Flavius Gruian
Copyright (C) 2005-2008, Martin Schoeberl (martin@jopdesign.com)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jopdesign.build;

import br.ufc.lesc.crc.Crc;
import java.util.*;
import java.io.PrintWriter;
import java.io.Serializable;

import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.Type;

/**
 * @author Flavius, Martin
 * 
 */
public class JopMethodInfoLesc extends JopMethodInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	//static List clinitList;
	//int codeAddress;
	// struct address is ONLY useful for <clinit> methods
	// and the boot/main methods!
	// Now it's necessary for debugging too.
	//int structAddress;
	//CodeException[] exctab;
	//int mstack, margs, mreallocals;
	//int len, exclen;
	//int vtindex;

	/**
	 * Constructor is only used by the ClassInfo visitor 
	 * 
	 * @param jc
	 * @param mid
	 */
	protected JopMethodInfoLesc(ClassInfo jc, String mid) {
		super(jc, mid);
		codeAddress = 0;
		structAddress = 0;
	}

	/**
	 * Return the correct type of cli
	 */
	@Override
	public JopClassInfo getCli() {
		return (JopClassInfo) super.getCli();
	}

	/**
	 * @param m
	 */
	@Override
	public void setInfo(int addr) {
		codeAddress = addr;

		Method m = getMethod();

		margs = 0;
		Type at[] = m.getArgumentTypes();
		for (int i = 0; i < at.length; ++i) {
			margs += at[i].getSize();
		}
		// FIXME! invokespecial adds an extra objref!!! inits, private, and
		// superclass calls
		// for now only handle inits
		if (!m.isStatic()) {
			margs++;
		}
		if (m.isAbstract()) {
			mstack = mreallocals = len = exclen = 0;
			exctab = null;
		} else {
			mstack = m.getCode().getMaxStack();
			// the 'real' locals - means without arguments
			mreallocals = m.getCode().getMaxLocals() - margs;
			// System.err.println(" ++++++++++++ "+methodId+" --> mlocals
			// ="+mlocals+" margs ="+margs);
			len = (m.getCode().getCode().length + 3) / 4;
			exctab = m.getCode().getExceptionTable();
			exclen = exctab != null ? exctab.length : 0;

			// TODO: couldn't len=JOP...MAX_SIZE/4 be ok?
			if (len >= JOPizer.METHOD_MAX_SIZE / 4 || mreallocals > 31 || margs > 31) {
				// we interprete clinit on JOP - no size restriction
				if (!m.getName().equals("<clinit>")) {
					System.err.println("len(max:" + (JOPizer.METHOD_MAX_SIZE / 4) + ")=" + len + "mreallocals(max:31)=" + mreallocals + " margs(max:31)=" + margs);
					System.err.println("wrong size: " + getCli().clazz.getClassName() + "." + methodId);
					System.exit(-1);
				}
			}
		// System.out.println((mstack+m.getCode().getMaxLocals())+" "+
		// m.getName()+" maxStack="+mstack+"
		// locals="+m.getCode().getMaxLocals());

		}
	}

	@Override
	public int getLength() {

		// Adicionando os 4 métodos extra do CRC
		// Aparentemente, o comprimento é dado em word onde na definição
		// do JOP, uma word equivale a 4 bytes... (a confirmar).
		return getMethod().isAbstract() ? 0 : len + 1 + 2 * exclen + 1;
	// return getMethod().isAbstract() ? 0 : len + 1 + 2 * exclen;
	}

	private void oldDumpMethodStruct(PrintWriter out, int addr) {

		if (methodId.equals(AppInfo.clinitSig) && len >= JOPizer.METHOD_MAX_SIZE / 4) {
			out.println("\t// no size for <clinit> - we iterpret it and allow larger methods!");
		}
		// java_lang_String
		// 0x01 TODO access
		// 2 TODO ? stack
		// code start:1736
		// code length:4
		// cp:3647
		// locals: 1 args size: 1

		String abstr = "";
		if (getMethod().isAbstract()) {
			abstr = "abstract ";
		}

		out.println("\t//\t" + addr + ": " + abstr + getCli().clazz.getClassName() + "." + methodId);
		out.println("\t\t//\tcode start: " + codeAddress);
		out.println("\t\t//\tcode length: " + len);
		out.println("\t\t//\tcp: " + getCli().cpoolAddress);
		out.println("\t\t//\tlocals: " + (mreallocals + margs) + " args size: " + margs);

		int word1 = codeAddress << 10 | len;
		// no length on large <clinit> methods
		// get interpreted at start - see Startup.clazzinit()
		if (methodId.equals(AppInfo.clinitSig) && len >= JOPizer.METHOD_MAX_SIZE / 4) {
			word1 = codeAddress << 10;
		}
		int word2 = getCli().cpoolAddress << 10 | mreallocals << 5 | margs;

		if (getMethod().isAbstract()) {
			word1 = word2 = 0;
		}

		out.println("\t\t" + word1 + ",");
		out.println("\t\t" + word2 + ",");
	}

	private void newDumpMethodStruct(PrintWriter out, int addr) {
		int oldLen = len;
		len++;

		if (methodId.equals(AppInfo.clinitSig) && len >= JOPizer.METHOD_MAX_SIZE / 4) {
			out.println("\t// no size for <clinit> - we iterpret it and allow larger methods!");
		}
		// java_lang_String
		// 0x01 TODO access
		// 2 TODO ? stack
		// code start:1736
		// code length:4
		// cp:3647
		// locals: 1 args size: 1

		String abstr = "";
		if (getMethod().isAbstract()) {
			abstr = "abstract ";
		}

		out.println("\t//\t" + addr + ": " + abstr + getCli().clazz.getClassName() + "." + methodId);
		out.println("\t\t//\tcode start: " + codeAddress);
		out.println("\t\t//\told code length: " + oldLen);
		out.println("\t\t//\tcode length: " + len);
		out.println("\t\t//\tcp: " + getCli().cpoolAddress);
		out.println("\t\t//\tlocals: " + (mreallocals + margs) + " args size: " + margs);

		int word1 = codeAddress << 10 | len;
		// no length on large <clinit> methods
		// get interpreted at start - see Startup.clazzinit()
		if (methodId.equals(AppInfo.clinitSig) && len >= JOPizer.METHOD_MAX_SIZE / 4) {
			word1 = codeAddress << 10;
		}
		int word2 = getCli().cpoolAddress << 10 | mreallocals << 5 | margs;

		if (getMethod().isAbstract()) {
			word1 = word2 = 0;
		}

		out.println("\t\t" + word1 + ",");
		out.println("\t\t" + word2 + ",");

		len = oldLen;
	}

	@Override
	public void dumpMethodStruct(PrintWriter out, int addr) {

		//oldDumpMethodStruct(out, addr);
		newDumpMethodStruct(out, addr);

	}

	private String toHex(int integer) {
		String vhex = Integer.toHexString(integer);
		int vhexlen = vhex.length();
		if (vhexlen == 1 || vhexlen == 3) {
			return "0x0" + vhex;
		} else {
			return "0x" + vhex;
		}
	}

	@Override
	public void dumpByteCode(PrintWriter out) {

		// Lista que salva as words geradas...
		List<Integer> words = new ArrayList<Integer>();

		out.println("//\t" + codeAddress + ": " + methodId);
		if (getCode() == null) {
			out.println("//\tabstract");
			return;
		}
		byte bc[] = getCode().getCode();
		String post = "// ";
		int i, word, j;
		for (j = 0, i = 3, word = 0; j < bc.length; j++) {
			int bits = (bc[j] & 0xFF);
			word = word << 8 | bits;
			post += bits + " (" + toHex(bits) + ") ";
			if (i == 0) {

                words.add(new Integer(word));
				out.println("\t" + word + ",\t // (" + toHex(word) + ") " 
                        + post + " CRC: 0x" + getCrcString(words));

				post = "// ";
				i = 3;
				word = 0;
			} else {
				i--;
			}
		}
		if (i != 3) {
			word = word << (i + 1) * 8;

			words.add(new Integer(word));
			out.println("\t" + word + ",\t // (" + toHex(word) + ") " 
                    + post + " CRC: 0x" + getCrcString(words));
		}

        // Adicionar o CRC
		post = "// CRC do Jardel: 0x" + getCrcString(words);
		out.println("\t" + getCrcInt(words) + ",\t" + post);

		word = getMethod().isSynchronized() ? 1 : 0;
		word = word << 16 | (exclen & 0xFFFF);
		out.println("\t" + word + ",\t//\tsynchronized?, exception table length");
		words.add(new Integer(word));
        

		for (i = 0; i < exclen; i++) {
			Integer idx = new Integer(exctab[i].getCatchType());
			int pos = getCli().cpoolUsed.indexOf(idx) + 1;

			word = exctab[i].getStartPC();
			post = "// start: " + exctab[i].getStartPC();
			word = word << 16 | exctab[i].getEndPC();
			post += "\tend: " + exctab[i].getEndPC();
			out.println("\t" + word + ",\t" + post);
			words.add(new Integer(word));

			word = exctab[i].getHandlerPC();
			post = "// target: " + exctab[i].getHandlerPC();
			word = word << 16 | pos;
			post += "\ttype: " + pos;
			out.println("\t" + word + ",\t" + post);
			words.add(new Integer(word));
		}

		// Adicionar o CRC
//		Crc crc = new Crc(words);
//		word = crc.getInt();
//		post = "// CRC do Jardel: 0x" + crc.getHex8PaddingString();
//		out.println("\t" + word + ",\t" + post);
	}

    private String getCrcString(List<Integer> words) {
        Crc crc = new Crc(words);
        return crc.getHex8PaddingString();
    }

    private int getCrcInt(List<Integer> words) {
        Crc crc = new Crc(words);
        return crc.getInt();
    }

	@Override
	public int getCodeAddress() {
		return codeAddress;
	}

	@Override
	public int getStructAddress() {
		return structAddress;
	}
}
