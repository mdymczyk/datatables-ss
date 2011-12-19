package pl.pplcanfly.datatables;

class IdColumn extends Column {

    private long displayStart;

    public IdColumn(String name, long displayStart) {
        super(/* no type needed */ null, name);
        this.displayStart = displayStart;
    }

    @Override
    public Object getValueFrom(Object row) {
        return displayStart++;
    }

    public long getDisplayStart() {
        return displayStart;
    }

}
