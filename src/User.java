import java.util.Random;

public class User {
    private String username;
    private String password;
    private int charge;
    private Ticket[] tickets;
    private int numberTickets;

    public User(String username, String password, int charge, Ticket[] tickets, int numberTickets) {
        this.username = username;
        this.password = password;
        this.charge = charge;
        this.tickets = tickets;
        this.numberTickets = numberTickets;
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

    public void addTicket(Admin admin, int numberFlight) {

        int ticketId;
        int numberTicket;
        Random random = new Random();
        ticketId = random.nextInt();


        numberTickets += 1;
        for (int i = 0; i < numberTickets; i++) {
            if (tickets[i] == null) {
                tickets[i] = new Ticket(admin.getFlights()[numberFlight].getFlightId(), admin.getFlights()[numberFlight].getOrigin()
                        , admin.getFlights()[numberFlight].getDestination(), admin.getFlights()[numberFlight].getDate()
                        , admin.getFlights()[numberFlight].getTime(), admin.getFlights()[numberFlight].getPrice(), ticketId);
                numberTicket = i;
                System.out.println(tickets[numberTicket].getTicketId());
                break;
            }
        }
    }

    public int findTicketId(int ticketId) {
        for (int i = 0; i < tickets.length; i++) {
            if (tickets[i] != null && ticketId == tickets[i].getTicketId()) {
                return i;
            }
        }
        return -1;
    }

    public void ticketCancellation(Admin admin, int numberTicket) {
        int numberFlight;
        numberFlight = admin.findFlightId(tickets[numberTicket].getFlightId());
        admin.changeSeats(numberFlight, admin.getFlights()[numberFlight].getSeats() + 1);
        tickets[numberTicket] = tickets[numberTickets - 1];
        tickets[numberTickets - 1] = null;
        numberTickets -= 1;
        System.out.println("successful ...");
        System.out.print("Press enter to return to the previous menu ...");
        Input.inputString();
    }
}
