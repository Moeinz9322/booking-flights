public class DateFlight {
    private String year;
    private String month;
    private String day;

    public DateFlight(String year, String month, String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        for (int i = 0; year.length() < 4; i++) {
            year = "0" + year;
        }
        if (month.length() == 1)
            month = "0" + month;
        if (day.length() == 1)
            day = "0" + day;
        return year + "_" + month + "_" + day;
    }
}
