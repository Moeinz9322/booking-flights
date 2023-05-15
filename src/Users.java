import java.io.RandomAccessFile;

public class Users {
    User[] customers;
    Admin admin;

    public Users(User[] customers, Admin admin) {
        this.customers = customers;
        this.admin = admin;
    }

    public User[] getCustomers() {
        return customers;
    }

    public void setCustomers(User[] customers) {
        this.customers = customers;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

//    public String findUsername(String username) {
//        if (username.equals(admin.getUsername())) {
//            return "admin";
//        } else {
//            for (int i = 0; i < customers.length; i++) {
//                if (customers[i] != null && username.equals(customers[i].getUsername())) {
//                    return String.valueOf(i);
//                }
//            }
//        }
//        return "-1";
//    }

    public void bookingTicket(int userId, int numberFlight) {
        admin.changeSeats(numberFlight, admin.getFlights()[numberFlight].getSeats() - 1);

        customers[userId].setCharge(customers[userId].getCharge() - admin.getFlights()[numberFlight].getPrice());

        customers[userId].setNumberTickets(customers[userId].getNumberTickets() + 1);

        customers[userId].addTicket(admin, numberFlight);
    }
}
