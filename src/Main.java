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
    public static final int numberOfFlight = 1000;
    public static final int numberOfUser = 1000;

    public static void main(String[] args) {
        Flight[] flights = new Flight[numberOfFlight];
        flights[0] = new Flight("WX-12", "yazd", "tehran", new DateFlight("1401", "12", "10")
                , new TimeFlight("12", "30"), 700000, 51, 51);
        flights[1] = new Flight("WZ-15", "mashhad", "ahvaz", new DateFlight("1401", "12", "11")
                , new TimeFlight("08", "00"), 900000, 245, 245);
        flights[2] = new Flight("BG-22", "shiraz", "tabriz", new DateFlight("1401", "12", "12")
                , new TimeFlight("22", "30"), 1100000, 12, 12);
        Admin admin = new Admin("Admin", "Admin", flights);
        Users users = new Users(new User[numberOfUser], admin);
        try {
            RandomAccessFile file = new RandomAccessFile("file.dat","rw");
            FileFlight fileFlight = new FileFlight(file);
            fileFlight.write(flights[0]);
            file.seek(0);
            System.out.println(fileFlight.read().toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Menu.startMenu(users);
    }
}