package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private int line = 0;
  private boolean[] newline = {true, true};

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    //Objects.checkFromIndexSize(off, len, str.length()); // @since 9 (can replace next two lines)

    if ((str.length() | off | len) < 0 || len > str.length() - off)
      throw new IndexOutOfBoundsException();

    for(int i = off; i < len + off; ++i)
      this.write(str.charAt(i));
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    //Objects.checkFromIndexSize(off, len, cbuf.length); // @since 9 (can replace next two lines)

    if ((cbuf.length | off | len) < 0 || len > cbuf.length - off)
      throw new IndexOutOfBoundsException();

    for(int i = off; i < len + off; ++i)
      this.write(cbuf[i]);
  }

  @Override
  public void write(int c) throws IOException {
    if(c == '\n' || c == '\r') {
      newline[0] = true;
      newline[1] = false;

      if(c == '\n') {
        newline[1] = true;
        super.write(c);
      }
    } else
      newline[1] = true;

    if(newline[0] && newline[1]) {
      String str = ++line + "\t";
      super.write(str, 0, str.length());

      newline[0] = false;

      // si le caractere n'est pas un retour mais que newline est à true c'est la premiere ligne
      if(c != '\n' && c != '\r')
        super.write(c);
    } else
      super.write(c);
  }
}
