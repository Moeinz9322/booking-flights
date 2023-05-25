import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class File {
    RandomAccessFile file;
    public final int FIX_SIZE = 15;//size of string

    public File(RandomAccessFile file) {
        this.file = file;
    }

    /**
     * write a string in a file of child
     * param str
     * throws IOException
     */
    public void writeString(String str) throws IOException {
        file.writeChars(fixSizeToWrite(str));
    }

    /**
     * fix size So that we can read the file later
     * param str
     * return
     */
    public String fixSizeToWrite(String str) {
        while (str.length() < FIX_SIZE) {
            str += " ";
        }
        return str.substring(0, FIX_SIZE);
    }

    /**
     * read a string to the length FIX_SIZE
     * return  a string to the length FIX_SIZE
     * throws IOException
     */
    public String readFixString() throws IOException {
        String tmp = "";
        for (int i = 0; i < FIX_SIZE; i++) {
            tmp += file.readChar();
        }
        return tmp.trim();
    }

    /**
     * read date in a record
     * return DateFlight
     * throws IOException
     */
    public DateFlight readDate() throws IOException {
        String date = new String(readFixString());
        return new DateFlight(date.substring(0, 4), date.substring(5, 7), date.substring(8, 10));
    }

    /**
     * read time in a record
     * return  TimeFlight
     * throws IOException
     */
    public TimeFlight readTime() throws IOException {
        String date = new String(readFixString());
        return new TimeFlight(date.substring(0, 2), date.substring(3, 5));
    }
}
