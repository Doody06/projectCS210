public class TaskNode {
    int sectorID;
    String taskID;
    String description;
    String status;
    TaskNode next;
    public TaskNode(int sectorID, String taskID, String description, String status) {
        this.sectorID = sectorID;
        this.taskID = taskID;
        this.description = description;
        this.status = status;
        this.next = null;
    }
    public String toString() {
        return "Sector: " + sectorID +
               ", Task ID: " + taskID +
               ", Description: " + description +
               ", Status: " + status;

    }
}