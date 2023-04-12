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

    public void updateFlight() {
    }

    public void removeFlight() {
    }

    public void flightSchedules() {
    }
}
