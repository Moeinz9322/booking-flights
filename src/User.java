import java.io.IOException;
import java.io.RandomAccessFile;
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
     *
     * @param userId
     * @param numberFlight
     */
    public void addTicket(int userId , int numberFlight) throws IOException {
        RandomAccessFile usersFile = new RandomAccessFile("fileUsers.dat","rw");
        RandomAccessFile flightsFile = new RandomAccessFile("fileFlights.dat","rw");
        RandomAccessFile ticketsFile = new RandomAccessFile("fileTickets.dat","rw");
        FileUsers fileUsers = new FileUsers(usersFile);
        FileFlight fileFlight = new FileFlight(flightsFile);
        FileTickets fileTickets = new FileTickets(ticketsFile);

        int ticketId1;
        Random random = new Random();
        ticketId1 = random.nextInt();
        if (ticketId1 < 0) ticketId1 *= -1;
        ticketId += 1;
        String ticketId2 = ticketId1 + String.valueOf(ticketId);

        usersFile.seek(userId*FileUsers.RECORD_LENGTH);
        flightsFile.seek(numberFlight*FileFlight.RECORD_LENGTH);
        ticketsFile.seek(ticketsFile.length());
        String username = fileUsers.readFixString();
        String flightId = fileFlight.readFixString();
        fileTickets.write(username,flightId,ticketId2);

        System.out.println(ticketId2);

    }

    public int findTicketId(String ticketId) {
        for (int i = 0; i < tickets.length; i++) {
            if (tickets[i] != null && ticketId.equals(tickets[i].getTicketId())) {
                return i;
            }
        }
        return -1;
    }

    public void ticketCancellation(Admin admin, int numberTicket) throws IOException {
        int numberFlight;
        numberFlight = admin.findFlightId(tickets[numberTicket].getFlightId());
        admin.changeSeats(numberFlight, admin.getFlights()[numberFlight].getSeats() + 1);
        tickets[numberTicket] = tickets[numberTickets - 1];
        tickets[numberTickets - 1] = null;
        charge += admin.getFlights()[numberFlight].getPrice();
        numberTickets -= 1;
        System.out.println("successful ...");
    }
}
