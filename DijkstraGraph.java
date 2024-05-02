// === CS400 Spring 2024 File Header Information ===
// Name: Andrew Kim
// Email: akim227@wisc.edu
// Lecturer: Florian Heimerl
// Notes to Grader: None

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     */
    public DijkstraGraph() {
        super(new PlaceholderMap<>());
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        if (!this.containsNode(start) || !this.containsNode(start)) {
            throw new NoSuchElementException("nodes not present!");
        }
        PriorityQueue<SearchNode> pq = new PriorityQueue<SearchNode>(); //queue of paths
        Node startNode = this.nodes.get(start); //retrieve starting node from this graph's nodes
        SearchNode root = new SearchNode(startNode, 0, null); //first path, from starting node
        pq.add(root);
        PlaceholderMap<NodeType, SearchNode> map = new PlaceholderMap<NodeType, SearchNode>(); //keeps track of visited nodes
        // map.put(start, root);
        while (!pq.isEmpty()) { //iterate through all elements in PQ, find all shortest paths
            SearchNode current = pq.remove(); //remove lowest cost path
            if (!map.containsKey(current.node.data)) { //if node not visited yet
                map.put(current.node.data, current); //put node in list of visited
                for (Edge edge : current.node.edgesLeaving) { //iterate through all outgoing edges
                    if (!map.containsKey(edge.successor.data)) { //if destination of edge not yet visited
                        SearchNode next = new SearchNode(edge.successor, current.cost + (Integer)edge.data, current); 
                        // ^ new path to destination of edge, cost of old path plus edge weight, predecessor is old path
                        pq.add(next); //add new path to priorityQueue
                    }
                }
            }
        }
        if (map.containsKey(end)) {
            return map.get(end);
        } else {
            throw new NoSuchElementException("no path found!");
        }
        // implement in step 5.3
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        ArrayList<NodeType> nodes = new ArrayList<NodeType>(); //ArrayList that stores node data
        SearchNode path = computeShortestPath(start, end); //set current path to shortest path from start to end
        while (path.predecessor != null) { //loop until path is start to start
            nodes.add(0, path.node.data); //add current dest to start of list
            path = path.predecessor;
        }
        nodes.add(0, start);
        return nodes;
        // implement in step 5.4
	}

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        SearchNode path = computeShortestPath(start, end);
        return path.cost;
        // implement in step 5.4
    }

    // TODO: implement 3+ tests in step 4.1

    /**
     * Creates a graph with 8 nodes and 11 edges and checks the path that
     * returned between two of the nodes based on the shortestPathData
     * and shortestPathData methods. Graph and path based on those shown
     * in lecture.
     */
    @Test
    public void testCorrectPath1() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("A", "E", 15);
        graph.insertEdge("B", "D", 1);
        graph.insertEdge("B", "E", 10);
        graph.insertEdge("C", "D", 5);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("D", "F", 0);
        graph.insertEdge("F", "D", 2);
        graph.insertEdge("F", "H", 4);
        graph.insertEdge("G", "H", 4);

        Assertions.assertTrue(graph.computeShortestPath("A", "D").node.data.equals("D"));
        Assertions.assertTrue(graph.computeShortestPath("A", "D").predecessor.node.data.equals("B"));
        Assertions.assertEquals(5, graph.shortestPathCost("A", "D"));
        Assertions.assertEquals("[A, B, D]", graph.shortestPathData("A", "D").toString());

    }

    /**
     * Creates a graph with 8 nodes and 11 edges and checks the path that
     * returned between two of the nodes based on the shortestPathData
     * and shortestPathData methods. Tests new path from the same graph
     * as before.
     */
    @Test
    public void testCorrectPath2() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("A", "E", 15);
        graph.insertEdge("B", "D", 1);
        graph.insertEdge("B", "E", 10);
        graph.insertEdge("C", "D", 5);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("D", "F", 0);
        graph.insertEdge("F", "D", 2);
        graph.insertEdge("F", "H", 4);
        graph.insertEdge("G", "H", 4);

        Assertions.assertTrue(graph.computeShortestPath("B", "E").node.data.equals("E"));
        Assertions.assertTrue(graph.computeShortestPath("B", "E").predecessor.node.data.equals("D"));
        Assertions.assertEquals(4, graph.shortestPathCost("B", "E"));
        Assertions.assertEquals("[B, D, E]", graph.shortestPathData("B", "E").toString());
    }

    /**
     * Creates a graph with 8 nodes and 11 edges and checks that 
     * a NoSuchElementException is thrown if a path from start 
     * to end node does not exist.
     */
    @Test 
    public void impossiblePath() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>();
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("A", "E", 15);
        graph.insertEdge("B", "D", 1);
        graph.insertEdge("B", "E", 10);
        graph.insertEdge("C", "D", 5);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("D", "F", 0);
        graph.insertEdge("F", "D", 2);
        graph.insertEdge("F", "H", 4);
        graph.insertEdge("G", "H", 4);

        Assertions.assertThrows(NoSuchElementException.class, () -> graph.shortestPathData("A", "G"));
    }
}
