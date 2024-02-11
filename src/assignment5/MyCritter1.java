package assignment5;



import java.util.List;

import assignment5.Critter.TestCritter;

/**
 * Wise critter, fight only when it has energy
 */
public class MyCritter1 extends TestCritter {

    @Override
    public void doTimeStep() {
        walk(0);
    }

    @Override
    public boolean fight(String opponent) {
         return true;
        
    }
    
   
    
    public String toString() {
        return "1";
    }

    public void test(List<Critter> l) {
    }

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return null;
	}
}
