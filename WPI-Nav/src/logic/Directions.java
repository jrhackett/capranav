package logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class Directions {
	private static double totalDistance = 0;
	private final Logger logger = LoggerFactory.getLogger(Directions.class);

	/**
	 * stepByStep takes in an arrayList of Nodes and outputs a list of
	 * 
	 * @param aStarPath
	 * @return
	 */
	public static ArrayList<String> stepByStep(ArrayList<INode> aStarPath, HashMap<Integer, Map> maps) {
		// TODO: Implement skipping of straight path nodes

		ArrayList<String> directions = new ArrayList<String>();

		// Do special case for first node
		double dist;
		try {
			dist = Math.sqrt(Math.pow((aStarPath.get(0).getX() - aStarPath.get(1).getX()), 2)
					+ Math.pow((aStarPath.get(0).getY() - aStarPath.get(1).getY()), 2));
		} catch (IndexOutOfBoundsException e){
			e.printStackTrace(); //logging doesnt work in a static context!
			dist = 7; //LOL
		}
		
		double scalar = 1;
		
		if(maps.containsKey(aStarPath.get(0).getMap_id())){
			scalar = maps.get(aStarPath.get(0).getMap_id()).getPixelToFeetRatio();
			dist *= scalar;
		} else {
			// throw exception
		}

		totalDistance += dist;
		double angle;
		try {
			angle = Math.atan2((aStarPath.get(0).getY() - aStarPath.get(1).getY()),
					(aStarPath.get(0).getX() - aStarPath.get(1).getX()));
		} catch (IndexOutOfBoundsException e){
			angle = 30;
		}

		angle = Math.round(angle * 180 / Math.PI - 180);
		if (angle < 0) {
			angle += 360;
		}

		// This is used to store the text for a given angle
		String anglePhrase = "Error";

		// This if block converts the string to cardinal directions
		// Assumes: East = 0 degrees, South = 90 degrees
		if (angle >= 0 && angle <= 22)
			anglePhrase = "East";
		if (angle > 22 && angle <= 67)
			anglePhrase = "South-East";
		if (angle > 67 && angle <= 112)
			anglePhrase = "South";
		if (angle > 112 && angle <= 157)
			anglePhrase = "South-West";
		if (angle > 157 && angle <= 202)
			anglePhrase = "West";
		if (angle > 202 && angle <= 247)
			anglePhrase = "North-West";
		if (angle > 247 && angle <= 292)
			anglePhrase = "North";
		if (angle > 292 && angle <= 337)
			anglePhrase = "North-East";
		if (angle > 337 && angle <= 360)
			anglePhrase = "East";

		String distPhrase = Math.round(dist) + " feet.";

		directions.add("Face " + anglePhrase + ", and walk " + distPhrase);

		for (int i = 0; i < aStarPath.size() - 2; i++) {
			INode prev = aStarPath.get(i);
			INode turn = aStarPath.get(i + 1);
			INode next = aStarPath.get(i + 2);

			// get the distance to the next node and angle
			dist = Math.sqrt(Math.pow((turn.getX() - next.getX()), 2) + Math.pow((turn.getY() - next.getY()), 2));
			
			if(maps.containsKey(turn.getMap_id())){
				scalar = maps.get(turn.getMap_id()).getPixelToFeetRatio();
				dist *= scalar;
			} else {
				// throw exception
			}
			
			totalDistance += dist;
			angle = getAngle(prev, turn, next);
			angle = angle * 180 / Math.PI - 180;
			if (angle < -180) {
				angle += 360;
			}
			if (angle > 180) {
				angle -= 360;
			}

			anglePhrase = AngletoString((int) Math.round(angle));
			distPhrase = Math.round(dist) + " feet.";
			// TODO: implement special phrase for when a node is a landmark
			// if (next is a landmark){
			// distPhrase = next.specialPhrase;
			// } else {
			// distPhrase = Math.round(dist) + " feet.";
			// }

			directions.add("Turn " + anglePhrase + ", and walk " + distPhrase);

		}

		directions.add("You have reached your destination");

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
	private static double getAngle(INode previous, INode turn, INode next) {
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
	public static String AngletoString(int angle) {
		if (angle <= 10 && angle >= -10)
			return "straight ahead";
		if (angle < -10 && angle >= -35)
			return "slight left";
		if (angle < -35 && angle >= -60)
			return "left";
		if (angle < -60 && angle >= -110)
			return "hard left";
		if (angle < -110 && angle >= -180)
			return "a near U-turn left";
		if (angle > 10 && angle <= 35)
			return "slight right";
		if (angle > 35 && angle <= 60)
			return "right";
		if (angle > 60 && angle <= 110)
			return "hard right";
		if (angle > 110 && angle <= 180)
			return "a near U-turn right";
		return String.valueOf(angle);
	}

}
