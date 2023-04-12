import java.util.Date;

public class Menu {

    public static void startMenu() {
        Flight[] flights = new Flight[100];
        flights[0] = new Flight("WX-12", "Yazd", "Tehran", new Date(1401, 12, 10)
                , new TimeFlight(12, 30), 700000, 51, 51);
        flights[1] = new Flight("WZ-15", "Mashhad", "Ahvaz", new Date(1401, 12, 11)
                , new TimeFlight(8, 0), 900000, 245, 245);
        flights[2] = new Flight("BG-22", "Shiraz", "Tabriz", new Date(1401, 12, 12)
                , new TimeFlight(22, 30), 1100000, 12, 12);
        Admin admin = new Admin("Admin", "Admin", flights);
        Users users = new Users(new User[1000], admin);
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
                Menu.userMenu(users);
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
        String input;
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
                    admin.addFlight();
                    break;
                case "2", "UPDATE":
                    admin.updateFlight();
                    break;
                case "3", "REMOVE":
                    admin.removeFlight();
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

    private static void userMenu(Users users) {
        System.out.println("user menu");
    }
}
