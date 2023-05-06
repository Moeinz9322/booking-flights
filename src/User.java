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
     * @param admin
     * @param numberFlight
     */
    public void addTicket(Admin admin, int numberFlight) {

        int ticketId1;
        int numberTicket;
        Random random = new Random();
        ticketId1 = random.nextInt();
        if (ticketId1 < 0) ticketId1 *= -1;
        ticketId += 1;
        String ticketId2 = ticketId1 + String.valueOf(ticketId);

        numberTickets += 1;
        for (int i = 0; i < numberTickets; i++) {
            if (tickets[i] == null) {
                tickets[i] = new Ticket(admin.getFlights()[numberFlight].getFlightId(), admin.getFlights()[numberFlight].getOrigin(), admin.getFlights()[numberFlight].getDestination(), admin.getFlights()[numberFlight].getDateFlight(), admin.getFlights()[numberFlight].getTimeFlight(), admin.getFlights()[numberFlight].getPrice(), ticketId2);
                numberTicket = i;
                System.out.println(tickets[numberTicket].getTicketId());
                break;
            }
        }
    }

    public int findTicketId(String ticketId) {
        for (int i = 0; i < tickets.length; i++) {
            if (tickets[i] != null && ticketId.equals(tickets[i].getTicketId())) {
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
        charge += admin.getFlights()[numberFlight].getPrice();
        numberTickets -= 1;
        System.out.println("successful ...");
    }
}
