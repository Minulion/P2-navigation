// === CS400 File Header Information ===
// Name: Camilla Liu
// Email: cliu649@wisc.edu
// Group and Team: G4 (does not have a team color yet)
// Group TA: Manas T
// Lecturer: 004
// Notes to Grader: <optional extra notes>

import java.util.*;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;
import java.util.List;

/**
 * This class extends the BaseGraph data structure with additional methods for computing the total
 * cost and list of node data along the shortest path connecting a provided starting to ending
 * nodes. This class makes use of Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number> extends BaseGraph<NodeType, EdgeType>
    implements GraphADT<NodeType, EdgeType> {

   /**
    * While searching for the shortest path between two nodes, a SearchNode contains data about one
    * specific path between the start node and another node in the graph. The final node in this
    * path is stored in its node field. The total cost of this path is stored in its cost field. And
    * the predecessor SearchNode within this path is referened by the predecessor field (this field
    * is null within the SearchNode containing the starting node in its node field).
    *
    * SearchNodes are Comparable and are sorted by cost so that the lowest cost SearchNode has the
    * highest priority within a java.util.PriorityQueue.
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
      super(new HashtableMap<>());
   }

   /**
    * This helper method creates a network of SearchNodes while computing the shortest path between
    * the provided start and end locations. The SearchNode that is returned by this method is
    * represents the end of the shortest path that is found: it's cost is the cost of that shortest
    * path, and the nodes linked together through predecessor references represent all of the nodes
    * along that shortest path (ordered from end to start).
    *
    * @param start the data item in the starting node for the path
    * @param end   the data item in the destination node for the path
    * @return SearchNode for the final end node within the shortest path
    * @throws NoSuchElementException when no path from start to end is found or when either start or
    *                                end data do not correspond to a graph node
    */
   protected SearchNode computeShortestPath(NodeType start, NodeType end) {
      // Initialize a priority queue to store nodes based on their current cost
      PriorityQueue<SearchNode> pq = new PriorityQueue<>();

      // Initialize a map to keep track of visited nodes and their associated SearchNode
      MapADT<NodeType, SearchNode> visited = new HashtableMap<>();

      // Add the starting node to the priority queue with a cost of 0
      pq.offer(new SearchNode(nodes.get(start), 0, null));

      // While there are still nodes to visit in the priority queue
      while (!pq.isEmpty()) {
         // Extract the node with the lowest cost from the priority queue
         SearchNode current = pq.poll();

         // If the current node is the destination node, return it
         if (current.node.data.equals(end)) {
            return current;
         }

         // If the current node has already been visited, skip it
         if (visited.containsKey(current.node.data)) {
            continue;
         }

         // Mark the current node as visited and store its SearchNode
         visited.put(current.node.data, current);

         // Explore all neighbors of the current node
         for (Edge edge : current.node.edgesLeaving) {
            // Calculate the total cost to reach the neighbor node
            double totalCost = current.cost + edge.data.doubleValue();

            // Add the neighbor to the priority queue if it hasn't been visited yet
            if (!visited.containsKey(edge.successor.data)) {
               pq.offer(new SearchNode(edge.successor, totalCost, current));
            }
         }
      }

      // If no path was found, throw a NoSuchElementException
      throw new NoSuchElementException("No path from " + start.toString() + " to " + end.toString());
   }

   /**
    * Returns the list of data values from nodes along the shortest path from the node with the
    * provided start value through the node with the provided end value. This list of data values
    * starts with the start value, ends with the end value, and contains intermediary values in the
    * order they are encountered while traversing this shorteset path. This method uses Dijkstra's
    * shortest path algorithm to find this solution.
    *
    * @param start the data item in the starting node for the path
    * @param end   the data item in the destination node for the path
    * @return list of data item from node along this shortest path
    */
   public List<NodeType> shortestPathData(NodeType start, NodeType end) {
      // Call the computeShortestPath helper method to find the shortest path
      SearchNode endNode = computeShortestPath(start, end);

      // If the end node is null, indicating no path was found, throw NoSuchElementException
      if (endNode == null) {
         throw new NoSuchElementException("No path from " + start.toString() + " to " + end.toString());
      }

      // Initialize a list to store the nodes in the shortest path
      List<NodeType> shortestPath = new LinkedList<>();

      // Traverse the path backwards from the end node to the start node
      while (endNode != null) {
         shortestPath.add(0, endNode.node.data); // Add the current node to the beginning of the list
         endNode = endNode.predecessor; // Move to the predecessor node
      }

      return shortestPath;
   }

   /**
    * Returns the cost of the path (sum over edge weights) of the shortest path freom the node
    * containing the start data to the node containing the end data. This method uses Dijkstra's
    * shortest path algorithm to find this solution.
    *
    * @param start the data item in the starting node for the path
    * @param end   the data item in the destination node for the path
    * @return the cost of the shortest path between these nodes
    */
   public double shortestPathCost(NodeType start, NodeType end) {
      // Call the computeShortestPath helper method to find the shortest path
      SearchNode endNode = computeShortestPath(start, end);

      // If the end node is null, indicating no path was found, throw NoSuchElementException
      if (endNode == null) {
         throw new NoSuchElementException("No path from " + start.toString() + " to " + end.toString());
      }

      // Return the cost of the shortest path
      return endNode.cost;
   }


   // TODO: implement 3+ tests in step 4.1

   /**
    * Test for normal shortest path, example from lecture
    */
   @Test
   public void testShortestPath() {
      DijkstraGraph<String, Double> graph = new DijkstraGraph<>();

      graph.insertNode("A");
      graph.insertNode("B");
      graph.insertNode("C");
      graph.insertNode("D");
      graph.insertNode("E");
      graph.insertNode("F");
      graph.insertNode("G");
      graph.insertNode("H");

      graph.insertEdge("A", "B", 4.0);
      graph.insertEdge("A", "C", 2.0);
      graph.insertEdge("A", "E", 15.0);
      graph.insertEdge("B", "D", 1.0);
      graph.insertEdge("B", "E", 10.0);
      graph.insertEdge("C", "D", 5.0);
      graph.insertEdge("D", "E", 3.0);
      graph.insertEdge("D", "F", 0.0);
      graph.insertEdge("F", "D", 2.0);
      graph.insertEdge("F", "H", 4.0);
      graph.insertEdge("G", "H", 4.0);

      List<String> shortestPath = graph.shortestPathData("A", "E");

      List<String> expectedPath = Arrays.asList("A", "B", "D", "E");

      double shortestPathCost = graph.shortestPathCost("A", "E");

      double expectedCost = 8;

      Assertions.assertIterableEquals(expectedPath, shortestPath,
          "Path matches");

      Assertions.assertEquals(expectedCost, shortestPathCost, "Cost matches");

   }


   /**
    * Test for unreachable shortest path, example from lecture
    */
   @Test
   public void testShortestPathUnreachable() {
      DijkstraGraph<String, Double> graph = new DijkstraGraph<>();

      graph.insertNode("A");
      graph.insertNode("B");
      graph.insertNode("C");
      graph.insertNode("D");
      graph.insertNode("E");
      graph.insertNode("F");
      graph.insertNode("G");
      graph.insertNode("H");

      graph.insertEdge("A", "B", 4.0);
      graph.insertEdge("A", "C", 2.0);
      graph.insertEdge("A", "E", 15.0);
      graph.insertEdge("B", "D", 1.0);
      graph.insertEdge("B", "E", 10.0);
      graph.insertEdge("C", "D", 5.0);
      graph.insertEdge("D", "E", 3.0);
      graph.insertEdge("D", "F", 0.0);
      graph.insertEdge("F", "D", 2.0);
      graph.insertEdge("F", "H", 4.0);
      graph.insertEdge("G", "H", 4.0);

      Assertions.assertThrows(NoSuchElementException.class, () -> {
         graph.shortestPathData("A", "G");
      });

      Assertions.assertThrows(NoSuchElementException.class, () -> {
         graph.shortestPathCost("A", "G");
      });
   }

   /**
    * Test for self shortest path, example from lecture
    */
   @Test
   public void testShortestPathSelf() {
      DijkstraGraph<String, Double> graph = new DijkstraGraph<>();

      graph.insertNode("A");
      graph.insertNode("B");
      graph.insertNode("C");
      graph.insertNode("D");
      graph.insertNode("E");
      graph.insertNode("F");
      graph.insertNode("G");
      graph.insertNode("H");

      graph.insertEdge("A", "B", 4.0);
      graph.insertEdge("A", "C", 2.0);
      graph.insertEdge("A", "E", 15.0);
      graph.insertEdge("B", "D", 1.0);
      graph.insertEdge("B", "E", 10.0);
      graph.insertEdge("C", "D", 5.0);
      graph.insertEdge("D", "E", 3.0);
      graph.insertEdge("D", "F", 0.0);
      graph.insertEdge("F", "D", 2.0);
      graph.insertEdge("F", "H", 4.0);
      graph.insertEdge("G", "H", 4.0);

      List<String> shortestPath = graph.shortestPathData("A", "A");

      List<String> expectedPath = Arrays.asList("A");

      Assertions.assertIterableEquals(expectedPath, shortestPath,
          "Path matches");

      System.out.println(graph.shortestPathCost("A", "A"));

      double shortestPathCost = graph.shortestPathCost("A", "A");

      double expectedCost = 0.0;

      Assertions.assertEquals(expectedCost, shortestPathCost, "Cost matches");
   }

   /**
    * Test for shortest path with starting node switch, example from lecture
    */
   @Test
   public void testShortestPathWithDifferentStarting() {
      DijkstraGraph<String, Double> graph = new DijkstraGraph<>();

      graph.insertNode("A");
      graph.insertNode("B");
      graph.insertNode("C");
      graph.insertNode("D");
      graph.insertNode("E");
      graph.insertNode("F");
      graph.insertNode("G");
      graph.insertNode("H");

      graph.insertEdge("A", "B", 4.0);
      graph.insertEdge("A", "C", 2.0);
      graph.insertEdge("A", "E", 15.0);
      graph.insertEdge("B", "D", 1.0);
      graph.insertEdge("B", "E", 10.0);
      graph.insertEdge("C", "D", 5.0);
      graph.insertEdge("D", "E", 3.0);
      graph.insertEdge("D", "F", 0.0);
      graph.insertEdge("F", "D", 2.0);
      graph.insertEdge("F", "H", 4.0);
      graph.insertEdge("G", "H", 4.0);

      //Start at A
      List<String> shortestPath = graph.shortestPathData("A", "F");

      List<String> expectedPath = Arrays.asList("A", "B", "D", "F");

      double shortestPathCost = graph.shortestPathCost("A", "F");

      double expectedCost = 5;

      Assertions.assertIterableEquals(expectedPath, shortestPath,
          "Path matches");

      Assertions.assertEquals(expectedCost, shortestPathCost, "Cost matches");

      // Start at F
      Assertions.assertThrows(NoSuchElementException.class, () -> {
         graph.shortestPathData("F", "A");
      });

      Assertions.assertThrows(NoSuchElementException.class, () -> {
         graph.shortestPathCost("F", "A");
      });
   }
}
