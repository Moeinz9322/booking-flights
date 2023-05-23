import java.io.IOException;
import java.io.RandomAccessFile;

public class FileTickets extends File{
    public static final int RECORD_LENGTH = 90;
    public FileTickets(RandomAccessFile file) {
        super(file);
    }
    public void write(String username , String flightId , String ticketId) throws IOException {
        file.writeChars(fixSizeToWrite(username));//30
        file.writeChars(fixSizeToWrite(flightId));//30
        file.writeChars(fixSizeToWrite(ticketId));//30
        //record length=90
    }
}
