package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.pplcanfly.datatables.types.Types;

public class JsonFormatterTest {

    @Test
    public void should_transform_to_json_with_column_order_determied_by_request_params() {
        // given
        RequestParams requestParams = mock(RequestParams.class);
        stub(requestParams.getEcho()).toReturn(3);

        List<Something> rowsToShow = Arrays.asList(new Something("abc", 123), new Something("def", 987));

        int totalRows = 20;
        int displayRows = 10;

        JsonFormatter formatter = new JsonFormatter(Arrays.asList(new Column(Types.text(), "foo"),
                new Column(Types.numeric(), "bar")), requestParams);

        // when
        String json = formatter.format(rowsToShow, totalRows, displayRows);

        // then
        assertThat(json).isEqualTo("{\"sEcho\":3," +
                "\"iTotalRecords\":20," +
                "\"iTotalDisplayRecords\":10," +
                "\"aaData\":[[\"abc\",123],[\"def\",987]]}");
    }


}
