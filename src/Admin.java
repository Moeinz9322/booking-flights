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

    public void updateFlight(Flight flight , int numberFlight) {
        if (!flight.getFlightId().equals(""))
            flights[numberFlight].setFlightId(flight.getFlightId());
        if (!flight.getOrigin().equals(""))
            flights[numberFlight].setOrigin(flight.getOrigin());
        if (!flight.getDestination().equals(""))
            flights[numberFlight].setDestination(flight.getDestination());
        if (!flight.getDate().equals(""))
            flights[numberFlight].setDate(flight.getDate());
        if (!flight.getTime().equals(""))
            flights[numberFlight].setTime(flight.getTime());
        if (!String.valueOf(flight.getPrice()).equals(""))
            flights[numberFlight].setPrice(Integer.valueOf(flight.getPrice()));
        if (!String.valueOf(flight.getSeats()).equals("")){
            flights[numberFlight].setSeats(Integer.valueOf(flight.getSeats()));
            flights[numberFlight].setCapacity(Integer.valueOf(flight.getSeats()));
        }
    }

    public void removeFlight() {
    }

    public void flightSchedules() {
    }

    public int findFlightId(String flightId) {
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && flights[i].getFlightId().equals(flightId)){
                return i;
            }
        }
        return -1;
    }
}
