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
 * Copyright (c) Martin Schoeberl, martin@jopdesign.com
 * All rights reserved.
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 */

package tal;

/**
*	Tal.java: test main.
*
*	Author: Martin Schoeberl (martin@jopdesign.com)
*
*	2004-09-09	Version 0.05
*	2006-09-20	Version 0.06: shorten loop cycle to 5 ms to get short
*				pulses (10 ms)
*	2006-10-09	Version 1.00: update JOP to ignore HW handshake
*				by default
*
*/

import joprt.RtThread;
import util.Dbg;
import util.Serial;
import util.Timer;

import com.jopdesign.sys.Const;

import ejip.CS8900;
import ejip.Html;
import ejip.LinkLayer;
import ejip.Net;

/**
*	TAL Main.
*/

public class Tal {

	static Net net;
	static LinkLayer ipLink;
	static Serial ser;

	static Modem modem;
	static Fwp fwp;
	static Param par;
	private static Loop loop;	// reference not used by anyone else
	
	private static boolean run;
	
	private static boolean simpc;
	private static boolean evn;
	/**
	*	Start all threads and enter forever loop.
	*/
	
	public static void main(String[] args) {

simpc = args!=null;	// we provide a null pointer in Startup.java for JOP
evn = true;
		run = true;
		// not so good for final application
		// will be UDP...
		if (simpc) {
			Dbg.initSer();
		} else {
			Dbg.init();
		}

		//
		//	start TCP/IP and all (four) threads
		//
		net = Net.init();
		int[] outReg = new int[1];
		outReg[0] = 0;
		Html.setOutValArray(outReg);
		// don't use CS8900 when simulating on PC
		if (!simpc) {
			int[] eth = {0x00, 0xe0, 0x98, 0x33, 0xb0, 0xf8};
			int ip = (192<<24) + (168<<16) + (0<<8) + 123;
			ipLink = CS8900.init(eth, ip);
		}
		// use instead Slip for PC simulation
		// LinkLayer ipLink = Slip.init(Const.IO_UART_BG_MODEM_BASE,
		//	(192<<24) + (168<<16) + (1<<8) + 2); 

		//
		//	start device driver threads
		//

		new RtThread(5, 10000) {
			public void run() {
				for (;;) {
					waitForNextPeriod();
					net.loop();
					if (!simpc) ipLink.loop();
				}
			}
		};

		par = new Param();
		fwp = new Fwp();
		if (simpc) {
			ser = new Serial(Const.IO_UART_BG_MODEM_BASE);
		} else {
			ser = new Serial(Const.IO_UART1_BASE);
		}
		if (evn) {
			modem = Modem.getInstance(5, 100000, ser);
		} else {
			new Modbus(5, 10000, ser, outReg);
		}

		new RtThread(10, 3000) {
			public void run() {
				for (;;) {
					waitForNextPeriod();
					ser.loop();
				}
			}
		};
		
		loop = new Loop(7, 5000);

		RtThread.startMission();
		forever();
	}
	
	public static void stop() {
		run = false;
	}

	private static void forever() {

		//
		//	Just do the WD blink with lowest priority.
		//	=> if the other threads take to long there will be a reset
		//
		for (int cnt=0;cnt<20;) {
			RtThread.sleepMs(500);
			Timer.wd();
			if (!run) ++cnt;	// stop in 10 seconds
		}
	}
}
