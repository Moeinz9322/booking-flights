import java.util.ArrayList;
import java.util.Date;

public class Admin {
    private String username;
    private String password;
    private Flight[] flights;

    public Admin(String username, String password, Flight[] flights) {
        this.username = username;
        this.password = password;
        this.flights = flights;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Flight[] getFlights() {
        return flights;
    }

    public void setFlights(Flight[] flights) {
        this.flights = flights;
    }

    public void addFlight(Flight flight) {
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] == null) {
                flights[i] = new Flight(flight.getFlightId(), flight.getOrigin()
                        , flight.getDestination(), flight.getDate(), flight.getTime(), flight.getPrice()
                        , flight.getSeats(), flight.getSeats());
                break;
            }
        }
    }

    public void updateFlight(Flight flight, int numberFlight) {
        if (!flight.getFlightId().equals(""))
            flights[numberFlight].setFlightId(flight.getFlightId());
        if (!flight.getOrigin().equals(""))
            flights[numberFlight].setOrigin(flight.getOrigin());
        if (!flight.getDestination().equals(""))
            flights[numberFlight].setDestination(flight.getDestination());
        DateFlight dateFlight = new DateFlight(flights[numberFlight].getDate().getYear(), flights[numberFlight].getDate().getMonth(), flights[numberFlight].getDate().getDay());
        if (!flight.getDate().getYear().equals("-1")) {
            dateFlight.setYear(flight.getDate().getYear());
        }
        if (!flight.getDate().getMonth().equals("-1")) {
            dateFlight.setMonth(flight.getDate().getMonth());
        }
        if (!flight.getDate().getDay().equals("-1")) {
            dateFlight.setDay(flight.getDate().getDay());
        }
        flights[numberFlight].setDate(dateFlight);
        TimeFlight timeFlight = new TimeFlight(flights[numberFlight].getTime().getHours(), flights[numberFlight].getTime().getMinutes());
        if (!flight.getTime().getHours().equals("-1")) {
            timeFlight.setHours(flight.getTime().getHours());
        }
        if (!flight.getTime().getMinutes().equals("-1")) {
            timeFlight.setMinutes(flight.getTime().getMinutes());
        }
        flights[numberFlight].setTime(timeFlight);
        if (!String.valueOf(flight.getPrice()).equals("-1"))
            flights[numberFlight].setPrice(Integer.valueOf(flight.getPrice()));
        if (!String.valueOf(flight.getSeats()).equals("-1")) {
            flights[numberFlight].setSeats(Integer.valueOf(flight.getSeats()));
            flights[numberFlight].setCapacity(Integer.valueOf(flight.getSeats()));
        }
    }

    public void removeFlight(int numberFlight) {
        flights[numberFlight] = null;
    }

    public void printFlightForSearch(ArrayList<Integer> arrayNumberFlight) {
        Menu.printFlightsMenu();
        Menu.printFlights(arrayNumberFlight, flights);
    }

    public void flightSchedules() {
        System.out.printf("|%-12s|%-12s|%-12s|%-12s|%-12s|%-12s|%-12s|\n%s\n"
                , "FlightId"
                , "Origin"
                , "Destination"
                , "Date"
                , "Time"
                , "Price"
                , "Seats"
                , "............................................................................................"
        );
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null) {
                System.out.printf("|%-12s|%-12s|%-12s|%-12s|%-12s|%,-12d|%-12s|\n%-12s\n"
                        , flights[i].getFlightId()
                        , flights[i].getOrigin()
                        , flights[i].getDestination()
                        , flights[i].getDate().toString()
                        , flights[i].getTime().toString()
                        , flights[i].getPrice()
                        , flights[i].getSeats()
                        , "............................................................................................"
                );
            }
        }
    }

    public int findFlightId(String flightId) {
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && flights[i].getFlightId().equals(flightId)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Integer> findOriginSimilar(String origin) {
        ArrayList<Integer> originSimilar = new ArrayList<>();
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && flights[i].getOrigin().equals(origin)) {
                originSimilar.add(i);
            }
        }
        return originSimilar;
    }

    public ArrayList<Integer> findDestinationSimilar(String destination) {
        ArrayList<Integer> destinationSimilar = new ArrayList<>();
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && flights[i].getDestination().equals(destination)) {
                destinationSimilar.add(i);
            }
        }
        return destinationSimilar;
    }

    public ArrayList<Integer> findDateSimilar(DateFlight since, DateFlight until) {
        ArrayList<Integer> dateSimilar = new ArrayList<>();
        double sinceDate = 10000 * Integer.parseInt(since.getYear()) + 100 * Integer.parseInt(since.getMonth()) + Integer.parseInt(since.getDay());
        double upToDate = 10000 * Integer.parseInt(until.getYear()) + 100 * Integer.parseInt(until.getMonth()) + Integer.parseInt(until.getDay());
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && sinceDate <= 10000 * Integer.parseInt(flights[i].getDate().getYear()) +
                    Integer.parseInt(flights[i].getDate().getMonth()) * 100 +
                    Integer.parseInt(flights[i].getDate().getDay()) &&
                    10000 * Integer.parseInt(flights[i].getDate().getYear()) +
                            Integer.parseInt(flights[i].getDate().getMonth()) * 100 +
                            Integer.parseInt(flights[i].getDate().getDay()) <= upToDate) {
                dateSimilar.add(i);
            }
        }
        return dateSimilar;
    }

    public ArrayList<Integer> findTimeSimilar(TimeFlight since, TimeFlight until) {
        ArrayList<Integer> timeSimilar = new ArrayList<>();
        double sinceTime = Integer.parseInt(since.getHours()) + Integer.parseInt(since.getMinutes()) / 100;
        double upToTime = Integer.parseInt(until.getHours()) + Integer.parseInt(until.getMinutes()) / 100;
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && sinceTime <= Integer.parseInt(flights[i].getTime().getHours()) + Integer.parseInt(flights[i].getTime().getMinutes()) / 100
                    && Integer.parseInt(flights[i].getTime().getHours()) + Integer.parseInt(flights[i].getTime().getMinutes()) / 100 <= upToTime) {
                timeSimilar.add(i);
            }
        }
        return timeSimilar;
    }

    public ArrayList<Integer> findPriceSimilar(int fromPrice, int upToPrice) {
        ArrayList<Integer> priceSimilar = new ArrayList<>();
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && fromPrice <= flights[i].getPrice() && flights[i].getPrice() <= upToPrice) {
                priceSimilar.add(i);
            }
        }
        return priceSimilar;
    }

    public void changeSeats(Admin admin, int numberFlight, int seats) {
        admin.flights[numberFlight].setSeats(seats);
    }
}
