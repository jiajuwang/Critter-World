package assignment5;

import assignment5.Critter.CritterShape;

/*
 * CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Guadalupe Melendez
 * gm28642
 * 17150
 * Jiaju Wang
 * jw56255
 * 17150
 * Slip days used: <0>
 * Spring 2023
 */
// Fighters run in squares and don't hurt civilians and other fighters
public class Critter3 extends Critter {
	
	private int range;
	private int currentD;
	private int dir;
	
	/*range indicates how large the square they run in
	 * currentD indicates their current location in the square
	 * dir indicates their starting direction
	 */
	public Critter3() {
		range = 6+Critter.getRandomInt(5);
		currentD = 0;
		dir = getRandomInt(8);
	}
    @Override
    //Fighter's symbol
    public String toString() {
        return "3";
    }
    
	@Override
	//run in a different side when arriving at the edge
	public void doTimeStep() {
		// TODO Auto-generated method stub
		if(currentD<range) {
			run(dir);
			currentD+=2;
		}
		else {
			currentD = 0;
			dir = (dir+2)%8;
			run(dir);
		}
		
	}

	
	@Override
	//Fight everyone except themselves and civilians.
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		look(0,true);
		if((oponent.equals("3"))||(oponent.equals("4"))) {
			return false;
		}
		return true;
	}
	
	public CritterShape viewShape() {
		 return CritterShape.CIRCLE;
	 }
	
    public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.RED;
    }

}
