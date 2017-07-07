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

package jdk;

import java.util.*;
import jvm.TestCase;

public class TestVector extends TestCase {

	public String getName() {
		return "Vector";
	}

	public boolean test() {

		boolean ret = true;
		Vector v = new Vector();
		
		for (int i=0; i<10; ++i) {
			v.addElement(new Integer(i));
		}
		for (int i=0; i<10; ++i) {
			Integer ival = (Integer) v.elementAt(i);
			if (ival.intValue() != i) {
				ret = false;
			}
		}
		return ret;
	}

}
