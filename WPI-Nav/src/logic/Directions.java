package logic;

import java.util.ArrayList;

public class Directions {
	private static double totalDistance = 0;

	/**
	 * stepByStep takes in an arrayList of Nodes and outputs a list of
	 * 
	 * @param aStarPath
	 * @return
	 */
	public static ArrayList<String> stepByStep(ArrayList<Node> aStarPath) {
		ArrayList<String> directions = new ArrayList<String> ();

		// Do special case for first node
		double dist = Math.sqrt(Math.pow((aStarPath.get(0).getX() - aStarPath.get(1).getX()), 2)
				+ Math.pow((aStarPath.get(0).getY() - aStarPath.get(1).getY()), 2));

		totalDistance += dist;

		double angle = Math.atan2((aStarPath.get(0).getY() - aStarPath.get(1).getY()),
				(aStarPath.get(0).getX() - aStarPath.get(1).getX()));
		
		angle = Math.round(angle*180/Math.PI - 180);
		if(angle < 0){
			angle+= 360;
		}

		directions.add("Face " + Math.round(angle) + ", and walk " + Math.round(dist) + " feet.");

		for (int i = 0; i < aStarPath.size() - 2; i++) {
			Node prev = aStarPath.get(i);
			Node turn = aStarPath.get(i + 1);
			Node next = aStarPath.get(i + 2);

			// get the distance to the next node and angle
			dist = Math.sqrt(Math.pow((turn.getX() - prev.getX()), 2) + Math.pow((turn.getY() - prev.getY()), 2));
			totalDistance += dist;
			angle = getAngle(prev, turn, next);
			angle = angle*180/Math.PI - 180;
			if(angle < -180){
				angle+= 360;
			}
			if(angle > 180){
				angle-= 360;
			}

			directions.add(
					"Turn " + Math.round(angle) + ", and walk " + Math.round(dist) + " feet.");

		}
		// TODO Add english words for angles
		// TODO Add landmarks in place of distance
		return directions;
	}

	/**
	 * the function getAngle takes in 3 nodes and determines the turn angle at
	 * the center node
	 * 
	 * @param previous
	 *            Node came form
	 * @param turn
	 *            Node turning at
	 * @param next
	 *            Node walking to after turn
	 * @return angle in radians
	 */
	private static double getAngle(Node previous, Node turn, Node next) {
		double theta1;
		double theta2;
		double angle;

		theta1 = Math.atan2((turn.getY() - previous.getY()), (turn.getX() - previous.getX()));
		theta2 = Math.atan2((next.getY() - turn.getY()), (next.getX() - turn.getX()));

		angle = (Math.PI - theta1 + theta2) % (2 * Math.PI);
		return angle;
	}

	public double getTotalDistance() {
		return totalDistance;
	}
	
	
	// This method converts a given angle into the proper string 
	public static String AngletoString(double angle){
        if (angle <= 10 && angle >= -10) return "straight ahead"; //straight forward
        if (angle < -10 && angle >= -35) return"slight left";
        if (angle < -35 && angle >= -60) return"left";
        if (angle < -60 && angle >= -110) return"hard left";
        if (angle < -110 && angle >= -180) return"a near U-turn left";
        if (angle > 10 && angle <= 35) return"slight right";
        if (angle > 35 && angle <= 60) return"right";
        if (angle > 60 && angle <= 110) return"hard right";
        if (angle > 110 && angle <= 180) return"a near U-turn right";
        else return"back to an actual coordinate system";
    }

}
