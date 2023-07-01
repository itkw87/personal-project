package personal.project.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataInputStream extends FileInputStream {

  public DataInputStream(String filename) throws FileNotFoundException {
    super(filename);
  }

  public int readInt() throws IOException {
    return this.read() << 24 | this.read() << 16 | this.read() << 8 | this.read();
  }

  public int readShort() throws IOException {
    return this.read() << 8 | this.read();
  }

  public String readUTF() throws IOException {
    int length = this.read() << 8 | this.read();
    byte[] buf = new byte[length];
    this.read(buf);
    return new String(buf, "UTF-8");
  }
  public long readLong() throws IOException {
    return (long) this.read() << 56 | (long) this.read() << 48 | (long) this.read() << 40
        | (long) this.read() << 32 | (long) this.read() << 24 | (long) this.read() << 16
        | (long) this.read() << 8 | this.read();
  }

  public boolean readBoolean() throws IOException {
    return this.read() == 1 ? true : false;
  }

  public char readChar() throws IOException {
    return (char) (this.read() << 8 | this.read());
  }

  public float readFloat() throws IOException {
    return Float
        .intBitsToFloat(this.read() << 24 | this.read() << 16 | this.read() << 8 | this.read());

  }


}
