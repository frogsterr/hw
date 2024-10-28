import java.io.Reader;

import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

/**
 * Class that implements a red-black tree which implements the MyMap interface.
 * @author Brian S. Borowski
 * @version 1.2.1 March 5, 2024
 */
public class RBTreeMap<K extends Comparable<K>, V> extends BSTreeMap<K, V>
        implements MyMap<K, V> {

    /**
     * Creates an empty red-black tree map.
     */
    public RBTreeMap() { }

    /**
     * Creates a red-black tree map from the array of key-value pairs.
     * @param elements an array of key-value pairs
     */
    public RBTreeMap(Pair<K, V>[] elements) {
        insertElements(elements);
    }

    /**
     * Creates a red-black tree map of the given key-value pairs. If
     * sorted is true, a balanced tree will be created via a divide-and-conquer
     * approach. If sorted is false, the pairs will be inserted in the order
     * they are received, and the tree will be rotated to maintain the red-black
     * tree properties.
     * @param elements an array of key-value pairs
     */
    public RBTreeMap(Pair<K, V>[] elements, boolean sorted) {
        if (!sorted) {
            insertElements(elements);
        } else {
            root = createBST(elements, 0, elements.length - 1);
        }
    }

    /**
     * Recursively constructs a balanced binary search tree by inserting the
     * elements via a divide-snd-conquer approach. The middle element in the
     * array becomes the root. The middle of the left half becomes the root's
     * left child. The middle element of the right half becomes the root's right
     * child. This process continues until low > high, at which point the
     * method returns a null Node.
     * All nodes in the tree are black down to and including the deepest full
     * level. Nodes below that deepest full level are red. This scheme ensures
     * that all paths from the root to the nulls contain the same number of
     * black nodes.
     * @param pairs an array of <K, V> pairs sorted by key
     * @param low   the low index of the array of elements
     * @param high  the high index of the array of elements
     * @return      the root of the balanced tree of pairs
     */
    protected Node<K, V> createBST(Pair<K, V>[] pairs, int low, int high) {
        // TODO
        if (low > high) {
            return null;
        }
        int mid = low + (high - low) / 2;
        
        Pair<K, V> pair = pairs[mid];
        RBNode<K, V> parent = new RBNode<>(pair.key, pair.value);

        double depth = Math.log(pairs.length/(high-low+1))/Math.log(2);
        double maxDepth = Math.log(high-low+1)/Math.log(2);
        if (depth <=maxDepth){
            parent.color = RBNode.BLACK;
        }
        size++;
        parent.setLeft(createBST(pairs, low, mid - 1));
        if (parent.getLeft() != null) {
            parent.getLeft().setParent(parent);
        }
        parent.setRight(createBST(pairs, mid + 1, high));
        if (parent.getRight() != null) {
            parent.getRight().setParent(parent);
        }

        return parent;
    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is replaced
     * by the specified value.
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    @Override
    public V put(K key, V value) {
        // TODO
        RBNode<K, V> x = (RBNode<K, V>) root, y = null;
        while (x != null) {
            y = x;
            int comparison = key.compareTo(x.key);
            if (comparison < 0) {
                x = x.getLeft();
            } else if (comparison > 0) {
                x = x.getRight();
            } else {
                // The key was found in the tree. Return the previous value
                // associated with the key.
                V oldValue = x.value;
                x.value = value;
                return oldValue;
            }
        }
        RBNode<K, V> z = new RBNode<>(key, value);
        z.setParent(y);
        if (y == null) {
            root = z;
        } else if (key.compareTo(y.key) < 0) {
            y.setLeft(z);
        } else {
            y.setRight(z);
        }
        insertFixup(z);
        size++;
        // There was no previous mapping for this key.
        return null;

    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    public V remove(K key) {
        // TODO, otherwise return null.
        Node<K, V> z = iterativeSearch(key);
        if (z == null) {
            return null;
        }
        if (root == z && z.getLeft() == null && z.getRight() == null) {
            root = null;
            size--;
            return z.value;
        }

        Node<K, V> fixNode;
        byte originalColor = ((RBNode<K, V>) z).color;
        byte y_originalColor = originalColor;
        if (z.getLeft() == null) {
            fixNode = z.getRight();
            transplant(z, z.getRight());
        } else if (z.getRight() == null) {
            fixNode = z.getLeft();
            transplant(z, z.getLeft());
        } else {
            Node<K, V> y = treeMinimum(z.getRight());
            y_originalColor = ((RBNode<K, V>) y).color;
            // Could be null fixNode!
            fixNode = y.getRight();
            if (y.getParent() != z) {
                transplant(y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
            transplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            ((RBNode<K, V>) y).color = originalColor;
        }
        if (y_originalColor == RBNode.BLACK){
            deleteFixup((RBNode<K, V>) fixNode);
        }
        size--;
        return z.value;
    }

    /**
     * Fixup method described on p. 339 of CLRS, 4e.
     */
    private void insertFixup(RBNode<K, V> z) {
        while (z!= root && z.getParent().color==RBNode.RED){
            RBNode<K, V> gp = z.getParent().getParent();
            //
            if (gp.getLeft()==z.getParent()){
                RBNode<K, V> uncle =gp.getRight();
                if (uncle!=null && uncle.color==RBNode.RED){
                    z.getParent().color = RBNode.BLACK;
                    uncle.color = RBNode.BLACK;
                    gp.color = RBNode.RED;
                    z = gp;
                } 
                else if (uncle==null || uncle.color==RBNode.BLACK){
                    // Bend-case - > Straight
                    if (z.getParent().getRight()==z){
                        z = z.getParent();
                        leftRotate(z);
                    }
                    // Straight-case
                    z.getParent().color = RBNode.BLACK;
                    gp.color = RBNode.RED;
                    rightRotate(gp);
                }
            }

            if (gp.getRight()==z.getParent()){
                RBNode<K, V> uncle = gp.getLeft();
                if (uncle != null && uncle.color == RBNode.RED){
                    z.getParent().color = RBNode.BLACK;
                    uncle.color = RBNode.BLACK;
                    gp.color = RBNode.RED;
                    z = gp;
                }
                else if (uncle == null || uncle. color == RBNode.BLACK){
                    // Bend - > Straight
                    if (z.getParent().getLeft()==z){
                        z = z.getParent(); 
                        rightRotate(z);
                    }
                    z.getParent().color = RBNode.BLACK;
                    gp.color = RBNode.RED;
                    leftRotate(gp);
                }
            }
        }
        ((RBNode<K, V>)root).color = RBNode.BLACK;
    }

    /**
     * Fixup method described on p. 351 of CLRS, 4e.
     */
    private void deleteFixup(RBNode<K, V> x) {
        while (x != null && x != (RBNode<K, V>)root && x.color == RBNode.BLACK){
            if (x==null){
                return;
            }
            // Left side
            if (x==x.getParent().getLeft()){
                //Sibling
                RBNode<K, V> w = x.getParent().getRight();
                
                // Case 1 Sibling is red
                if (w != null && w.color == RBNode.RED){
                    w.color = RBNode.BLACK;
                    x.getParent().color = RBNode.RED;
                    leftRotate(x.getParent());
                    w = x.getParent().getRight();
                }
                // Case 2 sibling left black and right black
                if (w==null || ((w.getLeft() == null || w.getLeft().color== RBNode.BLACK) && 
                (w.getRight() == null || w.getRight().color == RBNode.BLACK))) {
                    if (w!=null) w.color = RBNode.RED;
                    x = x.getParent();
                } else {
                    if (w.getRight()==null || w.getRight().color == RBNode.BLACK){
                        if (w.getLeft() !=null) w.getLeft().color = RBNode.BLACK;
                        w.color = RBNode.RED;
                        rightRotate(w);
                        w = x.getParent().getRight();
                    }
                    w.color = x.getParent().color;
                    x.getParent().color = RBNode.BLACK;
                    if (w.getRight()!=null) w.getRight().color = RBNode.BLACK;
                    leftRotate(x.getParent());
                    x = (RBNode<K, V>) root;
                }
            // Right Side
            }else {
                RBNode<K, V> w = x.getParent().getLeft();
                if (w!=null && w.color == RBNode.RED){
                    w.color = RBNode.BLACK;
                    x.getParent().color = RBNode.RED;
                    rightRotate(x.getParent());
                    w = x.getParent().getLeft();
                }
                // Case 2 sibling children black
                if (w==null || ((w.getLeft() == null || w.getLeft().color == RBNode.BLACK) && 
                (w.getRight() == null || w.getRight().color == RBNode.BLACK))) {
                    if(w!=null) w.color = RBNode.RED;
                    x = x.getParent();
                } else {
                    if (w.getLeft()==null || w.getLeft().color==RBNode.BLACK){
                        if (w.getRight()!=null) w.getRight().color = RBNode.BLACK;
                        w.color = RBNode.RED;
                        leftRotate(w);
                        w = x.getParent().getLeft();
                    }
                    w.color = x.getParent().color;
                    x.getParent().color = RBNode.BLACK;
                    if (w.getLeft()!=null) w.getLeft().color = RBNode.BLACK;
                    rightRotate(x.getParent());
                    x = (RBNode<K, V>) root;
                }
            }
        }
        if (x!= null){
            x.color = RBNode.BLACK;
        }
        ((RBNode<K, V>)root).color = RBNode.BLACK;
    }

    /**
     * Left-rotate method described on p. 336 of CLRS, 4e.
     */
    private void leftRotate(Node<K, V> x) {
        // TODO
        Node<K, V> y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft()!=null){
            y.getLeft().setParent(x);
        }
        if (x.getParent()==null){
            root = y;
        } else if (x.getParent().getLeft()==x){
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }
        y.setParent(x.getParent());
        y.setLeft(x);
        x.setParent(y);
    }

    /**
     * Right-rotate method described on p. 336 of CLRS, 4e.
     */
    private void rightRotate(Node<K, V> x) {
        // TODO
        Node<K, V> y = x.getLeft();
        x.setLeft(y.getRight());
        if (y.getRight()!=null){
            y.getRight().setParent(x);
        }
        if (x.getParent()==null){
            root = y;
        } else if (x.getParent().getLeft()==x){
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }
        y.setParent(x.getParent());
        y.setRight(x);
        x.setParent(y);
    }
}
