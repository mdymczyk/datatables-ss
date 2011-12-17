package pl.pplcanfly.datatables;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import pl.pplcanfly.datatables.accessors.ValueAccessor;
import pl.pplcanfly.datatables.types.Type;

public class ServerSideDataTable {

    private List<Column> columns = new ArrayList<Column>();
    private String idColumnName;

    private ServerSideDataTable() { }

    public static Builder build() {
        return new Builder();
    }

    List<Column> getColumnsByName(List<String> columnNames) {
        List<Column> result = new ArrayList<Column>();
        for (String name : columnNames) {
            if (!name.equals(idColumnName)) {
                result.add(findColumn(name));
            }
        }
        return result;
    }

    List<Column> getColumnsByName(List<String> columnNames, long displayStart) {
        List<Column> result = new ArrayList<Column>();
        for (String name : columnNames) {
            if (name.equals(idColumnName)) {
                result.add(new IdColumn(idColumnName, displayStart));
            } else {
                result.add(findColumn(name));
            }
        }
        return result;
    }

    List<Column> getColumns() {
        return columns;
    }

    void setIdColumnName(String name) {
        this.idColumnName = name;
    }

    private Column findColumn(String columnName) {
        for (Column c : getColumns()) {
            if (c.getName().equals(columnName)) {
                return c;
            }
        }
        throw new ColumnNotFoundException(String.format("%s, defined columns = [%s]", columnName,
                getDefinedColumnNames()));
    }

    private String getDefinedColumnNames() {
        List<String> names = new ArrayList<String>();
        for (Column column : columns) {
            names.add(column.getName());
        }
        return StringUtils.join(names, ", ");
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

        public Builder idColumn(String name) {
            dataTable.setIdColumnName(name);
            return this;
        }

        public ServerSideDataTable done() {
            return dataTable;
        }

    }

}
