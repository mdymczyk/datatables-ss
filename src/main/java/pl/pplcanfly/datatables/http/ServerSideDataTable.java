package pl.pplcanfly.datatables.http;

import java.util.ArrayList;
import java.util.List;

import pl.pplcanfly.datatables.Column;
import pl.pplcanfly.datatables.accessors.ValueAccessor;
import pl.pplcanfly.datatables.types.Type;

public class ServerSideDataTable {

    private List<Column> columns = new ArrayList<Column>();

    public void addColumn(Type type, String name) {
        columns.add(new Column(type, name));
    }

    public void addColumn(Type type, String name, ValueAccessor valueAccessor) {
        columns.add(new Column(type, name, valueAccessor));
    }

    public List<Column> getColumns() {
        return columns;
    }

    Column findColumn(String columnName) {
        for (Column c : getColumns()) {
            if (c.getName().equals(columnName)) {
                return c;
            }
        }
        return null;
    }

}
