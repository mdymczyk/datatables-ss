package pl.pplcanfly.datatables.http;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pl.pplcanfly.datatables.Something;
import pl.pplcanfly.datatables.Type;

public class DataTablesRequestTest {

    private ServerSideDataTable dataTable;
    private RequestParams params;
    private DataTablesRequest request;

    @Before
    public void setUp() {
        dataTable = new ServerSideDataTable();
        dataTable.addColumn(Type.STRING, "foo");
        dataTable.addColumn(Type.INTEGER, "bar");

        params = mock(RequestParams.class);
        request = new DataTablesRequest(params);
    }

    @Test
    public void should_not_change_order_of_input_rows() throws IOException {
        setSortCols(params, "foo");
        setSortDirs(params, "asc");
        
        List<Something> rows = load("1");
        
        // when
        request.process(dataTable, rows);
        
        // then
        assertThat(rows).isEqualTo(load("1"));
    }
    
    @Test
    public void should_sort_by_one_column_asc() throws IOException {
        setSortCols(params, "foo");
        setSortDirs(params, "asc");
        
        List<Something> rows = load("1");
        
        // when
        DataTablesResponse response = request.process(dataTable, rows);
        
        // then
        assertThat(response.getProcessedRows()).isEqualTo(load("1_foo_asc"));
    }
    
    @Test
    public void should_sort_by_one_column_desc() throws IOException {
        setSortCols(params, "foo");
        setSortDirs(params, "desc");
        
        List<Something> rows = load("1");
        
        // when
        DataTablesResponse response = request.process(dataTable, rows);
        
        // then
        assertThat(response.getProcessedRows()).isEqualTo(load("1_foo_desc"));
    }
    
    private void setSortCols(RequestParams params, String... cols) {
        stub(params.getSortCols()).toReturn(Arrays.asList(cols));
        stub(params.getSortingColsCount()).toReturn(cols.length);
    }
    
    private void setSortDirs(RequestParams params, String... dirs) {
        stub(params.getSortDirs()).toReturn(Arrays.asList(dirs));
    }
    
    private List<Something> load(String file) throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/fixtures/" + file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        List<Something> list = new ArrayList<Something>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] splitted = line.split(" ");
            list.add(new Something(splitted[0], Integer.parseInt(splitted[1])));
        }

        return list;
    }
}
