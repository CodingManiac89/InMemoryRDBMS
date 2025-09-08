package ddl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import constants.ColumnType;
import exception.InMemoryDDLException;
import operations.DDLOperations;
import table.Column;
import table.Table;

public class DeletionTests {
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
	public void testDropTable() {
		db.dropTable("employee");
		assertThrows(InMemoryDDLException.class, ()->{
			db.getTable("employee");
		});
	}

}
