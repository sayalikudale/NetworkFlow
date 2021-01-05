/**
 * Class to represent a Node
 * Node has id and label
 * @author Sayali Kudale
 */
public class Node {

    private int Id;
    private String Label;
    /**
     *
     * @param id
     * @param label
     */
    public Node(int id, String label) {
        Id = id;
        Label = label;
    }

    /**
     * accessor for the id
     * @return
     * pre: node object should be initialised
     * post: returns the id of node
     */
    public int getId() {
        return Id;
    }

    /**
     *accessor to set the id
     * @param id
     * pre: node object should be initialised
     * post: id of node object is getting set
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     *accessor to the label
     * @return
     * pre: node object should be initialised
     * post: set the label of node to given value
     */
    public String getLabel() {
        return Label;
    }

}
