package pl.pplcanfly.datatables.http;

import java.util.List;
import java.util.Map;

import pl.pplcanfly.datatables.ServerSideDataTable;
import pl.pplcanfly.datatables.sorting.DefaultSorter;
import pl.pplcanfly.datatables.sorting.Sorter;

public class DataTablesRequest {

    private RequestParams params;
    private ServerSideDataTable dataTable;
    private Sorter sorter;

    public DataTablesRequest(Map<String, String[]> params, ServerSideDataTable dataTable) {
        this(new RequestParams(params), dataTable);
    }

    DataTablesRequest(RequestParams params, ServerSideDataTable dataTable) {
        this.params = params;
        this.dataTable = dataTable;
        this.sorter = new DefaultSorter(dataTable, params);
    }

    public DataTablesResponse process(List<?> rows) {
        List<?> processedRows = sorter.sort(rows);

        return new DataTablesResponse(
                new ResponseParams(params.getEcho(), rows.size(), processedRows.size()), dataTable,
                offsetAndLimit(processedRows));
    }

    private List<?> offsetAndLimit(List<?> processedRows) {
        return processedRows.subList(params.getDisplayStart(),
                Math.min(processedRows.size(), params.getDisplayStart() + params.getDisplayLength()));
    }

    void setSorter(Sorter sorter) {
        this.sorter = sorter;
    }

}
