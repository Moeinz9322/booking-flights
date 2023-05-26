import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * run program
 *
 * @author Moein Zanjirian Zadeh
 * @since 2023/4/11
 * This program is for booking plane tickets online .
 */
public class Main {
    public static final int NUMBER_OF_FLIGHT = 1000;

    public static void main(String[] args) {
        try {
            RandomAccessFile file = new RandomAccessFile("fileUsers.dat", "rw");
            RandomAccessFile fileFlights = new RandomAccessFile("fileFlights.dat", "rw");
            FileUsers fileUsers = new FileUsers(file);
            FileFlight fileFlight = new FileFlight(fileFlights);
            //In fact, there is no need to fill the flight file because it must be filled by the admin, but to speed up the test, this initialization is done.
            if (fileFlights.length() == 0) {
                Flight[] flights = new Flight[3];
                flights[0] = new Flight("WX-12", "yazd", "tehran", new DateFlight("1401", "12", "10")
                        , new TimeFlight("12", "30"), 700000, 51, 51);
                flights[1] = new Flight("WZ-15", "mashhad", "ahvaz", new DateFlight("1401", "12", "11")
                        , new TimeFlight("08", "00"), 900000, 245, 245);
                flights[2] = new Flight("BG-22", "shiraz", "tabriz", new DateFlight("1401", "12", "12")
                        , new TimeFlight("22", "30"), 1100000, 12, 12);
                for (int i = 0; i < 3; i++) {
                    fileFlights.seek(i * FileFlight.RECORD_LENGTH);
                    fileFlight.write(flights[i]);
                }
            }

            file.seek(0);
            fileUsers.writeString("Admin");
            fileUsers.writeString("Admin");
            file.writeInt(0);
            file.close();
            fileFlights.close();
            Menu.startMenu();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}