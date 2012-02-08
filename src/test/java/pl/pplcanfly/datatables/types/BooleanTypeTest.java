package pl.pplcanfly.datatables.types;


import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class BooleanTypeTest {

    private BooleanType booleanType = new BooleanType();

    @Test
    public void should_compare_strings_case_insensitive() {
        assertThat(booleanType.compare(true, false)).isNotEqualTo(0);
        assertThat(booleanType.compare(true, true)).isZero();
    }

}
