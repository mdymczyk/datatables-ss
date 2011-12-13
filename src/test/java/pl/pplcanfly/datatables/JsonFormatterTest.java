package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.pplcanfly.datatables.params.ResponseParams;
import pl.pplcanfly.datatables.types.Types;

public class JsonFormatterTest {

    @Test
    public void should_transform_to_json() {
        // given
        ResponseParams responseParams = new ResponseParams(3, 20, 2);

        ServerSideDataTable dataTable = ServerSideDataTable.build()
                .column(Types.text(), "foo")
                .column(Types.numeric(), "bar")
                .done();

        List<Something> rows = Arrays.asList(new Something("abc", 123), new Something("def", 987));

        // when
        String json = new JsonFormatter().format(responseParams, dataTable, rows);

        // then
        assertThat(json).isEqualTo("{\"sEcho\":3," +
                "\"iTotalRecords\":20," +
                "\"iTotalDisplayRecords\":2," +
                "\"aaData\":[[\"abc\",123],[\"def\",987]]}");
    }


}
