import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Menu {

    public static void startMenu() throws IOException {
        Menu menu = new Menu();
        String input;
        while (true) {
            clearScreen();
            menu.printMenu();
            input = Input.inputInStartMenu();
            clearScreen();
            switch (input) {
                case "1", "SIGN IN":
                    menu.signIn();
                    break;
                case "2", "SIGN UP":
                    menu.signUp();
                    break;
            }
        }
    }

    /**
     * print start menu
     */
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

    /**
     * sign Menu
     */
    private void signIn() throws IOException {
        clearScreen();
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                    Log in                     "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* username : "
        );
        String userId = Input.inputInSignIn();
        switch (userId) {
            case "admin":
                adminMenu();
                break;
            case "-1":
                System.out.println("""
                        Please check your password or username :(
                        if you haven't registered yet, you can register in the sign up section :)
                        Press enter to return to the previous menu""");
                Input.inputString();
                break;
            default:
                userMenu(Integer.valueOf(userId));
        }
    }

    /**
     * sign in for user
     */
    private void signUp() throws IOException {
        clearScreen();
        RandomAccessFile file = new RandomAccessFile("fileUsers.dat", "rw");
        FileUsers fileUsers = new FileUsers(file);
        Input input = new Input();
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                    Sign up                    "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* username : "
        );
        String username = input.inputInSignUp();
        System.out.print("* password : ");
        file.seek(file.length());
        fileUsers.writeString(username);
        fileUsers.writeString(Input.inputStringNotNull());
        file.writeInt(0);
        System.out.println("successful ...");
//        System.out.println(username);
        pauseInputEnter();
        file.close();
    }

    /**
     * admin Menu
     * throws IOException
     */
    private static void adminMenu() throws IOException {
        Input input = new Input();
        Admin admin = new Admin(null, null, null);
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
                    addFlightMenu();
                    break;
                case "2", "UPDATE":
                    updateFlightMenu();
                    break;
                case "3", "REMOVE":
                    removeFlight();
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

    /**
     * print remove flight and call admin.removeFlight(int numberFlight)
     * throws IOException
     */
    private static void removeFlight() throws IOException {
        clearScreen();
        RandomAccessFile file = new RandomAccessFile("fileFlights.dat", "rw");
        FileFlight fileFlight = new FileFlight(file);
        Admin admin = new Admin(null, null, null);
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                   remove                      "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* FlightId : "
        );
        int numberFlight = Admin.findFlightId(Input.inputString());
        if (numberFlight == -1) {
            System.out.println("Please check flight id :(");
            pauseInputEnter();
            file.close();
            return;
        }
        file.seek(numberFlight * FileFlight.RECORD_LENGTH + fileFlight.FIX_SIZE * 10 + 4);
        if (file.readInt() != file.readInt()) {
            System.out.println("You can't remove this flight because it is reserved by the user :(");
            pauseInputEnter();
            file.close();
            return;
        }
        admin.removeFlight(numberFlight);
        System.out.println("successful :) ...\n Press enter to return to the previous menu");
        Input.inputString();
        file.close();
    }

    /**
     * update flight for admin and call some method in admin and input class
     * throws IOException
     */
    private static void updateFlightMenu() throws IOException {
        clearScreen();
        RandomAccessFile file = new RandomAccessFile("fileFlights.dat", "rw");
        FileFlight fileFlight = new FileFlight(file);
        Admin admin = new Admin(null, null, null);
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                   update                      "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* Flight Id : "
        );
        int numberFlight = Admin.findFlightId(Input.inputString());
        if (numberFlight == -1) {
            System.out.println("Please check flight id :(");
            pauseInputEnter();
            file.close();
            return;
        }
        file.seek(numberFlight * FileFlight.RECORD_LENGTH + fileFlight.FIX_SIZE * 10 + 4);
        if (file.readInt() != file.readInt()) {
            System.out.println("You can't update this flight because it is reserved by the user :(");
            pauseInputEnter();
            file.close();
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
            file.close();
            return;
        }
        admin.updateFlight(Input.inputForUpdateFlight(), numberFlight);
        System.out.println("successful :) ...");
        pauseInputEnter();
        file.close();
    }

    /**
     * add flight for admin and call some method in admin and input class
     */
    private static void addFlightMenu() {
        clearScreen();
        Admin admin = new Admin(null, null, null);
        System.out.printf("%s\n%s\n%s\n"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                     Add                       "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
        );
        admin.addFlight(Input.inputForAddFlight());
        pauseInputEnter();
    }

    /**
     * user Menu
     * To increase the speed and because of the high need, give a userId pass
     */
    private static void userMenu(int userId) throws IOException {
        Menu menu = new Menu();
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
                    changePassword(userId);
                    break;
                case "2":
                    searchFlight();
                    break;
                case "3":
                    menu.bookingTicket(userId);
                    break;
                case "4":
                    menu.ticketCancellation(userId);
                    break;
                case "5":
                    menu.bookedTickets(userId);
                    break;
                case "6":
                    menu.addCharge(userId);
                    break;
                case "0":
                    flag = false;
                    break;
            }
        }
    }

    /**
     * change password in user Menu and call input.inputForChangePassword(int userId)
     */
    private static void changePassword(int userId) throws IOException {
        clearScreen();
        System.out.printf("%s\n%s\n%s\n"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "               change password                 "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
        );
        Input input = new Input();
        input.inputForChangePassword(userId);
        pauseInputEnter();
    }

    /**
     * اگر خط هایی را که روبروی آن نوشته شده comment// را کامنت کنید آنگاه در سرچ براساس فیلدی که یافت می‌شود سرچ می‌شود
     * و نیافتن یک فیلد باعث نمی‌شود که به Not found بر بخورید
     * مثلا وقتی مقصد را تهران وارد کنید
     * در حالی که پروازی با مقصد تهران وجود نداشته باشد ولی بقیه فیلد های پر شده موجود باشد
     * فیلدی که موجود نیست (مقصد) درنظر گرفته نمی‌شود و سرچ بر اساس فیلد های موجود انجام می‌شود
     * پس توصیه میشود که خط کد هایی که با comment// مشخص شده اند را کامنت کنید
     * تا جستجو حتی وقتی فیلدی یافت نشد نتیجه داشته باشد
     * throws IOException
     */
    private static void searchFlight() throws IOException {
        clearScreen();
        System.out.printf("%s\n%s\n%s\n%s\n"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                Search flight                  "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "Press enter if you want to skip the field , otherwise type the desired word in front of it"
        );
        boolean wasFound = true;
        Admin admin = new Admin(null, null, null);
        ArrayList<Integer> arraySimilarFlights = new ArrayList<>();
        ArrayList<Integer> arrayFlights;
        Flight flight = new Flight(Input.inputFlightId(), Input.inputOrigin(), Input.inputDestination(), null, null, 0, 0, 0);
        System.out.println("(Sine)");
        flight.setDateFlight(Input.inputDate());
        DateFlight upToDate = null;
        if (flight.getDateFlight().getYear() != null) {// چک می‌شود اگر از زمان را کاربر پر نکرده باشد تا زمان را از او نگیرد
            System.out.println("(Until)");
            while (true) {
                upToDate = Input.inputDate();
                if (upToDate.getYear() != null)
                    break;
                System.out.println("You should fill this section :) ---> ");
            }
        }
        System.out.println("(Sine)");
        flight.setTimeFlight(Input.inputTime());
        TimeFlight upToTime = null;
        if (flight.getTimeFlight().getHours() != null) {
            System.out.println("(Until)");
            while (true) {
                upToTime = Input.inputTime();
                if (upToTime.getHours() != null)
                    break;
                System.out.println("You should fill this section :) ---> ");
            }
        }
        System.out.println("(From)");
        flight.setPrice(Input.inputPrice());
        int upToPrice = 0;
        if (flight.getPrice() != -1) {
            System.out.println("(up to)");
            upToPrice = Input.inputPrice();
        }
        //  در هر if بررسی می‌شود که آیا همچین فیلدی وجود دارد و سپس با فیلد های قبل مقایسه می‌شود
        /*اگر خط هایی را که روبروی آن نوشته شده comment را کامنت کنید آنگاه در سرچ براساس فیلدی که یافت می‌شود سرچ می‌شود
        و نیافتن یک فیلد باعث نمی‌شود که به Not found بر بخورید
        مثلا وقتی مقصد را تهران وارد کنید
        در حالی که پروازی با مقصد تهران وجود نداشته باشد ولی بقیه فیلد های پر شده موجود باشد
       فیلدی که موجود نیست (مقصد) درنظر گرفته نمی‌شود و سرچ بر اساس فیلد های موجود انجام می‌شود
       پس توصیه میشود که خط کد هایی که با comment// مشخص شده اند را کامنت کنید
     تا جستجو حتی وقتی فیلدی یافت نشد نتیجه داشته باشد
         */
        if (!flight.getFlightId().equals("")) {
            int flightNumber = admin.findFlightId(flight.getFlightId());
            if (flightNumber != -1) {
                arraySimilarFlights.add(flightNumber);
            } else wasFound = false;//comment
        }
        if (!flight.getOrigin().equals("")) {
            arrayFlights = admin.findOriginSimilar(flight.getOrigin());
            if (arrayFlights.size() == 0) wasFound = false;//comment
            arraySimilarFlights = findSimilarHomesTwoArray(arrayFlights, arraySimilarFlights);
        }
        if (!flight.getDestination().equals("")) {
            arrayFlights = admin.findDestinationSimilar(flight.getDestination());
            if (arrayFlights.size() == 0) wasFound = false;//comment
            arraySimilarFlights = findSimilarHomesTwoArray(arrayFlights, arraySimilarFlights);
        }
        if (flight.getDateFlight().getYear() != null && !flight.getDateFlight().equals("")) {
            arrayFlights = admin.findDateSimilar(flight.getDateFlight(), upToDate);
            if (arrayFlights.size() == 0) wasFound = false;//comment
            arraySimilarFlights = findSimilarHomesTwoArray(arrayFlights, arraySimilarFlights);
        }
        if (flight.getTimeFlight().getHours() != null && !flight.getTimeFlight().equals("")) {
            arrayFlights = admin.findTimeSimilar(flight.getTimeFlight(), upToTime);
            if (arrayFlights.size() == 0) wasFound = false;//comment
            arraySimilarFlights = findSimilarHomesTwoArray(arrayFlights, arraySimilarFlights);
        }
        if (flight.getPrice() != -1) {
            arrayFlights = admin.findPriceSimilar(flight.getPrice(), upToPrice);
            if (arrayFlights.size() == 0) wasFound = false;//comment
            arraySimilarFlights = findSimilarHomesTwoArray(arrayFlights, arraySimilarFlights);
        }
        if (arraySimilarFlights.size() == 0) wasFound = false;
        if (wasFound) {
            admin.printFlightForSearch(arraySimilarFlights);
            pauseInputEnter();
        } else {
            System.out.println("Not Found ... !!!:(\nPlease change your search fields :)");
            pauseInputEnter();
        }
    }

    /**
     * این تابع برای مقایسه دو آرایه است که اگر در سرچ چند فیلد فیلتر شده باشد را مقایسه می‌کند
     * param arrayFlights
     * param arraySimilarFlights
     * return لیستی از پرواز های با فیلد مشابه
     */
    private static ArrayList<Integer> findSimilarHomesTwoArray(ArrayList<Integer> arrayFlights, ArrayList<Integer> arraySimilarFlights) {
        ArrayList<Integer> arraySharingArrays = new ArrayList<>();
        if (arraySimilarFlights.size() == 0) {
            return arrayFlights;
        }
        for (int i = 0; i < arraySimilarFlights.size(); i++) {
            for (int j = 0; j < arrayFlights.size(); j++) {
                if (arraySimilarFlights.get(i) == arrayFlights.get(j))
                    arraySharingArrays.add(arrayFlights.get(j));
            }
        }
        return arraySharingArrays;
    }

    public static void printFlights(Flight[] flights) {
        clearScreen();
        for (Flight flight : flights) {
            if (flight != null) {
                System.out.printf("|%-12s|%s%-11s|%s%-11s|%-12s|%-12s|%,-12d|%-12s|\n%-12s\n"
                        , flight.getFlightId()
                        , flight.getOrigin().substring(0, 1).toUpperCase()
                        , flight.getOrigin().substring(1)
                        , flight.getDestination().substring(0, 1).toUpperCase()
                        , flight.getDestination().substring(1)
                        , flight.getDateFlight().toString()
                        , flight.getTimeFlight().toString()
                        , flight.getPrice()
                        , flight.getSeats()
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

    private void bookingTicket(int userId) throws IOException {
        RandomAccessFile usersFile = new RandomAccessFile("fileUsers.dat", "rw");
        RandomAccessFile flightFile = new RandomAccessFile("fileFlights.dat", "rw");
        clearScreen();
        Users users = new Users(null, null);
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
            usersFile.close();
            flightFile.close();
            return;
        }
        flightFile.seek(numberFlight * FileFlight.RECORD_LENGTH + 150);
        usersFile.seek(userId * FileUsers.RECORD_LENGTH + 60);
        if (flightFile.readInt() > usersFile.readInt()) {
            System.out.println("Please check your charge ...");
            pauseInputEnter();
            usersFile.close();
            flightFile.close();
            return;
        }

        flightFile.seek(numberFlight * FileFlight.RECORD_LENGTH + 154);
        if (flightFile.readInt() <= 0) {
            System.out.println("It has no capacity");
            pauseInputEnter();
            usersFile.close();
            flightFile.close();
            return;
        }
        users.bookingTicket(userId, numberFlight);
        pauseInputEnter();
        usersFile.close();
        flightFile.close();
    }

    /**
     * for cancel a ticket shift some records in fileTickets
     * param userId
     * throws IOException
     */
    private void ticketCancellation(int userId) throws IOException {
        clearScreen();
        User user = new User(null, null, 0, null, 0, 0);
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "              Ticket cancellation              "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* Ticket Id : "
        );
        String ticketId = Input.inputStringNotNull();
        int numberTicket = user.findTicketId(ticketId);
        if (numberTicket == -1) {
            System.out.println("Please check Ticket Id ...");
        } else {
            user.ticketCancellation(userId, numberTicket);
        }
        pauseInputEnter();
    }

    /**
     * print tickets of user with userId
     * param userId
     * throws IOException
     */
    private void bookedTickets(int userId) throws IOException {
        clearScreen();

        RandomAccessFile usersFile = new RandomAccessFile("fileUsers.dat", "rw");
        FileUsers fileUsers = new FileUsers(usersFile);
        usersFile.seek(userId * FileUsers.RECORD_LENGTH);

        ArrayList<Ticket> tickets = arrayOfTickets(fileUsers.readFixString());

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
        for (Ticket ticket : tickets) {
            System.out.printf("|%-12s|%s%-11s|%s%-11s|%-12s|%-12s|%-12s|%-12s|\n%-12s\n"
                    , ticket.getFlightId()
                    , ticket.getOrigin().substring(0, 1).toUpperCase()
                    , ticket.getOrigin().substring(1)
                    , ticket.getDestination().substring(0, 1).toUpperCase()
                    , ticket.getDestination().substring(1)
                    , ticket.getDate()
                    , ticket.getTime()
                    , ticket.getPrice()
                    , ticket.getTicketId()
                    , "............................................................................................"
            );
        }
        pauseInputEnter();
    }

    /**
     * param username
     * return list of tickets of a user with param username
     * throws IOException
     */
    private ArrayList<Ticket> arrayOfTickets(String username) throws IOException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        RandomAccessFile ticketsFile = new RandomAccessFile("fileTickets.dat", "rw");
        RandomAccessFile flightsFile = new RandomAccessFile("fileFlights.dat", "rw");
        FileTickets fileTickets = new FileTickets(ticketsFile);
        FileFlight fileFlight = new FileFlight(flightsFile);
//        Admin admin = new Admin(null,null,null);
        Flight flight = null;
        for (int i = 0; i < ticketsFile.length() / FileTickets.RECORD_LENGTH; i++) {
            ticketsFile.seek(i * FileTickets.RECORD_LENGTH);
            if (fileTickets.readFixString().equals(username)) {
                flightsFile.seek(Admin.findFlightId(fileTickets.readFixString()) * FileFlight.RECORD_LENGTH);
                flight = fileFlight.read();
                tickets.add(new Ticket(flight.getFlightId(), flight.getOrigin(), flight.getDestination()
                        , flight.getDateFlight(), flight.getTimeFlight(), flight.getPrice(), fileTickets.readFixString()));
            }
        }
        return tickets;
    }

    /**
     * add charge and print charge
     * param userId
     * throws IOException
     */
    private void addCharge(int userId) throws IOException {
        clearScreen();
        RandomAccessFile file = new RandomAccessFile("fileUsers.dat", "rw");
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                   Add charge                  "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* price : "
        );
        file.seek(userId * FileUsers.RECORD_LENGTH + 60);
        int charge = file.readInt() + Integer.parseInt(Input.inputIntegerNotNullToString());
//        System.out.println(charge + "\n" + userId);
        file.seek(userId * FileUsers.RECORD_LENGTH + 60);
        file.writeInt(charge);
        System.out.println("charge : " + charge);
        pauseInputEnter();
        file.close();
    }

    /**
     * for clean screen it Runs only in the terminal
     */
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");//This is the command to clear the screen
    }

    /**
     * Pause so the user can view the output
     */
    private static void pauseInputEnter() {
        System.out.print("Press enter to return to the previous menu ...");
        Input.inputString();
    }
}
