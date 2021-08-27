package com.godrej.surveys;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static com.godrej.surveys.util.CommonUtil.isEqual;

@SpringBootTest
class SurveysApplicationTests {

	@Test
	void contextLoads() {
		System.out.println(isEqual(10l,20l));
	}

}
