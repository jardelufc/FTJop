/*
 * Copyright (c) Martin Schoeberl, martin@jopdesign.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *	This product includes software developed by Martin Schoeberl
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

package ejip;

/**
*	Loopback.java
*
*	Loopback link layer driver.
*/


/**
*	Loopback driver.
*/

public class Loopback extends LinkLayer {

	private static final int MAX_BUF = 1500;		// or should we use 1006


/**
*	The one and only reference to this object.
*/
	private static Loopback single;
	
/**
*	private constructor. The singleton object is created in init().
*/
	private Loopback() {
	}

/**
*	allocate buffer, start serial buffer and slip Thread.
*/
	public static LinkLayer init() {


		if (single != null) return single;	// allready called init()

		single = new Loopback();
		single.ip = (127<<24) + (0<<16) + (0<<8) + 1;
		return single;
	}

	public int getIpAddress() {
		return ip;
	}

	/**
	*	Set connection strings and connect.
	*/
	public void startConnection(StringBuffer dialstr, StringBuffer connect, StringBuffer user, StringBuffer passwd) {
		// useless for loopback driver
	}
	/**
	*	Forces the connection to be new established.
	*	On Slip ignored.
	*/
	public void reconnect() {
	}
	static int timer;
	
/**
*	main loop.
*/
	public void loop() {

		Packet p;

		//
		// get a ready to send packet with source from this driver.
		//
		p = Packet.getPacket(single, Packet.SND_DGRAM, Packet.ALLOC);
		// TODO: we need to copy a TCP packet
		if (p!=null) {
			//
			// and simple mark it as received packet.
			//
			p.setStatus(Packet.RCV);		// inform upper layer
		}
	}

/* (non-Javadoc)
 * @see ejip.LinkLayer#getConnCount()
 */
public int getConnCount() {
	return 0;
}


}
