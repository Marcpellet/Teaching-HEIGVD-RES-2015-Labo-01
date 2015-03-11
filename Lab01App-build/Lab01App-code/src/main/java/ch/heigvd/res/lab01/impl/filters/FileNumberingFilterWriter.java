package ch.heigvd.res.lab01.impl.filters;

import ch.heigvd.res.lab01.impl.Utils;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
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
  private int counter = 1;
  private boolean findR = false;
  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
     String[] temp = new String[2];
     
     String toWrite = new String();
     str = str.substring(off, off+len);
     
     if(counter == 1){
         toWrite = String.valueOf(counter++) + '\t';
     }
     while(!(temp = Utils.getNextLine(str))[0].isEmpty()){
         toWrite +=  temp[0] + String.valueOf(counter++) + '\t';
         str = temp[1];
     }
     toWrite += temp[1];
     out.write(toWrite);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(String.valueOf(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    if(c == '\r' && !findR){
        out.write(c);
        findR = true;
    }else if(c!= '\n' && findR){
        out.write(counter++ + '\t' + String.valueOf((char) c));
        findR = false;
    }else{
        findR = false;
        write(String.valueOf((char) c)); 
    }
    
  }

}
