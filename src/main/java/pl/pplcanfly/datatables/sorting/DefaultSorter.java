package pl.pplcanfly.datatables.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.pplcanfly.datatables.Column;
import pl.pplcanfly.datatables.RowComparator;
import pl.pplcanfly.datatables.ServerSideDataTable;
import pl.pplcanfly.datatables.SortOrder;
import pl.pplcanfly.datatables.http.RequestParams;

public class DefaultSorter implements Sorter {

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

            RowComparator newComparator = new RowComparator(column.getType(), SortOrder.valueOf(params.getSortDirs()
                        .get(i).toUpperCase()), column.getValueAccessor());

            if (comparator == null) {
                comparator = newComparator;
            } else {
                comparator.append(newComparator);
            }
        }
        return comparator;
    }

}
