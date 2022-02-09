//Name:David Wang 	URID:31322379
//Lab section: MW 200-315 Gavet
//email: dwang56@u.rochester.edu

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Graph extends JPanel {

	// implement adjacency list using 2D array
	public static HashMap<String, Node> map = new HashMap<>();
	public static HashMap<String, Edge> edges = new HashMap<>();
	public static HashMap<String, Edge> mape = new HashMap<>();
	public static double lat_Min, lat_Max, long_Min, long_Max, height, length;
	public static String src = null, end = null;
	public JPanel mainPanel = new JPanel();
	public JPanel topPanel = new JPanel();
	public JPanel lowPanel = new JPanel();

	//Calculates the Maximum and Minimum Latitude and Longitude values which helps to scale the graph properly
	public void getLat_Min_Max(String src) {
		//Sets arbitrary values as Max and Min values
		Graph.lat_Min = map.get(src).getLatitude();
		Graph.lat_Max = map.get(src).getLatitude();
		Graph.long_Min = map.get(src).getLongitude();
		Graph.long_Max = map.get(src).getLongitude();

		for (Map.Entry<String, Node> entry : map.entrySet()) {
			//Iterates through all nodes and finds the maximum and minimum values
			if (map.get(entry.getKey()).getLatitude() < lat_Max) {
				lat_Max = map.get(entry.getKey()).getLatitude();
			} else if (map.get(entry.getKey()).getLatitude() > lat_Min) {
				lat_Min = map.get(entry.getKey()).getLatitude();
			}
			if (map.get(entry.getKey()).getLongitude() < long_Min) {
				long_Min = map.get(entry.getKey()).getLongitude();
			} else if (map.get(entry.getKey()).getLongitude() > long_Max) {
				long_Max = map.get(entry.getKey()).getLongitude();
			}
		}
		
		height = lat_Max - lat_Min;
		length = long_Max - long_Min;
	}

	// Paint component method draws the map
	public void paintComponent(Graphics g) {
	
		for (Map.Entry<String, Edge> entry : mape.entrySet()) {
			x(g, mape.get(entry.getKey()));
		}
		
		for (Map.Entry<String, Edge> entry : edges.entrySet()) {
			y(g, edges.get(entry.getKey()));	
		}
		z(g, map.get(src));
		zz(g, map.get(end));
	}

	// x method as a helper of paintComponent which draws the map
	public void x(Graphics g, Edge e) {

		double x1 = e.getNode1().getLongitude();
		double x2 = e.getNode2().getLongitude();
		double y1 = e.getNode1().getLatitude();
		double y2 = e.getNode2().getLatitude();
		g.setColor(Color.BLACK);
		g.drawLine((int) ((x1 - long_Min) * getWidth() / length), (int) ((y1 - lat_Min) * getHeight() / height),
				(int) ((x2 - long_Min) * getWidth() / length), (int) ((y2 - lat_Min) * getHeight() / height));
	}

	// y method as a helper of paintComponent which draws the shortest path from Dijkstra
	public void y(Graphics g, Edge e) {

		double x1 = e.getNode1().getLongitude();
		double x2 = e.getNode2().getLongitude();
		double y1 = e.getNode1().getLatitude();
		double y2 = e.getNode2().getLatitude();
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(3));
		g2.drawLine((int) ((x1 - long_Min) * getWidth() / length), (int) ((y1 - lat_Min) * getHeight() / height),
				(int) ((x2 - long_Min) * getWidth() / length), (int) ((y2 - lat_Min) * getHeight() / height));
	}
	
	// z method as a helper of paintComponent which highlights the start and destination intersections
	public void z(Graphics g, Node n) {

		g.setColor(Color.GREEN);
		g.drawOval((int) ((n.getLongitude() - long_Min) * getWidth() / length) -5, (int) ((n.getLatitude() - lat_Min) * getHeight() / height) -5, 10, 10);
		g.setColor(Color.BLUE);
		g.drawString("Start: "+n.getName(), 30, getHeight() - 30);
	}
	public void zz(Graphics g, Node n) {

		g.setColor(Color.GREEN);
		g.fillOval((int) ((n.getLongitude() - long_Min) * getWidth() / length) -5, (int) ((n.getLatitude() - lat_Min) * getHeight() / height) -5, 10, 10);
		g.setColor(Color.BLUE);
		g.drawString("Destination: "+n.getName(), 30, getHeight() - 15);
	}
	
	
	//finds the shortest path between two nodes and saves the edges into the HashMap edges
	public void dijkstras(String src, String end) {
		LinkedList<Node> shortest = new LinkedList<>();
		PriorityQueue<Node> pq = new PriorityQueue<>();
		
		//sets tentative distance of the initial node to zero
		map.get(src).setDistance(0);
		//Iterates through all nodes and adds them to the priority queue
		for (Map.Entry<String, Node> entry : map.entrySet()) {
			pq.add(map.get(entry.getKey()));
		}
		
		//keeps polling Node with smallest distance value while the priority queue isn't empty
		while (!pq.isEmpty()) {
			Node curr = pq.poll();

			for (int i = 0; i < curr.getEdges().size(); i++) {
				double distance_Current_Tentative = curr.getEdges().get(i).targetNode(curr).getDistance();
				double distance_To_Current_Node = curr.getDistance();
				double distance_Current_To_Neighbour = curr.getEdges().get(i).getWeight();

				if (distance_Current_Tentative > distance_To_Current_Node + distance_Current_To_Neighbour) {
					curr.getEdges().get(i).targetNode(curr)
							.setDistance(curr.getDistance() + curr.getEdges().get(i).getWeight());
					curr.getEdges().get(i).targetNode(curr).setParent(curr);
					pq.offer(curr.getEdges().get(i).targetNode(curr));
				}
			}
		}
		//starting from the destination node, calls for the parent node and adds it to the LinkedList shortest
		Node parent = map.get(end);
		while (parent.getParent() != null) {
			shortest.add(parent);
			parent = parent.getParent();
		}
		shortest.add(map.get(src));
		
		//0.4506042865074057 mi
		System.out.println("distance: " + map.get(end).getDistance()*58.88428194078548 +" mi");
		System.out.println();

		//prints out the names of each node/intersection and adds it to the HashMap edges 
		System.out.print(map.get(src).getName() + ", ");
		for (int i = shortest.size() - 2 ; i>=0; i--) {
			Edge edge = shortest.get(i).targetEdge(shortest.get(i), shortest.get(i + 1));
			System.out.print(shortest.get(i).getName() + ", ");
			edges.put(edge.getName(), edge);
		}
		
	}

	//reads the specified files and runs the entire program
	public void run(String mapName, String show, String direction, String src, String end) {
		
		if (show.equals("--show") || direction.equals("--directions")) {

			try {
				//The content of the speicified files are read with FileReader
				String fileName = mapName;

				Scanner reader = new Scanner(new FileReader(fileName));

				Graph graph = new Graph();

				String[] nums = null;

				while (reader.hasNext()) {
					String content = reader.nextLine();
					nums = content.split("\\s+");

					for (int i = 0; i < nums.length; i++) {

						if (nums[i].equals("i")) {
							//creates a node and adds specified properties from text file
							Node node = new Node();
							node.setName(nums[i + 1]);
							node.setLatitude(Double.valueOf(nums[i + 2]));
							node.setLongitude(Double.valueOf(nums[i + 3]));
							//adds node to HashMap
							map.put(nums[i + 1], node);

						} else if (nums[i].equals("r")) {
							//creates an edge and adds specified properties from text file
							Edge edge = new Edge();
							edge.setName(nums[i + 1]);
							edge.setNode1(map.get(nums[i + 2]));
							edge.setNode2(map.get(nums[i + 3]));
							edge.setWeight();
							//adds edge to HashMap
							mape.put(nums[i + 1], edge);
							// insert Node1 into LinkedList of Node2 and vice versa
							edge.getNode1().addEdge(edge);
							edge.getNode2().addEdge(edge);

						} else {

						}
					}
				}

				graph.getLat_Min_Max(src);

				if (direction.equals("--directions")) {
					graph.dijkstras(src, end);
				}
				
				this.src = src;
				this.end = end;
				
				// JFrame and its corresponding methods to make everything visible and resizable
				JFrame frame = new JFrame("Graph");
				frame.setBackground(Color.PINK);
				frame.add(graph);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(640, 640);
				frame.setResizable(true);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

		}

	}

}
