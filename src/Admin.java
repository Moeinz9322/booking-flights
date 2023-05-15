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
            RandomAccessFile file = new RandomAccessFile("fileFlights.dat", "rw");
            FileFlight fileFlight = new FileFlight(file);
            file.seek(file.length());
            fileFlight.write(flight);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateFlight(Flight flight, int numberFlight) throws IOException {
        RandomAccessFile file = new RandomAccessFile("fileFlights.dat","rw");
        FileFlight fileFlight = new FileFlight(file);
        if (!flight.getFlightId().equals("")) {
            file.seek(numberFlight*162);
            fileFlight.writeString(flight.getFlightId());
        }
        if (!flight.getOrigin().equals("")) {
            file.seek(numberFlight*162+30);
            fileFlight.writeString(flight.getOrigin());
        }
        if (!flight.getDestination().equals("")) {
            file.seek(numberFlight*162+60);
            fileFlight.writeString(flight.getDestination());
        }
        if (!flight.getDateFlight().getYear().equals("-1")) {
            file.seek(numberFlight*162+90);
            fileFlight.writeString(flight.getDateFlight().toString());
        }
        if (!flight.getTimeFlight().getHours().equals("-1")) {
            file.seek(numberFlight*162+120);
            fileFlight.writeString(flight.getTimeFlight().toString());
        }
        if (!String.valueOf(flight.getPrice()).equals("-1")){
            file.seek(numberFlight*162+150);
            file.writeInt(flight.getPrice());
        }
        if (!String.valueOf(flight.getSeats()).equals("-1")) {
            file.seek(numberFlight*162+154);
            file.writeInt(flight.getSeats());
            file.writeInt(flight.getSeats());
        }
    }

    public void removeFlight(int numberFlight) throws IOException {
        RandomAccessFile file = new RandomAccessFile("fileFlights.dat", "rw");
        FileFlight fileFlight = new FileFlight(file);
        Flight flight;
        for (int i = numberFlight; i < file.length()/162-1; i++) {
            file.seek((i+1)*162);
            flight=fileFlight.read();
            file.seek(i*162);
            fileFlight.write(flight);
        }
        file.setLength(file.length()-162);
    }

    public void printFlightForSearch(ArrayList<Integer> arrayNumberFlight) {
        Menu.printFlightsMenu();

        Menu.printFlights(flights);
    }

    public void flightSchedules() throws IOException {
        Menu.printFlightsMenu();
        Flight[] flights1 = new Flight[1000];
        RandomAccessFile file = new RandomAccessFile("fileFlights.dat", "r");
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
    public static int findFlightId(String flightId) throws IOException {
        RandomAccessFile file = new RandomAccessFile("fileFlights.dat", "rw");
        FileFlight fileFlight = new FileFlight(file);
        for (int i = 0; i < file.length()/162; i++) {
            file.seek(i*162);
            if (fileFlight.readFixString().equals(flightId)) {
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
