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
}