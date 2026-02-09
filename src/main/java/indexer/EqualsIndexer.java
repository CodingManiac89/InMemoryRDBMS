package indexer;

import java.util.*;

public class EqualsIndexer{
    private class EqualsIndex{
        private Map<Object,Set<Integer>> index;

        public EqualsIndex(Map<Object,Set<Integer>> index) {
            this.index = index;
        }

        public void updateIndex(Object value, int rowId){
            if(this.index.get(value)==null){
                Set<Integer> set = new HashSet<>();
                set.add(rowId);
                this.index.put(value, set);
            }
            else{
                Set<Integer> set = this.index.get(value);
                set.add(rowId);
            }
        }

        public Set<Integer> getRowIds(Object value){
            if(this.index.get(value)!=null)
                return this.index.get(value);
            else
               return new HashSet<>();
        }

        public void removeIndexForValue(Object value){
            if(this.index.get(value)!=null)
                this.index.remove(value);
        }

        public void removeRowForValue(Object value, int rowId){
            (this.index.get(value)).remove(rowId);
        }
    }

    private Map<String,EqualsIndex> indexes;

    private EqualsIndexer() {
        this.indexes = new HashMap<>();
    }

    public static EqualsIndexer createInstance(Set<String> columns){
        EqualsIndexer indexer = new EqualsIndexer();
        columns.forEach(column -> {
            indexer.indexes.put(column, null);
        });
        return indexer;
    }

    public void updateIndex(String columnName, Object value, int rowId){
        if(this.indexes.get(columnName)==null){
            Map<Object,Set<Integer>> map = new HashMap<>();
            EqualsIndex index = new EqualsIndex(map);
            index.updateIndex(value, rowId);
            this.indexes.put(columnName, index);
        }
        else{
            EqualsIndex index = this.indexes.get(columnName);
            index.updateIndex(value, rowId);
        }
    }


    public Set<Integer> search(String columnName, Object value){
        EqualsIndex index = this.indexes.get(columnName);
        return index.getRowIds(value);
    }

    public void deleteIndexForColumn(String columnName){
        this.indexes.remove(columnName);
    }

    public void removeRowFromIndex(String columnName, Object value, int rowId){
        EqualsIndex index = this.indexes.get(columnName);
        index.removeRowForValue(value, rowId);
    }

}