package visuals;

import javafx.scene.Node;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;


public class InfoTip {
    String info;
    Node owner;
    public PopOver display;
    private PopOver.ArrowLocation arrowLocation;

    public InfoTip(String info, Node owner, PopOver.ArrowLocation arrowLocation){
        this.info = info;
        this.owner = owner;
        this.arrowLocation = arrowLocation;
    }

    public void show(){
        if(display == null) {
            Text text = new Text(info);
            display = new PopOver();
            display.setContentNode(text);
            display.setArrowLocation(arrowLocation);
            display.setDetachable(false);
            //display.setId("infoTip");
            text.setId("infoTip");
        }
        this.display.show(owner, 5);
    }

}
