package ddl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import constants.ColumnType;
import exception.InMemoryDDLException;
import jakarta.validation.ValidationException;
import operations.DDLOperations;
import table.Column;
import table.Table;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.*;

public class CreationTests {
	private static DDLOperations db;
	
	@BeforeAll
	public static void setupOnce() {
		db = new DDLOperations();
	}
	
	@Test
	public void testCreationFail1() {
		List<Column> columns = List.of();
		Table t = new Table("Employee", columns);
		assertThrows(ValidationException.class, ()->{
			db.createTable(t);
		});
	}
	
	@Test
	public void testCreationFail2() {
		Table t = new Table(null,null);
		assertThrows(ValidationException.class, ()->{
			db.createTable(t);
		});
	}
	
	@Test
	public void testCreateSuccess() throws InMemoryDDLException {
		Column c1 = new Column("emp_no",ColumnType.NUMBER);
		Column c2 = new Column("emp_name",ColumnType.VARCHAR);
		Column c3 = new Column("date_of_joining", ColumnType.DATETIME);
		List<Column> cols = List.of(c1,c2,c3);
		Table t = new Table("employee",cols);
		
	}
}
