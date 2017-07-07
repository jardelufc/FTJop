/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.lesc.crc;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jilseph
 */
public class Crc {

//	private byte[] bytes;
//
//	public Crc(byte[] bytes) {
//		if (bytes == null)
//			throw new NullPointerException("bytes");
//
//		int len = bytes.length;
//		this.bytes = new byte[len];
//		for (int i = 0; i < len; i++) {
//			this.bytes[i] = bytes[i];
//		}
//	}
	private List<Integer> words;
	private int[] CRC = new int[32];
	private int[] Inic = new int[32];
	private int[] D = new int[32];
	private int[] C = new int[32];
	private int crc = 0;
	private String crcHexString = "00000000";
	private boolean generate = false;

	public Crc(List<Integer> words) {
		if (words == null) {
			throw new NullPointerException("words");
		}

		this.words = new ArrayList<Integer>();
		for (Integer word : words) {
            int rev = revert(word.intValue());
            //System.out.println(Integer.toHexString(word.intValue()) +
            //        " = " + Integer.toHexString(rev));
			this.words.add(new Integer(rev));
		}

		InitC();
	}

    private int revert(int word) {
        return Integer.reverseBytes(word);
        //return ((word << 24) & 0xFF000000) |
        //       ((word << 16) & 0x00FF0000) |
        //       ((word <<  8) & 0x0000FF00) |
        //       ( word        & 0x000000FF);
    }

	private void calculate() {
		if (generate) {
			return;
		}

		for (Integer word : words) {
			updateD(word);
			updateCRC();
			updateC();
		}

		crcHexString = crcToHexString();
		crc = CrcUtils.toInt(crcHexString);

		generate = true;
	}

	public String getHex8PaddingString() {
		calculate();
		return crcHexString;
	}

	public int getInt() {
		calculate();
		return crc;
	}

	private void InitC() {
		for (int i = 0; i < 32; i++) {
			C[i] = 0;
		}
	}

	protected final String toHexString(int word) {
		String hex = Integer.toHexString(word);
		if (hex.length() < 8) {
			for (int i = hex.length(); i < 8; i++) {
				hex = "0" + hex;
			}
		}
		return hex.toUpperCase();
	}

	private void updateD(Integer word) {
		updateD(word.intValue());
	}

	private void updateD(int word) {
		String hex = toHexString(word);
		for (int i = 0; i < 8; i++) {
			int int_bin = CrcUtils.hexToBin(hex.charAt(i));
			D[31 - (i * 4)] = int_bin / 1000;
			D[30 - (i * 4)] = (int_bin / 100) % 10;
			D[29 - (i * 4)] = (int_bin / 10) % 10;
			D[28 - (i * 4)] = (int_bin % 10);
		}
	}

	private void updateC() {
		for (int i=0; i<32; i++) {
        	C[i] = CRC[i];
      	}
	}

	private String crcToHexString() {
		String hexCrc = "";
		for (int i=0; i < 8; i++) {
			int bin = ((CRC[31 - i*4]*1000) + (CRC[30 - i*4]*100) +
				(CRC[29 - i*4]*10) + (CRC[28 - i*4]));
			hexCrc = hexCrc + new Character(CrcUtils.binToHex(bin)).toString();
		}
		return hexCrc; 
	}

	private void updateCRC() {
		CRC[0] = D[31] ^ D[30] ^ D[29] ^ D[28] ^ D[26] ^ D[25] ^
			D[24] ^ D[16] ^ D[12] ^ D[10] ^ D[9] ^ D[6] ^ D[0] ^
			C[0] ^ C[6] ^ C[9] ^ C[10] ^ C[12] ^ C[16] ^ C[24] ^
			C[25] ^ C[26] ^ C[28] ^ C[29] ^ C[30] ^ C[31];
		CRC[1] = D[28] ^ D[27] ^ D[24] ^ D[17] ^ D[16] ^ D[13] ^
			D[12] ^ D[11] ^ D[9] ^ D[7] ^ D[6] ^ D[1] ^D[0] ^
			C[0] ^ C[1] ^ C[6] ^ C[7] ^ C[9] ^C[11] ^ C[12] ^
			C[13] ^ C[16] ^ C[17] ^ C[24] ^C[27] ^ C[28];
		CRC[2] = D[31] ^ D[30] ^ D[26] ^ D[24] ^ D[18] ^ D[17] ^
			D[16] ^ D[14] ^ D[13] ^ D[9] ^ D[8] ^ D[7] ^
			D[6] ^ D[2] ^ D[1] ^ D[0] ^ C[0] ^ C[1] ^
			C[2] ^ C[6] ^ C[7] ^ C[8] ^ C[9] ^ C[13] ^
			C[14] ^ C[16] ^ C[17] ^ C[18] ^ C[24] ^ C[26] ^
			C[30] ^ C[31];
		CRC[3] = D[31] ^ D[27] ^ D[25] ^ D[19] ^ D[18] ^ D[17] ^
			D[15] ^ D[14] ^ D[10] ^ D[9] ^ D[8] ^ D[7] ^
			D[3] ^ D[2] ^ D[1] ^ C[1] ^ C[2] ^ C[3] ^
			C[7] ^ C[8] ^ C[9] ^ C[10] ^ C[14] ^ C[15] ^
			C[17] ^ C[18] ^ C[19] ^ C[25] ^ C[27] ^ C[31];
		CRC[4] = D[31] ^ D[30] ^ D[29] ^ D[25] ^ D[24] ^ D[20] ^
			D[19] ^ D[18] ^ D[15] ^ D[12] ^ D[11] ^ D[8] ^
			D[6] ^ D[4] ^ D[3] ^ D[2] ^ D[0] ^ C[0] ^
			C[2] ^ C[3] ^ C[4] ^ C[6] ^ C[8] ^ C[11] ^
			C[12] ^ C[15] ^ C[18] ^ C[19] ^ C[20] ^ C[24] ^
			C[25] ^ C[29] ^ C[30] ^ C[31];
		CRC[5] = D[29] ^ D[28] ^ D[24] ^ D[21] ^ D[20] ^ D[19] ^
			D[13] ^ D[10] ^ D[7] ^ D[6] ^ D[5] ^ D[4] ^
			D[3] ^ D[1] ^ D[0] ^ C[0] ^ C[1] ^ C[3] ^
			C[4] ^ C[5] ^ C[6] ^ C[7] ^ C[10] ^ C[13] ^
			C[19] ^ C[20] ^ C[21] ^ C[24] ^ C[28] ^ C[29];
		CRC[6] = D[30] ^ D[29] ^ D[25] ^ D[22] ^ D[21] ^ D[20] ^
			D[14] ^ D[11] ^ D[8] ^ D[7] ^ D[6] ^ D[5] ^
			D[4] ^ D[2] ^ D[1] ^ C[1] ^ C[2] ^ C[4] ^
			C[5] ^ C[6] ^ C[7] ^ C[8] ^ C[11] ^ C[14] ^
			C[20] ^ C[21] ^ C[22] ^ C[25] ^ C[29] ^ C[30];
		CRC[7] = D[29] ^ D[28] ^ D[25] ^ D[24] ^ D[23] ^ D[22] ^
			D[21] ^ D[16] ^ D[15] ^ D[10] ^ D[8] ^ D[7] ^
			D[5] ^ D[3] ^ D[2] ^ D[0] ^ C[0] ^ C[2] ^
			C[3] ^ C[5] ^ C[7] ^ C[8] ^ C[10] ^ C[15] ^
			C[16] ^ C[21] ^ C[22] ^ C[23] ^ C[24] ^ C[25] ^
			C[28] ^ C[29];
		CRC[8] = D[31] ^ D[28] ^ D[23] ^ D[22] ^ D[17] ^ D[12] ^
			D[11] ^ D[10] ^ D[8] ^ D[4] ^ D[3] ^ D[1] ^
			D[0] ^ C[0] ^ C[1] ^ C[3] ^ C[4] ^ C[8] ^
			C[10] ^ C[11] ^ C[12] ^ C[17] ^ C[22] ^ C[23] ^
			C[28] ^ C[31];
		CRC[9] = D[29] ^ D[24] ^ D[23] ^ D[18] ^ D[13] ^ D[12] ^
			D[11] ^ D[9] ^ D[5] ^ D[4] ^ D[2] ^ D[1] ^
			C[1] ^ C[2] ^ C[4] ^ C[5] ^ C[9] ^ C[11] ^
			C[12] ^ C[13] ^ C[18] ^ C[23] ^ C[24] ^ C[29];
		CRC[10] = D[31] ^ D[29] ^ D[28] ^ D[26] ^ D[19] ^ D[16] ^
			D[14] ^ D[13] ^ D[9] ^ D[5] ^ D[3] ^ D[2] ^
			D[0] ^ C[0] ^ C[2] ^ C[3] ^ C[5] ^ C[9] ^
			C[13] ^ C[14] ^ C[16] ^ C[19] ^ C[26] ^ C[28] ^
			C[29] ^ C[31];
		CRC[11] = D[31] ^ D[28] ^ D[27] ^ D[26] ^ D[25] ^ D[24] ^
			D[20] ^ D[17] ^ D[16] ^ D[15] ^ D[14] ^ D[12] ^
			D[9] ^ D[4] ^ D[3] ^ D[1] ^ D[0] ^ C[0] ^
			C[1] ^ C[3] ^ C[4] ^ C[9] ^ C[12] ^ C[14] ^
			C[15] ^ C[16] ^ C[17] ^ C[20] ^ C[24] ^ C[25] ^
			C[26] ^ C[27] ^ C[28] ^ C[31];
		CRC[12] = D[31] ^ D[30] ^ D[27] ^ D[24] ^ D[21] ^ D[18] ^
			D[17] ^ D[15] ^ D[13] ^ D[12] ^ D[9] ^ D[6] ^
			D[5] ^ D[4] ^ D[2] ^ D[1] ^ D[0] ^ C[0] ^
			C[1] ^ C[2] ^ C[4] ^ C[5] ^ C[6] ^ C[9] ^
			C[12] ^ C[13] ^ C[15] ^ C[17] ^ C[18] ^ C[21] ^
			C[24] ^ C[27] ^ C[30] ^ C[31];
		CRC[13] = D[31] ^ D[28] ^ D[25] ^ D[22] ^ D[19] ^ D[18] ^
			D[16] ^ D[14] ^ D[13] ^ D[10] ^ D[7] ^ D[6] ^
			D[5] ^ D[3] ^ D[2] ^ D[1] ^ C[1] ^ C[2] ^
			C[3] ^ C[5] ^ C[6] ^ C[7] ^ C[10] ^ C[13] ^
			C[14] ^ C[16] ^ C[18] ^ C[19] ^ C[22] ^ C[25] ^
			C[28] ^ C[31];
		CRC[14] = D[29] ^ D[26] ^ D[23] ^ D[20] ^ D[19] ^ D[17] ^
			D[15] ^ D[14] ^ D[11] ^ D[8] ^ D[7] ^ D[6] ^
			D[4] ^ D[3] ^ D[2] ^ C[2] ^ C[3] ^ C[4] ^
			C[6] ^ C[7] ^ C[8] ^ C[11] ^ C[14] ^ C[15] ^
			C[17] ^ C[19] ^ C[20] ^ C[23] ^ C[26] ^ C[29];
		CRC[15] = D[30] ^ D[27] ^ D[24] ^ D[21] ^ D[20] ^ D[18] ^
			D[16] ^ D[15] ^ D[12] ^ D[9] ^ D[8] ^ D[7] ^
			D[5] ^ D[4] ^ D[3] ^ C[3] ^ C[4] ^ C[5] ^
			C[7] ^ C[8] ^ C[9] ^ C[12] ^ C[15] ^ C[16] ^
			C[18] ^ C[20] ^ C[21] ^ C[24] ^ C[27] ^ C[30];
		CRC[16] = D[30] ^ D[29] ^ D[26] ^ D[24] ^ D[22] ^ D[21] ^
			D[19] ^ D[17] ^ D[13] ^ D[12] ^ D[8] ^ D[5] ^
			D[4] ^ D[0] ^ C[0] ^ C[4] ^ C[5] ^ C[8] ^
			C[12] ^ C[13] ^ C[17] ^ C[19] ^ C[21] ^ C[22] ^
			C[24] ^ C[26] ^ C[29] ^ C[30];
		CRC[17] = D[31] ^ D[30] ^ D[27] ^ D[25] ^ D[23] ^ D[22] ^
			D[20] ^ D[18] ^ D[14] ^ D[13] ^ D[9] ^ D[6] ^
			D[5] ^ D[1] ^ C[1] ^ C[5] ^ C[6] ^ C[9] ^
			C[13] ^ C[14] ^ C[18] ^ C[20] ^ C[22] ^ C[23] ^
			C[25] ^ C[27] ^ C[30] ^ C[31];
		CRC[18] = D[31] ^ D[28] ^ D[26] ^ D[24] ^ D[23] ^ D[21] ^
			D[19] ^ D[15] ^ D[14] ^ D[10] ^ D[7] ^ D[6] ^
			D[2] ^ C[2] ^ C[6] ^ C[7] ^ C[10] ^ C[14] ^
			C[15] ^ C[19] ^ C[21] ^ C[23] ^ C[24] ^ C[26] ^
			C[28] ^ C[31];
		CRC[19] = D[29] ^ D[27] ^ D[25] ^ D[24] ^ D[22] ^ D[20] ^
			D[16] ^ D[15] ^ D[11] ^ D[8] ^ D[7] ^ D[3] ^
			C[3] ^ C[7] ^ C[8] ^ C[11] ^ C[15] ^ C[16] ^
			C[20] ^ C[22] ^ C[24] ^ C[25] ^ C[27] ^ C[29];
		CRC[20] = D[30] ^ D[28] ^ D[26] ^ D[25] ^ D[23] ^ D[21] ^
			D[17] ^ D[16] ^ D[12] ^ D[9] ^ D[8] ^ D[4] ^
			C[4] ^ C[8] ^ C[9] ^ C[12] ^ C[16] ^ C[17] ^
			C[21] ^ C[23] ^ C[25] ^ C[26] ^ C[28] ^ C[30];
		CRC[21] = D[31] ^ D[29] ^ D[27] ^ D[26] ^ D[24] ^ D[22] ^
			D[18] ^ D[17] ^ D[13] ^ D[10] ^ D[9] ^ D[5] ^
			C[5] ^ C[9] ^ C[10] ^ C[13] ^ C[17] ^ C[18] ^
			C[22] ^ C[24] ^ C[26] ^ C[27] ^ C[29] ^ C[31];
		CRC[22] = D[31] ^ D[29] ^ D[27] ^ D[26] ^ D[24] ^ D[23] ^
			D[19] ^ D[18] ^ D[16] ^ D[14] ^ D[12] ^ D[11] ^
			D[9] ^ D[0] ^ C[0] ^ C[9] ^ C[11] ^ C[12] ^
			C[14] ^ C[16] ^ C[18] ^ C[19] ^ C[23] ^ C[24] ^
			C[26] ^ C[27] ^ C[29] ^ C[31];
		CRC[23] = D[31] ^ D[29] ^ D[27] ^ D[26] ^ D[20] ^ D[19] ^
			D[17] ^ D[16] ^ D[15] ^ D[13] ^ D[9] ^ D[6] ^
			D[1] ^ D[0] ^ C[0] ^ C[1] ^ C[6] ^ C[9] ^
			C[13] ^ C[15] ^ C[16] ^ C[17] ^ C[19] ^ C[20] ^
			C[26] ^ C[27] ^ C[29] ^ C[31];
		CRC[24] = D[30] ^ D[28] ^ D[27] ^ D[21] ^ D[20] ^ D[18] ^
			D[17] ^ D[16] ^ D[14] ^ D[10] ^ D[7] ^ D[2] ^
			D[1] ^ C[1] ^ C[2] ^ C[7] ^ C[10] ^ C[14] ^
			C[16] ^ C[17] ^ C[18] ^ C[20] ^ C[21] ^ C[27] ^
			C[28] ^ C[30];
		CRC[25] = D[31] ^ D[29] ^ D[28] ^ D[22] ^ D[21] ^ D[19] ^
			D[18] ^ D[17] ^ D[15] ^ D[11] ^ D[8] ^ D[3] ^
			D[2] ^ C[2] ^ C[3] ^ C[8] ^ C[11] ^ C[15] ^
			C[17] ^ C[18] ^ C[19] ^ C[21] ^ C[22] ^ C[28] ^
			C[29] ^ C[31];
		CRC[26] = D[31] ^ D[28] ^ D[26] ^ D[25] ^ D[24] ^ D[23] ^
			D[22] ^ D[20] ^ D[19] ^ D[18] ^ D[10] ^ D[6] ^
			D[4] ^ D[3] ^ D[0] ^ C[0] ^ C[3] ^ C[4] ^
			C[6] ^ C[10] ^ C[18] ^ C[19] ^ C[20] ^ C[22] ^
			C[23] ^ C[24] ^ C[25] ^ C[26] ^ C[28] ^ C[31];
		CRC[27] = D[29] ^ D[27] ^ D[26] ^ D[25] ^ D[24] ^ D[23] ^
			D[21] ^ D[20] ^ D[19] ^ D[11] ^ D[7] ^ D[5] ^
			D[4] ^ D[1] ^ C[1] ^ C[4] ^ C[5] ^ C[7] ^
			C[11] ^ C[19] ^ C[20] ^ C[21] ^ C[23] ^ C[24] ^
			C[25] ^ C[26] ^ C[27] ^ C[29];
		CRC[28] = D[30] ^ D[28] ^ D[27] ^ D[26] ^ D[25] ^ D[24] ^
			D[22] ^ D[21] ^ D[20] ^ D[12] ^ D[8] ^ D[6] ^
			D[5] ^ D[2] ^ C[2] ^ C[5] ^ C[6] ^ C[8] ^
			C[12] ^ C[20] ^ C[21] ^ C[22] ^ C[24] ^ C[25] ^
			C[26] ^ C[27] ^ C[28] ^ C[30];
		CRC[29] = D[31] ^ D[29] ^ D[28] ^ D[27] ^ D[26] ^ D[25] ^
			D[23] ^ D[22] ^ D[21] ^ D[13] ^ D[9] ^ D[7] ^
			D[6] ^ D[3] ^ C[3] ^ C[6] ^ C[7] ^ C[9] ^
			C[13] ^ C[21] ^ C[22] ^ C[23] ^ C[25] ^ C[26] ^
			C[27] ^ C[28] ^ C[29] ^ C[31];
		CRC[30] = D[30] ^ D[29] ^ D[28] ^ D[27] ^ D[26] ^ D[24] ^
			D[23] ^ D[22] ^ D[14] ^ D[10] ^ D[8] ^ D[7] ^
			D[4] ^ C[4] ^ C[7] ^ C[8] ^ C[10] ^ C[14] ^
			C[22] ^ C[23] ^ C[24] ^ C[26] ^ C[27] ^ C[28] ^
			C[29] ^ C[30];
		CRC[31] = D[31] ^ D[30] ^ D[29] ^ D[28] ^ D[27] ^ D[25] ^
			D[24] ^ D[23] ^ D[15] ^ D[11] ^ D[9] ^ D[8] ^
			D[5] ^ C[5] ^ C[8] ^ C[9] ^ C[11] ^ C[15] ^
			C[23] ^ C[24] ^ C[25] ^ C[27] ^ C[28] ^ C[29] ^
			C[30] ^ C[31];
	}
}
