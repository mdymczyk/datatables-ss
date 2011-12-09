package pl.pplcanfly.datatables;

public class ColumnNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -35602407292626707L;

    public ColumnNotFoundException(String columnName) {
        super(columnName);
    }

}
