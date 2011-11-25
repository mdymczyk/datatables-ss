package pl.pplcanfly.datatables.http;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.pplcanfly.datatables.Something;
import pl.pplcanfly.datatables.types.Types;

public class DataTablesResponseTest {

    @Test
    public void should_transform_to_json() {
        // given
        ServerSideDataTable dataTable = new ServerSideDataTable();
        dataTable.addColumn(Types.text(), "foo");
        dataTable.addColumn(Types.numeric(), "bar");
        
        List<Something> rows = Arrays.asList(new Something("abc", 123), new Something("def", 987));
        DataTablesResponse response = new DataTablesResponse(new ResponseParams(3, 20, 2), dataTable, rows);
        
        // when
        String json = response.toJson();
        
        // then
        assertThat(json).isEqualTo("{\"sEcho\":3," +
        		"\"iTotalRecords\":20," +
        		"\"iTotalDisplayRecords\":2," +
        		"\"aaData\":[[\"abc\",123],[\"def\",987]]}");
    }

}
