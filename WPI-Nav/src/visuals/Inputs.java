package visuals;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import logic.Node;

import java.util.HashMap;

/**
 * Currently using a 'simple' ComboBox.
 * Looking into alternate/advanced/accurate options
 */
public class Inputs extends ComboBox {
	private double WIDTH;
	private String initial;
	int rank;

	@SuppressWarnings("unchecked") /* probably should remove this */
	public Inputs(String s,double WIDTH ){
		super();
		this.WIDTH = WIDTH;
		initial = s;
		this.setMaxWidth(WIDTH);
		this.setMinWidth(WIDTH);

		//		this.setItems(getDummyLocations());
		/*
		this.valueProperty().addListener(new ChangeListener<Node>(){
			
			@Override
			public void changed(ObservableValue<? extends Node> arg0, Node oldValue, Node newValue) {
				if (oldValue != null){
					System.out.print(oldValue.getName() + "->");
				}
				System.out.println(newValue.getName());
			
			}
		});
	*/

		/*
		this.setCellFactory(new Callback<ListView<Node>, ListCell<Node>>() {

			@Override
			public ListCell<Node> call(ListView<Node> param) {
				ListCell<Node> cell = new ListCell<>(){
					@Override
					public void updateItem(Node node, boolean empty){
						if (node != null){
							setText(node.toString());
							 
							
						}
						setTextFill(Color.GREEN);
					}
				}
				return null;
			}
			
		});
		*/
	}

	/* this will be replaced later with actual data */
	public ObservableList<Node> getDummyLocations() {
		ObservableList<Node> data = FXCollections.observableArrayList();
		
		data.addAll(
				new Node("Faraday", 1, 1, 2, 0),
				new Node("Campus Center", 2, 3, 4, 0),
				new Node("Student Center", 3, 5, 4, 0));
		
		return data;
	}

	/**
	 * Takes in an ArrayList of logic.Maps
	 * @param maps
	 * @return
     */
	public ObservableList<logic.Map> getMaps(HashMap<Integer, logic.Map> maps) {
		ObservableList<logic.Map> data = FXCollections.observableArrayList();

		maps.forEach((k,v) -> {data.add(v);});

		return data;
	}
	
	
}