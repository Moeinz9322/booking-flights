public class Flight {
    private String flightId;
    private String origin;
    private String destination;
    private DateFlight dateFlight;
    private TimeFlight timeFlight;
    private int price;
    private int seats;
    private int capacity;

    public Flight(String flightId, String origin, String destination, DateFlight dateFlight, TimeFlight timeFlight, int price, int seats, int capacity) {
        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.dateFlight = dateFlight;
        this.timeFlight = timeFlight;
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
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public DateFlight getDateFlight() {
        return dateFlight;
    }

    public void setDateFlight(DateFlight dateFlight) {
        this.dateFlight = dateFlight;
    }

    public TimeFlight getTimeFlight() {
        return timeFlight;
    }

    public void setTimeFlight(TimeFlight timeFlight) {
        this.timeFlight = timeFlight;
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

    @Override
    public String toString() {
        return "Flight{" +
                "flightId='" + flightId + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", dateFlight=" + dateFlight +
                ", timeFlight=" + timeFlight +
                ", price=" + price +
                ", seats=" + seats +
                ", capacity=" + capacity +
                '}';
    }
}
