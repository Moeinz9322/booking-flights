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
            FileUsers fileUsers = new FileUsers(file);
            file.seek(0);
            fileUsers.writeString("Admin");
            fileUsers.writeString("Admin");
            file.writeInt(0);
            file.seek(0);
            file.close();
            Menu.startMenu();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}