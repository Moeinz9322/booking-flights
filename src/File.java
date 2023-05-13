import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class File {
    RandomAccessFile file;
    protected final int FIX_SIZE=30;

    public File(RandomAccessFile file) {
        this.file = file;
    }

    public void write() {

    }
    public void read(){

    }
    public String fixSizeToWrite(String str){
        while (str.length()<FIX_SIZE){
            str+=" ";
        }
        return str.substring(0,FIX_SIZE);
    }

    public String readFixString() throws IOException {
        String tmp = "";
        for (int i = 0; i < FIX_SIZE; i++) {
            tmp += file.readChar();
        }
        return tmp.trim();
    }
    public DateFlight readDate() throws IOException {
        String date=new String(readFixString());
        return new DateFlight(date.substring(0,4),date.substring(4,6),date.substring(6,8));
    }
}
