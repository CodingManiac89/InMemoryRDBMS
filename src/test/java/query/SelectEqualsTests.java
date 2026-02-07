package query;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import constants.ColumnType;
import exception.InMemoryDDLException;
import exception.InMemoryDMLException;
import operations.DDLOperations;
import operations.DMLOperations;
import operations.SelectOperations;
import table.Column;
import table.Row;
import table.Table;

public class SelectEqualsTests {
	private SelectOperations select = new SelectOperations();
	
	
	@BeforeEach
	public void setup() throws InMemoryDMLException, InMemoryDDLException {
		DDLOperations db = new DDLOperations();
		Column c1 = new Column("empid", ColumnType.ID);
		Column c2 = new Column("empname", ColumnType.VARCHAR);
		Column c3 = new Column("salary", ColumnType.DECIMAL);
		
		Column indexColumn = new Column("empname", ColumnType.VARCHAR);
		Table t = new Table("employee", List.of(c1,c2,c3), indexColumn);
		db.createTable(t);
		
		DMLOperations insert = new DMLOperations();
		Map<String, Object> r1Vals = Map.of(
				"empid",1,
				"empname","siddu",
				"salary",1232.23
		);
		
		Map<String, Object> r2Vals = Map.of(
				"empid",2,
				"empname","siddu",
				"salary",1242.23
		);
		
		Row r1 = new Row(r1Vals);
		Row r2 = new Row(r2Vals);
		insert.addRow("employee", r1, true);
		insert.addRow("employee", r2, true);
		
	}
	
	
	@Test
	public void selectEqualsSimple() throws Exception {
		List<Row> rows = select.equals("employee", "empname", "siddu");
		assertEquals(rows.size(), 2);
	}
}
