package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import pl.pplcanfly.datatables.utils.TestUtils;

public class LimiterTest {

    @Test
    public void should_return_sublist_of_input_rows() {
        // given
        List<Something> rows = TestUtils.load("1");

        Limiter limiter = new Limiter(1, 2);

        // when
        List<?> processed = limiter.process(rows);

        // then
        assertThat(processed).hasSize(2).onProperty("foo").containsExactly("a", "d");
    }

}
