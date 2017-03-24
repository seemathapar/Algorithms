package com.graph;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class ShortestPathTest {
	

	Graph g = new Graph();
	private void setup() {
		g.addNode("A");
		g.addNode("B");
		g.addNode("C");

		g.addEdge("A", "B", 1);
		g.addEdge("B", "C", 2);
		g.addEdge("A", "C", 5);
		g.addEdge("E", "F", 9);
		g.addEdge("A", "F", 5);
		g.addEdge("B", "E", 5);
		
		g.addEdge("A", "E", 6);
		g.addEdge("A", "D", 10);
	}
	
	
	ShortestPath classUnderTest = new ShortestPath(g);

	@Test
	public void test_MultiplePaths_ReturnShortest() {
		setup();
		assertEquals(3, classUnderTest.findShortestPath("A", "C"));
		
	}
	
	@Test
	public void test_DuplicatePaths_ReturnShortestHops() {
		setup();
		assertEquals(6, classUnderTest.findShortestPath("A", "E"));
		List<String> answer = new LinkedList<String>();
		answer.add("A");
		answer.add("E");
		assertEquals(answer, classUnderTest.getShortestPath("E"));
		
	}
	
	@Test
	public void test_NoNode_Return_MAXValue() {
		setup();
		assertEquals(Integer.MAX_VALUE, classUnderTest.findShortestPath("B", "D"));
		
		
	}
	
	@Test
	public void test_NoPath_Return_MAXValue() {
		setup();
		assertEquals(Integer.MAX_VALUE, classUnderTest.findShortestPath("C", "F"));
	
	}
	
	

}
