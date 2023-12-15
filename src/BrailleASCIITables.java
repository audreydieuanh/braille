import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Store translation information as bit trees
 *
 * @author Audrey Trinh
 */
public class BrailleASCIITables {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * braille to ASCII bit tree
   */
  BitTree brailleToASCII;

  /**
   * braille to unicode bit tree
   */
  BitTree brailleToUnicode;

  /**
   * ascii to braille bit tree
   */
  BitTree ASCIIToBraille;

  // +-------------+------------------------------------------------------
  // | Constructor |
  // +-------------+

  /**
   * Create a table that stores translation information
   */
  public BrailleASCIITables() {
    this.brailleToASCII = new BitTree(6);
    this.ASCIIToBraille = new BitTree(8);
    this.brailleToUnicode = new BitTree(6);

    try {
      // load file
      InputStream file = new FileInputStream("brailleToASCII.txt");
      brailleToASCII.load(file);

      file = new FileInputStream("ASCIIToBraille.txt");
      ASCIIToBraille.load(file);

      file = new FileInputStream("brailleToUnicode.txt");
      brailleToUnicode.load(file);

    } catch (FileNotFoundException e) {
      System.out.println("Dictionary file(s) does not exist");
    } // catch
  } // constructor

  // +---------+------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * converts an ASCII character to a string of bits
   * representing the corresponding braille character
   */
  public String toBraille(char letter) {
    String binaryString = String.format("%8s", Integer.toBinaryString(letter)).replace(' ', '0');
    return ASCIIToBraille.get(binaryString);
  }

  /**
   * converts a string of bits representing a braille character
   * to the corresponding ASCII character
   */
  public String toASCII(String bits) {
    return brailleToASCII.get(bits);
  } // toASCII(String)

  /**
   * converts a string of bits representing a braille character
   * to the corresponding Unicode braille character for those bits
   */
  public String toUnicode(String bits) {
    return brailleToUnicode.get(bits);
  } // toUnicode(String)
}
