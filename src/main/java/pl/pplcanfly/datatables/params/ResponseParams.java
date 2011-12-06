package pl.pplcanfly.datatables.params;

public class ResponseParams {
    private int echo;
    private int totalRecords;
    private int totalDisplayRecords;

    public ResponseParams(int echo, int totalRecords, int totalDisplayRecords) {
        this.echo = echo;
        this.totalRecords = totalRecords;
        this.totalDisplayRecords = totalDisplayRecords;
    }

    public int getEcho() {
        return echo;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public int getTotalDisplayRecords() {
        return totalDisplayRecords;
    }

}
