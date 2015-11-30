package visuals;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import logic.Floor;
import logic.IMap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Currently using a 'simple' ComboBox.
 * Looking into alternate/advanced/accurate options
 */
public class Inputs extends ComboBox {
	private String initial;
	ObservableList<InputItem> data;
	ObservableList<Walking> walking;
	Controller controller;

	@SuppressWarnings("unchecked") /* probably should remove this */
	public Inputs(String s, double WIDTH, Controller controller) {
		super();
		initial = s;
		this.controller = controller;
		this.setMaxWidth(WIDTH); //TODO sizing here
		this.setMinWidth(0);
		this.setEditable(true);

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


	/****************************************************************************************************************
	 * ConvertMaps and ConvertNodes will no longer be used - we need to convert them together.
	 ****************************************************************************************************************/

	public ObservableList<InputItem> createInputItems(HashMap<Integer, logic.INode> nodes, HashMap<Integer, logic.IMap> maps) {
		this.data = FXCollections.observableArrayList();
		nodes.forEach((k, v) -> { //For each node
			 addNode(v, maps.get(v.getMap_id()));
		});

		return data.sorted();
	}

	public ObservableList<Walking> createWalkingItems(ArrayList<Walking> walkingArrayList) {
		this.walking = FXCollections.observableArrayList();
		this.walking.addAll(walkingArrayList);
		return this.walking;
	}



	public InputItem addNode(logic.INode v, IMap map) {
		InputItem item = null;
		if (v.isInteresting()) {
			for (String s : v.getNames()) {//For each of its names
				if (map.inside()) {//FOOD should probably also not have map extensions
					for (String m : getNames(((Floor) map).getBuildingID())) {//TODO we should just do buildings and campus separately
						item = new InputItem(v.getID(), m + " " + s);
						if (!data.contains(item)) data.add(item); //TODO this work around will probably not work
					}
				} else {
					item = new InputItem(v.getID(), s);
					data.add(item);
				}
			}
		} else if(v.isTransition()) {
			if (map.inside()) {//FOOD should probably also not have map extensions
				for (String m : getNames(((Floor) map).getBuildingID())) {//TODO we should just do buildings and campus separately
					item = new InputItem(v.getID(), m + " " + v.toString());
					if (!data.contains(item)) data.add(item); //TODO this work around will probably not work
				}
			} else {
				item = new InputItem(v.getID(), v.toString());
				data.add(item);
			}

		}

		return item;
	}

	public void removeNode(int id) {
		InputItem iii = null;
		for (InputItem ii : data) {
			if (ii.getId() == id) {
				iii = ii;
			}
		}
		data.remove(iii);
	}

	public ArrayList<String> getNames(int building_id) {
		return controller.getBuildingNames(building_id);
	}
}