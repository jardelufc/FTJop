/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufc.lesc.crc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jilseph
 */
public class CrcUtilsTest {

    public CrcUtilsTest() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

	@Test
	public void testHexToBin() {
		assertEquals(0, CrcUtils.hexToBin('0'));
		assertEquals(1, CrcUtils.hexToBin('1'));
		assertEquals(10, CrcUtils.hexToBin('2'));
		assertEquals(11, CrcUtils.hexToBin('3'));
		assertEquals(100, CrcUtils.hexToBin('4'));
		assertEquals(101, CrcUtils.hexToBin('5'));
		assertEquals(110, CrcUtils.hexToBin('6'));
		assertEquals(111, CrcUtils.hexToBin('7'));
		assertEquals(1000, CrcUtils.hexToBin('8'));
		assertEquals(1001, CrcUtils.hexToBin('9'));
		assertEquals(1010, CrcUtils.hexToBin('A'));
		assertEquals(1010, CrcUtils.hexToBin('a'));
		assertEquals(1011, CrcUtils.hexToBin('B'));
		assertEquals(1011, CrcUtils.hexToBin('b'));
		assertEquals(1100, CrcUtils.hexToBin('C'));
		assertEquals(1100, CrcUtils.hexToBin('c'));
		assertEquals(1101, CrcUtils.hexToBin('D'));
		assertEquals(1101, CrcUtils.hexToBin('d'));
		assertEquals(1110, CrcUtils.hexToBin('E'));
		assertEquals(1110, CrcUtils.hexToBin('e'));
		assertEquals(1111, CrcUtils.hexToBin('F'));
		assertEquals(1111, CrcUtils.hexToBin('f'));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testHexToBinInvalid() {
		CrcUtils.hexToBin('w');
	}

	@Test
	public void testBinToHex() {
		assertEquals('0', CrcUtils.binToHex(0));
		assertEquals('1', CrcUtils.binToHex(1));
		assertEquals('2', CrcUtils.binToHex(10));
		assertEquals('3', CrcUtils.binToHex(11));
		assertEquals('4', CrcUtils.binToHex(100));
		assertEquals('5', CrcUtils.binToHex(101));
		assertEquals('6', CrcUtils.binToHex(110));
		assertEquals('7', CrcUtils.binToHex(111));
		assertEquals('8', CrcUtils.binToHex(1000));
		assertEquals('9', CrcUtils.binToHex(1001));
		assertEquals('A', CrcUtils.binToHex(1010));
		assertEquals('B', CrcUtils.binToHex(1011));
		assertEquals('C', CrcUtils.binToHex(1100));
		assertEquals('D', CrcUtils.binToHex(1101));
		assertEquals('E', CrcUtils.binToHex(1110));
		assertEquals('F', CrcUtils.binToHex(1111));
	}

	@Test
	public void testToHexString() {
		assertEquals("00045E3F", CrcUtils.toHex8PaddingString(0x45E3F));
		assertEquals("00000001", CrcUtils.toHex8PaddingString(0x1));
		assertEquals("FFFFFFFF", CrcUtils.toHex8PaddingString(0xFFFFFFFF));
		assertEquals("00ABCDEF", CrcUtils.toHex8PaddingString(0xABCDEF));
		assertEquals("00000000", CrcUtils.toHex8PaddingString(0x0));
	}

	@Test
	public void testToInt() {
		assertEquals(0xCCCCCCCC, CrcUtils.toInt("CCCCCCCC"));
		assertEquals(0xCCCC, CrcUtils.toInt("0000CCCC"));
		assertEquals(0xFFFFFFFF, CrcUtils.toInt("FFFFFFFF"));
		assertEquals(0x80000000, CrcUtils.toInt("80000000"));
	}
}