import java.util.Iterator;
import java.util.Stack;

/**
 * Class that implements a binary search tree which implements the MyMap
 * interface.
 * @author
 * @version 1.1.1 October 17, 2024
 */
public class BSTreeMap<K extends Comparable<K>, V> implements MyMap<K, V> {
    public static final int PREORDER = 1, INORDER = 2, POSTORDER = 3;
    protected Node<K, V> root;
    protected int size;

    /**
     * Creates an empty binary search tree map.
     */
    public BSTreeMap() { }

    /**
     * Creates a binary search tree map of the given key-value pairs.
     * @param elements an array of key-value pairs
     */
    public BSTreeMap(Pair<K, V>[] elements) {
        insertElements(elements);
    }

    /**
     * Creates a binary search tree map of the given key-value pairs. If
     * sorted is true, a balanced tree will be created. If sorted is false,
     * the pairs will be inserted in the order they are received.
     * @param elements an array of key-value pairs
     */
    public BSTreeMap(Pair<K, V>[] elements, boolean sorted) {
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
     * @param pairs an array of <K, V> pairs sorted by key
     * @param low   the low index of the array of elements
     * @param high  the high index of the array of elements
     * @return      the root of the balanced tree of pairs
     */
    protected Node<K, V> createBST(Pair<K, V>[] pairs, int low, int high) {
        // TODO
        if (low > high){
            return null;
        }

        int mid = low + (high - low) / 2;
        Node<K, V> n = new Node<>(pairs[mid].key, pairs[mid].value);
        size++;

        n.setLeft(createBST(pairs, low, mid-1));

        if (n.getLeft() != null) {
            n.getLeft().setParent(n);
        }

        n.setRight(createBST(pairs, mid+1, high));

        if (n.getRight() != null) {
            n.getRight().setParent(n);
        }

        return n;

    }

    /**
     * Inserts the pairs into the tree in the order they appear in the given
     * array.
     * @param pairs the array of <K, V> pairs to insert
     */
    protected void insertElements(Pair<K, V>[] pairs) {
        for (Pair<K, V> pair : pairs) {
            put(pair);
        }
    }

    /**
     * Returns the number of key-value mappings in this map.
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this map contains no key-value mappings.
     * @return true if this map contains no key-value mappings
     */
    public boolean isEmpty() {
        // TODO
        return root == null;
    }

    /**
     * Returns a String of the key-value pairs visited with a preorder
     * traversal. Uses a StringBuilder for efficiency.
     * @return a String of the key-value pairs visited with a preorder
     *         traversal
     */
    public String preorder() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        preorder(root, builder, 0);
        builder.append("]");
        return builder.toString();
    }

    /**
     * Visits the Nodes of the tree in a preorder traversal. Each Node's
     * toString() return value should be appended to the StringBuilder. A ", "
     * must appear between each Node's data in the final String.
     * @param n            the current Node
     * @param builder      the StringBuilder used to build up the output
     * @param nodesVisited the number of nodes visited so far. Useful for
     *                     determining when to append ", ".
     * @return the number of nodes visited after each recursive call
     */
    private int preorder(Node<K, V> n, StringBuilder builder,
                         int nodesVisited) {
        // TODO
        if (n == null){
            return nodesVisited;
        }

        builder.append(n.toString());
        nodesVisited++;

        if (nodesVisited < size){
            builder.append(", ");
        }
        

        nodesVisited = preorder(n.getLeft(), builder, nodesVisited);
        nodesVisited = preorder(n.getRight(), builder, nodesVisited);

        return nodesVisited;
    }

    /**
     * Returns a String of the key-value pairs visited with an inorder
     * traversal. Uses a StringBuilder for efficiency.
     * @return a String of the key-value pairs visited with an inorder
     *         traversal
     */
    public String inorder() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        inorder(root, builder, 0);
        builder.append("]");
        return builder.toString();
    }

    /**
     * Visits the Nodes of the tree in an inorder traversal. Each Node's
     * toString() return value should be appended to the StringBuilder. A ", "
     * must appear between each Node's data in the final String.
     * @param n            the current Node
     * @param builder      the StringBuilder used to build up the output
     * @param nodesVisited the number of nodes visited so far. Useful for
     *                     determining when to append ", ".
     * @return the number of nodes visited after each recursive call
     */
    private int inorder(Node<K, V> n, StringBuilder builder,
                        int nodesVisited) {
        if (n==null){
            return nodesVisited;
        }
       nodesVisited = inorder(n.getLeft(), builder, nodesVisited);

       builder.append(n.toString());
       nodesVisited++;
       
       if (nodesVisited < size){ //this might be wrong size!!!!
        builder.append(", ");
       }
       
       nodesVisited = inorder(n.getRight(), builder, nodesVisited);

       return nodesVisited;
    }
    /**
     * Returns a String of the key-value pairs visited with a postorder
     * traversal. Uses a StringBuilder for efficiency.
     * @return a String of the key-value pairs visited with a postorder
     *         traversal
     */
    public String postorder() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        postorder(root, builder, 0);
        builder.append("]");
        return builder.toString();
    }

    /**
     * Visits the Nodes of the tree in a postorder traversal. Each Node's
     * toString() return value should be appended to the StringBuilder. A ", "
     * must appear between each Node's data in the final String.
     * @param n            the current Node
     * @param builder      the StringBuilder used to build up the output
     * @param nodesVisited the number of nodes visited so far. Useful for
     *                     determining when to append ", ".
     * @return the number of nodes visited after each recursive call
     */
    private int postorder(Node<K, V> n, StringBuilder builder,
                          int nodesVisited) {
        // TODO
        if (n==null){
            return nodesVisited;
        }

        nodesVisited = postorder(n.getLeft(), builder, nodesVisited);
        nodesVisited = postorder(n.getRight(), builder, nodesVisited);

        builder.append(n.toString());
        nodesVisited++;

        if (nodesVisited < size){
            builder.append(", ");
        }


        return nodesVisited;


    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     * @param  key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this
     *         map contains no mapping for the key
     */
    public V get(K key) {
        Node<K, V> x = iterativeSearch(key);
        return x != null ? x.value : null;
    }

    /**
     * Determines if the supplied key is found in the tree. If so, it returns a
     * reference to the Node containing the key. Otherwise, null is returned.
     * @param key key whose mapping is to be removed from the map
     * @return a reference to the Node containing the specified key
     */
    private Node<K, V> iterativeSearch(K key) {
        Node<K, V> curr = root;
        while (curr != null) {
            if (curr.key.compareTo(key) > 0) {
                curr = curr.getLeft();
            } else if(curr.key.compareTo(key) < 0){
                curr = curr.getRight();
            } else {
                return curr;
            }
        }

        return null;
    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is replaced
     * by the specified value.
     * @param pair  the key-value mapping to insert into the tree
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    public V put(Pair<K, V> pair) {
        return put(pair.key, pair.value);
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
    public V put(K key, V value) {
        Node<K, V> n = iterativeSearch(key);
        V oldValue;
        if(n!=null){
            oldValue = n.value;
            n.value = value;
        } else {
            size++;
            oldValue = null;
            Node<K, V> newNode = new Node<>(key, value);
            if (root==null){
                root = newNode;
                return oldValue;
            }
            Node<K, V> curr = root;
            Node<K, V> p = curr; 

            while (curr != null){
                p = curr;
                if (newNode.key.compareTo(curr.key) > 0) {
                    curr = curr.getRight();
                } else {
                    curr = curr.getLeft();
                }
            }
            if (newNode.key.compareTo(p.key) > 0){
                p.setRight(newNode);
            } else {
                p.setLeft(newNode);
            }

            newNode.setParent(p);

        }

        return oldValue;
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    public V remove(K key) {
        Node<K, V> n = iterativeSearch(key);
        if (n==null){
            return (V) null;
        }
        return delete(n).value;
    }

    /**
     * Deletes Node from map
     * @param n Node to remove
     * @return value of removed node
     */
    protected Node<K, V> delete(Node<K, V> n){
         // Node has no left child
         if (n.getLeft()== null){
            //Connects the n.right p to n.p no need to connect w/ left bc null.
            transplant(n, n.getRight());
        }
        // Node has no right child
        else if(n.getRight() == null){
            //Connects the n.left p to n.p no need to connect w/ right bc null.
            transplant(n, n.getLeft());
        }
        else {
            Node<K, V> succ = treeMinimum(n.getRight());
            // Successor is node with depth
            if (succ.getParent()!=n){
                // Connects succ.right to parent! Its ok if null. 
                transplant(succ, succ.getRight());
                // Connects succ.right w/ n.right
                succ.setRight(n.getRight());
                // connects succ.right to succ
                succ.getRight().setParent(succ);
            }
            // succ takes position of n             
            transplant(n, succ);
            // Succ takes n.left
            succ.setLeft(n.getLeft());
            // n.left parent becomes succ
            succ.getLeft().setParent(succ);
        }
        size--;
        return n;
    }
    /**
     * Connects parents of U to V. 
     * @param u Node to be replaced
     * @param v Replacing Node
     */
    protected void transplant(Node<K, V> u, Node<K,V> v){
        if (u.getParent() == null){
            root = v;
        } else if (u==u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else{
            u.getParent().setRight(v);
        }
        if (v!=null){
            v.setParent(u.getParent());
        } 
    }

    /**
     * Returns a reference to the Node whose key value is the minimum key in the
     * tree.
     * @param x the Node at which to start the traversal
     * @return a reference to the Node whose key value is the minimum key in the
     *         tree
     */
    protected Node<K, V> treeMinimum(Node<K, V> x) {
        while (x.getLeft() != null) {
            x = x.getLeft();
        }
        return x;
    }

    /**
     * Returns a String representation of the tree, where the Nodes are visited
     * with an inorder traversal.
     * @return a String representation of the tree
     */
    public String toString() {
        return inorder();
    }

    /**
     * Returns an ASCII drawing of the tree.
     * @return an ASCII drawing of the tree
     */
    public String toAsciiDrawing() {
        BinarySearchTreePrinter<K, V> printer =
                new BinarySearchTreePrinter<>();
        printer.createAsciiTree(root);
        return printer.toString();
    }

    public void printTraversal(int type) {
        switch (type) {
            case PREORDER -> {
                System.out.print("Preorder traversal:       ");
                System.out.println(preorder());
            }
            case INORDER -> {
                System.out.print("Inorder traversal:        ");
                System.out.println(inorder());
            }
            case POSTORDER -> {
                System.out.print("Postorder traversal:      ");
                System.out.println(postorder());
            }
        }
    }

    /**
     * Returns an iterator over the Entries in this map in the order
     * in which they appear.
     * @return an iterator over the Entries in this map
     */
    public Iterator<Entry<K, V>> iterator() {
        return new BinaryTreeItr();
    }

    private class BinaryTreeItr implements Iterator<Entry<K, V>> {
        private Node<K, V> current;
        private final Stack<Node<K, V>> parentStack = new Stack<>();

        BinaryTreeItr() {
            current = root;
        }

        @Override
        public boolean hasNext() {
            return !parentStack.isEmpty() || current != null;
        }

        @Override
        public Entry<K, V> next() {
            while (hasNext()) {
                if (current != null) {
                    parentStack.push(current);
                    current = current.getLeft();
                } else {
                    Node<K, V> toReturn = parentStack.pop();
                    current = toReturn.getRight();
                    return toReturn;
                }
            }
            return null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns the height of the tree. If the tree is null, the height is -1.
     * @return the height of the tree
     */
    public int height() {
        return height(root) ;
    }

    protected int height(Node<K, V> node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    /**
     * Returns the number of null references in the tree. Uses a recursive
     * helper method to count the null references.
     * @return the number of null references in the tree
     */
    public int nullCount() {
        return nullCount(root);
    }

    private int nullCount(Node<K, V> node) {
        if (node == null) {
            return 1;
        }
        return nullCount(node.getLeft()) + nullCount(node.getRight());
    }

    /**
     * Returns the sum of the levels of each non-null node in the tree starting
     * at the root.
     * For example, the tree
     *   5 <- level 0
     *  / \
     * 2   8 <- level 1
     *      \
     *      10 <- level 2
     * has sum 0 + 2(1) + 2 = 4.
     * @return the sum of the levels of each non-null node in the tree starting
     *         at the root
     */
    public int sumLevels() {
        return sumLevels(root, 0);
    }

    private int sumLevels(Node<K, V> node, int level) {
        if (node == null){
            return 0;
        }

        int leftSum = sumLevels(node.getLeft(), level+1);
        int rightSum = sumLevels(node.getRight(), level+1);

        return level + leftSum + rightSum;
    }

    /**
     * Returns the sum of the levels of each null node in the tree starting at
     * the root.
     * For example, the tree
     *    5 <- level 0
     *   / \
     *  2   8 <- level 1
     * / \ / \
     * * * * 10 <- level 2
     *       / \
     *       * * <- level 3
     * has sum 3(2) + 2(3) = 12.
     * @return the sum of the levels of each null node in the tree starting at
     *         the root
     */
    public int sumNullLevels() {
        return sumNullLevels(root, 0);
    }

    private int sumNullLevels(Node<K, V> node, int level) {
        // TODO
        if (node == null){
            return level;
        }

        int leftSum = sumNullLevels(node.getLeft(), level+1);
        int rightSum = sumNullLevels(node.getRight(), level+1);

        return leftSum + rightSum;

    }

    public double successfulSearchCost() {
        return size == 0 ? 0 : 1 + (double)sumLevels() / size;
    }

    public double unsuccessfulSearchCost() {
        return (double)sumNullLevels() / nullCount();
    }
}
