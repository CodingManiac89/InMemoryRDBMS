package operations;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import constants.ColumnType;
import db.Database;
import exception.InMemoryDDLException;
import exception.InMemoryDMLException;
import table.Column;
import table.Row;
import table.Table;
import utils.Utils;

public class DMLOperations {
	private AtomicInteger seq = new AtomicInteger(0);
	
	public int addRow(String tableName, Row row, boolean isPrimaryKeyPresent) throws InMemoryDMLException,InMemoryDDLException {
		Utils.validateDb(tableName);
		Table table = Database.getInstance().getTables().get(tableName);
		if(!isRowValid(row,table.getColumns())) {
			throw new InMemoryDMLException("Invalid row inserted");
		}
		Map<Integer, Row> rows = table.getRows();
		Optional<Column> idColumn = table.getColumns().values()
			    .stream()
			    .filter(c -> c.getColumnType().equals(ColumnType.ID))
			    .findAny();

		if (idColumn.isEmpty()) {
			throw new InMemoryDMLException("Table " + tableName + " has no ID column");
		}
		String idColName = idColumn.get().getColumnName();		
		Object idObj = row.getValues().get(idColName);
		if (!(idObj instanceof Integer)) {
		    throw new InMemoryDMLException("Primary key must be an Integer");
		}
		int idVal = (Integer) idObj;
		int id=isPrimaryKeyPresent ? idVal : seq.incrementAndGet();
		if(rows.get(id)!=null) {
			throw new InMemoryDMLException("The primary key "+id+" is already present");
		}
		row.setRowId(id);
		rows.put(id, row);
		return id;
	}
	
	public List<Row> getAllRows(String tableName){
		Utils.validate(tableName);
		return Database.getInstance().getTables().get(tableName).getRows().values().stream().collect(Collectors.toList());
	}

	private boolean isRowValid(Row row, Map<String, Column> columns) {
		if (!row.getValues().keySet().equals(columns.keySet())) {
		    return false;
		}
		for(String colName:row.getValues().keySet()) {
			Object val = row.getValues().get(colName);
			ColumnType type = columns.get(colName).getColumnType();
			if(!Utils.isValidDatatype(val, type)) {
				return false;
			}
		}
		return true;
	}

}
