/**
 * This Class is the starting point of program
 * Functionality includes:
 * 1. read inputs from given file
 * 2. validate inputs
 * 3. exception handling
 * 4. create nodes and initial residue graph from given inputs
 * 5. pass graph to the method to compute result
 * @author Sayali Kudale
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BipartiteMatching {

    /**
     *This main method
     * 1. prepare the Node List from given input file
     * 2. prepare the residual graph from given input
     * 3. print the error message if any
     * 4. Pass the graph to the method to find maximum bipartite matching
     * @param args
     * pre: none
     * post: print error to the console if input is not valid otherwise pass graph to the MaximumBipartiteMatching class
     */
    public static void main(String args[]){

        final String inputFileName = "program3data.txt";
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        int numberOfRecords = 0,numberOfEdges=0;
        try {
            fileReader = new FileReader(inputFileName);
            bufferedReader = new BufferedReader(fileReader);
            numberOfRecords = Integer.parseInt(bufferedReader.readLine().trim());

            //special case for 0 input
            if (numberOfRecords == 0)
                throw new IllegalArgumentException("File is empty");

            //special case for 1 input
            if (numberOfRecords == 1)
                throw new IllegalArgumentException("Number of nodes should be " +
                        "at least two to find matching");


            Graph graph=new Graph(numberOfRecords);

            createNodes(bufferedReader, numberOfRecords,graph);

            numberOfEdges = Integer.parseInt(bufferedReader.readLine().trim());

            //special case for 0 input
            if (numberOfEdges == 0)
                throw new IllegalArgumentException("Edges information is missing");
            else {

                createGraph(bufferedReader,numberOfEdges,graph);

                createSourceSink(graph,numberOfRecords);

                createInitialResidue(graph);

                MaximumBipartiteMatching maxBPT=new MaximumBipartiteMatching();

                maxBPT.findMaxMatching(graph);
            }


        } catch (FileNotFoundException fnfe) {
            System.err.println(inputFileName + " file not found !!");
        } catch (IOException ioe) {
            System.err.println("Error in Input Data!! \n" + ioe.getMessage());
        } catch (NumberFormatException nfe) {
            System.err.println("Nodes are not in number format " + nfe.getMessage());
        } catch (NullPointerException npe) {
            System.err.println("Input data is Invalid: " + npe.getMessage());
        } catch (IndexOutOfBoundsException iobe) {
            System.err.println("Input data is Invalid: " + iobe.getMessage());
        } catch (IllegalArgumentException iae) {
            System.err.println("Input data is Invalid: " + iae.getMessage());
        } finally {
            try {
                if (bufferedReader != null && fileReader != null) {
                    bufferedReader.close();
                    fileReader.close();
                }
            } catch (IOException ioe) {
                System.err.println("Error in InputStream close(): " + ioe);
            }
        }
    }

    /**
     * This method
     * 1. reads input from the given file and create node objects
     * 2. Pass node object to the addNode method
     * @param bufferedReader
     * @param numberOfRecords
     * @param g
     * @throws IOException
     * @throws NumberFormatException
     * pre: bufferedReader should be initialised
     * post: returns nodes gets added in the graph and nodeList
     */
    private static void createNodes(BufferedReader bufferedReader, int numberOfRecords, Graph g) throws IOException,
            NumberFormatException {
        for (int i = 1; i <= numberOfRecords; i++) {
            Node node=new Node(i,bufferedReader.readLine());
            g.addNodes(node);
        }
    }

    /**
     * This method
     * 1. reads input from the given file and gets the corrosponding node objects from nodeList and create Edge objects
     * 2. if input contains extra spaces then ignores it
     * 3. Pass edge object to the addEdge method
     * @param bufferedReader
     * @param numberEdges
     * @param g
     * @throws IOException
     * pre: bufferedReader should be initialised and nodes object should be present in the nodeList
     * post: initial graph gets created using input data
     */
    private static void createGraph(BufferedReader bufferedReader, int numberEdges, Graph g)throws IOException,IndexOutOfBoundsException{

        for (int i=0;i<numberEdges;i++){
            String[] arr = bufferedReader.readLine().split("\\s+");
            Node source=g.getNodeFromList(Integer.parseInt(arr[0])-1);
            Node dest=g.getNodeFromList(Integer.parseInt(arr[1])-1);
            Edge e=new Edge(source,dest);
            g.addEdge(e);
        }
    }

    /**
     *This method
     * 1. give call to the method which connects left node sets to source
     * 2. give call to the method which connects right node sets to sink
     * @param g
     * pre: initial graph structure should be created
     * post: initial residue graph gets created by connecting edges to source and sink nodes
     */
    private static void createInitialResidue(Graph g){
        g.connectSourceToLeft();
        g.connectSinkToRight();
    }

    /**
     *This method
     * 1. create object of source node with id 0
     * 2. create object of sink node with the id as number of nodes plus one
     * @param g
     * @param size
     * pre: initial graph structure should be created
     * post: source and sink nodes gets created in the initial residue graph
     */
    public  static void createSourceSink(Graph g, int size){
        Node source= new Node(0,"source");
        Node sink= new Node(size+1,"sink");
        g.addSourceSink(source,sink);
    }
}
