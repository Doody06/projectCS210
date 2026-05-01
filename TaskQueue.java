public class TaskQueue {
    private TaskNode front;
    private TaskNode rear;
    private int size;
    public TaskQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }
    public boolean isEmpty() {
        return this.front == null;
    }
    public int size() {
        return this.size;
    }
    public void enqueue(TaskNode newNode) {
        if (newNode == null) {
            return;
        }
        newNode.next = null;
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }
    public TaskNode dequeue() {
        if (isEmpty()) {
            return null;
        }
        TaskNode removedNode = front;
        front = front.next;
        removedNode.next = null; 
        size--;
        if (isEmpty()) {
            rear = null; 
        }
        return removedNode;
    }
    
    public TaskNode peek(){
        if (isEmpty()) {
            return null;
        }
        return front; 

    }
    public TaskNode remove(String taskID) {
        if (isEmpty()) {
            return null; 
        }
        if (front.taskID.equals(taskID)) {
            return dequeue(); 
        }

        TaskNode previous = front;
        TaskNode current = front.next;

        while (current != null) {
            if (current.taskID.equals(taskID)) {
                previous.next = current.next;
                current.next = null;
                size--;
                if (current == rear) {
                    rear = previous; 
                }
                return current; 
            }
            previous = current;
            current = current.next;
        }
        return null; 
    }
public void printQueue() {
        TaskNode current = front;
           if (current == null) {
            System.out.println("Queue is empty.");
            return;
        }
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
} 
}