import java.io.IOException;
import java.io.RandomAccessFile;

//username 30
//password 30
//charge 4
public class FileUsers extends File {
    public static final int RECORD_LENGTH = 64;

    public FileUsers(RandomAccessFile file) {
        super(file);
    }

    /**
     * found username in fileUsers and return userId
     * param username
     * throws IOException
     */
    public String findUsername(String username) throws IOException {
        file.seek(0);
        if (username.equals(readFixString())) {
            return "admin";
        } else {
            for (int i = 0; i < file.length() / RECORD_LENGTH; i++) {
                file.seek(i * RECORD_LENGTH);
                if (readFixString().equals(username)) {
                    return String.valueOf(i);
                }
            }
        }
        return "-1";
    }
}
