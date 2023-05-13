import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class File {
    RandomAccessFile file;
    protected final int FIX_SIZE=30;

    public File(RandomAccessFile file) {
        this.file = file;
    }

    public abstract void write(Flight flight);
    public abstract void read();
    public String fixSize(String str){
        while (str.length()<FIX_SIZE){
            str+=" ";
        }
        return str.substring(0,FIX_SIZE);
    }

    private String readFixString() throws IOException {
        String tmp = "";
        for (int i = 0; i < FIX_SIZE; i++) {
            tmp += file.readChar();
        }
        return tmp.trim();
    }
}
