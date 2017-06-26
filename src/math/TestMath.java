package math;

import static org.junit.Assert.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

public class TestMath {

	@Test
	public void testRandom() {
		int inclusive = 0;
		int exclusive = 100;
		int r = ThreadLocalRandom.current().nextInt(inclusive, exclusive);
		
		assertTrue(r < 100 && r >= 0);
	}
	
	public void testMod() {
		int a = 11;
		int b = 2;
		assertEquals(a % b, 1);
		a = 10;
		assertEquals(a % b, 0);
		a = 0;
		assertEquals(a % b, 0);
		a = -10;
		assertEquals(a % b, 0);
		a = -11;
		assertEquals(a % b, -1);
		b = 3;
		assertEquals(a % b, -2);
	}

	@Test
	public void testSentinels(){
		System.out.println(Integer.MIN_VALUE);
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Float.MIN_VALUE);
		System.out.println(Float.MAX_VALUE);
		System.out.println(Double.MIN_VALUE);
		System.out.println(Double.MAX_VALUE);
	}
}
