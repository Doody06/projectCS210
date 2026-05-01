public class ActionStack {
    private TaskNode top;
    private int size;

    public ActionStack() {
        this.top = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
    public void push(TaskNode newNode) {
        if (newNode == null) {
            return;
        }
        newNode.next = top;
        top = newNode;
        size++;
    }
    public TaskNode pop() {
        if (isEmpty()) {
            return null;
        }
        TaskNode removedNode = top;
        top = top.next;
        removedNode.next = null; 
        size--;
        return removedNode;
    }
    public TaskNode peek() {
        return top;
    }
    public void printStack(){
        TaskNode current = top;
        if (current == null){
            System.out.println("the stack is empty");
            return;
        }
        while (current != null){
            System.out.println(current);
            current = current.next;
        }
    }
}