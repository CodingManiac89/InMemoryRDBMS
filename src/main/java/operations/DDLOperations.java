package operations;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import constants.ColumnType;
import db.Database;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import table.Column;
import table.Table;
import utils.Utils;

public class DDLOperations {
	public void createTable(Table table) {
		Map<String, Table> tables = Database.getInstance().getTables();
		Set<ConstraintViolation<Table>> result = Utils.validate(table);
		if(!result.isEmpty()) {
			AtomicInteger integer = new AtomicInteger(1);
			String msg = result.stream().map(v->integer.getAndIncrement()+"."+v.getPropertyPath()+":"+v.getMessage()).collect(Collectors.joining("\n"));
			throw new ValidationException(msg);
		}
		if(tables.containsKey(table.getName())) {
			throw new IllegalArgumentException("Table already exists");
		}
		tables.put(table.getName(), table);
	}
	
	public Table getTable(String tableName) throws Exception {
		Map<String, Table> tables = Database.getInstance().getTables();
		validateDb(tableName);
		return tables.get(tableName);
	}
	
	public void changeColumnName(String tableName, String oldColumnName, String newColumnName) throws Exception {
		validateDb(tableName);
		Map<String, Table> tables = Database.getInstance().getTables();
		Table table = tables.get(tableName);
		Map<String, Column> columns = table.getColumns();
		Column column = columns.get(oldColumnName);
		if(column==null) {
			throw new IllegalArgumentException("The column "+oldColumnName+" is not present");
		}
		if(columns.containsKey(newColumnName)) {
			throw new IllegalArgumentException("The column "+newColumnName+" is already present");
		}
		columns.remove(oldColumnName);
		column.setColumnName(newColumnName);
		columns.put(newColumnName, column);
	}
	
	public void addNewColumn(String tableName, Column column) throws Exception {
		validateDb(tableName);
		Map<String, Table> tables = Database.getInstance().getTables();
		Table table = tables.get(tableName);
		Map<String, Column> columns = table.getColumns();
		if(columns.containsKey(column.getColumnName())) {
			throw new IllegalArgumentException("Column "+column.getColumnName()+" is already present");
		}
		columns.put(column.getColumnName(), column);
	}
	
	public void changeColumnDataType(String tableName, String columnName, ColumnType type) throws Exception {
		validateDb(tableName);
		Map<String, Table> tables = Database.getInstance().getTables();
		Table table = tables.get(tableName);
		Map<String, Column> columns = table.getColumns();
		Column column = columns.get(columnName);
		column.setColumnType(type);
	}
	
	private void validateDb(String tableName) throws Exception {
		Map<String, Table> tables = Database.getInstance().getTables();
		if(!tables.containsKey(tableName)) {
			throw new Exception("Table with name "+tableName+" not found in the database");
		}
	}
}
