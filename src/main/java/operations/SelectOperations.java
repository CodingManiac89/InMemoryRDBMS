package operations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import constants.LogicalOperator;
import constants.Operator;
import db.Database;
import exception.InMemoryDDLException;
import indexer.EqualsIndexer;
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
		
		Set<Integer> matchedRowIds = getMatchingRowIds(table, condition);
		if(matchedRowIds!=null){
			for(int id:matchedRowIds)
				rows.add(table.getRows().get(id));

			return rows;
		}

		throw new Exception("Non Indexed columns equals not supported yet");
	}

	public List<Row> compoundConditionEquals(String tableName, Condition cond1, Condition cond2, LogicalOperator operator) throws InMemoryDDLException{
		List<Row> rows = new ArrayList<>();
		Utils.validateDb(tableName);
		Table table = Database.getInstance().getTables().get(tableName);

		Set<Integer> firstMatchingRows = getMatchingRowIds(table, cond1);
		Set<Integer> secondMatchingRows = getMatchingRowIds(table, cond2);

		if(operator==LogicalOperator.AND){
			firstMatchingRows.retainAll(secondMatchingRows);
		}
		else if(operator == LogicalOperator.OR){
			firstMatchingRows.addAll(secondMatchingRows);
		}

		for(int id:firstMatchingRows){
			rows.add(table.getRows().get(id));
		}

		return rows;
	}


	private Set<Integer> getMatchingRowIds(Table table, Condition condition){
		Set<Integer> matchedRowIds = null;
		String columnName = condition.getColumnName();
		Object value = condition.getValue();
		EqualsIndexer indexer = table.getIndexer();

		if(table.getIndexColumns().contains(columnName)){
			matchedRowIds = indexer.search(columnName, value);
		}

		return matchedRowIds;
	}

}
