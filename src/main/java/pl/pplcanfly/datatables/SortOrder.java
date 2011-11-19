package pl.pplcanfly.datatables;

public enum SortOrder {
    ASC  { public int applyTo(int comparisonResult) { return comparisonResult; } },
    DESC { public int applyTo(int comparisonResult) { return comparisonResult * -1; } };
    
    public abstract int applyTo(int comparisonResult);
}
