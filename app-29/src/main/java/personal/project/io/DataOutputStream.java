package personal.project.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataOutputStream extends FileOutputStream {
  public DataOutputStream(String name) throws FileNotFoundException {
    super(name);
  }

  public void writeShort(int s) throws IOException {
    this.write(s >> 8);
    this.write(s);
  }

  public void writeInt(int i) throws IOException {
    this.write(i >> 24);
    this.write(i >> 16);
    this.write(i >> 8);
    this.write(i);
  }

  public void writeLong(long l) throws IOException {
    this.write((int) (l >> 56));
    this.write((int) (l >> 48));
    this.write((int) (l >> 40));
    this.write((int) (l >> 32));
    this.write((int) (l >> 24));
    this.write((int) (l >> 16));
    this.write((int) (l >> 8));
    this.write((int) l);

  }

  public void writeChar(int c) throws IOException {
    this.write(c >> 8);
    this.write(c);

  }

  public void writeUTF(String str) throws IOException {
    byte[] bytes = str.getBytes("UTF-8");
    this.write(bytes.length >> 8);
    this.write(bytes.length);
    this.write(bytes);
  }

  public void writeBoolean(boolean b) throws IOException {
    this.write(b ? (byte) 1 : (byte) 0);
  }

  public void writeFloat(float f) throws IOException {
    int floatBits = Float.floatToIntBits(f);
    this.write(floatBits >> 24);
    this.write(floatBits >> 16);
    this.write(floatBits >> 8);
    this.write(floatBits);

  }



}
