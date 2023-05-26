import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileAlreadyExistsException;
import java.util.Random;

public class User {
    private String username;
    private String password;
    private int charge;
    private Ticket[] tickets;
    private int numberTickets;
    private int ticketId;

    public User(String username, String password, int charge, Ticket[] tickets, int numberTickets, int ticketId) {
        this.username = username;
        this.password = password;
        this.charge = charge;
        this.tickets = tickets;
        this.numberTickets = numberTickets;
        this.ticketId = ticketId;
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

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public Ticket[] getTickets() {
        return tickets;
    }

    public void setTickets(Ticket[] tickets) {
        this.tickets = tickets;
    }

    public int getNumberTickets() {
        return numberTickets;
    }

    public void setNumberTickets(int numberTickets) {
        this.numberTickets = numberTickets;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * این تابع یک بلیط را به بلیط ها اضافه می‌کند
     * param userId
     * param numberFlight
     */
    public void addTicket(int userId, int numberFlight) throws IOException {
        RandomAccessFile usersFile = new RandomAccessFile("fileUsers.dat", "rw");
        RandomAccessFile flightsFile = new RandomAccessFile("fileFlights.dat", "rw");
        RandomAccessFile ticketsFile = new RandomAccessFile("fileTickets.dat", "rw");
        RandomAccessFile ticketIdFile = new RandomAccessFile("fileTicketId.dat", "rw");
        FileUsers fileUsers = new FileUsers(usersFile);
        FileFlight fileFlight = new FileFlight(flightsFile);
        FileTickets fileTickets = new FileTickets(ticketsFile);

        int ticketId1;
        if (ticketIdFile.length() == 0) {
            ticketIdFile.seek(0);
            ticketIdFile.writeInt(1000);
            ticketId1 = 1000;
        } else {
            ticketIdFile.seek(0);
            ticketId1 = ticketIdFile.readInt();
            ticketId1++;
            ticketIdFile.seek(0);
            ticketIdFile.writeInt(ticketId1);
        }
/*        Random random = new Random();
        ticketId1 = random.hashCode();
        if (ticketId1 < 0) ticketId1 *= -1;
        System.out.println(flightsFile.readInt());
*/
        flightsFile.seek(numberFlight * FileFlight.RECORD_LENGTH + fileFlight.FIX_SIZE * 10 + 4);
        String ticketId2 = String.valueOf(userId) + numberFlight + String.valueOf(flightsFile.readInt() + 1) + String.valueOf(ticketId1);
        usersFile.seek(userId * FileUsers.RECORD_LENGTH);
        flightsFile.seek(numberFlight * FileFlight.RECORD_LENGTH);
        ticketsFile.seek(ticketsFile.length());
        String username = fileUsers.readFixString();
        String flightId = fileFlight.readFixString();
        fileTickets.write(username, flightId, ticketId2);

        System.out.println("* your ticket id : " + ticketId2);
        ticketsFile.close();
        flightsFile.close();
        ticketIdFile.close();
    }

    /**
     * find a ticket in fileTickets
     * param ticketId
     * return
     * throws IOException
     */
    public int findTicketId(String ticketId) throws IOException {
        RandomAccessFile file = new RandomAccessFile("fileTickets.dat", "rw");
        FileTickets fileTickets = new FileTickets(file);
        for (int i = 0; i < file.length() / FileTickets.RECORD_LENGTH; i++) {
            file.seek(i * FileTickets.RECORD_LENGTH + fileTickets.FIX_SIZE * 4);
            if (fileTickets.readFixString().equals(ticketId)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * cancel a ticket with shift record in the fileTickets and add charge and add seat
     * param userId
     * param numberTicket
     * throws IOException
     */
    public void ticketCancellation(int userId, int numberTicket) throws IOException {
        RandomAccessFile ticketFile = new RandomAccessFile("fileTickets.dat", "rw");
        RandomAccessFile flightFile = new RandomAccessFile("fileFlights.dat", "rw");
        RandomAccessFile usersFile = new RandomAccessFile("fileUsers.dat", "rw");
        FileTickets fileTickets = new FileTickets(ticketFile);
        FileFlight fileFlight = new FileFlight(flightFile);
        FileUsers fileUsers = new FileUsers(usersFile);
        String username;
        String flightId;
        String ticketId;
        int numberFlight;
        Admin admin = new Admin(null, null, null);
        ticketFile.seek(numberTicket * FileTickets.RECORD_LENGTH + fileTickets.FIX_SIZE * 2);
//        System.out.println(numberTicket * FileTickets.RECORD_LENGTH + fileTickets.FIX_SIZE * 2);
        numberFlight = admin.findFlightId(fileTickets.readFixString());
        flightFile.seek(numberFlight * FileFlight.RECORD_LENGTH + fileFlight.FIX_SIZE * 10 + 4);
//        System.out.println(numberFlight * FileFlight.RECORD_LENGTH + fileFlight.FIX_SIZE * 10);
        admin.changeSeats(numberFlight, flightFile.readInt() + 1);

        flightFile.seek(numberFlight * FileFlight.RECORD_LENGTH + fileFlight.FIX_SIZE * 10);
        ticketFile.seek(numberTicket * FileTickets.RECORD_LENGTH);
        usersFile.seek(userId * FileUsers.RECORD_LENGTH + 4 * fileUsers.FIX_SIZE);
        charge = usersFile.readInt();
        charge += flightFile.readInt();

        usersFile.seek(userId * FileUsers.RECORD_LENGTH + 4 * fileUsers.FIX_SIZE);
        usersFile.writeInt(charge);

        for (int i = numberTicket; i < ticketFile.length() / FileTickets.RECORD_LENGTH - 1; i++) {
            ticketFile.seek((i + 1) * FileTickets.RECORD_LENGTH);
            username = fileTickets.readFixString();
            flightId = fileTickets.readFixString();
            ticketId = fileTickets.readFixString();
            ticketFile.seek(i * FileTickets.RECORD_LENGTH);
            fileTickets.write(username, flightId, ticketId);
        }
        ticketFile.setLength(ticketFile.length() - FileTickets.RECORD_LENGTH);
        System.out.println("successful ...");
    }
}
