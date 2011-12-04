package pl.pplcanfly.datatables.http;

import java.util.List;
import java.util.Map;

import pl.pplcanfly.datatables.ServerSideDataTable;
import pl.pplcanfly.datatables.filtering.DefaultFilter;
import pl.pplcanfly.datatables.filtering.Filter;
import pl.pplcanfly.datatables.sorting.DefaultSorter;
import pl.pplcanfly.datatables.sorting.Sorter;

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

        if (!"".equals(params.getSearch())) {
            processed = filter.filter(processed);
        }

        if (params.getSortingColsCount() != 0) {
            processed = sorter.sort(processed);
        }

        return new DataTablesResponse(
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
