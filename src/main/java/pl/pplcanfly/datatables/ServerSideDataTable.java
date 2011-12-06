package pl.pplcanfly.datatables;

import java.util.ArrayList;
import java.util.List;

import pl.pplcanfly.datatables.accessors.ValueAccessor;
import pl.pplcanfly.datatables.types.Type;

public class ServerSideDataTable {

    private List<Column> columns = new ArrayList<Column>();

    private ServerSideDataTable() { }

    public static Builder build() {
        return new Builder();
    }

    List<Column> getColumns() {
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

    public static class Builder {
        private ServerSideDataTable dataTable;

        public Builder() {
            dataTable = new ServerSideDataTable();
        }

        public Builder column(Type type, String name) {
            dataTable.getColumns().add(new Column(type, name));
            return this;
        }

        public Builder column(Type type, String name, ValueAccessor valueAccessor) {
            dataTable.getColumns().add(new Column(type, name, valueAccessor));
            return this;
        }

        public ServerSideDataTable done() {
            return dataTable;
        }

    }

}
