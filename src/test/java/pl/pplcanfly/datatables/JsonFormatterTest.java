package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.pplcanfly.datatables.params.RequestParams;
import pl.pplcanfly.datatables.params.ResponseParams;
import pl.pplcanfly.datatables.types.Types;

public class JsonFormatterTest {

    @Test
    public void should_transform_to_json_with_column_order_determied_by_request_params() {
        // given
        RequestParams requestParams = mock(RequestParams.class);
        stub(requestParams.getColumns()).toReturn(Arrays.asList("foo", "bar"));

        ResponseParams responseParams = new ResponseParams(3, 20, 2);

        ServerSideDataTable dataTable = ServerSideDataTable.build() // column in different order than in request params
                .column(Types.numeric(), "bar")
                .column(Types.text(), "foo")
                .done();

        List<Something> rows = Arrays.asList(new Something("abc", 123), new Something("def", 987));


        // when
        String json = new JsonFormatter().format(requestParams, responseParams, dataTable, rows);

        // then
        assertThat(json).isEqualTo("{\"sEcho\":3," +
                "\"iTotalRecords\":20," +
                "\"iTotalDisplayRecords\":2," +
                "\"aaData\":[[\"abc\",123],[\"def\",987]]}");
    }


}
