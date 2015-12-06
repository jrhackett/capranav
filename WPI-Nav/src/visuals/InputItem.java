package visuals;

/**
 * This class will be very simple. Instantiations are going to correspond directly,
 * 1 - 1, with the objects in the search pane. Multiple InputItems can/will correspond
 * to a single node on a specific map.
 */
public class InputItem {
    private int node_id;
    private String value;

    public InputItem(int id, String value){
        this.node_id = id;
        this.value = value;
    }

    public int getId()       {return  this.node_id; }
    public String toString() {return  this.value; }
    //wow
   // public boolean equals(InputItem ii) {return this.toString().equals(ii.toString());}
}
