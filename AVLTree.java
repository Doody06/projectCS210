public class AVLTree {
    AVLNode root;
    int size;
    //the pdf suggests we will need more attributes
    //run through the entire logic of the tree to see if it makes sense */
    public AVLTree() {
        root = null;
        size = 0;
    }

    //might need a helper class for balancing
    public void Insert(int sectorID) {
        root = Insert(root, sectorID);
        
    }       

    //I intentionally used method overloading a lot in the AVL tree
    private AVLNode Insert(AVLNode currentNode, int sectorID) {
        if(currentNode == null) {
            size++;
            return new AVLNode(sectorID);
        }
        if(currentNode.sectorID > sectorID) {
            currentNode.left = Insert(currentNode.left, sectorID);
        } else if(currentNode.sectorID < sectorID) {
            currentNode.right = Insert(currentNode.right, sectorID);
        } else {
            
            return currentNode;
        }
        updateHeight(currentNode);
        return Balance(currentNode); 
    }

    public AVLNode Remove(int sectorID) {
        return Remove(root, sectorID);
    }
    
    private AVLNode Remove(AVLNode currentNode, int sectorID) {
        if(currentNode == null) return null;
        if(currentNode.sectorID > sectorID) {
            currentNode.left = Remove(currentNode.left, sectorID);
        }
        else if(currentNode.sectorID < sectorID) {
            currentNode.right = Remove(currentNode.right, sectorID);
        }
        else {
            if(currentNode.left == null) return currentNode.right;
            if(currentNode.right == null) return currentNode.left;

            AVLNode replacer = findMin(currentNode.right);
            currentNode.sectorID = replacer.sectorID;
            currentNode.tasks = replacer.tasks;
            currentNode.right = Remove(currentNode.right, replacer.sectorID);

            updateHeight(currentNode);
            size--;
        }
        //I made a random change here, this might cause an error (I'm sleepy)
        currentNode = Balance(currentNode);
        return currentNode;
    }

    private AVLNode findMin(AVLNode node) {
        while(node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    private int returnHeight(AVLNode node) {
        if(node == null) return 0;
        return node.height;
    }
    private void updateHeight(AVLNode node) {
        //instead of doing multiple ifs
        //math.max does it in one line
        node.height = 1 + Math.max(returnHeight(node.left), returnHeight(node.right));
    }

    //we will use LVR 
    public AVLNode Search(int sectorID) {
        return Search(root, sectorID, 0);
    }

    private AVLNode Search(AVLNode currentNode, int sectorID, int comparisons) {
        if (currentNode == null) {
            //I'm pretty sure printing here is considered bad practice but it's the simplest way to use the number of comparisons I could think of.
            System.out.println("Sector " + sectorID + " not found after " + comparisons + " comparisons.");
            return null;
        }

        if(currentNode.sectorID == sectorID) {
            System.out.println("Sector " + sectorID + " found after " + comparisons + " comparisons.");
            return currentNode;
        }
        if(currentNode.sectorID > sectorID) {
            return Search(currentNode.left, sectorID, comparisons + 1);
        }
        if(currentNode.sectorID < sectorID) {
            return Search(currentNode.right, sectorID, comparisons + 1);
        }
        return null; //I put this just in case but I think the first line does the same thing
    }

    public void Traverse(AVLNode currentNode) {
        if(currentNode == null) return;
        Traverse(currentNode.left);
        System.out.println(currentNode); 
        Traverse(currentNode.right);
    }

    private AVLNode Balance(AVLNode currentNode) {
        if(currentNode == null) return null;
        
        int balanceFactor = returnHeight(currentNode.left) - returnHeight(currentNode.right);
        if(balanceFactor >= 2) {
            if(currentNode.left == null) return currentNode;
            
            int lowerBalanceFactor = returnHeight(currentNode.left.left) - returnHeight(currentNode.left.right);
            if(lowerBalanceFactor < 0) {
                //leftright case
                currentNode.left = RotateTreeLeft(currentNode.left);
                return RotateTreeRight(currentNode);
            } else {
                //leftleft case 
                return RotateTreeRight(currentNode);
            }
        }

        if(balanceFactor <= -2) {
            if(currentNode.right == null) return currentNode;
            
            int lowerBalanceFactor = returnHeight(currentNode.right.left) - returnHeight(currentNode.right.right);
            if(lowerBalanceFactor > 0) {
                //rightleft case
                currentNode.right = RotateTreeRight(currentNode.right);
                return RotateTreeLeft(currentNode);
            } else {
                //rightright case 
                return RotateTreeLeft(currentNode);
            }
        }
        return currentNode;
    }
    //this is for the leftleft and leftright cases
    public AVLNode RotateTreeRight(AVLNode node) {
        AVLNode newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;

        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    //this is for the rightright and rightleft cases
    public AVLNode RotateTreeLeft(AVLNode node) {
        AVLNode newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;

        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }
    public TaskNode completeTask(TaskNode task) {
        AVLNode sectorNode = Search(task.sectorID);
        if(sectorNode != null) {
            return sectorNode.tasks.completeTask(task.taskID);
        }
        return null;
    }

    @Override
    public String toString() {
        Traverse(root);
        return null;
    }
    }
    //The pdf suggests we may need more methods, there might be one in tasks
