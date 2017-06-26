package bitOperations;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestBitOperations {

	@Test
	public void testBitCount() {
		int n = 60; // 00111100
		int bitCount = Integer.bitCount(n);
		assertEquals(bitCount, 4);
		
		n = 13; // 00001101
		bitCount = Integer.bitCount(n);
		assertEquals(bitCount, 3);
		
		n = 0; // 00000000
		bitCount = Integer.bitCount(n);
		assertEquals(bitCount, 0);
	}
	
	@Test
	public void testGetBit() {
		int n = 60; // 00111100
		
		boolean bit = getBit(n, 0);
		assertEquals(bit, false);
		bit = getBit(n, 1);
		assertEquals(bit, false);
		bit = getBit(n, 2);
		assertEquals(bit, true);
		bit = getBit(n, 3);
		assertEquals(bit, true);
		bit = getBit(n, 4);
		assertEquals(bit, true);
		bit = getBit(n, 5);
		assertEquals(bit, true);
		bit = getBit(n, 6);
		assertEquals(bit, false);
		bit = getBit(n, 7);
		assertEquals(bit, false);
	}
	
	@Test
	public void testBitOperations(){
		int a = Integer.parseInt("00000001", 2);
		int b = Integer.parseInt("00001011", 2);
		assertEquals(a & b, Integer.parseInt("00000001", 2));
		assertEquals(a | b, Integer.parseInt("00001011", 2));
		assertEquals(a ^ b, Integer.parseInt("00001010", 2));
		assertEquals(a << 2, Integer.parseInt("00000100", 2));
		assertEquals((a << 2) >> 2, Integer.parseInt("00000001", 2));
		assertEquals(~a, -2); // This one is weird
		/*
		 *     00000001
		 *   & 00001011
		 *   = 00000001
		 *   
		 *     00000001
		 *   | 00001011
		 *   = 00001011
		 *   
		 *     00000001
		 *   ^ 00001011
		 *   = 00001010
		 *   
		 *     00000001 << 2
		 *   = 00000100
		 *   
		 *    ~00001011
		 *   = 11110100 
		 */
	}
	
	@Test
	public void testUpdateBit() {
		int n = 60; // 00111100
		
		boolean bit = getBit(n, 0);
		assertEquals(bit, false);
		n = updateBit(n, 0, true);
		bit = getBit(n, 0);
		assertEquals(bit, true);
		
		bit = getBit(n, 4);
		assertEquals(bit, true);
		n = updateBit(n, 4, false);
		bit = getBit(n, 4);
		assertEquals(bit, false);
	}

	boolean getBit(int n, int index){
		return ((n & (1 << index)) != 0);
	}
	
	int updateBit(int n, int index, boolean newBit){
		int value = newBit ? 1 : 0;
		int mask = ~(1 << index);
		return (n & mask) | (value << index);
	}
}
