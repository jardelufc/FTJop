/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufc.lesc.joprun;

import com.jopdesign.build.JOPizer;

/**
 *
 * @author Jilseph
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Criando argumentos
		String param1 = "-cp";
		String param2 = "../jop-test/dist/jop-test.jar";
		String param3 = "-o";
		String param4 = "HelloWorld.jop";
		String param5 = "test/HelloWorld";

		// Criando vetor de argumentos
		String[] jopizerArgs = new String[] {
			param1, param2, param3, param4, param5
		};

		// Chamando JOPizer
		JOPizer.main(jopizerArgs);
    }

}
