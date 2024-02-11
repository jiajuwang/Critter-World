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

/* Critter1 description:
 * critter that can only walk up, down, left, right. Moves a certain number of steps in the same direction depending on it's 'color.'
 * critter will change directions once it has reached such number of steps.
 * critter only fights members of its own kind
 * The critter also reproduces on a rate depending on it's color. After x time steps, the critter will reproduce.
 */

import javafx.scene.paint.Color;

public class Critter1 extends Critter {

    @Override
    public String toString() {
        return "1";
    }
    private int dir;
    private int color;
    private int steps;
    private int rate;

    public Critter1() {
        color = Critter.getRandomInt(7);
        dir = Critter.getRandomInt(6);
        steps = 0;

        if(dir%2 == 1){
            dir+=1;
        }

    }

    @Override
    //Only fight amongset themeselves
    public boolean fight(String oponent) {
        if(oponent.equals("1")) {
            return true;
        }
        return false;
    }

    @Override
    public void doTimeStep() {
        if(steps == color){
            steps = 0;
            int tempDir = dir;
            while(tempDir != dir){
                tempDir = Critter.getRandomInt(6);
                if(tempDir%2 == 1){
                    if(tempDir == 7){
                        tempDir = 6;
                    }
                    else{
                        tempDir+=1;
                    }
                }
            }
            dir = tempDir;
        }
        walk(dir);

        if (getEnergy() > Params.MIN_REPRODUCE_ENERGY) {
            if(color == 0 && rate == 2){
                rate = 0;
                Critter1 child = new Critter1();
                reproduce(child, Critter.getRandomInt(8));
            }
            else if(color > 0 && color < 4 && rate == 2*color){
                rate = 0;
                Critter1 child = new Critter1();
                reproduce(child, Critter.getRandomInt(8));
            }
            else if (color < 8 && rate == color) {
                rate = 0;
                Critter1 child = new Critter1();
                reproduce(child, Critter.getRandomInt(8));
            }

        }

    }
    public CritterShape viewShape() {
        return CritterShape.SQUARE;
    }

    public javafx.scene.paint.Color viewColor() {
        return Color.PURPLE;
    }

    public javafx.scene.paint.Color viewOutlineColor() { return Color.GRAY; }

}

