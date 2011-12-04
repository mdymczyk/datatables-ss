package pl.pplcanfly.datatables.types;


import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class TextTypeTest {

    private TextType textType = new TextType();

    @Test
    public void should_compare_strings_case_insensitive() {
        assertThat(textType.compare("a", "b")).isNotEqualTo(0);
        assertThat(textType.compare("aBcD", "AbcD")).isZero();
    }

}
