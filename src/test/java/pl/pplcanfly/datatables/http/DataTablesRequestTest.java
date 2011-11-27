package pl.pplcanfly.datatables.http;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pl.pplcanfly.datatables.ServerSideDataTable;
import pl.pplcanfly.datatables.Something;
import pl.pplcanfly.datatables.sorting.Sorter;
import pl.pplcanfly.datatables.types.Types;

public class DataTablesRequestTest {

    private ServerSideDataTable dataTable;
    private RequestParams params;
    private DataTablesRequest request;
    private Sorter sorter;

    @Before
    public void setUp() {
        dataTable = new ServerSideDataTable();
        dataTable.addColumn(Types.text(), "foo");
        dataTable.addColumn(Types.numeric(), "bar");

        params = mock(RequestParams.class);
        stub(params.getDisplayStart()).toReturn(0);
        stub(params.getDisplayLength()).toReturn(20);

        request = new DataTablesRequest(params);
        sorter = mock(Sorter.class);
    }

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void should_set_response_params_after_sorting() {
        // given
        stub(params.getEcho()).toReturn(3);

        List<Something> rows = load("1");
        List processed = new ArrayList<Something>();
        processed.add(new Something());
        processed.add(new Something());
        when(sorter.sort(rows)).thenReturn(processed);

        // when
        DataTablesResponse response = request.process(dataTable, rows, sorter);

        // then
        assertThat(response.getParams().getEcho()).isEqualTo(3);
        assertThat(response.getParams().getTotalRecords()).isEqualTo(4);
        assertThat(response.getParams().getTotalDisplayRecords()).isEqualTo(2);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_offset_and_limit_processed_rows() {
        // given
        stub(params.getDisplayStart()).toReturn(1);
        stub(params.getDisplayLength()).toReturn(2);

        List<Something> rows = load("1");
        List processed = load("1_foo_asc");
        when(sorter.sort(rows)).thenReturn(processed);

        // when
        DataTablesResponse response = request.process(dataTable, rows, sorter);

        // then
        assertThat(response.getProcessedRows()).hasSize(2).onProperty("foo").containsExactly("b", "c");
    }

    private List<Something> load(String file) {
        InputStream is = this.getClass().getResourceAsStream("/fixtures/" + file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        List<Something> list = new ArrayList<Something>();
        String line = null;
        while ((line = readLine(reader)) != null) {
            String[] splitted = line.split(" ");
            list.add(new Something(splitted[0], Integer.parseInt(splitted[1])));
        }

        return list;
    }

    private String readLine(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
