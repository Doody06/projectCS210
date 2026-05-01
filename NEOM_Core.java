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
        // FIX: Create separate TaskNode objects for each data structure.
        // Reason: one TaskNode has only one "next", so sharing the same node breaks Queue/Stack/List.
        TaskNode sectorTask = new TaskNode(sectorID, taskID, desc, "Pending");
        TaskNode queueTask = new TaskNode(sectorID, taskID, desc, "Pending");
        TaskNode stackTask = new TaskNode(sectorID, taskID, desc, "Pending");

        AVLNode sectorNode = sectors.Search(sectorID);

        if (sectorNode == null) {
            sectors.Insert(sectorID);
            sectorNode = sectors.Search(sectorID);
        }

        // FIX: Add the sector copy into the AVL sector task list.
        sectorNode.tasks.addLast(sectorTask);

        // FIX: Add the queue copy into the deployment queue.
        deployment.enqueue(queueTask);

        // FIX: Add the stack copy into the undo stack.
        undoLog.push(stackTask);
    }

  public void processNextTask() {
        TaskNode nextTask = deployment.dequeue();

        // FIX: Prevent crash if the queue is empty.
        if (nextTask == null) {
            System.out.println("No task to process.");
            return;
        }

        // FIX: Complete the real task stored inside the AVL tree.
        TaskNode completedTask = sectors.completeTask(nextTask);

        if (completedTask != null) {
            // FIX: Archive should get its own node copy.
            TaskNode archiveTask = new TaskNode(
                    completedTask.sectorID,
                    completedTask.taskID,
                    completedTask.description,
                    completedTask.status
            );

            history.append(archiveTask);

            System.out.println("Processed task: " + completedTask.taskID);
        } else {
            System.out.println("Task not found in sector list.");
        }
    }

     public void undoLastAction() {
        TaskNode removedTask = undoLog.pop();

        // FIX: Prevent crash if stack is empty.
        if (removedTask == null) {
            System.out.println("Nothing to undo.");
            return;
        }

        // FIX: Do not remove the whole sector.
        // We only remove the task from that sector's linked list.
        AVLNode sectorNode = sectors.Search(removedTask.sectorID);

        if (sectorNode != null) {
            sectorNode.tasks.remove(removedTask.taskID);

            // Optional: if the sector has no tasks left, remove the sector from AVL tree.
            if (sectorNode.tasks.size() == 0) {
                sectors.Remove(removedTask.sectorID);
            }
        }

        // FIX: Remove the same task from the queue, not just the front task.
        deployment.remove(removedTask.taskID);

        System.out.println("Undid task: " + removedTask.taskID);
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
