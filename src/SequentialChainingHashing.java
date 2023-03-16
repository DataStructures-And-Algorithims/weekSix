import java.util.Scanner;

public class SequentialChainingHashing<Key,Value> {
    private static Scanner itemNameScanner;
    private static Scanner itemKeyScanner;
    private static Scanner end;
    private static Scanner deleteItem;
    private static Scanner deleteChoice;
    private int M = 10;
    private Node[] st = new Node[M];

    private static class Node {
        private Object key; // Object used to enable generics since there are no generic arrays
        private Object val;
        private Node next;

        public Node(Object key,Object val,Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7FFFFFFF) % M;
    }

    public void put(Key key, Value val) {
        int i = hash(key);
        // This for loop iterates to the next node. In this case the Node.next is accessed
        for(Node x = st[i]; x != null; x = x.next)
            if (key.equals(x.key)) {x.val = val; return;} // If the values are equal reset value and exit the method
        st[i] = new Node(key,val,st[i]);
    }

    public Node get(Key key) {
        int i = hash(key);
        for(Node x = st[i]; x != null; x = x.next)
            if(key.equals(x.key)) return  x;
        return null;
    }

    public void delete(Key key) {
        int hash = hash(key);
        // The node at st[hash is the head]
        Node current = st[hash];
        Node previous = null;
        Node tobeDeleted = get(key);
        for(Node x = st[hash]; x != null; x = x.next) {
            // Node is the head
            if(tobeDeleted.key.equals(x.key) && x.key.equals(st[hash].key)) {
                st[hash] = tobeDeleted.next; return;
            }
           // Node is somewhere other than head
            // Keep track of the previous node
            while(current != null && !current.key.equals(tobeDeleted.key)){
                previous = current;
                current = current.next;
            }
            if(current != null && previous != null) {
                // Unlink the current node
                previous.next = current.next;
            }
        }
    }

    public void printChain(int i) {
        for(Node x = st[i]; x != null; x = x.next) {
            System.out.print("[ " + x.key + ", " + x.val + " ]" + " -> ");
            // If the next chain is reached exit method
            if(hash((Key) x.key) != i)
                return;
        }
    }

    public void printChainAll() {
        for(int i = 0; i < st.length; i++) {
            if(st[i] != null)
                printChain(i);
        }
    }

    public static void main(String[] args) {
        SequentialChainingHashing<String,Integer> sch = new SequentialChainingHashing<>();
        itemNameScanner = new Scanner(System.in);
        itemKeyScanner = new Scanner(System.in);
        deleteItem = new Scanner(System.in);
        deleteChoice = new Scanner(System.in);
        end = new Scanner(System.in);
        String ended = "n";
        System.out.println("Hash table via separate chaining");
        while(!ended.equals("end")) {
            String itemName;
            int itemValue;
            String choice;
            System.out.print("Enter string key -> ");
            itemName = itemNameScanner.nextLine();
            System.out.println();
            System.out.print("Enter int value -> ");
            itemValue = itemKeyScanner.nextInt();
            sch.put(itemName,itemValue);
            System.out.println();
            System.out.print("Current hash table values ");
            sch.printChainAll();
            System.out.println();
            System.out.print("Chain  " + sch.hash(itemName) + " ");
            sch.printChain(sch.hash(itemName));
            System.out.println();
            System.out.print("If you wish to delete any value type x : -  " );
            choice = deleteChoice.nextLine();
            if(choice.equals("x")) {
                System.out.print("Enter item to delete: " );
                String key = deleteItem.nextLine();
                sch.delete(key);
                System.out.print("Current hash table values ");
                sch.printChainAll();
                System.out.println();
                System.out.print("Chain  " + sch.hash(key) + " ");
                sch.printChain(sch.hash(key));
                System.out.println();
            }
            System.out.print("If you wish to exit sequential chaining hash operations type the word 'end' ->  ");
            ended = end.nextLine();
        }
    }
}
