package pl.pplcanfly.datatables;

import java.util.List;
import java.util.Map;


public class DataTablesRequest {

    private RowsProcessor sorter;
    private RowsProcessor filter;
    private RowsProcessor limiter;
    private Formatter formatter;

    public DataTablesRequest(Map<String, String[]> params, ServerSideDataTable dataTable) {
        this(new RequestParams(params), dataTable);
    }

    DataTablesRequest(RequestParams params, ServerSideDataTable dataTable) {
        this.sorter = new Sorter(dataTable.getColumns(params.getSortCols()),
                SortOrder.toEnumList(params.getSortDirs()));

        this.filter = new Filter(dataTable.getColumns(params.getSearchableCols()),
                params.getSearch());

        this.limiter = new Limiter(params.getDisplayStart(), params.getDisplayLength());

        this.formatter = new JsonFormatter(dataTable.getColumns(params.getColumns(), params.getDisplayStart() + 1),
                params);
    }

    public DataTablesResponse process(List<?> rows) {
        List<?> processed = rows;

        processed = filter.process(processed);
        processed = sorter.process(processed);
        processed = limiter.process(processed);

        String json = formatter.format(processed, rows.size(), processed.size());

        return new DataTablesResponse(processed, json);
    }

    void setSorter(RowsProcessor sorter) {
        this.sorter = sorter;
    }

    void setFilter(RowsProcessor filter) {
        this.filter = filter;
    }

    void setLimiter(RowsProcessor limiter) {
        this.limiter = limiter;
    }

    void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

}
