package dml;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import constants.ColumnType;
import db.Database;
import exception.InMemoryDMLException;
import operations.DDLOperations;
import operations.DMLOperations;
import table.Column;
import table.Row;
import table.Table;

public class InsertionTests {
	private DMLOperations dmlOps=new DMLOperations();
	
	@BeforeEach
	public void setup() {
		DDLOperations db = new DDLOperations();
		Column c1 = new Column("empid", ColumnType.ID);
		Column c2 = new Column("empname", ColumnType.VARCHAR);
		Column c3 = new Column("salary", ColumnType.DECIMAL);
		Table t = new Table("employee", List.of(c1,c2,c3));
		db.createTable(t);
	}
	
	@Test
	public void testInsert() throws Exception {
		Map<String,Object> vals=Map.of(
				"empid",123,
				"empname","siddu",
				"salary",432.233
				);
		Row row = new Row(vals);
		dmlOps.addRow("employee", row, true);
		assertNotNull(Database.getInstance().getTables().get("employee").getRows().get(123));
		
	}
	
	@Test
	public void testInsertFailDataType() {
		Map<String,Object> vals=Map.of(
				"empid",123,
				"empname",345,
				"salary",432.233
				);
		Row row = new Row(vals);
		assertThrows(InMemoryDMLException.class, ()->{
			dmlOps.addRow("employee", row, true);
		});
	}
	
	@Test
	public void testInsertFailColumnName() {
		Map<String,Object> vals=Map.of(
				"empid",123,
				"department","Abc",
				"salary",432.233
				);
		Row row = new Row(vals);
		assertThrows(InMemoryDMLException.class, ()->{
			dmlOps.addRow("employee", row, true);
		});
	}
	
	
	@AfterEach
	public void cleanUp() {
		DDLOperations db = new DDLOperations();
		db.dropTable("employee");
	}
}
