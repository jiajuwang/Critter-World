package assignment5;

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
/* Critter2 description:
 * critter runs diagonally.
 * critter only fights if it is a certain age.
 * The critter also wil only reproduces if it is old enough.
 * critter takes eight steps (4 timesteps since its running) before changing direction
 *
 */

import javafx.scene.paint.Color;

public class Critter2 extends Critter{
    @Override
    public String toString() {
        return "2";
    }

    private int age;
    private int dir;
    private int steps;

    public Critter2() {
        dir = Critter.getRandomInt(7);
        steps = 0;

        if(dir%2 == 0){
            dir+=1;
        }

    }

    @Override
    //Only fight if they are above age 13
    public boolean fight(String oponent) {
        if(age > 13) {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void doTimeStep() {
        if(steps%4 == 0){
            int tempDir = dir;
            while(tempDir != dir){
                tempDir = Critter.getRandomInt(7);
                if(tempDir%2 == 0){
                    tempDir +=1;
                }
            }
            dir = tempDir;
        }
        run(dir);
        steps++;

        if (getEnergy() > Params.MIN_REPRODUCE_ENERGY) {
            if(age < 15){
                Critter1 child = new Critter1();
                reproduce(child, Critter.getRandomInt(8));
            }
        }
    }
    public CritterShape viewShape() {
        return CritterShape.DIAMOND;
    }

    public javafx.scene.paint.Color viewColor() {
        return Color.DARKSEAGREEN;
    }
}
