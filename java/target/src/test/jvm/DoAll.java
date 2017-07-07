/*
  This file is part of JOP, the Java Optimized Processor
    see <http://www.jopdesign.com/>

  Copyright (C) 2001-2008, Martin Schoeberl (martin@jopdesign.com)

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

/*
 * Created on 30.07.2005
 *
 */
package jvm;

import jvm.math.*;
import jvm.obj.*;

/**
 * @author Martin Schoeberl (martin@jopdesign.com)
 *
 */
public class DoAll {

	public static void main(String[] args) {
		
		TestCase tc[] = {
				new Basic(),
				new Basic2(),
				new TypeMix(),
				new Static(),
				new Array(),
				new ArrayTest2(),
				new ArrayTest3(),
				new Clinit(),
				new Clinit2(),
				new Iface(),
				new Conversion(),
				new FloatTest(),
				new StackManipulation(),
				new Imul(),
				new LongTest(),
				new LongField(),
				new MultiArray(),
				new Switch(),
				new InstanceCheckcast(),
				new Except(),
				new SystemCopy(),
				new CheckCast(),
				new Ifacmp(),
				new BranchTest1(),
				new BranchTest2(),
				new BranchTest3(),
				new Switch2(),
				new Logic1(),
				new Logic2(),
				new Logic3(),
				new InvokeSpecial(),
				new PutRef()
		};
		
		for (int i=0; i<tc.length; ++i) {
			System.out.print(tc[i].getName());
			if (tc[i].test()) {
				System.out.println(" ok");
			} else {
				System.out.println(" failed!");
			}
		}
		
//		jbe.DoAll.main(args);
	}
}
