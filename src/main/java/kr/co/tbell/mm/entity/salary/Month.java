package kr.co.tbell.mm.entity.salary;

public enum Month {
    JANUARY("1"),
    FEBRUARY("2"),
    MARCH("3"),
    APRIL("4"),
    MAY("5"),
    JUNE("6"),
    JULY("7"),
    AUGUST("8"),
    SEPTEMBER("9"),
    OCTOBER("10"),
    NOVEMBER("11"),
    DECEMBER("12");

    private String monthNumber;
    private Month(String monthNumber) { this.monthNumber = monthNumber; }
    public String getMonthNumber() { return this.monthNumber; }

    public static Month convert(Integer monthInteger) {
        for (Month value : values()) {
            if (monthInteger.toString().equals(value.monthNumber)) {
                return value;
            }
        }
        return null;
    }
}
