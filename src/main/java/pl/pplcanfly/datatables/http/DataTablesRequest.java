package pl.pplcanfly.datatables.http;

import java.util.List;
import java.util.Map;

import pl.pplcanfly.datatables.ServerSideDataTable;
import pl.pplcanfly.datatables.sorting.DefaultSorter;
import pl.pplcanfly.datatables.sorting.Sorter;

public class DataTablesRequest {

    private RequestParams params;

    public DataTablesRequest(Map<String, String[]> params) {
        this.params = new RequestParams(params);
    }

    DataTablesRequest(RequestParams params) {
        this.params = params;
    }

    public DataTablesResponse process(ServerSideDataTable dataTable, List<?> rows) {
        return process(dataTable, rows, new DefaultSorter(dataTable, params));
    }

    DataTablesResponse process(ServerSideDataTable dataTable, List<?> rows, Sorter sorter) {
        List<?> processedRows = sorter.sort(rows);

        return new DataTablesResponse(
                new ResponseParams(params.getEcho(), rows.size(), processedRows.size()), dataTable,
                offsetAndLimit(processedRows));
    }

    private List<?> offsetAndLimit(List<?> processedRows) {
        return processedRows.subList(params.getDisplayStart(),
                Math.min(processedRows.size(), params.getDisplayStart() + params.getDisplayLength()));
    }

}
