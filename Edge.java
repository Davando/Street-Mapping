//Name:David Wang 	URID:31322379
//Lab section: MW 200-315 Gavet
//email: dwang56@u.rochester.edu

public class Edge {
	private double weight;
	private String name;
	private Node node1;
	private Node node2;

	public Node targetNode(Node node) {
		if (node == node1) {
			return node2;
		} else if (node == node2) {
			return node1;
		}
		return null;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public void setWeight() {
		weight = Math.abs(Math.sqrt(Math.pow(node2.getLongitude() - node1.getLongitude(), 2)
				+ Math.pow(node2.getLatitude() - node1.getLatitude(), 2)));
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Node getNode1() {
		return node1;
	}

	public void setNode1(Node node1) {
		this.node1 = node1;
	}

	public Node getNode2() {
		return node2;
	}

	public void setNode2(Node node2) {
		this.node2 = node2;
	}
	
}
