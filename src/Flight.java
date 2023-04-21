public class Flight {
    private String flightId;
    private String origin;
    private String destination;
    private DateFlight date;
    private TimeFlight time;
    private int price;
    private int seats;
    private int capacity;

    public Flight(String flightId, String origin, String destination, DateFlight date, TimeFlight time, int price, int seats, int capacity) {
        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.price = price;
        this.seats = seats;
        this.capacity = capacity;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin.toLowerCase();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination.toLowerCase();
    }

    public DateFlight getDate() {
        return date;
    }

    public void setDate(DateFlight date) {
        this.date = date;
    }

    public TimeFlight getTime() {
        return time;
    }

    public void setTime(TimeFlight timeFlight) {
        this.time = timeFlight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
