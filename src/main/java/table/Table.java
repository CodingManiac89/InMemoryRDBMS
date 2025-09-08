package table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
	
	private Map<Integer,Row> rows=new HashMap<>();
	
	public Table(String name, List<Column> columns) {
		this.name = name;
		Map<String, Column> colMap = columns!=null ? columns.stream().collect(Collectors.toMap(Column::getColumnName, c->c)) : null;
		this.columns = colMap;
	}
	public String getName() {
		return name;
	}
	public Map<String, Column> getColumns() {
		return columns;
	}
	public Map<Integer,Row> getRows() {
		return rows;
	}	
}
