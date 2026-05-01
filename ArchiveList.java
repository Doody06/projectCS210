public class ArchiveList {
    private TaskNode head;
    private TaskNode tail;
    private int size;
    public ArchiveList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    public boolean isEmpty() {
        return this.head == null;
    }
    public int size() {
        return this.size;
    }
    public void append(TaskNode newNode) {
        if (newNode == null) {
            return;
        }
        newNode.next = null;
         if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }
      public void printHistory() {
        TaskNode current = head;

        if (current == null) {
            System.out.println(" the Archive is empty ");
            return;
        }
        
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
    } 

} 