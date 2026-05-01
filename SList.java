public class SList {
    private TaskNode head;
    private int size;

    public SList() {
        this.head = null;
        this.size = 0;
    }
    public boolean isEmpty() {
        return this.head == null;
    }
    public int size() {
        return this.size;
    }
    //I think we can change its name to "Insert"
    public void addLast(TaskNode newNode) {
        if (newNode == null) {
            return;
        }
        newNode.next = null;
        if(head == null) {
            head = newNode;
        } else {
            TaskNode current = head;
            // Traverse only up to current size to avoid following queue/stack links
            // We needed to add this because an infinite loop was happening randomly when different data structures have the same next.
            for(int i = 0; i < size && current.next != null; i++) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }
    public TaskNode search(String taskID) {
        TaskNode current = head;
        while (current != null) {
            if (current.taskID.equals(taskID)) {
                return current;
            }
            current = current.next;
        }
        return null; 
    }
    public TaskNode remove(String taskID) {
        if (head == null) {
            return null; 
        }
        if (head.taskID.equals(taskID)) {
            TaskNode removedNode = head;
            head = head.next; 
           removedNode.next = null;
            size--;
            return removedNode;
        }

        TaskNode previous = head;
        TaskNode current = head.next;

        while (current != null) {
            if (current.taskID.equals(taskID)) {
                previous.next = current.next;
                current.next = null;
                size--;
                return current;
            }

            previous = current;
            current = current.next;
        }

        return null;
    }

    public void printList() {
        TaskNode current = head;

        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
    }

    public TaskNode completeTask(String taskID) {
        TaskNode task = search(taskID);
        if (task != null) {
            task.status = "Completed";
        }
        return task;
    }
}