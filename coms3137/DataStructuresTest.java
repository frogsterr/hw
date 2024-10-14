import java.util.ArrayList;

// LinkedList implementation (remove binary search method)
class Node {
    int data;
    Node next;
    
    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList {
    Node head;
    
    // TODO: Implement remove element
    public void removeElement(int value) {
        if (head==null){return;}

        Node p = head;
        Node q = null;

        if (p.data == value) {
            head = head.next;
            return;
        }
        while(p !=null){
          if (p.data==value){
            q.next = p.next;
            return;
          }
          q = p;
          p = p.next;
        }
    }
    
    // TODO: Implement reverse list
    public void reverse() {
        if (head.next==null){
            return;
        }
        Node p = head;
        Node q = null;

        while(p!=null){
            Node next = p.next;
            p.next = q;
            q=p;
            p = next;
        }
        head = q;
        
    }
}

// ArrayList with Binary Search
class ArrayListWithBinarySearch {
    private ArrayList<Integer> list;

    public ArrayListWithBinarySearch() {
        this.list = new ArrayList<>();
    }

    public void add(int value) {
        list.add(value);
        list.sort(null); // Keep the list sorted
    }

    // TODO: Implement binary search
    public boolean binarySearch(int target) {
        // Your code here
        int low = 0;
        int high = list.size() -1;
        while (low <= high){
            med = 
        }
    }
}

// Binary Search Tree implementation
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    
    TreeNode(int val) {
        this.val = val;
    }
}

class BinarySearchTree {
    TreeNode root;

    // TODO: Implement insert
    public void insert(int value) {
        // Your code here
    }

    // TODO: Implement search
    public boolean search(int value) {
        // Your code here
        return false;
    }

    // TODO: Implement inorder traversal
    public void inorderTraversal(TreeNode node) {
        // Your code here
    }
}

/// Test class
public class DataStructuresTest {
    public static void main(String[] args) {
        testLinkedList();
        //testArrayListBinarySearch();
        //testBinarySearchTree();
        //testCircularQueue();
        //testParenthesisChecker();
    }
    
    public static void testLinkedList() {
        System.out.println("Testing LinkedList...");
        LinkedList list = new LinkedList();
        
        // Create a list: 1 -> 3 -> 5 -> 7
        list.head = new Node(1);
        list.head.next = new Node(3);
        list.head.next.next = new Node(5);
        list.head.next.next.next = new Node(7);
        
        System.out.print("Original list: ");
        printList(list.head);
        
        // Test remove element
        list.removeElement(3);
        System.out.print("After removing 3: ");
        printList(list.head); // Should print: 1 5 7
        
        // Test reverse
        list.reverse();
        System.out.print("After reversing: ");
        printList(list.head); // Should print: 7 5 1
    }
    
    public static void testArrayListBinarySearch() {
        System.out.println("\nTesting ArrayList Binary Search...");
        ArrayListWithBinarySearch list = new ArrayListWithBinarySearch();
        
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(7);
        list.add(9);
        
        System.out.println("Binary Search for 5: " + list.binarySearch(5)); // Should return true
        System.out.println("Binary Search for 4: " + list.binarySearch(4)); // Should return false
        System.out.println("Binary Search for 9: " + list.binarySearch(9)); // Should return true
    }
    
    public static void testBinarySearchTree() {
        System.out.println("\nTesting Binary Search Tree...");
        BinarySearchTree bst = new BinarySearchTree();
        
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(1);
        bst.insert(9);
        
        System.out.println("Search for 7: " + bst.search(7)); // Should return true
        System.out.println("Search for 4: " + bst.search(4)); // Should return false
        
        System.out.print("Inorder traversal: ");
        bst.inorderTraversal(bst.root); // Should print: 1 3 5 7 9
        System.out.println();
    }
    
    public static void testCircularQueue() {
        // Circular Queue test remains the same
    }
    
    public static void testParenthesisChecker() {
        // Parenthesis Checker test remains the same
    }
    
    private static void printList(Node head) {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
}