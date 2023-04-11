import java.util.Scanner;

public class Menu {

    public static void startMenu() {
        String input;
        while (true) {
            System.out.print("\033[H\033[2J");//This is the command to clear the screen
            printMenu();
            input = inputString();
            System.out.print("\033[H\033[2J");//This is the command to clear the screen
            while (true) {
                if (input.toUpperCase().equals("1") || input.toUpperCase().equals("2")
                        || input.toUpperCase().equals("SIGN IN") || input.toUpperCase().equals("SIGN UP")) {
                    break;
                } else {
                    System.out.println("Please check your command :(");
                    input = inputString();
                }
            }
            switch (input.toUpperCase()) {
                case "1", "SIGN IN":
                    signIn();
                    break;
                case "2", "SIGN UP":
                    signUp();
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

    private static String inputString() {
        Scanner input0 = new Scanner(System.in);
        return input0.nextLine();
    }

    private static void signIn() {
        System.out.println("Sign in ...");
    }

    private static void signUp() {
        System.out.println("Sign up ...");
    }

}
