package strings;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class TestStringOperations {
	
	@Test
	public void testStringBuilder(){
		StringBuilder sb = new StringBuilder("hi"); // Better for loops/appending
		assertEquals(sb.toString(), "hi");
		sb.append("de");
		assertEquals(sb.toString(), "hide");
		sb.reverse(); // edih
		assertEquals(sb.toString(), "edih");
	}
	
	@Test
	public void testStringPassing(){
		// String
		String str = "hello";
		assertEquals(str, "hello");
		changeString(str);
		assertEquals(str, "hello");
		
		// StringBuilder
		StringBuilder sb = new StringBuilder("hello");
		assertEquals(sb.toString(), "hello");
		changeStringBuilder(sb);
		assertEquals(sb.toString(), "hellofoo");
		
		// StringBuilder Swap
		StringBuilder a = new StringBuilder("a");
		StringBuilder b = new StringBuilder("b");
		swapStringBuilder(a, b);
		assertEquals(a.toString(), "a");
		assertEquals(b.toString(), "b");
	}
	
	public void swapStringBuilder(StringBuilder a, StringBuilder b){
		StringBuilder temp = a;
		a = b;
		b = temp;
	}
	
	public void changeString(String str){
		str = str + "foo";
	}
	
	public void changeStringBuilder(StringBuilder sb){
		sb.append("foo");
	}
	
	@Test
	public void testEscapeCharacters(){
		String backslash = "\\";
		String doublequote = "\"";
		
		assertTrue(backslash.length() == 1);
		assertTrue(doublequote.length() == 1);
		System.out.println(backslash + doublequote);
	}
	
	@Test
	public void testStringToNumber(){
		String str = "1234";
		try{
			Integer boxed = Integer.valueOf(str);
			int primitive = Integer.parseInt(str);		
			
			assertTrue(boxed.equals(1234));
			assertEquals(primitive, 1234);
		} catch(NumberFormatException nfe) { Assert.fail("Nonnumeric"); }
	}
	
	@Test
	public void testStringAssignment(){
		String str1 = "hello"; 
		String str2 = "hello";
		String str3 = str2; // Still  one "hello" object
		String str4 = new String("hello"); // Now two hello objects
		
		assertTrue(str1 == str2 && str2 == str3);
		assertFalse(str1 == str4);
		
		str1.replace('l', 'J'); // Now one "heJJo" object
		
		assertTrue(str2 == str3 && str2 != str4);
	}
	
	@Test
	public void testStringManipulation(){
		String str = "hello";
		
		// Reverse
		String rev = new StringBuilder(str).reverse().toString(); // "olleh"
		
		// Replace
		String rep = str.replace('l', 'J'); // "heJJo"
		
		// To Uppercase
		String uc = str.toUpperCase(); // "HELLO"
		
		assertEquals(rev, "olleh");
		assertEquals(rep, "heJJo");
		assertEquals(uc, "HELLO");
	}
	
	@Test
	public void testStringEquality(){
		String str = "hello";
		boolean equals = str.equals("hello");
		boolean ciEquals = str.equalsIgnoreCase("HeLlO");
		
		assertTrue(equals);
		assertTrue(ciEquals);
	}
	
	@Test
	public void testStringSearch(){
		String str = "mom";
		
		// Substring
		int inclusive = 1;
		int exclusive = 2;
		String sub = str.substring(inclusive, exclusive);
		
		// Regex
		boolean match = str.matches(".*o\\w+"); // true
		
		// Contains
		boolean contains = str.contains("om"); // true
		
		// Split
		String[] split = str.split("o"); // ["m", "m"]
		
		assertEquals(sub, "o");
		assertTrue(match);
		assertTrue(contains);
		assertArrayEquals(split, new String[]{"m", "m"});
	}
	
	@Test
	public void testStringCharArray(){
		char[] chArray = {'h', 'e', 'l', 'l', 'o'};
		String str = new String(chArray);
		char[] charArray = str.toCharArray();
		
		assertEquals(str, "hello");
		assertArrayEquals(charArray, new char[] {'h', 'e', 'l', 'l', 'o'});
	}
	
	@Test
	public void testStringIndexing(){
		String str = "mom";
		
		// Length
		int l = str.length();
		
		// First index of
		int firstChar = str.indexOf('m');
		int firstIdx = str.indexOf("m"); // 0
		
		// Last index of
		int lastChar = str.lastIndexOf('m'); 
		int lastIdx = str.lastIndexOf("m"); // 2
		
		char c = str.charAt(1); // 'o'
		
		assertEquals(l, 3);
		assertEquals(firstIdx, 0);
		assertEquals(firstChar, 0);
		assertEquals(lastChar, 2);
		assertEquals(lastIdx, 2);
		assertEquals(c, 'o');
	}
	
	@Test
	public void testStringFormat(){
		// Trim
		String str = " m m ";
		String trim = str.trim(); // "m m"
		
		// Pad Right and Left
		str = "hello";
		String r = String.format("%-8s", str); // "hello   "
		String l = String.format("%8s", str); // "   hello"
		
		assertEquals(trim, "m m");
		assertEquals(r, "hello   ");
		assertEquals(l, "   hello");
	}
	
	@Test
	public void testStringNumbers(){
		// Value of
		String strInt = String.valueOf(15);
		String strInt2 = Integer.toString(15);
		String strInt3 = String.valueOf(new Integer(15));
		String strDbl = String.valueOf(15.01);
		String strDbl2 = Double.toString(15.01);
		
		// To binary
		int radix = 2;
		String binary = Integer.toString(15, radix); //1111
		String toBinary = Integer.toBinaryString(15); //1111
		
		// To formatted binary
		String fmtBinary = String.format("%08d", Integer.valueOf(Integer.toBinaryString(15))); //00001111
		
		// To hex
		radix = 16;
		String hex = Integer.toString(15, radix); // f
		String fmtHex = String.format("0x%08X", 15); // 0x0000000F
		
		assertEquals(strInt, "15");
		assertEquals(strInt2, "15");
		assertEquals(strInt3, "15");
		assertEquals(strDbl, "15.01");
		assertEquals(strDbl2, "15.01");
		assertEquals(binary, "1111");
		assertEquals(toBinary, "1111");
		assertEquals(fmtBinary, "00001111");
		assertEquals(hex, "f");
		assertEquals(fmtHex, "0x0000000F");
	}

}
