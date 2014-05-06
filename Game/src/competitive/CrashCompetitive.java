package competitive;

import java.awt.geom.Point2D;

import logicClasses.Flight;

public class CrashCompetitive {

	private Flight flight1, flight2;
	private Point2D.Double pointOfCrash;
	private int countdownTillRemoval;

	// Used to store all information needed to carry out flight explosions
	// effectively.
	CrashCompetitive(Flight flight1, Flight flight2, Point2D.Double pointOfCrash) {
		this.flight1 = flight1;
		this.flight2 = flight2;
		this.pointOfCrash = pointOfCrash;
		this.countdownTillRemoval = 40;
	}

	public Flight getFlight1() {
		return flight1;
	}

	public void setFlight1(Flight flight1) {
		this.flight1 = flight1;
	}

	public Flight getFlight2() {
		return flight2;
	}

	public void setFlight2(Flight flight2) {
		this.flight2 = flight2;
	}

	public Point2D.Double getPointOfCrash() {
		return pointOfCrash;
	}

	public void setPointOfCrash(Point2D.Double pointOfCrash) {
		this.pointOfCrash = pointOfCrash;
	}

	public int getCountdownTillRemoval() {
		return countdownTillRemoval;
	}

	public void setCountdownTillRemoval(int countdownTillRemoval) {
		this.countdownTillRemoval = countdownTillRemoval;
	}

}
