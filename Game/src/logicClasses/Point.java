package logicClasses;


public class Point {

	protected double x;
	protected double y;
	protected String pointRef;


	// CONSTRUCTORS

	/**
	 *Point consturcot taking 2 doubles for X and Y coord 
	 * @param xcoord x coord
	 * @param ycoord y coord
	 */
	public Point(double xcoord, double ycoord) {
		x = xcoord;
		y = ycoord;
		pointRef = "-";
	}

	/**
	 * Point constructor that takes pointRef string to assign names to exitpoints 
	 * @param xcoord x coord
	 * @param ycoord y coord
	 * @param name waypoint name 
	 */
	public Point(double xcoord, double ycoord, String name){
		x = xcoord; 
		y = ycoord;
		pointRef = name;
	}


	// MUTATORS AND ACCESSORS
	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void setX(double newX) {
		this.x = newX;
	}

	public void setY(double newY) {
		this.y = newY;
	}

	public String getPointRef() {
		return this.pointRef;
	}

	public void setPointRef(String pointRef) {
		this.pointRef = pointRef;
	}

	public boolean equals(Point point){
		return ((point.getX()==x) && (point.getY()==y));
	}
}