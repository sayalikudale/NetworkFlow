/**
 * This Class implements Dinics algorithm to find maximum bipartite matching
 * Functionality includes:
 * 1. creating level graph from residual graph for a phase
 * 2. advancing from source to sink
 * 3. augment the path in residual graph
 * 4. remove back edges from level graph
 * 5. retreat if no further path available and remove incident edges from node
 * 6. determine the maximum bipartite matching and print the output
 * @author Sayali Kudale
 */

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;

public class MaximumBipartiteMatching {
    private Graph levelG;
    private Graph residualGraph;
    private LinkedList<Edge> path;
    private boolean matchingExists=true;
    private int numberOfNodes;

    /**
     *This method
     * 1. stores the node information and residual graph in local variables
     * 2. continue the phases until termination condition
     * 3. after termination give call to method to print output
     * @param graph
     * pre: residual graph and nodelist should be created
     * post: print the bipartite matching
     */
    public void findMaxMatching(Graph graph){

        this.residualGraph =graph;
        this.numberOfNodes= residualGraph.getNoOfNodesInResidual();

        while (matchingExists){
            levelG=createLevelGraph();
            path = new LinkedList<Edge>();
            int sourceIndex=0;
            advance(sourceIndex);
        }
        printBipartiteMatching();
    }

    /**
     * This method
     * 1. gets the list of edges of given source index
     * 2. if source edges has outgoing edges then add it to the path, get the first edge in list and advance to dest again
     * 3. if source edges doesnt have outgoing edges then give call to retreat  method
     * 4. if source index is same as sink then
     *      - give call to augment method for augmenting in residual graph
     *      - give call to the removeBackEdges method to remove augmented edges from level graph
     *      - set sourceIndex to source and perfrom advance again
     * @param sourceIndex
     * pre: nodelist, level graph and residual graph should be created
     * post: augment the residual graph from level graph for this phase
     */
    public void advance(int sourceIndex){

        if(sourceIndex== residualGraph.getSinkNode().getId()){
            augment(path);
            removeBackEdges(path);
            path = new LinkedList<>();
            sourceIndex=0;
            advance(sourceIndex);
        }else if(levelG.numberOfOutgoingEdges(sourceIndex)>0) {
            Edge next=levelG.getNextEdgeToAdvance(sourceIndex,path);
            if(next!=null && next.getDest().getId()!=residualGraph.getSourceNode().getId()) {
                path.add(next);
                sourceIndex = next.getDest().getId();
                advance(sourceIndex);
            }else
                retreat(sourceIndex);
        }else
            retreat(sourceIndex);
    }

    /**
     *This method
     * 1. check if source index is at source node
     * 2. if yes then give call to isAugmentingPathAvailable method to check if next phase is needed
     *     if method returns false then make terminate condition true
     * 3. if no then give call to removeIncidentEdgesOfNode to remove incident edges from sourceIndex
     *     and remove given edge from path  and advance by using previous node
     * @param sourceIndex
     * pre: nodelist, level graph and residual graph should be created
     * post: continue the phase or terminate it
     */
    private void retreat(int sourceIndex){

        if(sourceIndex== residualGraph.getSourceNode().getId()){

            if(!isAugmentingPathAvailable()){
                matchingExists=false;
            }
        }
        else {
            levelG.removeIncidentEdgesOfNode(sourceIndex);
            int newSource= removeLastEdgeFromPath();
            advance(newSource);
        }
    }

    /**
     *This method iterate through all the edges from path and give call to removeEdgeFromSource and addReverseEdge
     * methods to reverse the flow of augmented edges in residual graph
     * @param path
     * pre: residual graph and path shoule be created
     * post: edges present in path will be reversed augmented flow in residual graph
     */
    private void augment(LinkedList<Edge> path){

        for (Edge e:path) {
            residualGraph.removeEdgeFromSource(e);
            residualGraph.addReverseEdge(e);
        }
    }

    /**
     * This method removes the last edge from  path and
     * returns the source index of removed edge
     * @return
     * pre: path should have edges
     * post: returns the source index  of removed edge
     */
    private int removeLastEdgeFromPath(){

        Edge e=path.removeLast();
        return e.getSource().getId();
    }

    /**
     *This method iterate through all the edges from path and remove those edges from level graph
     * @param path
     * pre: level graph and path should be created
     * post: edges present in path will be removed from level graph
     */
    private void removeBackEdges(LinkedList<Edge> path){
        for (Edge e:path) {
            levelG.removeEdgeFromSource(e);
        }
    }

    /**
     *This method performs bfs on residual graph and creates level graph
     * @return
     * pre: nodelist and residual graph should be created
     * post: level graph gets created
     */
    private Graph createLevelGraph(){

        boolean visited[] = new boolean[numberOfNodes];
        Graph levelGraph=new Graph(residualGraph.getNoOfNodes());
        Node source= residualGraph.getSourceNode();
        levelGraph.createAllNodes(numberOfNodes);
        Deque<Integer> q = new ArrayDeque<>(numberOfNodes);
        q.offer(source.getId());
        visited[0]=true;

        while (!q.isEmpty()) {
            int node = q.poll();
            ArrayList<Edge> levelSource= residualGraph.getNodeFromGraph(node);
            for (Edge edge : levelSource) {
                Node dest=edge.getDest();
                if(!visited[dest.getId()]){
                    visited[dest.getId()]=true;
                    q.offer(dest.getId());
                }
                Edge e= new Edge(edge.getSource(),edge.getDest());
                levelGraph.addEdge(e);
            }
        }
        return levelGraph;
    }

    /**
     *This method checks the augmented edges by iterating over the right set of the nodes in the residual graph
     * and prints the matching edges information
     * pre: residual graph should be created
     * post: prints the matching in bipartite graph in required format
     */
    private void printBipartiteMatching(){

        int leftIndex= residualGraph.getSetSize();
        int rightIndex= residualGraph.getNoOfNodesInResidual();
        int matchingCount =0;
        for(int i=leftIndex+1; i<rightIndex-1 ;i++){
            Edge edge= residualGraph.getAugmentedEdge(i);
            if(edge!=null){
                System.out.println(edge.getDest().getLabel() + " / "+edge.getSource().getLabel());
                matchingCount++;
            }
        }
        System.out.println(matchingCount +" total matches");
    }

    /**
     *This method checks whether augmenting path available from source to the sink by
     * performing bfs on the residual graph
     * @return
     * pre: residual graph should be created and initialised
     * post: returns true if sink is reachable from source
     */
    public boolean isAugmentingPathAvailable(){
        boolean visited[] = new boolean[numberOfNodes];
        Node source= residualGraph.getSourceNode();
        Deque<Integer> q = new ArrayDeque<>(numberOfNodes);
        q.offer(source.getId());
        visited[0]=true;

        while (!q.isEmpty()) {
            int node = q.poll();
            ArrayList<Edge> sourceEdges= residualGraph.getNodeFromGraph(node);
            for (Edge edge : sourceEdges) {
                Node dest=edge.getDest();
                if(!visited[dest.getId()]){
                    visited[dest.getId()]=true;
                    q.offer(dest.getId());
                }
            }
        }
        return visited[numberOfNodes-1];
    }

}
