import java.util.Scanner;
//Check input if it was true return else print Check message
public class Input {
    /**
     * @return String
     */
    public static String inputString() {
        Scanner input0 = new Scanner(System.in);
        return input0.nextLine();
    }

    /**
     * This method input String line and check it was true
     *
     * @return string
     */
    public static String inputInStartMenu() {
        String input = inputString().toUpperCase();
        while (true) {
            if (input.equals("1") || input.equals("2")
                    || input.equals("SIGN IN") || input.equals("SIGN UP")) {
                break;
            } else {
                System.out.println("Please check your command :(");
                input = inputString();
            }
        }
        return input;
    }
    public static String inputInSignIn(Users users){
        String username = inputString();
        System.out.print("* Password : ");
        String password = inputString();
        String userId = users.findUsername(username);
        switch (userId){
            case "admin":
                if (password.equals(users.admin.getPassword()))
                    return "admin";
                break;
            case "-1":
                return "-1";
            default:
                if (password.equals(users.customers[Integer.parseInt(userId)].getPassword()))
                    return userId;
                break;
        }
        return "-1";
    }
    public static String inputInSignUp(Users users){
        String username;
        while (true){
            username = Input.inputString();
            if (username.equals(users.findUsername(username))){
                System.out.println("please change your username");
            }
            else {
                break;
            }
        }
        return username;
    }
}
