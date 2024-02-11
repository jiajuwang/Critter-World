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
//some civilians reproduce and they don't fight against anyone
public class Critter4 extends Critter{
	
	private boolean Reproductivity;
	private int dir;
	
	//assign Critter's walk direction as well as its ability to reproduce
	public Critter4() {
		if(Critter.getRandomInt(2)==0) {
			Reproductivity = false;
		}
		else {
			Reproductivity = true;
		}
		dir = Critter.getRandomInt(8);
	}
	
    @Override
    //Symbol for Civilian
    public String toString() {
        return "4";
    }
    
	@Override
	//reproduce when they can, otherwise walk 
	public void doTimeStep() {
		// TODO Auto-generated method stub
		look(1,false);
		if((getEnergy()>=Params.MIN_REPRODUCE_ENERGY)&&(Reproductivity)) {
			Critter4 child = new Critter4();
			int temp = Critter.getRandomInt(8);
			reproduce(child, temp);
			walk(dir);
		}
		else  {
			walk(dir);
		}
		
		
		
	}
	

	@Override
	//Don't fight
	public boolean fight(String oponent) {
		return false;
	}
	
	 public CritterShape viewShape() {
		 return CritterShape.CIRCLE;
	 }
	 
	    public javafx.scene.paint.Color viewColor() {
	        return javafx.scene.paint.Color.BLUE;
	    }

}
