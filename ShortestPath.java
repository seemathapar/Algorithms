package com.graph;

import java.util.*;

import junit.framework.Assert;

public class ShortestPath {

	HashMap<String, Integer> distance = new HashMap<String, Integer>();
	HashMap<String, String> prevLocations = new HashMap<String, String>();
	Set<String> visitedNodes = new HashSet<String>();
	Set<String> unMarkedNodes = new HashSet<String>();
	Graph g;

	ShortestPath(Graph graph) {
		g = graph;
	}

	public int findShortestPath(String source, String destination) {
        if (!g.containsNode(source) || !g.containsNode(destination)) {
        	return Integer.MAX_VALUE;
        }
		distance.put(source, 0);

		unMarkedNodes.add(source);

		while (!unMarkedNodes.isEmpty()) {

			String currentNode = getClosestNeighbor(unMarkedNodes);
			visitedNodes.add(currentNode);
			unMarkedNodes.remove(currentNode);
			findMinimumDistance(currentNode);
		}

		return getDistance(destination);

	}

	private List<String> getUnprocessedNeighbors(String currentNode) {
		List<Edge> adjList = g.getEdges(currentNode);

		List<String> neighbors = new LinkedList<String>();
		for (Edge e : adjList) {
			if (!visitedNodes.contains(e.getDestination())) {
				neighbors.add(e.getDestination());
			}
		}
		return neighbors;
	}

	public void findMinimumDistance(String currentNode) {

		List<String> unprocessedNeighbors = getUnprocessedNeighbors(currentNode);
		for (String target : unprocessedNeighbors) {
			if (getDistance(target) > getDistance(currentNode) + getEdgeDistance(currentNode, target)) {
				distance.put(target, getDistance(currentNode) + getEdgeDistance(currentNode, target));
				prevLocations.put(target, currentNode);
				unMarkedNodes.add(target);
			}
		}

	}

	public int getEdgeDistance(String currentNode, String target) {
		List<Edge> edges = g.getEdges(currentNode);
		for (Edge e : edges) {
			if (e.getDestination().equals(target)) {
				return e.getWeight();
			}
		}
		return 0;
	}

	public String getClosestNeighbor(Set<String> unMarkedNodes) {
		Integer minDistance = Integer.MAX_VALUE;
		String closestNode = null;
		for (String node : unMarkedNodes) {
			if (distance.get(node) < minDistance) {
				closestNode = node;
				minDistance = distance.get(node);
			}
		}

		return closestNode;

	}

	public int getDistance(String destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	public LinkedList<String> getShortestPath(String target) {
		LinkedList<String> path = new LinkedList<String>();
		String previous = target;

		if (prevLocations.get(previous) == null) {
			return null;
		}
		path.add(previous);
		while (prevLocations.get(previous) != null) {
			previous = prevLocations.get(previous);
			path.add(previous);
		}
		// Reverse the list --> source to destination
		Collections.reverse(path);
		return path;
	}

	public static void main(String[] args) {

		Graph route = new Graph();
		route.addNode("A");
		route.addNode("B");
		route.addNode("C");

		route.addEdge("A", "B", 1);
		route.addEdge("B", "C", 4);
		route.addEdge("A", "C", 5);
		route.addEdge("E", "F", 9);
		route.addEdge("A", "F", 5);

		System.out.println(route);
		ShortestPath sp = new ShortestPath(route);
		
		String source = "A";
		String destination = "C";
		int shortestDistance = sp.findShortestPath(source, destination);
		if (shortestDistance == Integer.MAX_VALUE) {
			System.out.println("There is no path between "+source + "  and "+ destination);
		} else {
			
			System.out.println("Shortest distance : " + shortestDistance);
			System.out.println("Shortest path : " +sp.getShortestPath(destination).toString());
		}

	}
	
	

}


class Edge {
	String source;
	String destination;
	int weight;

	Edge(String inSource, String inDestination, int inWeight) {
		this.source = inSource;
		this.destination = inDestination;
		this.weight = inWeight;

	}

	public String getSource() {
		return this.source;
	}

	public String getDestination() {
		return this.destination;
	}

	public int getWeight() {
		return weight;
	}

}

class Graph {

	private Map<String, LinkedList<Edge>> graph;

	Graph() {
		graph = new HashMap<String, LinkedList<Edge>>();
	}

	public Iterable<String> getLocations() {
		return graph.keySet();
	}

	public List<Edge> getEdges(String node) {
		return graph.get(node);

	}
	
	public boolean containsNode(String node) {
		if(graph.containsKey(node)) {
			return true;
		}
		return false;
	}
  
	public void addNode(String node) {
		if (graph.get(node) == null) {
			graph.put(node, new LinkedList<Edge>());
		}

	}

	public void addEdge(String source, String destination, int weight) {

		if (!graph.containsKey(source)) {
			addNode(source);
		}
		if (!graph.containsKey(destination)) {
			addNode(destination);
		}

		Edge e = new Edge(source, destination, weight);
		graph.get(source).add(e);

	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for (String v : vertices()) {
			s.append(v + ": ");
			for (Edge w : getEdges(v)) {
				s.append(w.getDestination() + " ");
			}
			s.append('\n');
		}
		return s.toString();

	}

	public Iterable<String> vertices() {
		return graph.keySet();

	}

}
