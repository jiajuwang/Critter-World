package assignment5;
/*
 * CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 *Guadalupe Melendez
 * gm28642
 * 17150
 * Jiaju Wang
 * jw56255
 * 17150
 * Slip days used: <0>
 * Spring 2023
 */
import java.util.Objects;

//record the position of all Critters
public class Position {
    int x;
    int y;
    int hashCode;
    public Position(int x, int y) {
    	this.x = x;
    	this.y = y;
    	this.hashCode = Objects.hash(x, y);
    }
    @Override
    public boolean equals(Object p) {
    	Position temp = (Position)p;
    	return (this.x == temp.x)&&(this.y==temp.y);
    }
    @Override
    public int hashCode() {
        return this.hashCode;
    }
    

}
