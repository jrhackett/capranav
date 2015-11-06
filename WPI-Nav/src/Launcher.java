import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.HashMap;
import java.util.Scanner;
import logic.*;

public class Launcher extends Application
{
	private TableView directions = new TableView();
	
	public static void main(String [] args)
	{
		Graph graph = parsing();
		
		//Uncomment this to start the application
		//launch(args);
	}
	
	/*
	 * Parses nodes and edges from nodes.txt and edges.txt
	 * into a Graph, returns the graph
	 */
	public static Graph parsing()
	{
		String inputNodes = "nodes.txt";
		String inputEdges = "edges.txt";
		Scanner scanner = null;
		Scanner scanner2 = null;
		HashMap<Integer, Node> hashmap = new HashMap<Integer, Node>();
		int i = 0;
		
		try
		{
			scanner = new Scanner(new File(inputNodes));
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		while (scanner.hasNextLine()) 
		{
            Scanner s1 = new Scanner(scanner.nextLine());
            String name = s1.next();
            int x = Integer.parseInt(s1.next());
            int y = Integer.parseInt(s1.next());
            int z = 0; //TODO: change this value later
            Node node = new Node(name, x, y, z);
            hashmap.put(i, node);
            i++;
		}
		
		try
		{
			scanner2 = new Scanner(new File(inputEdges));
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		while(scanner2.hasNextLine())
		{
			Scanner s2 = new Scanner(scanner2.nextLine());
			String firstNodeName = s2.next();
			String secondNodeName = s2.next();
			Node node1 = null;
			Node node2 = null;
			
			for(Node node : hashmap.values())
			{
				if(node.getName().equals(firstNodeName))
				{
					node1 = node;
				}
				else if(node.getName().equals(secondNodeName))
				{
					node2 = node;
				}
			}
			
			double weight = Math.sqrt(Math.pow(node1.getX() - node2.getX(),2.0) + Math.pow(node1.getY() - node2.getY(), 2.0));
			Edge edge = new Edge(node1, weight); //TODO: check this...
			
			for(Node node : hashmap.values())
			{
				if(node.getName().equals(firstNodeName) || node.getName().equals(secondNodeName))
				{
					node.addEdge(edge);
				}
			}
		}
		return new Graph(hashmap);
	}

	/*
	 * The JavaFX implementation to start the application
	 */
	@Override
	public void start(Stage primaryStage) 
	{
		primaryStage.setTitle("NavWPI");
		
		directions.setEditable(true);
		TableColumn firstNameCol = new TableColumn("Step");
		TableColumn secondNameCol = new TableColumn("Directions");
		firstNameCol.setMinWidth(50);
		secondNameCol.setMinWidth(100);
		
		directions.getColumns().addAll(firstNameCol, secondNameCol);
		
		VBox vbox = new VBox();
		vbox.setSpacing(8);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.setPrefWidth(200);
		vbox.getChildren().addAll(directions);
		
		Scene scene = new Scene(vbox, 1000, 700);
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
}
