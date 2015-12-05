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
		int mapstep=0;
		double distspec = 0;
		//boolean veryfirm = false;

		//^^NEW LINE, init var distspec

		ArrayList<ArrayList<Instructions>> directions = new ArrayList<ArrayList<Instructions>>();
		directions.add(new ArrayList<Instructions>());
		
		// Do special case for first node
		for (INode i : aStarPath){
			System.out.println(i.getID());
		}

		System.out.println(aStarPath.size());

		double dist = Math.sqrt(Math.pow((aStarPath.get(0).getX_univ() - aStarPath.get(1).getX_univ()), 2)
				+ Math.pow((aStarPath.get(0).getY_univ() - aStarPath.get(1).getY_univ()), 2));


		double scalar = 1;

		if(maps.containsKey(aStarPath.get(0).getMap_id())){
			scalar = maps.get(aStarPath.get(0).getMap_id()).getPixelToFeetRatio();
			dist *= scalar;
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
		String distPhrase = Math.round(distfirst) + " feet.";
		
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
		
			int j = 1;
			while(aStarPath.size()>i+j+2 && getAngle(aStarPath.get(i+j),aStarPath.get(i+j+1),aStarPath.get(i+j+2)) > 2.87979327 && getAngle(aStarPath.get(i+j-1),aStarPath.get(i+j),aStarPath.get(i+j+1)) < 3.40339204){
				//While loop checks if future turns are straight and we have not reached the end
				distspec += (Math.sqrt(Math.pow((aStarPath.get(i+j+1).getX_univ() - aStarPath.get(i+j+2).getX_univ()), 2) + Math.pow((aStarPath.get(i+j+1).getY_univ() - aStarPath.get(i+j+2).getY_univ()), 2)));
				//Add the distance of a step in the future
				j++;
			}

			//new functionality: special directions for landmarks. Hopefully doesn't return silly grammar.
			if(turn.isInteresting()){
				anglePhrase = AngletoString((int) Math.round(angle)) + "at " + turn.toString();
			}
			
			else anglePhrase = AngletoString((int) Math.round(angle));
			distPhrase = Math.round(distspec) + " feet.";
			//distPhrase now uses distspec.

			// specialdirs changed lines VV
			if (next instanceof Stairs || next instanceof TStairs) distPhrase = "climb the stairs and go " + Math.round(distspec) + " feet.";
			if (next instanceof Elevator) distPhrase = "enter the elevator."; //TODO: This should include what floor to select
			// specialdirs changed lines ^^
			//if outside node mapid different then go inside/outside message
			if (turn.getMap_id()==0 && next.getMap_id()!=0){ //going inside
				distPhrase = "out the door.";
			}
			if (turn.getMap_id()!=0 && next.getMap_id()==0){ //going outside
				distPhrase = "inside the building";
			}
			
			//if (angle<=-10 || angle>=10 || (aStarPath.size()==i+j+2 && veryfirm == false)){
			if (angle<=-10 || angle >=10){
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