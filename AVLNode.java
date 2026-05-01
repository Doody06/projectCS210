
public class AVLNode {
    int sectorID;
    int height;
    SList tasks;
    AVLNode left;
    AVLNode right;

    public AVLNode(int sectorID) {
        this.sectorID = sectorID;
        this.height = 0;
        this.tasks = new SList();
        this.left = null;
        this.right = null;
    }
    public AVLNode(int sectorID, int height, SList tasks, AVLNode left, AVLNode right) {
        this.sectorID = sectorID;
        this.height = height;
        this.tasks = tasks;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "AVLNode{" +
                "sectorID=" + sectorID +
                ", height=" + height +
                ", tasks=" + tasks +
                //Java conditional operator means (condition) ? (value if true) : (value if false)
                ", left=" + (left != null ? left.sectorID : "null") +
                ", right=" + (right != null ? right.sectorID : "null") +
                '}';
    }
}