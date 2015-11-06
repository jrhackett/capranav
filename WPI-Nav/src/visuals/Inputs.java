package visuals;

import logic.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class Inputs extends ComboBox {
	private final static int WIDTH = 160;
	private String initial;
	int rank;

	@SuppressWarnings("unchecked") /* probably should remove this */
	public Inputs(String s){
		super();
		initial = s;
		this.setMaxWidth(WIDTH);
		this.setMinWidth(WIDTH);
		this.setItems(getDummyLocations());
		this.valueProperty().addListener(new ChangeListener<Node>(){
			
			@Override
			public void changed(ObservableValue<? extends Node> arg0, Node oldValue, Node newValue) {
				// TODO Auto-generated method stub
				if (oldValue != null){
				System.out.print(oldValue.getName() + "->");
				}
				System.out.println(newValue.getName());
			
			}
		});
	
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
	private ObservableList<Node> getDummyLocations() {
		ObservableList<Node> data = FXCollections.observableArrayList();
		
		data.addAll(
				new Node("Faraday", 1, 2, 0),
				new Node("Campus Center", 3, 4, 0),
				new Node("Student Center",  5, 4, 0));
		
		return data;
	}
	
	
}