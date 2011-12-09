package pl.pplcanfly.datatables;


import static org.fest.assertions.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.pplcanfly.datatables.accessors.ReflectionValueAccessor;
import pl.pplcanfly.datatables.types.Type;

public class ServerSideDataTableTest {

    @Test
    public void should_find_defined_column() {
        // given
        Type type = new Type() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        };

        ServerSideDataTable dataTable = ServerSideDataTable.build()
                .column(type, "col")
                .done();

        // when
        assertThat(dataTable.getColumns()).hasSize(1);

        Column column = dataTable.getColumns().get(0);
        assertThat(column.getName()).isEqualTo("col");
        assertThat(column.getType()).isNotNull().isSameAs(type);
        assertThat(column.getValueAccessor()).isNotNull().isInstanceOf(ReflectionValueAccessor.class);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void should_throw_eception_if_column_was_not_found() {
        // given
        ServerSideDataTable dataTable = ServerSideDataTable.build()
                .column(null, "col1")
                .column(null, "col2")
                .done();

        thrown.expect(ColumnNotFoundException.class);
        thrown.expectMessage("idontexist, defined columns = [col1, col2]");

        // when
        dataTable.findColumn("idontexist");
    }

}
