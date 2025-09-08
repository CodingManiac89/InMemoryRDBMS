package ddl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import constants.ColumnType;
import db.Database;
import exception.InMemoryDDLException;
import operations.DDLOperations;
import table.Column;
import table.Table;

public class UpdationTests {
	
	private DDLOperations db;
	
	@BeforeEach
	public void setup() {
		db=new DDLOperations();
		Column c1 = new Column("empid", ColumnType.ID);
		Column c2 = new Column("empname", ColumnType.VARCHAR);
		Column c3 = new Column("salary", ColumnType.DECIMAL);
		Table t = new Table("employee", List.of(c1,c2,c3));
		db.createTable(t);
	}
	
	@Test
	public void testChangeColName() throws InMemoryDDLException {
		db.changeColumnName("employee", "empname", "emp_name");
		Table table = db.getTable("employee");
		assertNull(table.getColumns().get("empname"));
		assertNotNull(table.getColumns().get("emp_name"));
	}
	
	
	@Test
	public void testChangeColType() throws InMemoryDDLException {
		db.changeColumnDataType("employee", "empname", ColumnType.DATETIME);
		Table table = db.getTable("employee");
		assertEquals(ColumnType.DATETIME, table.getColumns().get("empname").getColumnType());
	}
	
	
	
	@AfterEach
	public void cleanUp() {
		db.dropTable("employee");
	}
	
	

}
