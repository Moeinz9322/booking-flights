import java.util.Scanner;

public class Menu {

    public static void startMenu() {
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

    private static void signIn() {
        System.out.println("Sign in ...");
    }

    private static void signUp() {
        System.out.println("Sign up ...");
    }

}
