package visuals;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
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
            VBox vbox = new VBox();
            vbox.setId("infoTip");
            Text text = new Text(info);
            text.setId("infoTip");
            vbox.getChildren().add(text);
            display = new PopOver();
            display.setContentNode(vbox);
            display.setArrowLocation(arrowLocation);
            display.setArrowSize(5.0);
            display.setDetachable(false);
            display.setId("infoTip");
        }
        this.display.show(owner, 5);
    }

}
