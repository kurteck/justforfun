package datastructures;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Graph<E> {
	
	/* Contains the list of destinations we may traverse */
	private HashMap<E, GraphNode> nodes = new HashMap<E, GraphNode>();
	
	/**
	 * Represents a destination (such as a city) in our graph.
	 */
	private class GraphNode {
		
		E element;
		Queue<GraphEdge> edges = new LinkedList<GraphEdge>();
		
		public GraphNode(E element) {
			this.element = element;
		}
	}

	
	/**
	 * Represents a one-way (directed) route to a destination
	 */
	private class GraphEdge {
		
		int cost;
		GraphNode destination;
		
		public GraphEdge(GraphNode destination, int cost) {
			this.destination = destination;
			this.cost = cost;
		}
	}
	
	
	public void addNode(E element) {
		
		if (element == null) {
			return;
		}
		
		if (nodes.containsKey(element)) {
			return;
		}
		
		GraphNode newNode = new GraphNode(element);
		nodes.put(element, newNode);
	}
	

	/**
	 * Adds a directed route from element E from to element E to.
	 * 
	 * @param from
	 * @param to
	 */
	public void addRoute(E from, E to, int cost) {
		
		GraphNode fromNode = nodes.get(from);
		GraphNode toNode   = nodes.get(to);
		
		if (from == null || to == null) {
			return; //throw new NoSuchElementException();
		}
		
		fromNode.edges.add(new GraphEdge(toNode, cost));
	}
	
	
	/**
	 * Returns true if a path exists from element from to element to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public boolean routeExists(E from, E to) {
		
		
		if (from == null || to == null) {
			return false;
		}
		
		// Get start and destination points
		GraphNode fromNode = nodes.get(from);
		GraphNode toNode   = nodes.get(to);
		if (fromNode == null || toNode == null) {
			return false;
		}

		// keep track of history to avoid circular paths
		HashMap<GraphNode, GraphNode> visited = new HashMap<GraphNode, GraphNode>();
		visited.put(fromNode, fromNode);

		Queue<GraphEdge> edgesToVisit = new LinkedList<GraphEdge>(fromNode.edges);
		while (!edgesToVisit.isEmpty()) {
			
			GraphEdge anEdge = edgesToVisit.remove();
			GraphNode aDest  = anEdge.destination;
			if (aDest.element.equals(to)) {
				return true;
			}
			
			// if this is not the destination we are looking for then search 
			// all of its routes so long as we have not been to this place before.
			if (!visited.containsKey(aDest)) {
				visited.put(aDest, aDest);
				edgesToVisit.addAll(aDest.edges);
			}
		}
		
		return false;
	}
	
	
	public static void main(String[] args) {
		
		Graph<String> cities = new Graph<String>();
		cities.addNode("San Francisco");
		cities.addNode("Santa Clara");
		cities.addNode("Atlanta");
		cities.addNode("Miami");
		cities.addNode("Ft. Lauderdale");
		cities.addNode("Seattle");
		cities.addNode("Tokyo");
		cities.addNode("Taipei");
		cities.addNode("Queenstown");
		cities.addNode("Okarito");
		cities.addNode("New York");
		
		cities.addRoute("Santa Clara", "San Francisco", 10);
		cities.addRoute("San Francisco", "Atlanta", 450);
		cities.addRoute("Atlanta", "Ft. Lauderdale", 150);

		System.out.println(cities.routeExists("Santa Clara", "San Francisco"));
		System.out.println(cities.routeExists("San Francisco", "Atlanta"));
		System.out.println(cities.routeExists("Atlanta", "Ft. Lauderdale"));
		System.out.println(cities.routeExists("Santa Clara", "Ft. Lauderdale"));
		System.out.println(cities.routeExists("Ft. Lauderdale", "Taipei"));

		cities.addRoute("San Francisco", "Santa Clara", 150);
		System.out.println(cities.routeExists("San Francisco", "Santa Clara"));
		System.out.println(cities.routeExists("Santa Clara", "Ft. Lauderdale"));
		System.out.println(cities.routeExists("Atlanta", "San Francisco"));
	}
}