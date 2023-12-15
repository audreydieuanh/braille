import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Store mappings from bits to values
 *
 * @author Audrey Trinh
 */
public class BitTree {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * number of bits
   */
  int numBits;

  /**
   * root of the bit tree
   */
  BitTreeNode root;

  // +-------------+------------------------------------------------------
  // | Constructor |
  // +-------------+

  /**
   * Create a bit tree
   */
  public BitTree(int n) {
    this.numBits = n;
    this.root = new BitTreeInteriorNode();
  }

  // +---------+------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * follows the path through the tree given by bits,
   * add or replace the value at the end with value
   */
  public void set(String bits, String values) throws Exception {
    // invalid length
    if (bits.length() != this.numBits) {
      throw new Exception("bits should have length " + this.numBits);
    }

    this.root = set(bits, values, this.root, 0);
  } // set(K,V)

  /**
   * helper set for recursion
   */
  BitTreeNode set(String bits, String values, BitTreeNode node, int depth) throws Exception {
    // end of bit
    if (bits.length() == depth) {
      return new BitTreeLeaf(bits, values);
    }

    BitTreeInteriorNode interiorNode = (BitTreeInteriorNode) node;
    if (interiorNode == null) {
      interiorNode = new BitTreeInteriorNode();
    }
    char bit = bits.toCharArray()[depth];
    depth++;

    if (bit == '0') {
      // traverse left
      interiorNode.left = set(bits, values, interiorNode.left, depth);
    } else if (bit == '1') {
      // traverse right
      interiorNode.right = set(bits, values, interiorNode.right, depth);
    } else {
      throw new Exception("invalid bits");
    }
    return interiorNode;
  }

  /**
   * follows the path through the tree given by bits,
   * returning the value at the end.
   */
  public String get(String bits) {
    if (bits.length() != this.numBits) {
      return "bits should have length " + this.numBits;
    }
    return get(bits, this.root, 0);
  } // get

  /**
   * helper get for recursion
   */
  String get(String bits, BitTreeNode node, int depth) {
    if (node == null) {
      return "No value found";
    }

    // if reach end of bit tree
    if (node.isLeaf()) {
      BitTreeLeaf leaf = (BitTreeLeaf) node;
      return leaf.getValues();
    } else {
      BitTreeInteriorNode interiorNode = (BitTreeInteriorNode) node;
      char bit = bits.toCharArray()[depth];
      depth++;

      if (bit == '0') {
        return get(bits, interiorNode.left, depth);
      } else if (bit == '1') {
        return get(bits, interiorNode.right, depth);
      } else {
        return bits + " Invalid bits";
      }
    }
  }

  /**
   * print put the content of the tree in CSV format
   */
  public void dump(PrintWriter pen) {
    dump(pen, this.root);
  }

  /**
   * helper dump for recursion
   */
  void dump(PrintWriter pen, BitTreeNode node) {
    if (node != null) {
      // if reach end of tree
      if (node.isLeaf()) {
        BitTreeLeaf leaf = (BitTreeLeaf) node;
        pen.println(leaf.getBits() + "," + leaf.getValues());
      } else {
        BitTreeInteriorNode interiorNode = (BitTreeInteriorNode) node;
        if ((interiorNode.getLeft() != null) || (interiorNode.getRight() != null)) {
          dump(pen, interiorNode.getLeft());
          dump(pen, interiorNode.getRight());
        }
      }
    }
  } // dump

  /**
   * reads a series of lines of the form bits,value and stores them in the tree.
   */
  public void load(InputStream source) {
    try {
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      int nRead;
      byte[] data = new byte[1024];
      while ((nRead = source.read(data, 0, data.length)) != -1) {
        buffer.write(data, 0, nRead);
      }
      buffer.flush();
      byte[] byteData = buffer.toByteArray();
      source.close();
      String[] lines = new String(byteData).split("\n");
      for (String pair : lines) {
        String[] elements = pair.split(",");
        if (elements.length >= 2) {
          set(elements[0], elements[1]);
        } else if (elements[0].equals("000000")) {
          set(elements[0], " ");
        } else {
          System.out.println("Invalid line: " + pair);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
