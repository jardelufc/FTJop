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
 * Created on 26.04.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

/**
 * @author admin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Echo {

	public static void main(String[] args) {

        int temp=0, count=0;

		System.out.println("Hello");
		for (;;) {
/*
			if ((Native.rd(Const.IO_STATUS)&Const.MSK_UA_RDRF)!=0) {
				int val = Native.rd(Const.IO_UART);
*/
			try {
				if (System.in.available()!=0) {

					int val = System.in.read();

                    switch(count){
                        case 0: val = (val + temp);
                                break;
                        case 1: val = (val*temp);
                                break;
                        case 2: val = (val&temp);
                                break;
                        case 3: val = ((val*2)+1);
                                break;
                        case 4: val = ((val*temp)+1);
                                break;
                        //case 5: val = (val||temp);
                        //        break;

                    }

                    temp = val;
                    count = (count +1);
                    if(count == 5) count = 0;

					System.out.print(val);
					System.out.print(" ");
				}
			} catch (Exception e) { /* do nothing in JOP */ }
		}
	}
}
