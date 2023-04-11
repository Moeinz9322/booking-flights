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
        User[] users = new User[1000];
        Admin admin = new Admin("Admin", "Admin", flights);
        String input;
        while (true) {
            System.out.print("\033[H\033[2J");//This is the command to clear the screen
            printMenu();
            input = Input.inputInStartMenu();
            System.out.print("\033[H\033[2J");//This is the command to clear the screen
            switch (input) {
                case "1", "SIGN IN":
                    signIn();
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

    private static void signIn() {
        System.out.println("Sign in ...");
    }

    /**
     * sign in for user
     *
     * @param users
     */
    private static void signUp(User[] users) {
        System.out.printf("%s\n%s\n%s\n%s"
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "                    Sign up                    "
                , ":::::::::::::::::::::::::::::::::::::::::::::::"
                , "* username : "
        );
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                Ticket[] tickets = new Ticket[100];
                users[i] = new User(Input.inputInStartMenu(), "0", 0, tickets, 0);
                System.out.print("* password : ");
                users[i].setPassword(Input.inputString());
                break;
            }
        }
    }

}
