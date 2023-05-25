import java.io.IOException;
import java.io.RandomAccessFile;
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
    public static String inputIntegerNotNullToString() {
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
        return input;
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
                input = inputString().toUpperCase();
            }
        }
        return input;
    }

    public static String inputInSignIn() throws IOException {
        RandomAccessFile file = new RandomAccessFile("fileUsers.dat", "rw");
        FileUsers fileUsers = new FileUsers(file);
        String username = inputString();
        System.out.print("* Password : ");
        String password = inputString();
        String userId = fileUsers.findUsername(username);
        switch (userId) {
            case "admin" -> {
                file.seek(fileUsers.FIX_SIZE * 2);
                if (password.equals(fileUsers.readFixString())) {
                    file.close();
                    return "admin";
                }
            }
            case "-1" -> {
                file.close();
                return "-1";
            }
            default -> {
                file.seek(Integer.valueOf(userId) * fileUsers.RECORD_LENGTH + fileUsers.FIX_SIZE * 2);
                if (password.equals(fileUsers.readFixString())) {
                    file.close();
                    return userId;
                }
            }
        }
        file.close();
        return "-1";
    }

    public String inputInSignUp() throws IOException {
        RandomAccessFile file = new RandomAccessFile("fileUsers.dat", "rw");
        FileUsers fileUsers = new FileUsers(file);
        String username;
        while (true) {
            username = inputString();
            if (fileUsers.findUsername(username).equals("-1")) {
                break;
            } else {
                System.out.println("please change your username");
            }
        }
        file.close();
        return username;
    }

    public String inputAdminMenu() {
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
        Flight flight = new Flight(inputFlightIdNotNull(), null, null, null, null, 0, 0, 0);
        String origin = inputOriginNotNull();
        String destination = inputDestinationNotNull();
        while (true) {
            if (!origin.equals(destination))
                break;
            destination = inputStringNotNull();
        }
        flight.setOrigin(origin);
        flight.setDestination(destination);
        System.out.print("* Date \n");
        flight.setDateFlight(inputDateNotNull());
        System.out.print("* Time \n");
        flight.setTimeFlight(inputTimeNotNull());
        System.out.print("* Price : ");
        flight.setPrice(Integer.valueOf(inputIntegerNotNullToString()));
        System.out.print("* Seats : ");
        int seats = Integer.parseInt(inputIntegerNotNullToString());
        flight.setSeats(seats);
        flight.setCapacity(seats);
        return flight;
    }

    private static TimeFlight inputTimeNotNull() {
        String hours;
        String minutes;
        System.out.print("* hours : ");
        while (true) {
            hours = inputIntegerNotNullToString();
            if (hours.length() < 3 && Integer.parseInt(hours) < 24) {
                break;
            }
        }
        System.out.print("* Minutes : ");
        while (true) {
            minutes = inputIntegerNotNullToString();
            if (minutes.length() < 3 && Integer.parseInt(minutes) < 60) {
                break;
            }
        }
        TimeFlight time = new TimeFlight(hours, minutes);
        return time;
    }

    private static DateFlight inputDateNotNull() {
        String year;
        String month;
        String day;
        System.out.print("* year : ");
        while (true) {
            year = inputIntegerNotNullToString();
            if (year.length() < 5) {
                break;
            }
        }
        System.out.print("* month : ");
        while (true) {
            month = inputIntegerNotNullToString();
            if (month.length() < 3) {
                break;
            }
        }
        System.out.print("* day : ");
        while (true) {
            day = inputIntegerNotNullToString();
            if (day.length() < 3) {
                break;
            }
        }
        DateFlight date = new DateFlight(year, month, day);
        return date;
    }

    public static Flight inputForUpdateFlight() {
        Flight flight = new Flight(inputFlightId(), inputOrigin(), inputDestination(), null, null, 0, 0, 0);

        System.out.print("* Date \n");
        flight.setDateFlight(inputDate());
        System.out.print("* Time \n");
        flight.setTimeFlight(inputTime());
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
        date.setDay(String.valueOf(inputInteger()));
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

    public void inputForChangePassword(int userId) throws IOException {
        RandomAccessFile file = new RandomAccessFile("fileUsers.dat", "rw");
        FileUsers fileUsers = new FileUsers(file);
        file.seek(userId * fileUsers.RECORD_LENGTH + fileUsers.FIX_SIZE * 2);
        System.out.print("* Current Password : ");
        if (!inputString().equals(fileUsers.readFixString())) {
            System.out.println("Please check your password:(");
            file.close();
            return;
        }
        System.out.print("* New Password : ");
        String newPassword = inputString();
        System.out.print("* Confirm Password : ");
        String confirmPassword;
        while (true) {
            confirmPassword = inputString();
            if (newPassword.equals(confirmPassword)) {
                file.seek(userId * fileUsers.RECORD_LENGTH + fileUsers.FIX_SIZE * 2);
                fileUsers.writeString(newPassword);
                System.out.println("successful ...");
                file.close();
                return;
            } else {
                System.out.println("The confirm password is false please check it :(");
            }
        }
    }

    public static String inputFlightId() {
        System.out.print("* Flight Id : ");
        return inputString();
    }

    public static String inputFlightIdNotNull() {
        System.out.print("* Flight Id : ");
        return inputStringNotNull();
    }

    public static String inputOrigin() {
        System.out.print("* Origin : ");
        return inputString().toLowerCase();
    }

    public static String inputOriginNotNull() {
        System.out.print("* Origin : ");
        return inputStringNotNull().toLowerCase();
    }

    public static String inputDestination() {
        System.out.print("* Destination : ");
        return inputString().toLowerCase();
    }

    public static String inputDestinationNotNull() {
        System.out.print("* Destination : ");
        return inputStringNotNull().toLowerCase();
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
        date.setMonth(String.valueOf(inputIntegerNotNullToString()));
        System.out.print("* day : ");
        date.setDay(String.valueOf(inputIntegerNotNullToString()));
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
        time.setMinutes(String.valueOf(inputIntegerNotNullToString()));
        return time;
    }

    public static int inputPrice() {
        System.out.print("* Price : ");
        return inputInteger();
    }
}
