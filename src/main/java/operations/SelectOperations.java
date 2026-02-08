package operations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import db.Database;
import table.Condition;
import table.Row;
import table.Table;
import utils.Utils;

public class SelectOperations {
	
	public Row equalsWithPK(Table table, int pk) {
		return table.getRows().get(pk);
	}
	
	public List<Row> equals(String tableName, Condition condition) throws Exception{
		List<Row> rows = new ArrayList<>();
		Utils.validateDb(tableName);
		Table table = Database.getInstance().getTables().get(tableName);
		String columnName = condition.getColumnName();
		Object value = condition.getValue();
		if(columnName.equals(table.getIndexColumn())) {
			Map<Object, Set<Integer>> index = table.getIndex();
			Set<Integer> matchedIds = index.get(value);
			if(matchedIds==null || matchedIds.isEmpty()) {
				return rows;
			}
			
			for(int id: matchedIds) {
				rows.add(table.getRows().get(id));
			}
			
			return rows;
		}
		throw new Exception("Non Indexed columns equals not supported yet");
	}

}
