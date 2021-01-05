/**
 * This class defines the graph data structure
 * Graph is represented by the adjacencyList which is collection of edges
 * and performs basic graph operations
 * @author Sayali Kudale
 */
import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {

    private int setSize;
    private int noOfNodes;
    private int noOfNodesInResidual;
    private ArrayList<ArrayList<Edge>> adjacencyList;
    private ArrayList<Node> nodeList;

    /**
     *constructor to initialise graph object
     * residual nodes size is including source and sink node
     * set size is half of the original graph nodes
     * @param nodes
     * pre: none
     * post: graph object gets initialised
     */
    public Graph(int nodes) {
        this.noOfNodes=nodes;
        this.noOfNodesInResidual=nodes+2;
        this.setSize=nodes/2;
        this.adjacencyList = new ArrayList<>();
        nodeList= new ArrayList<>();
    }

    /**
     * accessor for setSize
     * pre: none
     * post: returns value of setSize */
    public int getSetSize() {
        return setSize;
    }

    /**
     * accessor for noOfNodes
     * pre: none
     * post: returns value of noOfNodes */
    public int getNoOfNodes() {
        return noOfNodes;
    }

    /**
     * accessor for noOfNodesInResidual
     * pre: none
     * post: returns value of noOfNodesInResidual */
    public int getNoOfNodesInResidual() {
        return noOfNodesInResidual;
    }

    /**
     * add node object to the node list
     * @param node
     * pre: node list should be created
     * post: node gets added in the list of nodes */
    public void addNodeToList(Node node){
        nodeList.add(node);
    }

    /**
     * This method returns the source node which is at index 0
     * @return
     * pre: nodelist should be created and source node object added in the nodelist
     * post: returns the source node from nodeList
     */
    public Node getSourceNode(){
        return nodeList.get(0);
    }

    /**
     * This method returns the sink node which is at the last index
     * @return
     * pre: nodeList should be created and source node object added in the nodelist
     * post: returns the source node from nodeList
     */
    public Node getSinkNode(){
        return nodeList.get(nodeList.size()-1);
    }

    /**
     * This method returns the node from nodelist for specified id
     * @param id
     * @return
     * pre: nodeList should be created and should have element at specified index
     * post: returns the node object for given id
     */
    public Node getNodeFromList(int id) throws IndexOutOfBoundsException{
        return nodeList.get(id);
    }

    /**
     * This method performs add operation on adjacencyList and add edge to the source index of the edge
     * @param e
     * pre: adjacencyList should be created and initial node added at the source index
     * post: edge object gets added to the source node index
     */

    public void addEdge(Edge e) {
        adjacencyList.get(e.getSource().getId()).add(e);
    }

    /**
     * This method adds node object to the nodelist as well as initialised new arraylist of edges in graph adjacencyList
     * @param node
     * pre: adjacencyList should be created
     * post: node gets added in graph and nodelist
     */
    public void addNodes(Node node){
        ArrayList<Edge> graphNode=new ArrayList<Edge>();
        adjacencyList.add(graphNode);
        addNodeToList(node);
    }

    /**
     * This method returns the node data for specified in node index
     * @param node
     * @return
     * pre: adjacencyList object should be created and node should be added at the given index
     * post: returns the node data from the given index of adjacencyList
     */
    public  ArrayList<Edge> getNodeFromGraph(int node){
        return adjacencyList.get(node);
    }

    /**
     * This method add source node at 0th index and sink node at the last index in nodelist and adjacencyList
     * @param source
     * @param sink
     * pre: nodelist and adjacencyList object should be initialized
     * post: sink and source node gets added in the nodelist and adjacencyList
     */
    public void addSourceSink(Node source,Node sink){
        ArrayList<Edge> sourceNode=new ArrayList<Edge>();
        adjacencyList.add(sourceNode);
        nodeList.add(0,source);
        ArrayList<Edge> sinkNode=new ArrayList<Edge>();
        adjacencyList.add(sinkNode);
        nodeList.add(sink);
    }

    /**
     * This method connect source node to the left set of nodes in a graph
     * pre: nodes and source nodes should be present in the graph
     * post: edge added with dest as each node in left set from source node
     */
    public void connectSourceToLeft(){
        int leftIndex= getSetSize();
        Node source=getSourceNode();
        for (int i=1;i<=leftIndex;i++){
            Node dest=getNodeFromList(i);
            Edge e=new Edge(source,dest);
            addEdge(e);
        }
    }

    /**
     * This method connect sink node to the right set of nodes in a graph
     * pre: nodes and sink nodes should be present in the graph
     * post: edge added with dest as sink from each node of right set
     */
    public void connectSinkToRight(){
        int rightIndex= getSetSize();
        Node sink=getSinkNode();
        for (int i=rightIndex+1;i<=noOfNodes;i++){
            Node source=getNodeFromList(i);
            Edge e=new Edge(source,sink);
            addEdge(e);
        }
    }

    /**
     * This method removes the given edge object from the source index of adjacencyList
     * @param edge
     * pre: adjacencyList should have node data for the specified sourceIndex
     * post: removes edge from the sourceIndex of the adjacencyList
     *
     */
    public void removeEdgeFromSource(Edge edge){
        ArrayList<Edge> sourceEdges= adjacencyList.get(edge.getSource().getId());
        for (int i=0;i<sourceEdges.size();i++) {
            if(sourceEdges.get(i).getDest().getId()==edge.getDest().getId()){
                sourceEdges.remove(i);
            }
        }
    }

    /**
     * This method remove edge for the given source and dest index
     * @param sourceIndex
     * @param destIndex
     * pre: adjacencyList should have node data for the specified sourceIndex
     * post: removes edges from the adjacencyList for given sourceIndex and dest index
     */
    public void removeEdgeByIndex(int sourceIndex,int destIndex){
        ArrayList<Edge> edgeList= adjacencyList.get(sourceIndex);
        for (int i=0;i<edgeList.size();i++) {
            if(edgeList.get(i).getDest().getId()==destIndex){
                edgeList.remove(i);
            }
        }
    }


    /**
     * This method reverse the direction of flow for the given edge
     * @param e
     * pre: adjacencyList, source and dest nodes of edge should be present
     * post: new edge gets added adjacencyList with reverse direction
     */
    public void addReverseEdge(Edge e){
        Node newSource=e.getDest();
        Node newDest=e.getSource();
        Edge newEdge= new Edge(newSource,newDest,1);
        adjacencyList.get(newSource.getId()).add(newEdge);
    }

    /**
     *This method removes the incoming edges of the specified source index
     * @param index
     * pre: nodes should be added in the adjacency list
     * post: removes the incoming edges for the node in specified index
     */
    public void removeIncidentEdgesOfNode(int index){
        for (int i=0;i<noOfNodesInResidual;i++){
                removeEdgeByIndex(i,index);
            }
    }

    /**
     *This method returns the augmented edge for the speficied node index
     * @param sourceIndex
     * @return
     * pre: adjacencyList should be created and have the node created at specified source index
     * post:returns the augmented edge if present otherwise returns null
     */
    public Edge getAugmentedEdge(int sourceIndex){

        ArrayList<Edge> edgeList= adjacencyList.get(sourceIndex);

        for (Edge e:edgeList) {
            if(e.isAugmented())
                return e;
        }
        return null;
    }

    /**
     *This method creates the adjacency list for mentioned number of nodes
     * @param noOfNodes
     * pre: adjacencyList object should be created
     * post: ArrayList<Edge> objects gets added in adjacencyList for noOfNodes
     */
    public void createAllNodes(int noOfNodes){

        for (int i=0; i<noOfNodes;i++) {
            ArrayList<Edge> graphNode=new ArrayList<Edge>();
            adjacencyList.add(graphNode);
        }
    }

    /**
     * This method returns the total number of outgoing edges from given node index of adjacencyList
     * @param sourceIndex
     * @return
     * pre: adjacencyList should be created and node at given index should be present
     * post: returns the number of outgoing edges at source index of adjacencyList
     */
    public int numberOfOutgoingEdges(int sourceIndex){
        return adjacencyList.get(sourceIndex).size();
    }

    /**
     * This method returns a edge from node at given index of adjacencyList
     * note: as any path is shortest path in level graph so we are returning edge at 0th index
     * @param index
     * @return
     * pre: adjacencyList should be created and node at given index should be present
     * post: returns the edge at index 0 from given index from adjacencyList
\     */

    public Edge getNextEdgeToAdvance(int index, LinkedList<Edge> path){

        ArrayList<Edge> edgeList= adjacencyList.get(index);
        for (int i=0;i<edgeList.size();i++) {
            boolean destPresentInPath=false;
            Edge e=edgeList.get(i);

            //code may go wrong here
            for (Edge pathEdge:path) {
                if(e.getDest().getId()==pathEdge.getDest().getId()) {
                    destPresentInPath = true;
                    break;
                }
            }
            if(!destPresentInPath)
                return e;
        }
        return null;
    }

}
