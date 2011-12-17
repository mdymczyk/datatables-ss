package pl.pplcanfly.datatables;


import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

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

    @Test
    public void should_retrieve_columns_with_specific_names() {
        // when
        ServerSideDataTable dataTable = ServerSideDataTable.build()
                .column(null, "col1")
                .column(null, "col2")
                .column(null, "col3")
                .done();

        // when
        List<Column> columnsByName = dataTable.getColumnsByName(Arrays.asList("col2", "col3", "col1"));

        // when
        assertThat(columnsByName.get(0).getName()).isEqualTo("col2");
        assertThat(columnsByName.get(1).getName()).isEqualTo("col3");
        assertThat(columnsByName.get(2).getName()).isEqualTo("col1");
    }

    @Test
    public void should_allow_to_define_id_column() {
        ServerSideDataTable dataTable = ServerSideDataTable.build()
                .idColumn("id")
                .done();

        // when
        List<Column> columnsByName = dataTable.getColumnsByName(Arrays.asList("id"), 0);

        // when
        Column idColumn = columnsByName.get(0);
        assertThat(idColumn).isInstanceOf(IdColumn.class);
        assertThat(idColumn.getName()).isEqualTo("id");
    }

    @Test
    public void should_retrieve_columns_including_id_column_for_formatting() {
        // when
        ServerSideDataTable dataTable = ServerSideDataTable.build()
                .column(null, "col1")
                .idColumn("id")
                .done();

        // when
        List<Column> columnsByName = dataTable.getColumnsByName(Arrays.asList("id", "col1"), 10);

        // when
        assertThat(columnsByName.get(0).getName()).isEqualTo("id");
        assertThat(((IdColumn) columnsByName.get(0)).getDisplayStart()).isEqualTo(10);

        assertThat(columnsByName.get(1).getName()).isEqualTo("col1");
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
        dataTable.getColumnsByName(Arrays.asList("idontexist"));
    }

}
