import java.util.ArrayList;

public class Menu {

    public static void startMenu(Users users) {
        Menu menu = new Menu();
        String input;
        while (true) {
            clearScreen();
            menu.printMenu();
            input = Input.inputInStartMenu();
            clearScreen();
            switch (input) {
                case "1", "SIGN IN":
                    menu.signIn(users);
                    break;
                case "2", "SIGN UP":
                    menu.signUp(users);
                    break;
            }
        }
    }

    private void printMenu() {
        System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "    WELCOME TO AIRLINE RESERVATION SYSTEM      "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , " ................MENU OPTIONS................. "
                , "    <1> Sign in"
                , "    <2> Sign up"
        );
    }

    private void signIn(Users users) {
        clearScreen();
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
     */
    private void signUp(Users users) {
        clearScreen();
        Input input = new Input();
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                    Sign up                    "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* username : "
        );
        String username = input.inputInSignUp(users);
        for (int i = 0; i < users.customers.length; i++) {
            if (users.customers[i] == null) {
                Ticket[] tickets = new Ticket[100];
                users.customers[i] = new User(username, "0", 0, tickets, 0);
                System.out.print("* password : ");
                users.customers[i].setPassword(Input.inputString());
                break;
            }
        }
        System.out.println("successful ...");
        pauseInputEnter();
    }

    private static void adminMenu(Admin admin) {
        Input input = new Input();
        boolean flag = true;
        while (flag) {
            clearScreen();
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

            switch (input.inputAdminMenu()) {
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
                    pauseInputEnter();
                    break;
                case "0", "SIGN OUT":
                    flag = false;
                    break;
            }
        }
    }

    private static void removeFlight(Admin admin) {
        clearScreen();
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                   remove                      "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* FlightId : "
        );
        int numberFlight = admin.findFlightId(Input.inputString());
        if (numberFlight == -1) {
            System.out.println("Please check flight id :(");
            pauseInputEnter();
            return;
        }
        if (admin.getFlights()[numberFlight].getSeats() != admin.getFlights()[numberFlight].getCapacity()) {
            System.out.println("You can't remove this flight because it is reserved by the user :(");
            pauseInputEnter();
            return;
        }
        admin.removeFlight(numberFlight);
        System.out.println("successful :) ...\n Press enter to return to the previous menu");
        Input.inputString();
    }

    private static void updateFlightMenu(Admin admin) {
        clearScreen();
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                   update                      "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* Flight Id : "
        );
        int numberFlight = admin.findFlightId(Input.inputString());
        if (numberFlight == -1) {
            System.out.println("Please check flight id :(");
            pauseInputEnter();
            return;
        }
        if (admin.getFlights()[numberFlight].getSeats() != admin.getFlights()[numberFlight].getCapacity()) {
            System.out.println("You can't update this flight because it is reserved by the user :(");
            pauseInputEnter();
            return;
        }
        //can remove in update
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
        if (input.equals("1") || input.equals("YES")) {//can remove in update
            admin.removeFlight(numberFlight);
            pauseInputEnter();
            return;
        }
        admin.updateFlight(Input.inputForUpdateFlight(), numberFlight);
        System.out.println("successful :) ...");
        pauseInputEnter();
    }

    private static void addFlightMenu(Admin admin) {
        clearScreen();
        System.out.printf("%s\n%s\n%s\n"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                     Add                       "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
        );
        admin.addFlight(Input.inputForAddFlight());
        pauseInputEnter();
    }

    private static void userMenu(Users users, int userId) {
        boolean flag = true;
        while (flag) {
            clearScreen();
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
        clearScreen();
        System.out.printf("%s\n%s\n%s\n"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "               change password                 "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
        );
        users.customers[userId].setPassword(Input.inputForChangePassword(users, userId));
        pauseInputEnter();
    }

    private static void searchFlight(Admin admin) {
        clearScreen();
        System.out.printf("%s\n%s\n%s\n%s\n"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                Search flight                  "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                ,"Press enter if you want to skip the field , otherwise type the desired word in front of it"
        );
        ArrayList<Integer> arraySimilarFlights = new ArrayList<>();
        ArrayList<Integer> arrayFlights = new ArrayList<>();
        Flight flight = new Flight(Input.inputFlightId(), Input.inputOrigin(), Input.inputDestination(), null, null, 0, 0, 0);
        System.out.println("(Sine)");
        flight.setDateFlight(Input.inputDateForSearch());
        DateFlight upToDate = null;
        if (flight.getDateFlight().getYear() != null) {
            System.out.println("(Until)");
            while (true){
                upToDate = Input.inputDateForSearch();
                if (upToDate.getYear()!=null)
                    break;
            }
        }
        System.out.println("(Sine)");
        flight.setTimeFlight(Input.inputTimeForSearch());
        TimeFlight upToTime = null;
        if (flight.getTimeFlight().getHours() != null) {
            System.out.println("(Until)");
            while (true){
                upToTime = Input.inputTimeForSearch();
                if (upToTime.getHours()!=null)
                    break;
            }
        }
        System.out.println("(From)");
        flight.setPrice(Input.inputPrice());
        int upToPrice = 0;
        if (flight.getPrice() != -1) {
            System.out.println("(up to)");
            upToPrice = Input.inputPrice();
        }

        if (!flight.getFlightId().equals("")) {
            int flightNumber=admin.findFlightId(flight.getFlightId());
            if (flightNumber!=-1){
                arraySimilarFlights.add(flightNumber);
            }
        }
        if (!flight.getOrigin().equals("")) {
            arrayFlights = admin.findOriginSimilar(flight.getOrigin());
            arraySimilarFlights = findSimilarHomesTwoArray(arrayFlights, arraySimilarFlights);
        }
        if (!flight.getDestination().equals("")) {
            arrayFlights = admin.findDestinationSimilar(flight.getDestination());
            arraySimilarFlights = findSimilarHomesTwoArray(arrayFlights, arraySimilarFlights);
        }
        if (flight.getDateFlight().getYear() != null && !flight.getDateFlight().equals("")) {
            arrayFlights = admin.findDateSimilar(flight.getDateFlight(), upToDate);
            arraySimilarFlights = findSimilarHomesTwoArray(arrayFlights, arraySimilarFlights);
        }
        if (flight.getTimeFlight().getHours() != null && !flight.getTimeFlight().equals("")) {
            arrayFlights = admin.findTimeSimilar(flight.getTimeFlight(), upToTime);
            arraySimilarFlights = findSimilarHomesTwoArray(arrayFlights, arraySimilarFlights);
        }
        if (flight.getPrice() != -1) {
            arrayFlights = admin.findPriceSimilar(flight.getPrice(), upToPrice);
            arraySimilarFlights = findSimilarHomesTwoArray(arrayFlights, arraySimilarFlights);
        }
        admin.printFlightForSearch(arraySimilarFlights);
        pauseInputEnter();
    }

    private static ArrayList<Integer> findSimilarHomesTwoArray(ArrayList<Integer> arrayFlights, ArrayList<Integer> arraySimilarFlights) {
        ArrayList<Integer> arraySharingArrays = new ArrayList<>();
        if (arraySimilarFlights.size() == 0) {
            arraySimilarFlights = arrayFlights;
        }
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
        clearScreen();
        for (int i = 0; i < arrayNumberFlight.size(); i++) {
            if (flights[arrayNumberFlight.get(i)] != null) {
                System.out.printf("|%-12s|%s%-11s|%s%-11s|%-12s|%-12s|%,-12d|%-12s|\n%-12s\n"
                        , flights[arrayNumberFlight.get(i)].getFlightId()
                        , flights[arrayNumberFlight.get(i)].getOrigin().substring(0, 1).toUpperCase()
                        , flights[arrayNumberFlight.get(i)].getOrigin().substring(1)
                        , flights[arrayNumberFlight.get(i)].getDestination().substring(0, 1).toUpperCase()
                        , flights[arrayNumberFlight.get(i)].getDestination().substring(1)
                        , flights[arrayNumberFlight.get(i)].getDateFlight().toString()
                        , flights[arrayNumberFlight.get(i)].getTimeFlight().toString()
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
        clearScreen();
        System.out.printf("%s\n%s\n%s\n"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                Booking ticket                 "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
        );
        String flightId = Input.inputFlightId();
        int numberFlight = users.admin.findFlightId(flightId);
        if (numberFlight == -1) {
            System.out.println("Please check Flight Id");
            pauseInputEnter();
            return;
        }

        if (users.admin.getFlights()[numberFlight].getPrice() > users.customers[userId].getCharge()) {
            System.out.println("Please check your charge ...");
            pauseInputEnter();
            return;
        }

        if (users.admin.getFlights()[numberFlight].getSeats() < 0) {
            System.out.println("It has no capacity");
            pauseInputEnter();
            return;
        }
        users.bookingTicket(userId, numberFlight);
        pauseInputEnter();
    }

    private static void ticketCancellation(Users users, int userId) {
        clearScreen();
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "              Ticket cancellation              "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* Ticket Id : "
        );
        int ticketId = Integer.parseInt(Input.inputIntegerNotNullToString());
        int numberTicket = users.customers[userId].findTicketId(ticketId);
        if (numberTicket == -1) {
            System.out.println("Please check Ticket Id ...");
        } else {
            users.customers[userId].ticketCancellation(users.admin, numberTicket);
        }
        pauseInputEnter();
    }

    private static void bookedTickets(Users users, int userId) {
        clearScreen();
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
        pauseInputEnter();
    }

    private static void addCharge(Users users, int userId) {
        clearScreen();
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                   Add charge                  "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* price : "
        );

        users.customers[userId].setCharge(users.customers[userId].getCharge() + Integer.parseInt(Input.inputIntegerNotNullToString()));

        System.out.println("charge : " + users.customers[userId].getCharge());
        pauseInputEnter();
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");//This is the command to clear the screen
    }

    private static void pauseInputEnter() {
        System.out.print("Press enter to return to the previous menu ...");
        Input.inputString();
    }
}
