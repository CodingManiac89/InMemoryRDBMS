package utils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.bval.jsr.ApacheValidationProvider;

import constants.ColumnType;
import db.Database;
import exception.InMemoryDDLException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import table.Column;
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
	
	
	public static void validateDb(String tableName) throws InMemoryDDLException {
		Map<String, Table> tables = Database.getInstance().getTables();
		if(!tables.containsKey(tableName)) {
			throw new InMemoryDDLException("Table with name "+tableName+" not found in the database");
		}
	}
	
	public static boolean isValidDatatype(Object val, ColumnType type) {
		if(val==null || type==ColumnType.ID) {
			return true;
		}
		switch(type) {
		  case NUMBER:
			  return val instanceof Integer;
		  case BOOLEAN:
		  	  return val instanceof Boolean;
		  
		  case DATETIME:
			  return val instanceof Date;
		  case VARCHAR:
			  return val instanceof String;
		
		  case DECIMAL:
			  return val instanceof Double;
	      default:
	    	  return false;
		  
		}
	}


	public static void validateIndexer(Set<String> columns, List<Column> tableColumns) throws InMemoryDDLException{
		Set<String> tableColumnNames = tableColumns.stream().map(Column::getColumnName).collect(Collectors.toSet());
		if(!columns.containsAll(tableColumnNames)){
			throw new InMemoryDDLException("Unable to create index for the table due to mismatch in columns");
		}
	}
}
