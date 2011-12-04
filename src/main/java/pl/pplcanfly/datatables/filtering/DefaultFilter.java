package pl.pplcanfly.datatables.filtering;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import pl.pplcanfly.datatables.Column;
import pl.pplcanfly.datatables.ServerSideDataTable;
import pl.pplcanfly.datatables.http.RequestParams;

public class DefaultFilter implements Filter {

    private ServerSideDataTable dataTable;
    private RequestParams params;

    public DefaultFilter(ServerSideDataTable dataTable, RequestParams params) {
        this.dataTable = dataTable;
        this.params = params;
    }

    @Override
    public List<?> filter(List<?> rows) {
        List<Object> filteredRows = new ArrayList<Object>();
        List<Pattern> patterns = precompile();
        List<Column> columns = findColumns();

    outer:
        for (Object row : rows) {
            for (Column column : columns) {
                String columnValue = "";

                Object value = column.getValueAccessor().getValueFrom(row);
                if (value != null) {
                    columnValue = value.toString();
                }

                for (Pattern pattern : patterns) {
                    if (pattern.matcher(columnValue).matches()) {
                        filteredRows.add(row);
                        continue outer;
                    }
                }
            }
        }

        return filteredRows;
    }

    private List<Column> findColumns() {
        List<String> searchableCols = params.getSearchableCols();
        List<Column> columns = new ArrayList<Column>();
        for (String columnName : searchableCols) {
            columns.add(dataTable.findColumn(columnName));
        }
        return columns;
    }

    private List<Pattern> precompile() {
        String search = params.getSearch();

        List<Pattern> patterns = new ArrayList<Pattern>();
        String[] splitted = search.split(" ");
        for (String s : splitted) {
            Pattern pattern = Pattern.compile(".*" + s.toLowerCase() + ".*", Pattern.CASE_INSENSITIVE);
            patterns.add(pattern);
        }
        return patterns;
    }

}
