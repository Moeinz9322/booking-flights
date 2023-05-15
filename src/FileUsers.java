import java.io.IOException;
import java.io.RandomAccessFile;

public class FileUsers extends File {
    public FileUsers(RandomAccessFile file) {
        super(file);
    }

    public String findUsername(String username) throws IOException {
        file.seek(0);
        if (username.equals(readFixString())) {
            return "admin";
        } else {
            for (int i = 0; i < file.length() / 60; i++) {
                file.seek(i * 60);
                if (readFixString().equals(username)) {
                    return String.valueOf(i);
                }
            }
        }
        return "-1";
    }
}
