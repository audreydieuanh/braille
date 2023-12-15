import java.io.PrintWriter;

/**
 * Class for translating text
 *
 * @author Audrey Trinh
 */
public class BrailleASCII {
  public static void main(String[] args) {
    BrailleASCIITables table = new BrailleASCIITables();
    PrintWriter pen = new PrintWriter(System.out, true);

    switch (args[0]) {
      case "braille" -> {
        String[] asciiBits = args[1].split(" ");
        for (String bits : asciiBits) {
          if (!bits.matches("[01]+") || bits.length() != 8) {
            System.out.print("Invalid bits");
            continue;
          }
          char ch = (char) Integer.parseInt(bits, 2);
          pen.print(table.toBraille(ch) + " ");
        }
        pen.println();
      }
      case "ascii" -> {
        String brailleToASCII = args[1];
        pen.println(table.toASCII(brailleToASCII));
      }
      case "unicode" -> {
        String brailleToUnicode = args[1];
        pen.println(table.toUnicode(brailleToUnicode));
      }
      default -> System.out.println("Invalid argument");
    }
  }
}