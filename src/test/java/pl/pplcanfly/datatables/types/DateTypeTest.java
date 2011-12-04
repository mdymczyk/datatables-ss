package pl.pplcanfly.datatables.types;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;

public class DateTypeTest {

    private DateType type = new DateType();

    @Test
    public void should_compare_dates() {
        assertThat(type.compare(new Date(1L), new Date(2L))).isNegative();
        assertThat(type.compare(new Date(1L), new Date(1L))).isZero();
        assertThat(type.compare(new Date(2L), new Date(1L))).isPositive();
    }
}
