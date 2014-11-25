import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Queue;
import java.util.function.Function;

/**
 * @author your name(s) here.
 * Extra Credit Options Implemented, if any:  (mention them here.)
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

    public void initialize() {
        Ve = new LinkedHashSet<Vertex>();
        Ee = new LinkedHashSet<Edge>();
        map = new HashMap<Vertex, LinkedList<Edge>>();
        VeSize = 0;
        EeSize = 0;
        setOperators();
    }

    private void setOperators() {
        // TODO: Initialize the field operators with 6 Operators:
        // (i, j) = {(0, 1), (0, 2), (1, 0), (1, 2), (2, 0), (2, 1)}
        operators = new ArrayList<Operator>();
    }

    public int nvertices() {
        // TODO: Return size of collection of explored vertices
        return 0;
    }

    public int nedges() {
        // TODO: Return size of collection of explored edges
        return 0;
    }

    public void dfs(Vertex vi, Vertex vj) {
        // TODO: Reset all private fields and
        // implement depth first search algorithm
        // Hints: Choose the right data structure between queue and stack
        // You may need a private method to do the recursion if you need
    }

    public void bfs(Vertex vi, Vertex vj) {
        // TODO: Reset all private fields and
        // implement breath first search algorithm
        // Hints: Choose the right data structure between queue and stack
        // You may need a private method to do the recursion if you need
    }

    public ArrayList<Vertex> retrievePath(Vertex vj) {
        // TODO: retrieve the path to the vj(Goal Vertex)
        // Return a path as an array list
        ArrayList<Vertex> path = new ArrayList<Vertex>();
        return path;
    }

    public ArrayList<Vertex> shortestPath(Vertex vi, Vertex vj) {
        // TODO: return a shortest path as an array list
        ArrayList<Vertex> path = retrievePath(vj);
        return path;
    }

    public Set<Vertex> getVertices() {
        return Ve;
    }

    public Set<Edge> getEdges() {
        return Ee;
    }

    public static void main(String[] args) {
        ExploredGraph eg = new ExploredGraph();
        // Test the vertex constructor:
        // Vertex v0 = eg.new Vertex("[[4,3,2,1],[],[]]");
        // System.out.println(v0);
        // Add your own tests here.
        // The autograder code will be used to test your basic functionality
        // later.
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
        public boolean equals(Object v) {
            // TODO: Implement equals method by using hashCode
            return false;
        }

        @Override
        public int hashCode() {
            // TODO: Implement a good hashCode
            // to avoid duplicated hashCodes for different vertexes
            return 0;
        }

    }

    class Edge {
        public Vertex vi;
        public Vertex vj;

        public Edge(Vertex vi, Vertex vj) {
            this.vi = vi;
            this.vj = vj;
        }

        public String toString() {
            // TODO: return a String like "vi -> vj"
            return null;
        }

        @Override
        public boolean equals(Object e) {
            // TODO: Implement equals method by using hashCode
            return false;
        }

        @Override
        public int hashCode() {
            // TODO: Implement a good hashCode
            // to avoid duplicated hashCodes for different edges
            return 0;
        }
    }

    class Operator {
        private int i, j;

        public Operator(int i, int j) {
            this.i = i;
            this.j = j;
        }

        // Additional explanation of what to do here will be given in GoPost or
        // as extra text in the spec.
        @SuppressWarnings("rawtypes")
        Function getPrecondition() {
            // TODO: return a function that can be applied to a vertex (provided
            // that the precondition is true) to get a "successor" vertex -- the
            // result of making the move.
            return new Function() {
                @Override
                public Object apply(Object vertex) {
                    return false;
                }
            };
        }

        @SuppressWarnings("rawtypes")
        Function getTransition() {
            // TODO: should return a function that can be applied to a vertex
            // (provided that the precondition is true) to get a "successor"
            // vertex -- the result of making the move.
            return new Function() {
                @Override
                public Object apply(Object vertex) {
                    return null;
                }
            };
        }

        public String toString() {
            // TODO: return a string good enough
            // to distinguish different operators
            return "";
        }
    }

}
