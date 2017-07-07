/*
  This file is part of JOP, the Java Optimized Processor
    see <http://www.jopdesign.com/>

  Copyright (C) 2006, Rasmus Ulslev Pedersen

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

package gctest;

import joprt.RtThread;
import util.Dbg;
import com.jopdesign.sys.*;

// A test of the GCStackWalker to see of the threads are scheduled. TODO: Do some GC:-)

public class GCTest6 {

  public static void main(String s[]) {
	  
	  new RtThread(18, 500000) {
			public void run() {
				for (;;) {
     		  System.out.print("Thread 1");
     		  waitForNextPeriod();
				}
			}
		};

	  new RtThread(19, 500000) {
			public void run() {
				for (;;) {
          System.out.print("Thread 2");
					waitForNextPeriod();
				}
			}
		};
    
		RtThread.startMission();

		for(int i=0;i<3;i++){
  		System.out.println("Thread 0");
			RtThread.sleepMs(500);
		}
		System.out.println("Test 6 ok");
	} //main
}