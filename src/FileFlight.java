import java.io.IOException;
import java.io.RandomAccessFile;

public class FileFlight extends File {
    public static final int RECORD_LENGTH = 162;

    public FileFlight(RandomAccessFile file) {
        super(file);
    }

    /**
     * write record of Flight
     * param flight
     * throws IOException
     */
    public void write(Flight flight) throws IOException {
        file.writeChars(fixSizeToWrite(flight.getFlightId()));//30
        file.writeChars(fixSizeToWrite(flight.getOrigin()));//30
        file.writeChars(fixSizeToWrite(flight.getDestination()));//30
        file.writeChars(fixSizeToWrite(flight.getDateFlight().toString()));//30
        file.writeChars(fixSizeToWrite(flight.getTimeFlight().toString()));//30
        file.writeInt(flight.getPrice());//4
        file.writeInt(flight.getSeats());//4
        file.writeInt(flight.getCapacity());//4
        //record length=162
    }

    /**
     * read record of Flight
     * return
     * throws IOException
     */
    public Flight read() throws IOException {
        return new Flight(readFixString(), readFixString(), readFixString(), readDate()
                , readTime(), file.readInt(), file.readInt(), file.readInt());
    }

}
