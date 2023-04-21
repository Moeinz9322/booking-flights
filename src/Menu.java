import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public static int numberOfFlight() {
        return 1000;
    }

    public static int numberOfUser() {
        return 1000;
    }

    public static void startMenu() {
        Flight[] flights = new Flight[numberOfFlight()];
        flights[0] = new Flight("WX-12", "Yazd", "Tehran", new DateFlight("1401", "12", "10")
                , new TimeFlight("12", "30"), 700000, 51, 51);
        flights[1] = new Flight("WZ-15", "Mashhad", "Ahvaz", new DateFlight("1401", "12", "11")
                , new TimeFlight("08", "00"), 900000, 245, 245);
        flights[2] = new Flight("BG-22", "Shiraz", "Tabriz", new DateFlight("1401", "12", "12")
                , new TimeFlight("22", "30"), 1100000, 12, 12);
        Admin admin = new Admin("Admin", "Admin", flights);
        Users users = new Users(new User[numberOfUser()], admin);
        String input;
        while (true) {
            System.out.print("\033[H\033[2J");//This is the command to clear the screen
            printMenu();
            input = Input.inputInStartMenu();
            System.out.print("\033[H\033[2J");//This is the command to clear the screen
            switch (input) {
                case "1", "SIGN IN":
                    signIn(users);
                    break;
                case "2", "SIGN UP":
                    signUp(users);
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "    WELCOME TO AIRLINE RESERVATION SYSTEM      "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , " ................MENU OPTIONS................. "
                , "    <1> Sign in"
                , "    <2> Sign up"
        );
    }

    private static void signIn(Users users) {
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                    Log in                     "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* username : "
        );
        String userId = Input.inputInSignIn(users);
        switch (userId) {
            case "admin":
                adminMenu(users.admin);
                break;
            case "-1":
                System.out.println("Please check your password or username :(\n" +
                        "if you haven't registered yet, you can register in the sign up section :)\n" +
                        "Press enter to return to the previous menu");
                Input.inputString();
                break;
            default:
                userMenu(users, Integer.valueOf(userId));
        }
    }

    /**
     * sign in for user
     *
     * @param users
     */
    private static void signUp(Users users) {
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                    Sign up                    "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* username : "
        );
        String username = Input.inputInSignUp(users);
        for (int i = 0; i < users.customers.length; i++) {
            if (users.customers[i] == null) {
                Ticket[] tickets = new Ticket[100];
                users.customers[i] = new User(username, "0", 0, tickets, 0);
                System.out.print("* password : ");
                users.customers[i].setPassword(Input.inputString());
                break;
            }
        }
    }

    private static void adminMenu(Admin admin) {
        boolean flag = true;
        while (flag) {
            System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n"
                    , ":::::::::::::::::::::::::::::::::::::::::::::::"
                    , "              Admin MENU OPTIONS               "
                    , ":::::::::::::::::::::::::::::::::::::::::::::::"
                    , " ............................................. "
                    , "    <1> Add"
                    , "    <2> update"
                    , "    <3> Remove"
                    , "    <4> Flight schedules"
                    , "    <0> Sign out"
            );

            switch (Input.inputAdminMenu()) {
                case "1", "ADD":
                    addFlightMenu(admin);
                    break;
                case "2", "UPDATE":
                    updateFlightMenu(admin);
                    break;
                case "3", "REMOVE":
                    removeFlight(admin);
                    break;
                case "4", "FLIGHT SCHEDULES":
                    admin.flightSchedules();
                    break;
                case "0", "SIGN OUT":
                    flag = false;
                    break;
            }
        }
    }

    private static void removeFlight(Admin admin) {
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                   remove                      "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* FlightId : "
        );
        int numberFlight = admin.findFlightId(Input.inputString());
        if (numberFlight == -1) {
            System.out.println("Please check flight id :(");
            return;
        }
        if (admin.getFlights()[numberFlight].getSeats() != admin.getFlights()[numberFlight].getCapacity()) {
            System.out.println("You can't remove this flight because it is reserved by the user :(");
            return;
        }
        admin.removeFlight(numberFlight);
        System.out.println("successful :) ...\n Press enter to return to the previous menu");
        Input.inputString();
    }

    private static void updateFlightMenu(Admin admin) {
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                   update                      "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* Flight Id : "
        );
        int numberFlight = admin.findFlightId(Input.inputString());
        if (numberFlight == -1) {
            System.out.println("Please check flight id :(");
            return;
        }
        if (admin.getFlights()[numberFlight].getSeats() != admin.getFlights()[numberFlight].getCapacity()) {
            System.out.println("You can't update this flight because it is reserved by the user :(");
            return;
        }
        System.out.println("Do you want to remove it ?\n1) Yes\n2) No");
        String input = null;
        boolean flag = true;
        while (flag) {
            input = Input.inputString().toUpperCase();
            switch (input) {
                case "1", "2", "YES", "NO":
                    flag = false;
                    break;
                default:
                    System.out.println("Please check your command :(");
                    break;
            }
        }
        if (input.equals("1") || input.equals("YES")) {
            admin.removeFlight(numberFlight);
            return;
        }
        admin.updateFlight(Input.inputForUpdateFlight(), numberFlight);
        System.out.println("successful :) ...\n Press enter to return to the previous menu");
        Input.inputString();
    }

    private static void addFlightMenu(Admin admin) {
        System.out.printf("%s\n%s\n%s\n"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                     Add                       "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
        );
        admin.addFlight(Input.inputForAddFlight());
    }

    private static void userMenu(Users users, int userId) {
        boolean flag = true;
        while (flag) {
            System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n"
                    , ":::::::::::::::::::::::::::::::::::::::::::::::"
                    , "            PASSENGER MENU OPTIONS             "
                    , ":::::::::::::::::::::::::::::::::::::::::::::::"
                    , " ............................................. "
                    , "    <1> Change password"
                    , "    <2> Search flight tickets"
                    , "    <3> Booking tickets"
                    , "    <4> Ticket cancellation"
                    , "    <5> Booked ticket"
                    , "    <6> Add charge"
                    , "    <0> sign out"
            );

            switch (Input.inputForUserMenu()) {
                case "1":
                    changePassword(users, userId);
                    break;
                case "2":
                    searchFlight(users.admin);
                    break;
                case "3":
                    bookingTicket(users, userId);
                    break;
                case "4":
                    ticketCancellation(users, userId);
                    break;
                case "5":
                    bookedTickets(users, userId);
                    break;
                case "6":
                    addCharge(users, userId);
                    break;
                case "0":
                    flag = false;
                    break;
            }
        }
    }

    private static void changePassword(Users users, int userId) {
        System.out.printf("%s\n%s\n%s\n"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "               change password                 "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
        );
        users.customers[userId].setPassword(Input.inputForChangePassword(users, userId));
        System.out.print("Press enter to return to the previous menu ...");
        Input.inputString();
    }

    private static void searchFlight(Admin admin) {
        System.out.printf("%s\n%s\n%s\n"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                Search flight                  "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
        );
        ArrayList<Integer> arraySimilarFlights = new ArrayList<>();
        ArrayList<Integer> arrayFlights = new ArrayList<>();
        Flight flight = new Flight(Input.inputFlightId(), Input.inputOrigin(), Input.inputDestination(), null, null, 0, 0, 0);
        System.out.println("(Sine)");
        flight.setDate(Input.inputDateForSearch());
        DateFlight upToDate = null;
        if (flight.getDate().getYear() != null) {
            System.out.println("(Until)");
            upToDate = Input.inputDateForSearch();
        }
        System.out.println("(Sine)");
        flight.setTime(Input.inputTimeForSearch());
        TimeFlight upToTime = null;
        if (flight.getTime().getHours() != null) {
            System.out.println("(Until)");
            upToTime = Input.inputTimeForSearch();
        }
        System.out.println("(From)");
        flight.setPrice(Input.inputPrice());
        int upToPrice = 0;
        if (flight.getPrice() != -1) {
            System.out.println("(up to)");
            upToPrice = Input.inputPrice();
        }

        if (!flight.getFlightId().equals("")) {
            arraySimilarFlights.add(admin.findFlightId(flight.getFlightId()));
        }
        if (!flight.getOrigin().equals("")) {
            arrayFlights = admin.findOriginSimilar(flight.getOrigin());
            arraySimilarFlights = findSimilarHomesTwoArray(arrayFlights, arraySimilarFlights);
        }
        if (!flight.getDestination().equals("")) {
            arrayFlights = admin.findDestinationSimilar(flight.getDestination());
            arraySimilarFlights = findSimilarHomesTwoArray(arrayFlights, arraySimilarFlights);
        }
        if (flight.getDate().getYear() != null && !flight.getDate().equals("")) {
            arrayFlights = admin.findDateSimilar(flight.getDate(), upToDate);
            arraySimilarFlights = findSimilarHomesTwoArray(arrayFlights, arraySimilarFlights);
        }
        if (flight.getTime().getHours() != null && !flight.getTime().equals("")) {
            arrayFlights = admin.findTimeSimilar(flight.getTime(), upToTime);
            arraySimilarFlights = findSimilarHomesTwoArray(arrayFlights, arraySimilarFlights);
        }
        if (flight.getPrice() != -1) {
            arrayFlights = admin.findPriceSimilar(flight.getPrice(), upToPrice);
            arraySimilarFlights = findSimilarHomesTwoArray(arrayFlights, arraySimilarFlights);
        }
        admin.printFlightForSearch(arraySimilarFlights);
    }

    private static ArrayList<Integer> findSimilarHomesTwoArray(ArrayList<Integer> arrayFlights, ArrayList<Integer> arraySimilarFlights) {
        ArrayList<Integer> arraySharingArrays = new ArrayList<>();
        if (arraySimilarFlights.size() == 0)
            return arrayFlights;
        if (arrayFlights.size() == 0)
            return arraySimilarFlights;

        for (int i = 0; i < arraySimilarFlights.size(); i++) {
            for (int j = 0; j < arrayFlights.size(); j++) {
                if (arraySimilarFlights.get(i) == arrayFlights.get(j))
                    arraySharingArrays.add(arrayFlights.get(j));
            }
        }
        arrayFlights = new ArrayList<>();
        return arraySharingArrays;
    }

    public static void printFlights(ArrayList<Integer> arrayNumberFlight, Flight[] flights) {
        for (int i = 0; i < arrayNumberFlight.size(); i++) {
            if (flights[arrayNumberFlight.get(i)] != null) {
                System.out.printf("|%-12s|%-12s|%-12s|%-12s|%-12s|%,-12d|%-12s|\n%-12s\n"
                        , flights[arrayNumberFlight.get(i)].getFlightId()
                        , flights[arrayNumberFlight.get(i)].getOrigin()
                        , flights[arrayNumberFlight.get(i)].getDestination()
                        , flights[arrayNumberFlight.get(i)].getDate().toString()
                        , flights[arrayNumberFlight.get(i)].getTime().toString()
                        , flights[arrayNumberFlight.get(i)].getPrice()
                        , flights[arrayNumberFlight.get(i)].getSeats()
                        , "............................................................................................"
                );
            }
        }
    }

    public static void printFlightsMenu() {
        System.out.printf("|%-12s|%-12s|%-12s|%-12s|%-12s|%-12s|%-12s|\n%s\n"
                , "FlightId"
                , "Origin"
                , "Destination"
                , "Date"
                , "Time"
                , "Price"
                , "Seats"
                , "............................................................................................"
        );
    }

    private static void bookingTicket(Users users, int userId) {
        System.out.printf("%s\n%s\n%s\n"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                Booking ticket                 "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
        );
        String flightId = Input.inputFlightId();
        int numberFlight = users.admin.findFlightId(flightId);
        if (numberFlight == -1) {
            System.out.println("Please check Flight Id");
            return;
        }

        if (users.admin.getFlights()[numberFlight].getPrice() > users.customers[userId].getCharge()) {
            System.out.println("Please check your charge ...");
            return;
        }

        if (users.admin.getFlights()[numberFlight].getSeats() < 0) {
            System.out.println("It has no capacity");
            return;
        }
        users.bookingTicket(userId, numberFlight);
        System.out.print("Press enter to return to the previous menu ...");
        Input.inputString();
    }

    private static void ticketCancellation(Users users, int userId) {
    }

    private static void bookedTickets(Users users, int userId) {
        System.out.printf("|%-12s|%-12s|%-12s|%-12s|%-12s|%-12s|%-12s|\n%s\n"
                , "FlightId"
                , "Origin"
                , "Destination"
                , "Date"
                , "Time"
                , "Price"
                , "Ticket Id"
                , "............................................................................................"
        );
        for (int i = 0; i < users.customers[userId].getNumberTickets(); i++) {
            if (users.customers[userId].getTickets()[i] != null) {
                System.out.printf("|%-12s|%-12s|%-12s|%-12s|%-12s|%-12s|%-12s|\n%-12s\n"
                        , users.customers[userId].getTickets()[i].getFlightId()
                        , users.customers[userId].getTickets()[i].getOrigin()
                        , users.customers[userId].getTickets()[i].getDestination()
                        , users.customers[userId].getTickets()[i].getDate()
                        , users.customers[userId].getTickets()[i].getTime()
                        , users.customers[userId].getTickets()[i].getPrice()
                        , users.customers[userId].getTickets()[i].getTicketId()
                        , "............................................................................................"
                );
            }
        }
        System.out.print("Press enter to return to the previous menu ...");
        Input.inputString();
    }

    private static void addCharge(Users users, int userId) {
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                   Add charge                  "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* price : "
        );

        users.customers[userId].setCharge(users.customers[userId].getCharge() + Input.inputIntegerNotNull());

        System.out.println("charge : " + users.customers[userId].getCharge());
        System.out.print("Press enter to return to the previous menu ...");
        Input.inputString();
    }
}
