package pl.pplcanfly.datatables;

import java.util.List;
import java.util.Map;


public class DataTablesRequest {

    private RequestParams params;
    private RowsProcessor sorter;
    private RowsProcessor filter;
    private Formatter formatter;

    public DataTablesRequest(Map<String, String[]> params, ServerSideDataTable dataTable) {
        this(new RequestParams(params), dataTable);
    }

    DataTablesRequest(RequestParams params, ServerSideDataTable dataTable) {
        this.params = params;

        this.sorter = new Sorter(dataTable.getColumns(params.getSortCols()),
                SortOrder.toEnumList(params.getSortDirs()));

        this.filter = new Filter(dataTable.getColumns(params.getSearchableCols()),
                params.getSearch());

        this.formatter = new JsonFormatter(dataTable.getColumns(params.getColumns(), params.getDisplayStart() + 1),
                params);
    }

    public DataTablesResponse process(List<?> rows) {
        List<?> processed = rows;

        processed = filter.process(processed);
        processed = sorter.process(processed);

        List<?> limited = offsetAndLimit(processed);

        String json = formatter.format(limited, rows.size(), processed.size());

        return new DataTablesResponse(limited, json);
    }

    private List<?> offsetAndLimit(List<?> processedRows) {
        return processedRows.subList(params.getDisplayStart(),
                Math.min(processedRows.size(), params.getDisplayStart() + params.getDisplayLength()));
    }

    void setSorter(RowsProcessor sorter) {
        this.sorter = sorter;
    }

    void setFilter(RowsProcessor filter) {
        this.filter = filter;
    }

    void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

}
