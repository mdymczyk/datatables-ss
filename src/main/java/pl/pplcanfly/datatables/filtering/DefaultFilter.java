package pl.pplcanfly.datatables.filtering;

import java.util.ArrayList;
import java.util.List;

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
        String search = params.getSearch();
        List<String> searchableCols = params.getSearchableCols();
        for (Object row : rows) {
            StringBuilder sb = new StringBuilder();
            for (String columnName : searchableCols) {
                Column column = dataTable.findColumn(columnName);
                sb.append(column.getValueAccessor().getValueFrom(row)).append(" ");
            }
            String[] splitted = search.split(" ");
            boolean matches = false;
            for (String s : splitted) {
                if (sb.toString().toLowerCase().matches(".*" + s.toLowerCase() + ".*")) {
                    matches = true;
                }
            }
            if (matches) {
                filteredRows.add(row);
            }
        }
        return filteredRows;
    }

}
