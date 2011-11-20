package pl.pplcanfly.datatables.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import pl.pplcanfly.datatables.Column;
import pl.pplcanfly.datatables.SortOrder;
import pl.pplcanfly.datatables.comparators.RowComparator;

public class DataTablesRequest {

    private RequestParams params;

    public DataTablesRequest(Map<String, String> params) {
        this.params = new RequestParams(params);
    }
    
    DataTablesRequest(RequestParams params) {
        this.params = params;
    }

    public DataTablesResponse process(ServerSideDataTable dataTable, List<?> rows) {
        RowComparator comparator = null;
        for (int i = 0; i < params.getSortingColsCount(); i++) {
            String sortColumnName = params.getSortCols().get(i);
            Column col = findColumn(sortColumnName, dataTable);
            comparator = new RowComparator(col.getType(), SortOrder.valueOf(params.getSortDirs().get(i)
                    .toUpperCase()), sortColumnName);
        }
        List<Object> processedRows = new ArrayList<Object>(rows);
        Collections.sort(processedRows, comparator);
        return new DataTablesResponse(processedRows);
    }

    private Column findColumn(String col, ServerSideDataTable dataTable) {
        for (Column c : dataTable.getColumns()) {
            if (c.getName().equals(col)) {
                return c;
            }
        }
        return null;
    }

}
