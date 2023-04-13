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
                System.out.printf("|%-12s|%-12s|%-12s|%-12s|%-12s|%-12s|%-12s|\n%-12s\n"
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

    public int findFlightIdSimilar(String flightId) {
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && flights[i].getFlightId().equals(flightId)) {
                return i;
            }
        }
        return -1;
    }

    public void findOriginSimilar(int[] originSimilar, String origin) {
        int number = 0;
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && flights[i].getOrigin().equals(origin)) {
                originSimilar[number] = i;
                number++;
            }
        }
    }

    public void findDestinationSimilar(int[] destinationSimilar, String destination) {
        int number = 0;
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && flights[i].getDestination().equals(destination)) {
                destinationSimilar[number] = i;
                number++;
            }
        }
    }

    public void findDateSimilar(int[] dateSimilar, DateFlight since, DateFlight upToDate) {
        int number = 0;
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && Integer.parseInt(since.getYear()) <= Integer.parseInt(flights[i].getDate().getYear())
                    && Integer.parseInt(flights[i].getDate().getYear()) <= Integer.parseInt(upToDate.getYear())
                    && Integer.parseInt(since.getMonth()) <= Integer.parseInt(flights[i].getDate().getMonth())
                    && Integer.parseInt(flights[i].getDate().getMonth()) <= Integer.parseInt(upToDate.getMonth())
                    && Integer.parseInt(since.getDay()) <= Integer.parseInt(flights[i].getDate().getDay())
                    && Integer.parseInt(flights[i].getDate().getDay()) <= Integer.parseInt(upToDate.getDay())) {
                dateSimilar[number] = i;
                number++;
            }
        }
    }

    public void findTimeSimilar(int[] timeSimilar, TimeFlight since, TimeFlight upToTime) {
        int number = 0;
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && Integer.parseInt(since.getHours()) <= Integer.parseInt(flights[i].getTime().getHours())
                    && Integer.parseInt(flights[i].getTime().getHours()) <= Integer.parseInt(upToTime.getHours())
                    && Integer.parseInt(since.getMinutes()) <= Integer.parseInt(flights[i].getTime().getMinutes())
                    && Integer.parseInt(flights[i].getTime().getMinutes()) <= Integer.parseInt(upToTime.getMinutes())) {
                timeSimilar[number] = i;
                number++;
            }
        }
    }

    public void findPriceSimilar(int[] priceSimilar, int fromPrice, int upToPrice) {
        int number = 0;
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && fromPrice <= flights[i].getPrice() && flights[i].getPrice() <= upToPrice) {
                priceSimilar[number] = i;
                number++;
            }
        }
    }

    public void findSeats(int[] seatsSimilar, int fromSeats, int upToSeats) {
        int number = 0;
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && fromSeats <= flights[i].getSeats() && flights[i].getSeats() <= upToSeats) {
                seatsSimilar[number] = i;
                number++;
            }
        }
    }
}
