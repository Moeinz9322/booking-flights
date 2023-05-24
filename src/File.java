import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class File {
    RandomAccessFile file;
    public final int FIX_SIZE = 15;

    public File(RandomAccessFile file) {
        this.file = file;
    }

    //    public void write() {
//
//    }
//    public void read(){
//
//    }
    public void writeString(String str) throws IOException {
        file.writeChars(fixSizeToWrite(str));
    }

    public String fixSizeToWrite(String str) {
        while (str.length() < FIX_SIZE) {
            str += " ";
        }
        return str.substring(0, FIX_SIZE);
    }

    public String readFixString() throws IOException {
        String tmp = "";
        for (int i = 0; i < FIX_SIZE; i++) {
            tmp += file.readChar();
        }
        return tmp.trim();
    }

    public DateFlight readDate() throws IOException {
        String date = new String(readFixString());
        return new DateFlight(date.substring(0, 4), date.substring(5, 7), date.substring(8, 10));
    }

    public TimeFlight readTime() throws IOException {
        String date = new String(readFixString());
        return new TimeFlight(date.substring(0, 2), date.substring(3, 5));
    }
}
