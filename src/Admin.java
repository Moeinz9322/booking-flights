import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

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

    /**
     * این تابع پرواز فرستاده شده را به لیست پرواز ها اضافه می‌کند
     *
     * @param flight
     */
    public void addFlight(Flight flight) {
        try {
            RandomAccessFile file = new RandomAccessFile("file.dat", "rw");
            FileFlight fileFlight = new FileFlight(file);
            file.seek(file.length());
            fileFlight.write(flight);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateFlight(Flight flight, int numberFlight) {
        if (!flight.getFlightId().equals(""))
            flights[numberFlight].setFlightId(flight.getFlightId());
        if (!flight.getOrigin().equals(""))
            flights[numberFlight].setOrigin(flight.getOrigin());
        if (!flight.getDestination().equals(""))
            flights[numberFlight].setDestination(flight.getDestination());
        DateFlight dateFlight = new DateFlight(flights[numberFlight].getDateFlight().getYear(), flights[numberFlight].getDateFlight().getMonth(), flights[numberFlight].getDateFlight().getDay());
        if (!flight.getDateFlight().getYear().equals("-1")) {
            dateFlight.setYear(flight.getDateFlight().getYear());
        }
        if (!flight.getDateFlight().getMonth().equals("-1")) {
            dateFlight.setMonth(flight.getDateFlight().getMonth());
        }
        if (!flight.getDateFlight().getDay().equals("-1")) {
            dateFlight.setDay(flight.getDateFlight().getDay());
        }
        flights[numberFlight].setDateFlight(dateFlight);
        TimeFlight timeFlight = new TimeFlight(flights[numberFlight].getTimeFlight().getHours(), flights[numberFlight].getTimeFlight().getMinutes());
        if (!flight.getTimeFlight().getHours().equals("-1")) {
            timeFlight.setHours(flight.getTimeFlight().getHours());
        }
        if (!flight.getTimeFlight().getMinutes().equals("-1")) {
            timeFlight.setMinutes(flight.getTimeFlight().getMinutes());
        }
        flights[numberFlight].setTimeFlight(timeFlight);
        if (!String.valueOf(flight.getPrice()).equals("-1"))
            flights[numberFlight].setPrice(flight.getPrice());
        if (!String.valueOf(flight.getSeats()).equals("-1")) {
            flights[numberFlight].setSeats(flight.getSeats());
            flights[numberFlight].setCapacity(flight.getSeats());
        }
    }

    public void removeFlight(int numberFlight) {
        flights[numberFlight] = null;
    }

    public void printFlightForSearch(ArrayList<Integer> arrayNumberFlight) {
        Menu.printFlightsMenu();

        Menu.printFlights(flights);
    }

    public void flightSchedules() throws IOException {
        Menu.printFlightsMenu();
        Flight[] flights1 = new Flight[1000];
        RandomAccessFile file = new RandomAccessFile("file.dat", "r");
        FileFlight fileFlight = new FileFlight(file);
        file.seek(0);
        for (int i = 0; i < file.length() / 162; i++) {
            flights1[i] = fileFlight.read();
        }
        Menu.printFlights(flights1);
    }

    /**
     * این تابع شماره پروازه هایی که آیدی یکسان با آیدی فرستاده شده دارند را محاسبه می‌کند
     *
     * @param flightId
     * @return
     */
    public int findFlightId(String flightId) {
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && flights[i].getFlightId().equals(flightId)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * این تابع شماره پروازه هایی که مبدا یکسان با مبدا فرستاده شده دارند را محاسبه می‌کند
     *
     * @param origin
     * @return numberOfFlights
     */
    public ArrayList<Integer> findOriginSimilar(String origin) {
        ArrayList<Integer> originSimilar = new ArrayList<>();
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && flights[i].getOrigin().equals(origin)) {
                originSimilar.add(i);
            }
        }
        return originSimilar;
    }

    /**
     * این تابع شماره پروازه هایی که مقصدی مثل مقصد فرستاده شده دارند را محاسبه می‌کند
     *
     * @param destination
     * @return numberOfFlights
     */
    public ArrayList<Integer> findDestinationSimilar(String destination) {
        ArrayList<Integer> destinationSimilar = new ArrayList<>();
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && flights[i].getDestination().equals(destination)) {
                destinationSimilar.add(i);
            }
        }
        return destinationSimilar;
    }

    /**
     * این تابع شماره پروازه هایی که در بازه زمانی (تاریخ) فرستاده شده است را محاسبه می‌کند
     *
     * @param since
     * @param until
     * @return numberOfFlights
     */
    public ArrayList<Integer> findDateSimilar(DateFlight since, DateFlight until) {
        ArrayList<Integer> dateSimilar = new ArrayList<>();
        double sinceDate = 10000 * Integer.parseInt(since.getYear()) + 100 * Integer.parseInt(since.getMonth()) + Integer.parseInt(since.getDay());
        double upToDate = 10000 * Integer.parseInt(until.getYear()) + 100 * Integer.parseInt(until.getMonth()) + Integer.parseInt(until.getDay());
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && sinceDate <= 10000 * Integer.parseInt(flights[i].getDateFlight().getYear()) +
                    Integer.parseInt(flights[i].getDateFlight().getMonth()) * 100 +
                    Integer.parseInt(flights[i].getDateFlight().getDay()) &&
                    10000 * Integer.parseInt(flights[i].getDateFlight().getYear()) +
                            Integer.parseInt(flights[i].getDateFlight().getMonth()) * 100 +
                            Integer.parseInt(flights[i].getDateFlight().getDay()) <= upToDate) {
                dateSimilar.add(i);
            }
        }
        return dateSimilar;
    }

    /**
     * این تابع شماره پروازه هایی که در بازه زمانی (ساعت) فرستاده شده است را محاسبه می‌کند
     *
     * @param since
     * @param until
     * @return numberOfFlights
     */
    public ArrayList<Integer> findTimeSimilar(TimeFlight since, TimeFlight until) {
        ArrayList<Integer> timeSimilar = new ArrayList<>();
        double sinceTime = 100 * Integer.parseInt(since.getHours()) + Integer.parseInt(since.getMinutes());
        double upToTime = 100 * Integer.parseInt(until.getHours()) + Integer.parseInt(until.getMinutes());
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && sinceTime <= 100 * Integer.parseInt(flights[i].getTimeFlight().getHours()) + Integer.parseInt(flights[i].getTimeFlight().getMinutes())
                    && 100 * Integer.parseInt(flights[i].getTimeFlight().getHours()) + Integer.parseInt(flights[i].getTimeFlight().getMinutes()) <= upToTime) {
                timeSimilar.add(i);
            }
        }
        return timeSimilar;
    }

    /**
     * این تابع شماره پروازه هایی که در بازه قیمت فرستاده شده است را محاسبه می‌کند
     *
     * @param fromPrice
     * @param upToPrice
     * @return numberOfFlights
     */
    public ArrayList<Integer> findPriceSimilar(int fromPrice, int upToPrice) {
        ArrayList<Integer> priceSimilar = new ArrayList<>();
        for (int i = 0; i < flights.length; i++) {
            if (flights[i] != null && fromPrice <= flights[i].getPrice() && flights[i].getPrice() <= upToPrice) {
                priceSimilar.add(i);
            }
        }
        return priceSimilar;
    }

    public void changeSeats(int numberFlight, int seats) {
        flights[numberFlight].setSeats(seats);
    }
}
