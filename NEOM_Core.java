public class NEOM_Core {
    AVLTree sectors; 
    TaskQueue deployment;
    ActionStack undoLog;
    ArchiveList history;

    public NEOM_Core() {
        sectors = new AVLTree();
        deployment = new TaskQueue();
        undoLog = new ActionStack();
        history = new ArchiveList();
    }
    
    public void addTask(int sectorID, String taskID, String desc) {
        TaskNode newTask = new TaskNode(sectorID, taskID, desc, "Pending");
        //insert task into Node in AVL tree if it exists, else make the node and insert
        AVLNode sectorNode = sectors.Search(sectorID);
        if(sectorNode == null) {
            sectors.Insert(sectorID);
            sectorNode = sectors.Search(sectorID);
        }
        sectorNode.tasks.addLast(newTask);
        //insert into Queue
        deployment.enqueue(newTask);
        //push onto the stack
        undoLog.push(newTask);
    }

    public void processNextTask() {
        //dequeue next task
        TaskNode nextTask = deployment.dequeue(); 
        //update its status in the AVL Tree to "Completed"
        sectors.completeTask(nextTask);
        //Insert it at the tail of the ArchiveList
        history.append(nextTask);
    }

    public void undoLastAction() {
        TaskNode removedTask = undoLog.pop();
        sectors.Remove(removedTask.sectorID);
        deployment.dequeue(); 
        System.out.println("Undid task: " + removedTask);
    }

    public void systemAudit() {
        //Perform LVR traversal of AVL tree and print all sectors and their tasks
        sectors.Traverse(sectors.root); //I'll implement the printing inside the Traverse
    }

    public void printDeploymentHistory() {
        //Traverse ARchiveList from head to tail and print all tasks in order
        history.printHistory(); //Abdulaziz will implement the printing in the Traverse
    }

    public void searchSector(int sectorID) {
        System.out.println(sectors.Search(sectorID)); 
        System.out.println("Number of sectors: " + sectors.size);
        //print number of comparisons vs. total Sectors (demonstrate that it is O(log n))
        //I decided to print number of comparisons vs. total Sectors in the Search method itself.

    }
    @Override
    public String toString() {
        return "NEOM_Core{" +
                "sectors=" + sectors +
                ", deployment=" + deployment +
                ", undoLog=" + undoLog +
                ", history=" + history +
                '}';
    }
}
