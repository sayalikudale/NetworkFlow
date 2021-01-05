/**
 * This Class represent the object of Edge
 * Edge is represented by source node and dest node
 * and its flow and capacity
 * @author Sayali Kudale
 */
public class Edge {

    private static final int defaultEdgeCapacity = 1;	//This Flow network is for bipartite matching so the default capacity is always 1
    private static final int defaultEdgeFlow= 0;	//This Flow network is for bipartite matching so the default flow is always 0
    private int cap;
    private int flow;
    private Node source;
    private Node dest;

    /**
     *constructor to initialise Edge object
     * for flow and capacity default values are given
     * @param source
     * @param dest
     * pre: none
     * post: Edge object gets initialised
     */
    public Edge(Node source, Node dest) {
        this.flow = defaultEdgeFlow;
        this.cap=defaultEdgeCapacity;
        this.source = source;
        this.dest = dest;
    }

    /**
     *constructor to initialise Edge object by using source dest and flow
     *capacity default values is given
     * @param source
     * @param dest
     * @param flow
     * pre: none
     * post: Edge object gets initialised
     */
    public Edge(Node source, Node dest,int flow) {
        this.flow = flow;
        this.cap=defaultEdgeCapacity;
        this.source = source;
        this.dest = dest;
    }

    /**
     *accesor for the source node
     * @return
     * pre: edge object should be initialised
     * post: returns the source node that belongs to edge object
     */
    public Node getSource() {
        return source;
    }

    /**
     *accesor for the dest node
     * @return
     * pre: edge object should be initialised
     * post: returns the dest node that belongs to edge object
     */
    public Node getDest() {
        return dest;
    }

    /**
     * this method returns true if flow in a edge object is 1
     * @return
     * pre: edge object should be initialised
     * post: true and false is getting returned based on the flow value
     */
    public Boolean isAugmented() {

        return this.flow==1?true:false;
    }

}
