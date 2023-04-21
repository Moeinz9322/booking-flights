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

    public static String inputStringNotNull() {
        String input;
        do {
            input = inputString();
        } while (input.equals(""));
        return input;
    }

    public static int inputInteger() {
        String input;
        char[] chars;
        boolean flag;
        do {
            flag = false;
            input = inputString();
            chars = input.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (!Character.isDigit(chars[i])) {
                    flag = true;
                }
            }
        } while (flag);
        if (input.equals(""))
            input = "-1";
        return Integer.parseInt(input);
    }

    //not input enter
    public static int inputIntegerNotNull() {
        String input;
        char[] chars;
        boolean flag;
        do {
            flag = false;
            input = inputString();
            chars = input.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (!Character.isDigit(chars[i])) {
                    flag = true;
                }
            }
        } while (input.equals("") || flag);
        return Integer.parseInt(input);
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

    public static String inputInSignIn(Users users) {
        String username = inputString();
        System.out.print("* Password : ");
        String password = inputString();
        String userId = users.findUsername(username);
        switch (userId) {
            case "admin" -> {
                if (password.equals(users.admin.getPassword()))
                    return "admin";
            }
            case "-1" -> {
                return "-1";
            }
            default -> {
                if (password.equals(users.customers[Integer.parseInt(userId)].getPassword()))
                    return userId;
            }
        }
        return "-1";
    }

    public static String inputInSignUp(Users users) {
        String username;
        while (true) {
            username = inputString();
            if (users.findUsername(username).equals("-1")) {
                break;
            } else {
                System.out.println("please change your username");
            }
        }
        return username;
    }

    public static String inputAdminMenu() {
        boolean flag = true;
        String input = null;
        while (flag) {
            input = inputString().toUpperCase();
            switch (input) {
                case "0", "1", "2", "3", "4", "ADD", "UPDATE", "REMOVE", "FLIGHT SCHEDULES", "SIGN OUT": {
                    flag = false;
                    break;
                }
                default:
                    System.out.println("Please check your command :(");
            }
        }
        return input;
    }

    public static Flight inputForAddFlight() {
        System.out.print("* Flight Id : ");
        Flight flight = new Flight(inputStringNotNull(), null, null, null, null, 0, 0, 0);
        System.out.print("* Origin : ");
        flight.setOrigin(inputStringNotNull());
        System.out.print("* Destination : ");
        flight.setDestination(inputStringNotNull());
        System.out.print("* Date \n");
        flight.setDate(inputDateNotNull());
        System.out.print("* Time \n");
        flight.setTime(inputTimeNotNull());
        System.out.print("* Price : ");
        flight.setPrice(inputIntegerNotNull());
        System.out.print("* Seats : ");
        int seats = inputIntegerNotNull();
        flight.setSeats(seats);
        flight.setCapacity(seats);
        return flight;
    }

    private static TimeFlight inputTimeNotNull() {
        System.out.print("* hours : ");
        TimeFlight time = new TimeFlight(String.valueOf(inputIntegerNotNull()), "0");
        System.out.print("* Minutes : ");
        time.setMinutes(String.valueOf(inputIntegerNotNull()));
        return time;
    }

    private static DateFlight inputDateNotNull() {
        System.out.print("* year : ");
        DateFlight date = new DateFlight(String.valueOf(inputIntegerNotNull()), "0", "0");
        System.out.print("* month : ");
        date.setMonth(String.valueOf(inputIntegerNotNull()));
        System.out.print("* day : ");
        date.setYear(String.valueOf(inputIntegerNotNull()));
        return date;
    }

    public static Flight inputForUpdateFlight() {
        System.out.print("* Flight Id : ");
        Flight flight = new Flight(inputString(), null, null, null, null, 0, 0, 0);
        System.out.print("* Origin : ");
        flight.setOrigin(inputString());
        System.out.print("* Destination : ");
        flight.setDestination(inputString());
        System.out.print("* Date \n");
        flight.setDate(inputDate());
        System.out.print("* Time \n");
        flight.setTime(inputTime());
        System.out.print("* Price : ");
        flight.setPrice(inputInteger());
        System.out.print("* Seats : ");
        int seats = inputInteger();
        flight.setSeats(seats);
        flight.setCapacity(seats);
        return flight;
    }

    private static TimeFlight inputTime() {
        System.out.print("* hours : ");
        TimeFlight time = new TimeFlight(String.valueOf(inputInteger()), "0");
        System.out.print("* Minutes : ");
        time.setMinutes(String.valueOf(inputInteger()));
        return time;
    }

    private static DateFlight inputDate() {
        System.out.print("* year : ");
        DateFlight date = new DateFlight(String.valueOf(inputInteger()), "0", "0");
        System.out.print("* month : ");
        date.setMonth(String.valueOf(inputInteger()));
        System.out.print("* day : ");
        date.setYear(String.valueOf(inputInteger()));
        return date;
    }

    public static String inputForUserMenu() {
        String input = null;
        boolean flag = true;
        while (flag) {
            input = inputString().toUpperCase();
            switch (input) {
                case "0", "1", "2", "3", "4", "5", "6", "CHANGE PASSWORD", "SEARCH FLIGHT TICKETS", "BOOKING TICKETS"
                        , "TICKET CANCELLATION", "BOOKED TICKET", "ADD CHARGE", "SIGN OUT": {
                    flag = false;
                    break;
                }
                default:
                    System.out.println("Please check your command :(");
                    break;
            }
        }
        return input;
    }

    public static String inputForChangePassword(Users users, int userId) {
        System.out.print("* Current Password : ");
        if (!inputString().equals(users.customers[userId].getPassword())) {
            System.out.println("Please check your password:(");
            return users.customers[userId].getPassword();
        }
        System.out.print("* New Password : ");
        String newPassword = inputString();
        System.out.print("* Confirm Password : ");
        String confirmPassword;
        while (true) {
            confirmPassword = inputString();
            if (newPassword.equals(confirmPassword)) {
                System.out.println("successful ...");
                return newPassword;
            } else {
                System.out.println("The confirm password is false please check it :(");
            }
        }
    }

    public static String inputFlightId() {
        System.out.print("* Flight Id : ");
        return inputString();
    }

    public static String inputOrigin() {
        System.out.print("* Origin : ");
        return inputString();
    }

    public static String inputDestination() {
        System.out.print("* Destination : ");
        return inputString();
    }

    public static DateFlight inputDateForSearch() {
        DateFlight date = new DateFlight(null, null, null);
        System.out.print("* year : ");
        int year = inputInteger();
        if (year == -1) {
            return date;
        }
        date.setYear(String.valueOf(year));
        System.out.print("* month : ");
        date.setMonth(String.valueOf(inputIntegerNotNull()));
        System.out.print("* day : ");
        date.setDay(String.valueOf(inputIntegerNotNull()));
        return date;
    }

    public static TimeFlight inputTimeForSearch() {
        TimeFlight time = new TimeFlight(null, null);
        System.out.print("* hours : ");
        int hours = inputInteger();
        if (hours == -1) {
            return time;
        }
        time.setHours(String.valueOf(hours));
        System.out.print("* Minutes : ");
        time.setMinutes(String.valueOf(inputIntegerNotNull()));
        return time;
    }

    public static int inputPrice() {
        System.out.print("* Price : ");
        return inputInteger();
    }
}
