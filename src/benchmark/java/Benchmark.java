import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import pl.pplcanfly.datatables.DataTablesRequest;
import pl.pplcanfly.datatables.ServerSideDataTable;
import pl.pplcanfly.datatables.accessors.ValueAccessor;
import pl.pplcanfly.datatables.types.Types;

class Benchmark {
    private static List<ExampleRow> rows = new ArrayList<ExampleRow>();
    private static ServerSideDataTable dataTable;
    private static Map<String, String[]> params;

    public static void main(String[] args) throws IOException {
        initializeData();

        all_columns_accessing_columns_using_reflection();
        column_using_custom_accessor_without_reflection();
        without_framework();
    }

    private static void without_framework() {
        List<ExampleRow> copy = new ArrayList<ExampleRow>(rows);

        Date start = new Date();
        Collections.sort(copy, new Comparator<ExampleRow>() {
            @Override
            public int compare(ExampleRow o1, ExampleRow o2) {
                return o1.getFirst().compareTo(o2.getFirst());
            }
        });
        Date end = new Date();

        System.out.println(Thread.currentThread().getStackTrace()[1]);
        System.out.println(end.getTime() - start.getTime());
    }

    private static void all_columns_accessing_columns_using_reflection() {
        initializeTableAllByReflection();
        initializeParams();

        DataTablesRequest request = new DataTablesRequest(params, dataTable);

        Date start = new Date();
        request.process(rows);
        Date end = new Date();

        System.out.println(Thread.currentThread().getStackTrace()[1]);
        System.out.println(end.getTime() - start.getTime());
    }

    private static void column_using_custom_accessor_without_reflection() {
        initializeTableWithCustom();
        initializeParams();

        DataTablesRequest request = new DataTablesRequest(params, dataTable);

        Date start = new Date();
        request.process(rows);
        Date end = new Date();

        System.out.println(Thread.currentThread().getStackTrace()[1]);
        System.out.println(end.getTime() - start.getTime());
    }

    private static void initializeData() throws IOException {
        File wordsFile = new File("/usr/share/dict/words");
        BufferedReader reader = new BufferedReader(new FileReader(wordsFile));

        List<String> words = new ArrayList<String>();
        String word = null;
        while ((word = reader.readLine()) != null) {
            words.add(word);
        }

        words.addAll(words);
        words.addAll(words);

        Collections.shuffle(words, new Random(1));

        for (int i = 0; i < words.size(); i++) {
            rows.add(new ExampleRow(words.get(i), words.get(words.size() - i - 1)));
        }
    }

    private static void initializeTableAllByReflection() {
        dataTable = new ServerSideDataTable();
        dataTable.addColumn(Types.text(), "first");
        dataTable.addColumn(Types.text(), "second");
    }

    private static void initializeTableWithCustom() {
        dataTable = new ServerSideDataTable();
        dataTable.addColumn(Types.text(), "first", new ValueAccessor() {
            @Override
            public Object getValueFrom(Object obj) {
                return ((ExampleRow) obj).getFirst();
            }
        });
        dataTable.addColumn(Types.text(), "second");
    }

    private static void initializeParams() {
        params = new HashMap<String, String[]>();
        params.put("sEcho", new String[] { "3" });

        params.put("iDisplayStart", new String[] { "0" });
        params.put("iDisplayLength", new String[] { "20" });

        params.put("sColumns", new String[] { "first,second" });
        params.put("iSortingCols", new String[] { "1" });
        params.put("iSortCol_0", new String[] { "0" });
        params.put("sSortDir_0", new String[] { "asc" });

        params.put("sSearch", new String[] { "a" });
        params.put("bSearchable_0", new String[]{"true"});
        params.put("bSearchable_1", new String[]{"true"});
    }
}

class ExampleRow {
    private String first;
    private String second;

    public ExampleRow(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return first + "," + second;
    }

}