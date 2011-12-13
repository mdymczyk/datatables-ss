package pl.pplcanfly.datatables;

import java.util.List;
import java.util.Map;

import pl.pplcanfly.datatables.params.RequestParams;
import pl.pplcanfly.datatables.params.ResponseParams;

public class DataTablesRequest {

    private RequestParams params;
    private ServerSideDataTable dataTable;
    private Sorter sorter;
    private Filter filter;

    public DataTablesRequest(Map<String, String[]> params, ServerSideDataTable dataTable) {
        this(new RequestParams(params), dataTable);
    }

    DataTablesRequest(RequestParams params, ServerSideDataTable dataTable) {
        this.params = params;
        this.dataTable = dataTable;
        this.sorter = new DefaultSorter(dataTable, params);
        this.filter = new DefaultFilter(dataTable, params);
    }

    public DataTablesResponse process(List<?> rows) {
        List<?> processed = rows;

        if (params.hasSearchParams()) {
            processed = filter.filter(processed);
        }

        if (params.hasSortingParams()) {
            processed = sorter.sort(processed);
        }

        return new DataTablesResponse(params,
                new ResponseParams(params.getEcho(), rows.size(), processed.size()), dataTable,
                offsetAndLimit(processed));
    }

    private List<?> offsetAndLimit(List<?> processedRows) {
        return processedRows.subList(params.getDisplayStart(),
                Math.min(processedRows.size(), params.getDisplayStart() + params.getDisplayLength()));
    }

    void setSorter(Sorter sorter) {
        this.sorter = sorter;
    }

    void setFilter(Filter filter) {
        this.filter = filter;
    }

}
