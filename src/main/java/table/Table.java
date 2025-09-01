package table;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.bval.jsr.ApacheValidationProvider;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Table {
	@NotNull
	private String name;
	@NotNull
	@NotEmpty
	@Valid
	private Map<String, Column> columns;
	
	public Table(String name, Map<String, Column> columns) {
		this.name = name;
		this.columns = columns;
	}
	public String getName() {
		return name;
	}
	public Map<String, Column> getColumns() {
		return columns;
	}
	public void setColumns(Map<String, Column> columns) {
		this.columns = columns;
	}
	
	
}
