package table;
import java.util.*;

import constants.ColumnType;

public class Row {
	private int rowId;
	private Map<String,Object> values;
	public Row(Map<String, Object> values) {
		super();
		this.values = values;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public Map<String, Object> getValues() {
		return values;
	}
}
