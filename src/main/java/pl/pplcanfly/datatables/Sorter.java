package pl.pplcanfly.datatables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class Sorter implements RowsProcessor {

    private List<Column> columns;
    private List<SortOrder> sortOrders;

    public Sorter(List<Column> columns, List<SortOrder> sortOrders) {
        this.columns = columns;
        this.sortOrders = sortOrders;
    }

    @Override
    public List<?> process(List<?> rows) {
        if (columns.isEmpty()) {
            return rows;
        }

        RowComparator comparator = makeComparator();
        List<Object> processedRows = new ArrayList<Object>(rows);

        Collections.sort(processedRows, comparator);

        return processedRows;
    }

    private RowComparator makeComparator() {
        RowComparator comparator = null;
        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);

            RowComparator newComparator = new RowComparator(column, sortOrders.get(i));

            if (comparator == null) {
                comparator = newComparator;
            } else {
                comparator.append(newComparator);
            }
        }
        return comparator;
    }

}
