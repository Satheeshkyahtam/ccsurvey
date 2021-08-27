package com.godrej.surveys.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SampleTest {

	Sample sample;
	TestInfo info;
	TestReporter reporter;

	@BeforeEach
	void init(TestInfo info, TestReporter reporter) {
		this.info = info;
		this.reporter = reporter;
		sample = new Sample();
		reporter.publishEntry("Running " + info.getDisplayName() + " " + info.getTags());
	}
	
	@BeforeAll
	static void beforeAll() {
//		System.out.println("Before All .. ");
	}

	@Test
	@DisplayName("Testing Addition")
	void testAdd() {
		int expected = 12;
		int actual = sample.add(8, 4);
		assertEquals(expected, actual, "Should return right sum");
	}

	@RepeatedTest(3)
	@DisplayName("Testing Calculate Area")
	void testCalculateArea(RepetitionInfo info) {
		double expected = Math.PI * 10 * 10;
		double actual = sample.calculateArea(10);
		assertEquals(expected, actual,"Should resturn area of circle Math.PI*radius*radius");
	}

	@Nested
	@DisplayName("Testing add methods")
	class DevideTest{
		@Test
		@DisplayName("Testing Devide by Zero")
		void testDevideException() {
			boolean val = 1==2;
			assumeTrue(val);
			assertThrows(ArithmeticException.class, () -> sample.devide(10, 0),
					" Divide by should throw ArithmaticExpression");
		}
		
		@Test
		@DisplayName("Testing Devide operation with multiple cases")
		void testDevide() {
			assertAll(()-> assertEquals(4, sample.devide(8, 2)," Should return result 4"),
					()-> assertNotEquals(4, sample.devide(10, 2)," Should return result 4"));
		}
	}

}
