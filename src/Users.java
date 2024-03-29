import java.io.IOException;
import java.io.RandomAccessFile;

public class Users {
    User user;
    Admin admin;

    public Users(User user, Admin admin) {
        this.user = user;
        this.admin = admin;
    }

    public User getCustomers() {
        return user;
    }

    public void setCustomers(User user) {
        this.user = user;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    /**
     * add a record of ticket to fileTickets and print ticketId
     * param userId
     * param numberFlight
     * throws IOException
     */
    public void bookingTicket(int userId, int numberFlight) throws IOException {
        RandomAccessFile usersFile = new RandomAccessFile("fileUsers.dat", "rw");
        RandomAccessFile flightsFile = new RandomAccessFile("fileFlights.dat", "rw");
        FileUsers fileUsers = new FileUsers(usersFile);
        FileFlight fileFlight = new FileFlight(flightsFile);

        flightsFile.seek(numberFlight * FileFlight.RECORD_LENGTH + fileFlight.FIX_SIZE * 10 + 4);
        admin = new Admin(null, null, null);
        admin.changeSeats(numberFlight, flightsFile.readInt() - 1);

        flightsFile.seek(numberFlight * FileFlight.RECORD_LENGTH + fileFlight.FIX_SIZE * 10);
        usersFile.seek(userId * fileUsers.RECORD_LENGTH + 4 * fileUsers.FIX_SIZE);
        int charge = usersFile.readInt() - flightsFile.readInt();
        usersFile.seek(userId * fileUsers.RECORD_LENGTH + 4 * fileUsers.FIX_SIZE);
        usersFile.writeInt(charge);
        user = new User(null, null, 0, null, 0, 0);
        usersFile.close();
        flightsFile.close();
        user.addTicket(userId, numberFlight);
    }
}
