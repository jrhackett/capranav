package visuals;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Node;

import java.util.ArrayList;

public class Instructions {
	private String instruction_string;
	private int distance;
	Node end;
	Node begin;
	
	public Instructions(String s, int d){
		this.instruction_string = s;
		this.distance = d;
	}

	public String getInstruction_string() {
		return instruction_string;
	}
	public int getDistance() {
		return distance;
	}
	
	public static ArrayList<TableColumn<Instructions, ?>> getColumn(TableView table){
		int i;
		ArrayList<TableColumn<Instructions, ?>> columns = new ArrayList<TableColumn<Instructions, ?>>();
		
		String[] columnNames = {"Instructions"}; //, "Distance"
		String[] variableNames = {"instruction_string"}; //, "distance"
		Integer[] columnWidths = {100};
		
		i = 0;
		TableColumn<Instructions, String> instructionCol = new TableColumn<>(columnNames[i++]);
		//TableColumn<Instructions, Integer> distanceCol = new TableColumn<>(columnNames[i++]);

		i = 0;
		instructionCol.prefWidthProperty().bind(table.widthProperty().divide(100 / columnWidths[i++]));
		//distanceCol.prefWidthProperty().bind(table.widthProperty().divide(100 / columnWidths[i++]));
		
		i = 0;		
		instructionCol.setCellValueFactory(new PropertyValueFactory<Instructions, String>(variableNames[i++]));
		//distanceCol.setCellValueFactory(new PropertyValueFactory<Instructions, Integer>(variableNames[i++]));

		columns.add(instructionCol);
		//columns.add(distanceCol);
			
		return columns;
		
	}
	/* For images -> Nodes should have image ids/paths to images, and if it exists we show the image,
	 * or pass is the id/path of the image
	 */



}
