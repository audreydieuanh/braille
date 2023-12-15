
/**
 * interface for BitTreeNode
 *
 * @author Audrey Trinh
 * @author Samuel A. Rebelsky
 */

interface BitTreeNode {
  public boolean isLeaf();
} // interface BitTreeNode

/**
 * class for interior node
 */
class BitTreeInteriorNode implements BitTreeNode {
  BitTreeNode left;
  BitTreeNode right;

  public BitTreeInteriorNode() {
    this.left = null;
    this.right = null;
  }

  public boolean isLeaf() {
    return false;
  } // isLeaf

  public BitTreeNode getLeft() {
    return left;
  }

  public BitTreeNode getRight() {
    return right;
  }
} // class BitTreeInteriorNode

class BitTreeLeaf implements BitTreeNode {
  String bits;
  String values;

  /**
   * class for leaf
   */
  public BitTreeLeaf(String bits, String values) {
    this.bits = bits;
    this.values = values;
  }

  public boolean isLeaf() {
    return true;
  } // isLeaf()

  public String getBits() {
    return this.bits;
  }

  public String getValues() {
    return this.values;
  }

} // class BitTreeLeaf
