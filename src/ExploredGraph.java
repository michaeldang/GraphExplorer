import sun.awt.image.ImageWatched;

import java.util.*;
import java.util.function.Function;

/**
 * @author Michael Dang mwldang@uw.edu
 * Extra Credit Options Implemented, if any:  A5E1
 *
 * This program is used to solve the popular game called Towers of Hanoi. This program allows the user to solve
 * the game through two different searching algorithms, depth first search and breadth first search.
 *
 * Solution to Assignment 5 in CSE 373, Autumn 2014
 * University of Washington.
 * This assignment requires Java 8 JDK
 *
 * There are totally 15 TODOs, please read the instructions carefully
 * and don't change the signature of the methods
 *
 * Starter code provided by Steve Tanimoto and Si J. Liu, Nov. 21, 2014.
 *
 */

public class ExploredGraph {
    public final int NUMBER_OF_PEGS = 3; // number of pegs in this game
    private Set<Vertex> Ve; // collection of explored vertices
    private Set<Edge> Ee; // collection of explored edges
    private int VeSize; // size of collection of explored vertices
    private int EeSize; // size of collection of explored edges
    private List<Operator> operators; // collection of operators (6 in this game)
    private HashMap<Vertex, LinkedList<Edge>> map; // map of successor vertex with its edges

    public ExploredGraph() {
        initialize();
    }

    /**
     * Resets the Towers of Hanoi game configuration
     */
    public void initialize() {
        Ve = new LinkedHashSet<Vertex>();
        Ee = new LinkedHashSet<Edge>();
        map = new HashMap<Vertex, LinkedList<Edge>>();
        VeSize = 0;
        EeSize = 0;
        setOperators();
    }


    /**
     * Constructs the different possible moves for the Towers of Hanoi game
     */
    private void setOperators() {
        // (i, j) = {(0, 1), (0, 2), (1, 0), (1, 2), (2, 0), (2, 1)}
        operators = new ArrayList<Operator>();
        for (int beginPeg = 0; beginPeg < NUMBER_OF_PEGS; beginPeg++) {
            for (int endPeg = 0; endPeg < NUMBER_OF_PEGS; endPeg++) {
                if (endPeg != beginPeg) {
                    operators.add(new Operator(beginPeg, endPeg));
                }
            }
        }
    }

    /**
     * Returns the number of vertices explored when performing the current search
     * @return The number of vertices explored when performing the current search
     */
    public int nvertices() {
        return VeSize;
    }

    /**
     * Returns the number of edges created when performing the current search
     * @return The number of edges created when performing the current search
     */
    public int nedges() {
        return EeSize;
    }

    /**
     * Completes the Towers of Hanoi game using a depth first search approach
     * @param vi The Vertex containing the starting configuration of the disks
     * @param vj The Vertex containing the finished configuration of the disks
     */
    public void dfs(Vertex vi, Vertex vj) {
        initialize();
        addNewVertex(vi);
        map.put(vi, new LinkedList<Edge>());
        dfsHelper(vi, vj);
    }

    /**
     * Helper method used to complete Towers of Hanoi using the depth first search approach
     * @param currVertex The Vertex containing the current configuration of the disks
     * @param vj The Vertex containing the finished configuration of the disks
     * @return Whether the game has reached the end disk configuration
     */
    private boolean dfsHelper(Vertex currVertex, Vertex vj) {
        if (currVertex != vj) {
            boolean foundSolution = false;
            for (int currOperatorIndex = 0; currOperatorIndex < operators.size() && !foundSolution; currOperatorIndex++) {
                Operator currOperator = operators.get(currOperatorIndex);
                if (currOperator.getPrecondition().apply(currVertex)) {
                    Vertex newVertex = currOperator.getTransition().apply(currVertex);
                    if (!Ve.contains(newVertex)) {
                        addNewVertex(newVertex);
                        addNewEdge(currVertex, newVertex);
                        foundSolution = dfsHelper(newVertex, vj);
                    }
                }
            }
            return foundSolution;
        } else {
            return true;
        }
    }

    /**
     * Completes the Towers of Hanoi game using a breadth first search approach
     * @param vi The Vertex containing the starting configuration of the disks
     * @param vj The Vertex containing the finished configuration of the disks
     */
    public void bfs(Vertex vi, Vertex vj) {
        initialize();
        Queue<Vertex> verticesToExplore = new LinkedList<Vertex>();
        addNewVertex(vi);
        verticesToExplore.add(vi);
        map.put(vi, new LinkedList<Edge>());
        boolean foundSolution = false;
        while (!verticesToExplore.isEmpty() && !foundSolution) {
            Vertex currVertex = verticesToExplore.remove();
            for (int currOperatorIndex = 0; currOperatorIndex < operators.size() && !foundSolution; currOperatorIndex++) {
                Operator currOperator = operators.get(currOperatorIndex);
                if (currOperator.getPrecondition().apply(currVertex)) {
                    Vertex newVertex = currOperator.getTransition().apply(currVertex);
                    if (!Ve.contains(newVertex)) {
                        addNewVertex(newVertex);
                        addNewEdge(currVertex, newVertex);
                        if (newVertex.equals(vj)) {
                            foundSolution = true;
                        } else {
                            verticesToExplore.add(newVertex);
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds a new Vertex to list of vertices explored
     * @param newVertex the Vertex that is being explored
     */
    private void addNewVertex(Vertex newVertex) {
        Ve.add(newVertex);
        VeSize++;
    }

    /**
     * Creates a new Edge
     * @param currVertex The current Vertex containing the current disk configuration
     * @param newVertex The new Vertex containing the new disk configuration
     */
    private void addNewEdge(Vertex currVertex, Vertex newVertex) {
        Edge newEdge = new Edge(currVertex, newVertex);
        Ee.add(newEdge);
        EeSize++;
        LinkedList<Edge> newVertexPath = (LinkedList<Edge>)map.get(currVertex).clone();
        newVertexPath.add(newEdge); //Creates the path to the new Vertex
        map.put(newVertex, newVertexPath); //Maps the new path to the new Vertex
    }

    /**
     * Finds the list of steps taken to get from the start Vertex to the end Vertex
     * @param vj The Vertex containing the finished configuration of the disks
     * @return The list of steps taken to get from the start Vertex to the end Vertex
     */
    public ArrayList<Vertex> retrievePath(Vertex vj) {
        ArrayList<Vertex> path = new ArrayList<Vertex>();
        LinkedList<Edge> vjPath = map.get(vj);
        path.add(vjPath.getFirst().vi);
        for (Edge currEdge: vjPath) {
            path.add(currEdge.vj);
        }
        return path;
    }

    /**
     * Finds the shortest path to get from the start Vertex to the end Vertex
     * @param vi The Vertex containing the starting configuration of the disks
     * @param vj The Vertex containing the finished configuration of the disks
     * @return The list of steps taken to get from the start Vertex to the end Vertex
     */
    public ArrayList<Vertex> shortestPath(Vertex vi, Vertex vj) {
        // TODO: return a shortest path as an array list
        bfs(vi, vj);
        return retrievePath(vj);
    }

    /**
     * The set of vertices explored when performing the current search
     * @return The set of vertices explored when performing the current search
     */
    public Set<Vertex> getVertices() {
        return Ve;
    }

    /**
     * The set of edges created when performing the current search
     * @return The set of edges created when performing the current search
     */
    public Set<Edge> getEdges() {
        return Ee;
    }

    public static void main(String[] args) {
        ExploredGraph eg = new ExploredGraph();
        Vertex v0 = eg.new Vertex("[[4,3,2,1],[],[]]");
        Vertex v1 = eg.new Vertex("[[],[],[4,3,2,1]]");
        eg.shortestPath(v0, v1);
        ArrayList<Vertex> answerPath = eg.retrievePath(v1);
        for (Vertex vertex: answerPath) {
            System.out.println(vertex);
        }
        System.out.println("VeSize: " + eg.VeSize);
        System.out.println("EeSize: " + eg.EeSize);

        v0 = eg.new Vertex("[[4,3,2,1],[],[]]");
        v1 = eg.new Vertex("[[],[],[4,3,2,1]]");
        eg.dfs(v0, v1);
        answerPath = eg.retrievePath(v1);
        for (Vertex vertex: answerPath) {
            System.out.println(vertex);
        }
        System.out.println("VeSize: " + eg.VeSize);
        System.out.println("EeSize: " + eg.EeSize);
    }

    class Vertex {
        Stack<Integer>[] pegs; // Each vertex will hold a Towers-of-Hanoi state.

        // There will be 3 pegs in the standard version, but more if you do
        // extra credit option A5E1.

        // Constructor that takes a string such as "[[4,3,2,1],[],[]]":
        @SuppressWarnings("unchecked")
        public Vertex(String vString) {
            String[] parts = vString.split("\\],\\[");
            pegs = new Stack[NUMBER_OF_PEGS];
            for (int i = 0; i < NUMBER_OF_PEGS; i++) {
                pegs[i] = new Stack<Integer>();
                try {
                    parts[i] = parts[i].replaceAll("\\[", "");
                    parts[i] = parts[i].replaceAll("\\]", "");
                    ArrayList<String> al = new ArrayList<String>(
                            Arrays.asList(parts[i].split(",")));
                    // System.out.println("ArrayList al is: " + al);
                    Iterator<String> it = al.iterator();
                    while (it.hasNext()) {
                        Object item = it.next();
                        // System.out.println("item is: " + item);
                        Integer diskInteger = new Integer((String) item);
                        pegs[i].push(diskInteger);
                    }
                } catch (Exception e) {
                }
            }
        }

        /**
         * Constructor that copies the information of the given Vertex into the current Vertex
         * @param vertex The Vertex that the current Vertex copies information from
         */
        public Vertex(Vertex vertex) {
            pegs = new Stack[vertex.pegs.length];
            for (int index = 0; index < vertex.pegs.length; index++) {
                pegs[index] = (Stack<Integer>) vertex.pegs[index].clone();
            }
        }

        /**
         * Creates a String representation of the given Vertex
         * @return A String representation of the given Vertex
         */
        public String toString() {
            String ans = "[";
            for (int i = 0; i < NUMBER_OF_PEGS; i++) {
                ans += pegs[i].toString().replace(" ", "");
                if (i < NUMBER_OF_PEGS - 1) {
                    ans += ",";
                }
            }
            ans += "]";
            return ans;
        }
        
        @Override
        /**
         * Checks to see if the given object is equivalent to the current Vertex
         */
        public boolean equals(Object v) {
            return (v instanceof Vertex) && (hashCode() == v.hashCode());
        }

        @Override
        /**
         * Creates a hashcode for the current Vertex
         */
        public int hashCode() {
            return toString().hashCode();
        }
    }

    class Edge {
        public Vertex vi; //The starting disk configuration
        public Vertex vj; //The disk configuration after a move has been performed

        /**
         * Constructs an Edge using the given vertices
         * @param vi The starting disk configuration
         * @param vj The disk configuration after a move has been performed
         */
        public Edge(Vertex vi, Vertex vj) {
            this.vi = vi;
            this.vj = vj;
        }

        /**
         * Creates a String representation of the given edge
         * @return A String representation of the given edge
         */
        public String toString() {
            return vi + " -> " + vj;
        }

        @Override
        /**
         * Checks to see if the given object is equivalent to the current Edge
         */
        public boolean equals(Object e) { return (e instanceof Edge) && (hashCode() == e.hashCode()); }

        @Override
        /**
         * Creates a hashcode for the current Edge
         */
        public int hashCode() {
            return vi.hashCode() + vj.hashCode();
        }
    }

    /**
     * A
     */
    class Operator {
        private int i, j; //i = the peg you're taking a disk from. j = the peg you're putting the disk onto

        /**
         * Constructs an operator with the given peg information
         * @param i The peg you're taking a disk from
         * @param j The peg you're putting the disk onto
         */
        public Operator(int i, int j) {
            this.i = i;
            this.j = j;
        }

        /**
         * Checks to see if the current Operator can be performed on the given disk configuration (Vertex)
         * @return Whether the current Operator's move can be performed on the given Vertex
         */
        @SuppressWarnings("rawtypes")
        Function<Vertex, Boolean> getPrecondition() {
            return new Function<Vertex, Boolean>() {
                @Override
                public Boolean apply(Vertex vertex) {
                    Stack<Integer> currPeg = vertex.pegs[i];
                    if (!currPeg.empty()) {
                        Stack<Integer> pegToPlaceOn = vertex.pegs[j];
                        return pegToPlaceOn.empty() || (currPeg.peek() < pegToPlaceOn.peek());
                    } else {
                        return false;
                    }
                }
            };
        }

        /**
         * Performs the current Operator's move on the given Vertex
         * @return The resulting disk configuration (Vertex) after the current Operator's move has been performed
         */
        @SuppressWarnings("rawtypes")
        Function<Vertex, Vertex> getTransition() {
            return new Function<Vertex, Vertex>() {
                @Override
                public Vertex apply(Vertex vertex) {
                    vertex = new Vertex(vertex);
                    int diskMoving = vertex.pegs[i].pop();
                    vertex.pegs[j].push(diskMoving);
                    return vertex;
                }
            };
        }

        /**
         * Creates a string representation of the current Operation
         * @return A string representation of the current Operation
         */
        public String toString() {
            // TODO: return a string good enough
            // to distinguish different operators
            return i + " -> " + j;
        }
    }

}
