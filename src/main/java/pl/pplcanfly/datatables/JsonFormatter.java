package pl.pplcanfly.datatables;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

class JsonFormatter implements Formatter {

    private ServerSideDataTable dataTable;
    private RequestParams params;

    public JsonFormatter(ServerSideDataTable dataTable, RequestParams params) {
        this.dataTable = dataTable;
        this.params = params;
    }

    @Override
    public String format(List<?> rows, int totalRows, int displayRows) {
        JSONObject json = new JSONObject();
        json.accumulate("sEcho", params.getEcho());
        json.accumulate("iTotalRecords", totalRows);
        json.accumulate("iTotalDisplayRecords", displayRows);
        JSONArray jsonArray = new JSONArray();
        for (Object row : rows) {
            List<Object> values = new ArrayList<Object>();
            for(String columnName : params.getColumns()) {
                Column column = dataTable.findColumn(columnName);
                values.add(column.getValueAccessor().getValueFrom(row));
            }
            jsonArray.element(values);
        }
        json.accumulate("aaData", jsonArray.toString());
        return json.toString();
    }

}
