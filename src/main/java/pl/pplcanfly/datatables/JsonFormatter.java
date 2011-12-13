package pl.pplcanfly.datatables;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pl.pplcanfly.datatables.params.RequestParams;
import pl.pplcanfly.datatables.params.ResponseParams;

class JsonFormatter implements Formatter {

    @Override
    public String format(RequestParams requestParams, ResponseParams params, ServerSideDataTable dataTable, List<?> rows) {
        JSONObject json = new JSONObject();
        json.accumulate("sEcho", params.getEcho());
        json.accumulate("iTotalRecords", params.getTotalRecords());
        json.accumulate("iTotalDisplayRecords", params.getTotalDisplayRecords());
        JSONArray jsonArray = new JSONArray();
        for (Object row : rows) {
            List<Object> values = new ArrayList<Object>();
            for(String columnName : requestParams.getColumns()) {
                Column column = dataTable.findColumn(columnName);
                values.add(column.getValueAccessor().getValueFrom(row));
            }
            jsonArray.element(values);
        }
        json.accumulate("aaData", jsonArray.toString());
        return json.toString();
    }

}
