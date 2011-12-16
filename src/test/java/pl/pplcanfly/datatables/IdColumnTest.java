package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class IdColumnTest {

    @Test
    public void should_return_consecutive_values_from_id_column() {
        // given
        Column idCol = new IdColumn("id", 10);

        // when/then
        assertThat(idCol.getValueFrom(null)).isEqualTo(10L);
        assertThat(idCol.getValueFrom(null)).isEqualTo(11L);
        assertThat(idCol.getValueFrom(null)).isEqualTo(12L);
        assertThat(idCol.getValueFrom(null)).isEqualTo(13L);
        assertThat(idCol.getValueFrom(null)).isEqualTo(14L);
    }

}
