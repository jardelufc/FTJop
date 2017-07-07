/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.lesc.crc;

/**
 *
 * @author Jilseph
 */
public class CrcUtils {

	public static int hexToBin(char hex) {
		switch (hex) {
			case '0':
				return 0;
			case '1':
				return 1;
			case '2':
				return 10;
			case '3':
				return 11;
			case '4':
				return 100;
			case '5':
				return 101;
			case '6':
				return 110;
			case '7':
				return 111;
			case '8':
				return 1000;
			case '9':
				return 1001;
			case 'A':
			case 'a':
				return 1010;
			case 'B':
			case 'b':
				return 1011;
			case 'C':
			case 'c':
				return 1100;
			case 'D':
			case 'd':
				return 1101;
			case 'E':
			case 'e':
				return 1110;
			case 'F':
			case 'f':
				return 1111;
			default:
				throw new IllegalArgumentException("Char '" + hex + "' is not hexadecimal.");
		}
	}

	public static char binToHex(int bin) {
		switch (bin) {
			case 0:
				return '0';
			case 1:
				return '1';
			case 10:
				return '2';
			case 11:
				return '3';
			case 100:
				return '4';
			case 101:
				return '5';
			case 110:
				return '6';
			case 111:
				return '7';
			case 1000:
				return '8';
			case 1001:
				return '9';
			case 1010:
				return 'A';
			case 1011:
				return 'B';
			case 1100:
				return 'C';
			case 1101:
				return 'D';
			case 1110:
				return 'E';
			case 1111:
				return 'F';
			default:
				throw new IllegalArgumentException("Binary '" + bin + "' is invalid.");
		}
	}

	public static String toHex8PaddingString(int word) {
		String hex = Integer.toHexString(word);
		if (hex.length() < 8) {
			for (int i = hex.length(); i < 8; i++) {
				hex = "0" + hex;
			}
		}
		return hex.toUpperCase();
	}

	public static int toInt(String hex) {
		long longValue = Long.parseLong(hex, 16);
		return (int) (longValue & 0xFFFFFFFF);
	}
}
