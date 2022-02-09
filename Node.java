//Name:David Wang 	URID:31322379
//Lab section: MW 200-315 Gavet
//email: dwang56@u.rochester.edu

import java.util.LinkedList;

public class Node implements Comparable<Node> {
	private double longitude;
	private double latitude;
	private String name;
	private double distance = Integer.MAX_VALUE;
	private LinkedList<Edge> edges = new LinkedList<>();
	private Node parent = null;
	//private Node child;
	
	@Override
	public int compareTo(Node o) {
		if (this.distance > o.distance) {
			return 1;
		}
		else if (this.distance < o.distance) {
			return -1;
		}
		return 0;
	}
	
	public Edge targetEdge(Node n1, Node n2) {
		
		for(Edge edge: edges) {
			if(edge.targetNode(n1)==n2) {
				return edge;
			}
		}
		return null;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public void addEdge(Edge edge) {
		edges.add(edge);
	}
	
	public LinkedList<Edge> getEdges() {
		return edges;
	}

	public void setEdges(LinkedList<Edge> edges) {
		this.edges = edges;
	}
	
	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	/*public Node getChild() {
		return child;
	}

	public void setChild(Node child) {
		this.child = child;
	}*/

}
