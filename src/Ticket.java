import java.sql.Time;
import java.util.Date;

public class Ticket {
    private String flightId;
    private String origin;
    private String destination;
    private DateFlight date;
    private TimeFlight time;
    private int price;
    private int ticketId;

    public Ticket(String flightId, String origin, String destination, DateFlight date, TimeFlight time, int price, int ticketId) {
        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.price = price;
        this.ticketId = ticketId;
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

    public DateFlight getDate() {
        return date;
    }

    public void setDate(DateFlight date) {
        this.date = date;
    }

    public TimeFlight getTime() {
        return time;
    }

    public void setTime(TimeFlight time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }
}
