package com.iteye.wwwcomy;

import static org.junit.Assert.*;

import org.junit.Test;

public class FiveOutTest {

	@Test
	public void testCycleOut() {
		int[] array = new int[100];
		for (int i = 0; i < 99; i++)
			array[i] = i + 1;
		int index = new FiveOut().cycleOut(array, 5);
		assertEquals(index, 47);
	}

}
