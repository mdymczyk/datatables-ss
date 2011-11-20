package pl.pplcanfly.datatables.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import pl.pplcanfly.datatables.Column;
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
        for (Column c : dataTable.getColumns()) {
            comparator = RowComparator.ascending(c.getType(), c.getName());
        }
        List<Object> processedRows = new ArrayList<Object>(rows);
        Collections.sort(processedRows, comparator);
        return new DataTablesResponse(processedRows);
    }

}
