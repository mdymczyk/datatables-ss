package pl.pplcanfly.datatables;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


class DefaultFilter implements Filter {

    private List<Column> columns;
    private String search;

    public DefaultFilter(List<Column> columns, String search) {
        this.search = search;
        this.columns = columns;
    }

    @Override
    public List<?> filter(List<?> rows) {
        List<Object> filteredRows = new ArrayList<Object>();
        List<Pattern> patterns = precompile();

        for (Object row : rows) {
            boolean allPatternsWereMatched = true;

            for (Pattern pattern : patterns) {
                boolean patternMatchesAnyColumn = false;

                for (Column column : columns) {
                    String columnValue = "";

                    Object value = column.getValueFrom(row);
                    if (value != null) {
                        columnValue = value.toString();
                    }

                    if (pattern.matcher(columnValue).matches()) {
                        patternMatchesAnyColumn = true;
                    }
                }

                allPatternsWereMatched = allPatternsWereMatched && patternMatchesAnyColumn;
            }

            if (allPatternsWereMatched) {
                filteredRows.add(row);
            }
        }

        return filteredRows;
    }

    private List<Pattern> precompile() {
        List<Pattern> patterns = new ArrayList<Pattern>();
        String[] splitted = search.split(" ");
        for (String s : splitted) {
            Pattern pattern = Pattern.compile(".*" + s.toLowerCase() + ".*", Pattern.CASE_INSENSITIVE);
            patterns.add(pattern);
        }
        return patterns;
    }

}
