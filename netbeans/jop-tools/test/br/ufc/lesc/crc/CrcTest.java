/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.lesc.crc;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jilseph
 */
public class CrcTest {

	public CrcTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testCrc1() {
		List<Integer> words = new ArrayList<Integer>();
		words.add(new Integer(Integer.reverseBytes(0xD2881003)));
		words.add(new Integer(Integer.reverseBytes(0x9AD38610)));
		words.add(new Integer(Integer.reverseBytes(0xB3033D00)));
		words.add(new Integer(Integer.reverseBytes(0x00B80400)));
		words.add(new Integer(Integer.reverseBytes(0xB8061205)));
		words.add(new Integer(Integer.reverseBytes(0x00B30700)));
		words.add(new Integer(Integer.reverseBytes(0x00B80308)));
		words.add(new Integer(Integer.reverseBytes(0x0900B307)));
		words.add(new Integer(Integer.reverseBytes(0xB23BD304)));
		words.add(new Integer(Integer.reverseBytes(0x071A0900)));
		words.add(new Integer(Integer.reverseBytes(0x0A00B860)));
		words.add(new Integer(Integer.reverseBytes(0xB80B00B8)));
		words.add(new Integer(Integer.reverseBytes(0xB3040C00)));
		words.add(new Integer(Integer.reverseBytes(0x00B80400)));
		words.add(new Integer(Integer.reverseBytes(0xD38B100D)));
		words.add(new Integer(Integer.reverseBytes(0x00BD6404)));
		words.add(new Integer(Integer.reverseBytes(0x0F00E10E)));
		words.add(new Integer(Integer.reverseBytes(0xD2891004)));
		words.add(new Integer(Integer.reverseBytes(0xD2801004)));
		words.add(new Integer(Integer.reverseBytes(0x3BD38610)));
		words.add(new Integer(Integer.reverseBytes(0x14009A1A)));
		words.add(new Integer(Integer.reverseBytes(0x1A3BD304)));
		words.add(new Integer(Integer.reverseBytes(0x3BD36006)));
		words.add(new Integer(Integer.reverseBytes(0xB8DE1A03)));
		words.add(new Integer(Integer.reverseBytes(0x00A71100)));
		words.add(new Integer(Integer.reverseBytes(0x0F00E01C)));
		words.add(new Integer(Integer.reverseBytes(0x3264041A)));
		words.add(new Integer(Integer.reverseBytes(0xE00F00C6)));
		words.add(new Integer(Integer.reverseBytes(0x041A0F00)));
		words.add(new Integer(Integer.reverseBytes(0x00B93264)));
		words.add(new Integer(Integer.reverseBytes(0xA7000112)));
		words.add(new Integer(Integer.reverseBytes(0x00B10000)));
		Crc crc = new Crc(words);
		assertEquals("8BFF7F0D", crc.getHex8PaddingString().toUpperCase());
		assertEquals(0x8BFF7F0D, crc.getInt());
	}

	@Test
	public void testCrc2() {
		List<Integer> words = new ArrayList<Integer>();
		words.add(new Integer(Integer.reverseBytes(0x2900B62A)));
		words.add(new Integer(Integer.reverseBytes(0x1C3D033C)));
		words.add(new Integer(Integer.reverseBytes(0x1200A21B)));
		words.add(new Integer(Integer.reverseBytes(0x00B61C2A)));
		words.add(new Integer(Integer.reverseBytes(0x0E00B82A)));
		words.add(new Integer(Integer.reverseBytes(0x3D60041C)));
		words.add(new Integer(Integer.reverseBytes(0xB1EFFFA7)));
		Crc crc = new Crc(words);
		assertEquals("CC3ECFEE",crc.getHex8PaddingString().toUpperCase());
		assertEquals(0xCC3ECFEE,crc.getInt());
	}

	@Test
	public void testCrc3() {
		List<Integer> words = new ArrayList<Integer>();
		words.add(new Integer(Integer.reverseBytes(0x0C009B1B)));
		words.add(new Integer(Integer.reverseBytes(0x00E22A1B)));
		words.add(new Integer(Integer.reverseBytes(0x00A1BE00)));
		words.add(new Integer(Integer.reverseBytes(0x0B00BB0C)));
		words.add(new Integer(Integer.reverseBytes(0x00B71B59)));
		words.add(new Integer(Integer.reverseBytes(0xE22ABF17)));
		words.add(new Integer(Integer.reverseBytes(0x341B0000)));
		words.add(new Integer(Integer.reverseBytes(0x000000AC)));
		Crc crc = new Crc(words);
		assertEquals("AE7DC299",crc.getHex8PaddingString().toUpperCase());
		assertEquals(0xAE7DC299,crc.getInt());
	}
}
