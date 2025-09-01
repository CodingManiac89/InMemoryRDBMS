package table;

import constants.ColumnType;
import jakarta.validation.constraints.NotNull;

public class Column {
	@NotNull
	private String columnName;
	@NotNull
	private ColumnType columnType;
	
	public Column(String columnName,ColumnType columnType) {
		this.columnName = columnName;
		this.columnType = columnType;
	}
	public String getColumnName() {
		return columnName;
	}
	public ColumnType getColumnType() {
		return columnType;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public void setColumnType(ColumnType columnType) {
		this.columnType = columnType;
	}
	
}



