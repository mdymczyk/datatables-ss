package pl.pplcanfly.datatables.http;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.pplcanfly.datatables.Something;
import pl.pplcanfly.datatables.Type;

public class DataTablesRequestTest {
    
    @Test
    public void should_process_request_and_generate_response() {
        RequestParams params = mock(RequestParams.class);
        stub(params.getSortCols()).toReturn(Arrays.asList("foo"));
        stub(params.getSortDirs()).toReturn(Arrays.asList("asc"));
        
        DataTablesRequest request = new DataTablesRequest(params);
        
        ServerSideDataTable dataTable = new ServerSideDataTable();
        dataTable.addColumn(Type.STRING, "foo");
        
        List<Something> rows = new ArrayList<Something>();
        rows.add(new Something("abc3", 62));
        rows.add(new Something("abc2", 74));
        rows.add(new Something("abc4", 98));
        rows.add(new Something("abc1", 44));
        
        // when
        DataTablesResponse response = request.process(dataTable, rows);
        
        // then
        assertThat(rows).onProperty("foo").containsExactly("abc3", "abc2", "abc4", "abc1");
        
        assertThat(response.getProcessedRows()).onProperty("foo").containsExactly("abc1", "abc2", "abc3", "abc4");
    }
}
