package table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.bval.jsr.ApacheValidationProvider;

import exception.InMemoryDDLException;
import indexer.EqualsIndexer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import utils.Utils;

public class Table {
	@NotNull
	private String name;
	@NotNull
	@NotEmpty
	@Valid
	private Map<String, Column> columns;
	
	private Map<Integer,Row> rows=new HashMap<>();

	private EqualsIndexer indexer;

	private Set<String> indexColumns;


	
	public Table(String name, List<Column> columns) {
		this.name = name;
		Map<String, Column> colMap = columns!=null ? columns.stream().collect(Collectors.toMap(Column::getColumnName, c->c)) : null;
		
		this.columns = colMap;
	}
	
	public Table(String name, List<Column> columns, Set<String> indexColumns) throws InMemoryDDLException {
		this.name = name;
		Map<String, Column> colMap = columns!=null ? columns.stream().collect(Collectors.toMap(Column::getColumnName, c->c)) : null;
		this.columns = colMap;
		Utils.validateIndexer(indexColumns, columns);
		this.indexColumns=indexColumns;
		this.indexer = EqualsIndexer.createInstance(indexColumns);
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

	public Set<String> getIndexColumns() {
		return indexColumns;
	}	
	
		
	public EqualsIndexer getIndexer() {
		return indexer;
	}
	
}
