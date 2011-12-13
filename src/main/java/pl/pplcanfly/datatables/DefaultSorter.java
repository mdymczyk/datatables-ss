package pl.pplcanfly.datatables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class DefaultSorter implements Sorter {

    private ServerSideDataTable dataTable;
    private RequestParams params;

    public DefaultSorter(ServerSideDataTable dataTable, RequestParams params) {
        this.dataTable = dataTable;
        this.params = params;
    }

    @Override
    public List<?> sort(List<?> rows) {
        RowComparator comparator = makeComparator();
        List<Object> processedRows = new ArrayList<Object>(rows);

        Collections.sort(processedRows, comparator);

        return processedRows;
    }

    private RowComparator makeComparator() {
        RowComparator comparator = null;
        for (int i = 0; i < params.getSortingColsCount(); i++) {
            String sortColumnName = params.getSortCols().get(i);
            Column column = dataTable.findColumn(sortColumnName);

            RowComparator newComparator = new RowComparator(column,
                    SortOrder.valueOf(params.getSortDirs().get(i).toUpperCase()));

            if (comparator == null) {
                comparator = newComparator;
            } else {
                comparator.append(newComparator);
            }
        }
        return comparator;
    }

}
