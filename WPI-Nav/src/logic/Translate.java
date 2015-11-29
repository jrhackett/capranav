package logic;

import java.util.HashMap;

/**
 * Created by Josh on 11/29/2015.
 */
public class Translate {
    double x_local;
    double y_local;
    double z_local;

    double x_universal;
    double y_universal;
    double z_universal;

    double k;
    double omega;

    double translate_x;
    double translate_y;

    /**
     *
     * @param local_A
     * @param local_B
     * @param universal_A
     * @param universal_B
     */
    public Translate(INode local_A, INode local_B, INode universal_A, INode universal_B) {

        // calculate and set resize constant
        double length_L = AStarShortestPath.getHeuristic(local_A, local_B);
        double length_U = AStarShortestPath.getHeuristic(universal_A, universal_B); // TODO: double check this?
        k = length_U / length_L;

        // set rotate angle
        double theta_L = Math.atan2((local_B.getY() - local_A.getY()), (local_B.getX() - local_A.getX()));
        // TODO double check that the angle is the same for different coordinate systems
        double theta_U = Math.atan2((universal_B.getY() - universal_A.getY()),
                (universal_B.getX() - universal_A.getX()));

        omega = theta_U - theta_L; // TODO: double check which one is subtracted from which

        // set translate value
        double x_buffer = (local_A.getX() * k);
        double y_buffer = (local_A.getY() * k);

        translate_x = (universal_A.getX() - ((x_buffer * Math.cos(omega)) - (y_buffer * Math.sin(omega))));
        translate_y = (universal_A.getY() - ((x_buffer * Math.sin(omega)) + (y_buffer * Math.cos(omega))));

    }

    public void setUniversalCoordinates(HashMap<Integer, INode> local_coord){
        //name.forEach((key, v)->{};
        local_coord.forEach((k, v) -> { // for each Node in ArrayList<Node>
            // local_coord
            // get the local x,y,z coordinates for Node v
            x_local = v.getX();
            y_local = v.getY();
            z_local = v.getZ();

            resizeCoordinates(); //change the x_universal variable based on the resize coordinate value
            rotateCoordinates(); //change the y_universal variable based on the rotation angle value
            translateCoordinates(); //translate the x_universal and y_universal values based on translate coordinates;
            z_universal = z_local; //keep the z values the same for each coordinate

            // set Node v's universal Coordinates to the calculated values
            v.setX_univ(x_universal);
            v.setY_univ(y_universal);
            v.setZ_univ(z_universal);
        });
    }

    private void resizeCoordinates() {
        x_universal = x_local * k;
        y_universal = y_local * k;
    }

    private void rotateCoordinates() {
        double x_val = x_universal;
        double y_val = y_universal;

        x_universal = (x_val * Math.cos(omega))
                - (y_val * Math.sin(omega)); // sin and cos in radians
        y_universal = (x_val * Math.sin(omega))
                + (y_val * Math.cos(omega)); // sin and cos in radians
    }

    private void translateCoordinates() {
        x_universal += translate_x;
        y_universal += translate_y;
    }
}
