package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
    Pattern pattern = Pattern.compile("\r\n|\r|\n");
    Matcher matcher = pattern.matcher(lines);

    String[] output = new String[2];

    if(matcher.find()) {
      output[0] = lines.substring(0, matcher.end());
      output[1] = lines.substring(matcher.end());
    } else {
      output[0] = "";
      output[1] = lines;
    }


    return output;
  }

}
