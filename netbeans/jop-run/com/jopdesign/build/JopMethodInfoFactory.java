/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jopdesign.build;

/**
 *
 * @author Jilseph
 */
public abstract class JopMethodInfoFactory {

	/**
	 * Retorna uma nova instância de JopMethodInfo.
	 */
	public static JopMethodInfo create(ClassInfo jc, String mid) {
		// return new JopMethodInfo(jc, mid);
		return new JopMethodInfoLesc(jc, mid);
	}

}
