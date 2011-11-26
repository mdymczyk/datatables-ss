package pl.pplcanfly.datatables.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import pl.pplcanfly.datatables.Column;
import pl.pplcanfly.datatables.RowComparator;
import pl.pplcanfly.datatables.SortOrder;

public class DataTablesRequest {

    private RequestParams params;

    public DataTablesRequest(Map<String, String[]> params) {
        this.params = new RequestParams(params);
    }

    DataTablesRequest(RequestParams params) {
        this.params = params;
    }

    public DataTablesResponse process(ServerSideDataTable dataTable, List<?> rows) {
        RowComparator comparator = makeComparator(dataTable);
        List<Object> processedRows = new ArrayList<Object>(rows);

        Collections.sort(processedRows, comparator);

        return new DataTablesResponse(
                new ResponseParams(params.getEcho(), rows.size(), processedRows.size()), dataTable,
                offsetAndLimit(processedRows));
    }

    private List<Object> offsetAndLimit(List<Object> processedRows) {
        return processedRows.subList(params.getDisplayStart(),
                Math.min(processedRows.size(), params.getDisplayStart() + params.getDisplayLength()));
    }

    private RowComparator makeComparator(ServerSideDataTable dataTable) {
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
