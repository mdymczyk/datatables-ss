package pl.pplcanfly.datatables.accessors;

public class ValueNotAccessibleException extends RuntimeException {

    private static final long serialVersionUID = 2000993285069408113L;

    public ValueNotAccessibleException(String fieldName) {
        super(fieldName);
    }

}
