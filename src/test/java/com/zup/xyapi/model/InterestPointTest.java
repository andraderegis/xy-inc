package com.zup.xyapi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class InterestPointTest {

	private static Validator validator;
	private InterestPoint interestPoint;
	private Set<ConstraintViolation<InterestPoint>> constraintViolations;

	@BeforeClass
	public static void init() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@After
	public void destroy() {
		constraintViolations = null;
	}

	@Test
	public void shouldBeNullAndEmptyName() {

		try {
			interestPoint = new InterestPoint(null, 10, 10);

			constraintViolations = validator.validate(interestPoint);

			assertEquals(2, constraintViolations.size());

		} catch (Exception e) {
			testException(e);
		}
	}

	@Test
	public void shoudBeEmptyName() {

		try {
			interestPoint = new InterestPoint("", 10, 10);

			constraintViolations = validator.validate(interestPoint);
			
			assertEquals(1, constraintViolations.size());

		} catch (Exception e) {
			testException(e);
		}

	}
	
	@Test
	public void shoudBeInvalidRange() {

		try {
			interestPoint = new InterestPoint("Padaria", 0, 10);

			constraintViolations = validator.validate(interestPoint);
			
			assertEquals(1, constraintViolations.size());

		} catch (Exception e) {
			testException(e);
		}

	}
	
	private void testException(Exception e) {
		e.printStackTrace();
		fail("Exception: " + e.getMessage());
	}

}
