package visuals;

import controller.Controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import logic.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Currently using a 'simple' ComboBox. Looking into alternate/advanced/accurate
 * options
 */
public class Inputs<T> extends ComboBox<T> {
	private String initial;
	ObservableList<String> data;
	HashMap<String, Integer> stringToInt = new HashMap<>();
	Application controller;
	ObservableList<Walking> walking;

	public Inputs(String s, double WIDTH, Application controller ) {
		super();
		initial = s;
		this.controller = controller;
		this.setMaxWidth(WIDTH); // TODO sizing here
		this.setMinWidth(0);
		this.setEditable(true);

		// this.setItems(getDummyLocations());
		/*
		 * this.valueProperty().addListener(new ChangeListener<Node>(){
		 * 
		 * @Override public void changed(ObservableValue<? extends Node> arg0,
		 * Node oldValue, Node newValue) { if (oldValue != null){
		 * System.out.print(oldValue.getName() + "->"); }
		 * System.out.println(newValue.getName());
		 * 
		 * } });
		 */

		/*
		 * this.setCellFactory(new Callback<ListView<Node>, ListCell<Node>>() {
		 *
		 * @Override public ListCell<Node> call(ListView<Node> param) {
		 * ListCell<Node> cell = new ListCell<>(){
		 *
		 * @Override public void updateItem(Node node, boolean empty){ if (node
		 * != null){ setText(node.toString());
		 *
		 *
		 * } setTextFill(Color.GREEN); } } return null; }
		 *
		 * });
		 */
	}

	/**
	 * Converts a HashMap of maps to an ObserableList
	 * 
	 * @param maps
	 * @return
	 */
	public ObservableList<String> convertMaps(HashMap<Integer, IMap> maps) {
		//System.out.println("Turning maps into ObservableItemList");
		
		if (maps.isEmpty()) {
			//System.out.println("Hashmap of maps is empty.");
			return null;
		} else {
			ObservableList<String> dataMap = FXCollections.observableArrayList();
			maps.forEach((k, v) -> {
				dataMap.add(v.getFilePath());
			});
			return dataMap.sorted(); // NOTE: this wont work in java 8.40
		}

	}

	// This returns all the Obvservable List objects for use in the main
	// application
	public ObservableList<String> createInputItems(HashMap<Integer, logic.INode> nodes,
													  HashMap<Integer, logic.IMap> maps) {
		this.data = FXCollections.observableArrayList();
		nodes.forEach((k, v) -> { // For each node
			addNode(v, maps.get(v.getMap_id()));
		});

		return data.sorted();
	}

	public ObservableList<Walking> createWalkingItems(ArrayList<Walking> walkingArrayList) {
		this.walking = FXCollections.observableArrayList();
		this.walking.addAll(walkingArrayList);
		return this.walking;
	}


	public String addNode(logic.INode v, IMap map) {
		//System.out.println("INode: " + v);
		String item = new String();
		if (v.isInteresting()) {
			for (String s : v.getNames()) {// For each of its names
				if (map.inside()) {// FOOD should probably also not have map
									// extensions
					for (String m : getNames(((Floor) map).getBuildingID())) {
						item =  m + " " + s;
						if (!data.contains(item)){
							data.add(item);
							stringToInt.put(item, v.getID());
						}
					}
				} else {
					item = s;
					data.add(item);
					stringToInt.put(item, v.getID());
				}
			}
		} else if (v instanceof Transition && !(v instanceof TStairs || v instanceof Elevator)) {
			if(((Transition) v).getNames() != null) {
				for (String s : v.getNames()) {// For each of its names

					item = s;
					if (!data.contains(item)) {
						data.add(item);
						stringToInt.put(item, v.getID());
					}

				}
			}
		}

		return item;
	}

	public void removeNode(int id) {
		String iii = null;
		for (String ii : data) {
			if (stringToInt.get(ii) == id) {
				iii = ii;
			}
		}
		data.remove(iii);
	}

	public ArrayList<String> getNames(int building_id) {
		Controller temp = (Controller) controller;
		return temp.getBuildingNames(building_id);
	}

	public int getNode(String str){
		if(stringToInt.containsKey(str)){
			return stringToInt.get(str);
		} else {
			return 0;
		}
	}

	public boolean containsNode(String str){
		return stringToInt.containsKey(str);
	}
}