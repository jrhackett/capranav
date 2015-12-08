package logic;

import visuals.Instructions;

import java.util.ArrayList;
import java.util.HashMap;

public class Directions {
	private static double totalDistance = 0;

	public Directions(){
		totalDistance = 0;
	}
	//TODO: multiply ALL by campus pixel to feet ratio....which we do not know
	/**
	 * stepByStep takes in an arrayList of Nodes and outputs a list of
	 *
	 * @param aStarPath
	 * @return
	 */
	public static ArrayList<ArrayList<Instructions>> stepByStep(ArrayList<INode> aStarPath, HashMap<Integer, IMap> maps) {
		totalDistance = 0;
		int mapstep=0;
		double distspec = 0;
		String distPhrase =" ";
		//boolean veryfirm = false;

		//^^NEW LINE, init var distspec

		ArrayList<ArrayList<Instructions>> directions = new ArrayList<ArrayList<Instructions>>();
		directions.add(new ArrayList<Instructions>());

		// Do special case for first node

		double dist = Math.sqrt(Math.pow((aStarPath.get(0).getX_univ() - aStarPath.get(1).getX_univ()), 2)
				+ Math.pow((aStarPath.get(0).getY_univ() - aStarPath.get(1).getY_univ()), 2));


		double scalar = 1;

		if(maps.containsKey(aStarPath.get(0).getMap_id())){
			scalar = maps.get(aStarPath.get(0).getMap_id()).getPixelToFeetRatio();
			//dist *= scalar;
		} else {
			// throw exception
		}

		totalDistance += dist;

		double angle = Math.atan2((aStarPath.get(0).getY_univ() - aStarPath.get(1).getY_univ()),
				(aStarPath.get(0).getX_univ() - aStarPath.get(1).getX_univ()));


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

		if(aStarPath.size() == 2){
			distPhrase = Math.round(dist) + " feet.";
			directions.get(0).add(new Instructions("Face " + anglePhrase + ", and walk " + distPhrase,aStarPath.get(0)));
			return directions;
		}

		int p=0;
		double distfirst = dist;
		INode firstprev = aStarPath.get(0);
		INode firstturn = aStarPath.get(1);
		INode firstnext = aStarPath.get(2);
		while(aStarPath.size()>p+2 && getAngle(firstprev,firstturn,firstnext) > 2.87979327 && getAngle(firstprev,firstturn,firstnext) < 3.40339204){
			firstprev = aStarPath.get(p);
			firstturn = aStarPath.get(p + 1);
			firstnext = aStarPath.get(p + 2);
			distfirst += (Math.sqrt(Math.pow((firstturn.getX_univ() - firstnext.getX_univ()), 2) + Math.pow((firstturn.getY_univ() - firstnext.getY_univ()), 2)));
			p++;
		}
		distPhrase = Math.round(distfirst) + " feet.";

		directions.get(0).add(new Instructions("Face " + anglePhrase + ", and walk " + distPhrase,aStarPath.get(0)));

		for (int i = 0; i < aStarPath.size() - 2; i++) {
			INode prev = aStarPath.get(i);
			INode turn = aStarPath.get(i + 1);
			INode next = aStarPath.get(i + 2);

			// get the distance to the next node and angle
            // Set to use universal.
			dist = Math.sqrt(Math.pow((turn.getX_univ() - next.getX_univ()), 2) + Math.pow((turn.getY_univ() - next.getY_univ()), 2));

			distspec += dist;

			//add CURRENT dist to distspec, which is used for adding culled distances
			//Future steps' distance will be added to this variable later.

			if(maps.containsKey(turn.getMap_id())){
				scalar = maps.get(turn.getMap_id()).getPixelToFeetRatio();
				if(turn.getMap_id() != 0) {
					dist *= scalar;
				}
			} else {
				// throw exception
			}
			distspec += dist;
			totalDistance += dist;
			angle = getAngle(prev, turn, next);
			angle = angle * 180 / Math.PI - 180;
			if (angle < -180) {
				angle += 360;
			}
			if (angle > 180) {
				angle -= 360;
			}

			int j = 1;
			double futuredist = 0;
			while(aStarPath.size()>i+j+2 && getAngle(aStarPath.get(i+j),aStarPath.get(i+j+1),aStarPath.get(i+j+2)) > 2.87979327 && getAngle(aStarPath.get(i+j-1),aStarPath.get(i+j),aStarPath.get(i+j+1)) < 3.40339204){
				//While loop checks if future turns are straight and we have not reached the end
				futuredist = (Math.sqrt(Math.pow((aStarPath.get(i+j+1).getX_univ() - aStarPath.get(i+j+2).getX_univ()), 2) + Math.pow((aStarPath.get(i+j+1).getY_univ() - aStarPath.get(i+j+2).getY_univ()), 2)));
				if(maps.containsKey(turn.getMap_id())){
					scalar = maps.get(turn.getMap_id()).getPixelToFeetRatio();
					if(turn.getMap_id() != 0) {
						futuredist *= scalar;
					}
				} else {
					// throw exception
				}
				distspec += futuredist;
						//Add the distance of a step in the future
				j++;
			}

			//new functionality: special directions for landmarks. Hopefully doesn't return silly grammar.
			if(turn.isInteresting()){
				anglePhrase = AngletoString((int) Math.round(angle)) + " at " + turn.toString();
			} else {
				anglePhrase = AngletoString((int) Math.round(angle));
			}

			distPhrase = Math.round(distspec) + " feet.";
			//distPhrase now uses distspec.

			// specialdirs changed lines VV
			int zz = 0;
			int flights = 0;
			if(next instanceof TStairs && aStarPath.size() == i+3) distPhrase = "to the stairs.";
			if(next instanceof Elevator && aStarPath.size() == i+3) distPhrase = "to the elevator.";
			if(aStarPath.size() > i+3) {
				if (next instanceof TStairs && next.getMap_id() != aStarPath.get(i + 3).getMap_id()) {
					//i+2 is next
					//i+3 is next+1, the node on the next floor
					while (aStarPath.size() > i + zz + 3 && aStarPath.get(i + zz + 2) instanceof TStairs) {
						flights++;
						zz++;
					}
					if ((maps.get(aStarPath.get(i + 3).getMap_id()).getFloor() > (maps.get(next.getMap_id()).getFloor()))) { //going up
						distPhrase = "to the stairs, and climb up " + flights + " floor(s)";
					} else distPhrase = "to the stairs, and climb down " + flights + " floor(s)";
				}
				zz = 0;
				String elevatorend = "somewhere you can fix this bug."; //should never stay as this
				if (next instanceof Elevator && next.getMap_id() != aStarPath.get(i + 3).getMap_id()) {
					flights = ((Elevator) aStarPath.get(i + zz + 2)).getToFloor(); //initialize so that, in an edge case, it at least says to go where you already are
					while (aStarPath.size() > i + zz + 3 && aStarPath.get(i + zz + 2) instanceof Elevator) {

						zz++;
						flights = ((Elevator) aStarPath.get(i + zz + 2)).getToFloor();
					}
					if (flights == 0) elevatorend = "the basement";
					if (flights == -1) elevatorend = "the sub-basement";
					if (flights < -1) elevatorend = "the depths of the earth"; //should never occur
					if (flights > 0) elevatorend = "floor " + flights;
					distPhrase = "enter the elevator and go to " + elevatorend;
				}
				// specialdirs changed lines ^^
			}
			//if outside node mapid different then go inside/outside message
			if (turn.getMap_id()==0 && next.getMap_id()!=0){ //going inside
				distPhrase = "inside the building";
			}
			if (turn.getMap_id()!=0 && next.getMap_id()==0){ //going outside
				distPhrase = "out the door";
			}

			//if (angle<=-10 || angle>=10 || (aStarPath.size()==i+j+2 && veryfirm == false)){

			if (angle<=-10 || angle >=10 && !(turn instanceof TStairs) && !(turn instanceof Elevator)){

				directions.get(mapstep).add(new Instructions("Turn " + anglePhrase + ", and walk " + distPhrase,turn));
				//if(aStarPath.size()==i+j+2) {
					//veryfirm = true;
				}

			if(turn.isTransition() && next.getMap_id() != turn.getMap_id()){
				mapstep++;
				directions.add(new ArrayList<Instructions>());
			}
			distspec = 0;
		}

		directions.get(directions.size()-1).add(new Instructions("You have reached your destination.",aStarPath.get(aStarPath.size()-1)));

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

	//	theta1 = Math.atan2((turn.getY() - previous.getY()), (turn.getX() - previous.getX()));
	//	theta2 = Math.atan2((next.getY() - turn.getY()), (next.getX() - turn.getX()));

//We should be using universal
		theta1 = Math.atan2((turn.getY_univ() - previous.getY_univ()), (turn.getX_univ() - previous.getX_univ()));
		theta2 = Math.atan2((next.getY_univ() - turn.getY_univ()), (next.getX_univ() - turn.getX_univ()));

		angle = (Math.PI - theta1 + theta2) % (2 * Math.PI);
		return angle;
	}

	public static double getTotalDistance() {
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
			return "very hard left";
		if (angle > 10 && angle <= 35)
			return "slight right";
		if (angle > 35 && angle <= 60)
			return "right";
		if (angle > 60 && angle <= 110)
			return "hard right";
		if (angle > 110 && angle <= 180)
			return "very hard right";
		return String.valueOf(angle);
	}

	/*public static String getRelativeCurrentFloorString(INode current, INode next, HashMap<Integer, IMap> maps){
		if (maps.get(next.getMap_id()).getFloor() > (maps.get(current.getMap_id()).getFloor())){
			//we know we are going up
			return "up to" + ((Floor)maps.get(current.getMap_id())).getFloorName();
		} else {
			return "down to" + ((Floor)maps.get(current.getMap_id())).getFloorName();


		}
	}
	public static String getFloorDifference(INode current, INode next, HashMap<Integer, IMap> maps){
		if(maps.get(next.getMap_id()).getFloor()>(maps.get(current.getMap_id()).getFloor())){
			return "up "
		}
	}*/

	/****************************************************************************************************************
	 												TIME ESTIMATION
	 ****************************************************************************************************************/


	/**
	 * Returns a String with the time calculated to min/sec.
	 * Rounds sec value so that second values are either 0, 15, 30, 45
	 * (Would be a poor estimation if it said it takes 23 seconds to get somewhere)
	 */
	public static String getTime(double walkSpeed) {
		double time = timeEst(walkSpeed); //Get raw time
		System.out.println("Inside getTime: " + time);
		long min = 0;
		while(time >= 60) { //Convert time in seconds to minutes + seconds
			min++;
			time -= 60;
		}

		long sec = Math.round(time/15) * 15; //Rounds seconds to the nearest 1/4 minute
		if (sec == 60) { min++; sec = 0; } //leet hack
		System.out.println("Inside getTime: " + min + " " + sec);
		return min + " min, " + sec + " sec";
	}

	/**
	 * Returns the time estimation for a given route in seconds
	 * @param walkSpeed Person's walking speed in mph
	 *                     Assumes distance is in feet
	 * @return Time in seconds
	 */
	public static double timeEst(double walkSpeed) {
		if (walkSpeed != 0)
			return (getTotalDistance() / (walkSpeed/2.5));
		else return 0;
	}



	public static String getFloorDifference(INode current, INode next, HashMap<Integer, IMap> maps){
		if(maps.get(next.getMap_id()).getFloor()>(maps.get(current.getMap_id()).getFloor())){
			return "up ";
		} return null;
	}

}