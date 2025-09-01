package utils;

import java.util.Set;

import org.apache.bval.jsr.ApacheValidationProvider;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import table.Table;

public class Utils {
	private static Validator validator;
	static {
		ValidatorFactory factory = Validation
				.byProvider(ApacheValidationProvider.class)
				.configure()
				.buildValidatorFactory();
		validator = factory.getValidator();
	}
	public static <T> Set<ConstraintViolation<T>> validate(T object){
		
		return validator.validate(object);
	}
}
