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

package assignment5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;



/*
 * See the PDF for descriptions of the methods and fields in this
 * class.
 * You may add fields, methods or inner classes to Critter ONLY
 * if you make your additions private; no new public, protected or
 * default-package code or data can be added to Critter.
 */

public abstract class Critter {

	/* START --- NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}

	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background
	 *
	 * critters must override at least one of the following three methods, it is not
	 * proper for critters to remain invisible in the view
	 *
	 * If a critter only overrides the outline color, then it will look like a non-filled
	 * shape, at least, that's the intent. You can edit these default methods however you
	 * need to, but please preserve that intent as you implement them.
	 */
	public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.WHITE;
	}

	public javafx.scene.paint.Color viewOutlineColor() {
		return viewColor();
	}

	public javafx.scene.paint.Color viewFillColor() {
		return viewColor();
	}

	public abstract CritterShape viewShape();

	protected final String look(int direction, boolean steps) {

		this.energy -= Params.LOOK_ENERGY_COST;
		if ((this.energy <= 0) && (!(dead.contains(this)))) {
			dead.add(this);
		}
		Position ptemp = new Position(this.p.x, this.p.y);

		if (steps) {
			this.energy += Params.RUN_ENERGY_COST;
			this.run(direction);
		} else {
			this.energy += Params.WALK_ENERGY_COST;
			this.walk(direction);
		}
		if (col.containsKey(this.p)) {
			Object[] arr = col.get(this.p).toArray();
			this.p = new Position(ptemp.x, ptemp.y);
			return arr[0].toString();
		} else {
			this.p = new Position(ptemp.x, ptemp.y);
			return null;
		}
	}

	public static String runStats(List<Critter> critters) {
		// TODO Implement this method
    	/*String result = "" + critters.size() + " critters as follows -- ";
    	Map<String, Integer> critter_count = new HashMap<String, Integer>();
        for (Critter crit : critters) {
            String crit_string = crit.toString();
            critter_count.put(crit_string,
                    critter_count.getOrDefault(crit_string, 0) + 1);
        }
        String prefix = "";
        for (String s : critter_count.keySet()) {
            result += prefix + s + ":" + critter_count.get(s);
            prefix = ", ";
        }
        return result;*/

		try {
			String result = " amount: " + critters.size();
			return result;
		} catch (Exception e) {
			return null;
		}


	}



	public static void displayWorld(Object pane) {
		Stage critWorld;
		critWorld = (Stage) pane;
		GridPane cGrid = new GridPane();

		double height;
		double width;
		double size;
		if(Params.WORLD_WIDTH < 31 && Params.WORLD_HEIGHT < 31){
			width = 900;
			height = 900;
			if(Params.WORLD_HEIGHT < Params.WORLD_WIDTH){
				size = width/Params.WORLD_WIDTH;
			}
			else {
				size = height/Params.WORLD_HEIGHT;
			}
		}
		else {
			width = 1600;
			height = 970;

			if (height < width || (height / Params.WORLD_HEIGHT) * Params.WORLD_WIDTH > 960) {
				size = (width / Params.WORLD_WIDTH);
			} else {
				size = (height / Params.WORLD_HEIGHT);
			}
		}

		for (int i = 0; i < Params.WORLD_WIDTH; i++) {
			for (int j = 0; j < Params.WORLD_HEIGHT; j++) {
				Shape s = new Rectangle(size, size);
				s.setFill(null);
				s.setStroke(Color.GRAY);
				cGrid.add(s, i, j);
				Position current = new Position(i+1,j+1);
				if(col.containsKey(current)){
					Shape crit;
					ArrayList<Critter> a = new ArrayList<Critter>();
					for(Critter c : col.get(current)) {
						a.add(c);
					}
					if(a.get(0).viewShape() == CritterShape.CIRCLE){
						Circle critter = new Circle(size/2);
						critter.setFill(a.get(0).viewFillColor());
						critter.setStroke(a.get(0).viewOutlineColor());
						crit = critter;
					}
					else if(a.get(0).viewShape() == CritterShape.SQUARE){
						Rectangle critter = new Rectangle(size, size);
						critter.setFill(a.get(0).viewFillColor());
						critter.setStroke(a.get(0).viewOutlineColor());
						crit = critter;
					}
					else if(a.get(0).viewShape() == CritterShape.TRIANGLE){
						Polygon critter = new Polygon(
								size/2, 0,
								0, size,
								size,size);
						critter.setFill(a.get(0).viewFillColor());
						critter.setStroke(a.get(0).viewOutlineColor());
						crit = critter;
					}
					else if(a.get(0).viewShape() == CritterShape.DIAMOND){
						Polygon critter = new Polygon(
								size/2, 2,
								size, size/2,
								size/2, size,
								2,size/2
						);
						critter.setFill(a.get(0).viewFillColor());
						critter.setStroke(a.get(0).viewOutlineColor());
						crit = critter;
					}
					else if(a.get(0).viewShape() == CritterShape.STAR){
						Polygon critter = new Polygon(
								1.5, ((size*7)/20)+1.5,
								((size*7)/20)+1.5, ((size*7)/20)+1.5,
								size/2, 1.5,
								((size*13)/20)-1.5, ((size*7)/20)+1.5,
								size-2, ((size*7/20))+1.5,
								((size*14)/20)-1.5, ((size*12)/20)-1.5,
								((size*17)/20)-1.5, size-1.5,
								size/2, ((16*size)/20)-1.5,
								((size*3)/20)+1.5, size-1.5,
								((size*6)/20)+1.5, ((size*12)/20)-1.5);

						critter.setFill(a.get(0).viewFillColor());
						critter.setStroke(a.get(0).viewOutlineColor());
						crit = critter;
					}
					else {
						crit = new Rectangle(size,size);
						crit.setFill(null);
					}
					cGrid.add(crit, i, j);
				}

			}
		}


		Scene world = new Scene(cGrid);
		critWorld.setScene(world);
		critWorld.show();

	}

	/* END --- NEW FOR PROJECT 5
			rest is unchanged from Project 4 */

	private int energy = 0;

	private int x_coord;
	private int y_coord;

	private static List<Critter> population = new ArrayList<Critter>();
	private static List<Critter> babies = new ArrayList<Critter>();

	//data structure to record dead Critters
	private static List<Critter> dead = new ArrayList<Critter>();
	//Position variable
	private Position p = new Position(x_coord, y_coord);
	/*collection of all critters*/
	private static Map<Position, Set<Critter>> col = new HashMap<Position, Set<Critter>>();


	/* Gets the package name.  This assumes that Critter and its
	 * subclasses are all in the same package. */
	private static String myPackage;

	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	private static Random rand = new Random();

	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	public static void setSeed(long new_seed) {
		rand = new Random(new_seed);
	}



	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete
	 * subclass of Critter, if not, an InvalidCritterException must be
	 * thrown.
	 *
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static void createCritter(String critter_class_name) throws InvalidCritterException{

		try {
			Critter c =  (Critter)Class.forName(critter_class_name).newInstance();



			c.energy = Params.START_ENERGY;
			c.x_coord = Critter.getRandomInt(Params.WORLD_WIDTH);
			c.y_coord = Critter.getRandomInt(Params.WORLD_HEIGHT);
			c.p = new Position(c.x_coord, c.y_coord);
			Critter.population.add(c);
			if(col.containsKey(c.p)) {
				col.get(c.p).add(c);
			}
			else {
				Set<Critter> Ctemp = new HashSet<Critter>();
				Ctemp.add(c);
				col.put(c.p, Ctemp);
			}
		}
		catch(ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		catch(InstantiationException e) {
			throw new InvalidCritterException(critter_class_name);

		} catch (IllegalAccessException e) {
			throw new InvalidCritterException(critter_class_name);
		}

	}

	/**
	 * Gets a list of critters of a specific type.
	 *
	 * @param critter_class_name What kind of Critter is to be listed.
	 *        Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name)
			throws InvalidCritterException {

		try {
			List<Critter> result = new ArrayList<Critter>();
			for(Critter c: population) {


				if (c.getClass().isInstance(Class.forName(critter_class_name).newInstance())) {
					result.add(c);
				}



			}
			return result;
		}
		catch(ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		catch (InstantiationException e) {

			throw new InvalidCritterException(critter_class_name);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new InvalidCritterException(critter_class_name);
		}

	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		// TODO: Complete this method
		population.clear();
		col.clear();
		dead.clear();
		babies.clear();
	}


	public static void displayWorld() {
		System.out.print("+");
		for(int i = 0; i < Params.WORLD_WIDTH; i++){
			System.out.print("-");
		}
		System.out.println("+");
		for(int i = 0; i < Params.WORLD_HEIGHT; i++){
			for(int j = 0; j < Params.WORLD_WIDTH+2; j++){

				if(j == 0)  {
					System.out.print("|");
				}
				else if(j == Params.WORLD_WIDTH+1){
					System.out.println("|");
				}
				else {
					Position current = new Position(j-1,i);
					if(col.containsKey(current)){
						ArrayList<Critter> a = new ArrayList<Critter>();
						for(Critter c : col.get(current)) {
							a.add(c);
						}
						System.out.print(a.get(0).toString());
					}
					else{
						System.out.print(" ");
					}
				}
			}

		}
		System.out.print("+");
		for(int i = 0; i < Params.WORLD_WIDTH; i++){
			System.out.print("-");
		}
		System.out.println("+");

	}

	//update critters' position to the collection
	private static void updateP() {
		for(Critter c: dead) {
			population.remove(c);
		}
		col.clear();
		for(Critter c: population) {


			if(col.containsKey(c.p)) {
				col.get(c.p).add(c);
			}
			else {
				Set<Critter> temp = new HashSet<Critter>();
				temp.add(c);
				col.put(c.p, temp);
			}
		}
		dead.clear();
	}


	public static void worldTimeStep() {
		// TODO: Complete this method
		for(Critter c : population) {
			c.energy -= Params.REST_ENERGY_COST;
			if(c.energy <= 0) {
				dead.add(c);
			}
			c.doTimeStep();

		}
		updateP();
		Critter.doEncounter();
		updateP();
		for(Critter c: babies) {
			population.add(c);
			if(col.containsKey(c.p)) {
				col.get(c.p).add(c);
			}
			else {
				Set<Critter> temp = new HashSet<Critter>();
				temp.add(c);
				col.put(c.p, temp);
			}
		}
		babies.clear();

		//generate cloves
		for(int i = 0; i<Params.REFRESH_CLOVER_COUNT;i++) {
			Clover c = new Clover();
			c.setEnergy( Params.START_ENERGY);
			c.setX_coord(Critter.getRandomInt(Params.WORLD_WIDTH));
			c.setY_coord(Critter.getRandomInt(Params.WORLD_HEIGHT));
			Position temp = new Position(c.getX_coord(), c.getY_coord());
			population.add(c);
			if(col.containsKey(temp)) {
				col.get(temp).add(c);
			}
			else {
				Set<Critter> Ctemp = new HashSet<Critter>();
				Ctemp.add(c);
				col.put(temp, Ctemp);
			}
		}
	}

	private static void doEncounter() {
		population.clear();
		for(Position p : col.keySet()) {
			Object[] arr =  col.get(p).toArray();
			Set<Critter> temp = new HashSet<Critter>();
			ArrayList<Critter> a = new ArrayList<Critter>();
			for(int i=0;i<arr.length;i++) {
				a.add((Critter) arr[i]);
			}
			Critter c = Battle(a);
			if(c!=null) {
				population.add(Battle(a));
			}
		}

	}

	//Battle for Critters for one spot
	private static Critter Battle(ArrayList<Critter> s) {
		if(s.get(0) == null) {
			return null;
		}
		if(s.size()==1) {
			return s.get(0);
		}
		else {
			Critter a = s.remove(0);
			Critter b = s.remove(0);

			s.add(Duel(a,b));

			return Battle(s);

		}
	}



	//Helper method for Battle, fight between two Critters
	private static Critter Duel(Critter a, Critter b) {
		if(dead.contains(a)&&(dead.contains(b))) {
			return null;
		}
		else if(dead.contains(a)) {
			return b;
		}
		else if(dead.contains(b)) {
			return a;
		}
		else {
			int aE = Critter.getRandomInt(a.getEnergy()+1);
			int bE = Critter.getRandomInt(b.getEnergy()+1);
			Position tempa = a.p;
			Position tempb = b.p;
			boolean af = a.fight(b.toString());
			boolean bf = b.fight(a.toString());

			if(a instanceof Clover) {
				return b;
			}
			else if(b instanceof Clover) {
				return a;
			}


			else if((af)&&(bf)){
				if(aE>=bE) {
					a.energy += b.energy/2;
					return a;
				}
				else {
					b.energy += a.energy/2;
					return b;
				}
			}
			else if(af){
				bE = 0;
				aE = Critter.getRandomInt(a.getEnergy()+1);
				if(col.containsKey(b.p)) {
					b.p = a.p;
				}

				if(aE>=bE) {
					dead.add(b);
					a.energy += b.energy/2;
					return a;
				}
				else {
					dead.add(a);
					b.energy += a.energy/2;
					return b;
				}
			}
			else if(bf) {
				aE = 0;
				bE = Critter.getRandomInt(b.getEnergy()+1);
				if(col.containsKey(a.p)) {
					a.p = b.p;
				}

				if(aE>=bE) {
					dead.add(b);
					a.energy += b.energy/2;
					return a;
				}
				else {
					dead.add(a);
					b.energy += a.energy/2;
					return b;
				}
			}
			else {
				if(col.containsKey(a.p)) {
					a.p = tempa;
					if(col.containsKey(b.p)) {
						b.p = tempb;
						dead.add(b);
						a.energy += b.energy/2;
					}
					return a;
				}
				else if(col.containsKey(b.p)) {
					b.p = tempb;
					return b;
				}
				else {
					population.add(a);
					population.add(b);
					return null;
				}


			}
		}
	}



	/**
	 * Prints out how many Critters of each type there are on the
	 * board.
	 *
	 * @param critters List of Critters.
	 */


	public abstract void doTimeStep();

	public abstract boolean fight(String oponent);

	/* a one-character long string that visually depicts your critter
	 * in the ASCII interface */
	public String toString() {
		return "";
	}

	protected int getEnergy() {
		return energy;
	}


	/* 3 2 1
	 * 4   0
	 * 5 6 7
	 */
	protected final void walk(int direction) {
		// TODO: Complete this method
		this.energy = this.energy- Params.WALK_ENERGY_COST;
		if(direction==0) {
			this.p.x = ((this.p.x+1)%Params.WORLD_WIDTH);
			this.p = new Position(this.p.x,this.p.y);

		}
		else if(direction==1) {
			this.p.x=((this.p.x+1)%Params.WORLD_WIDTH);
			this.p.y =((this.p.y+(Params.WORLD_HEIGHT-1))%Params.WORLD_HEIGHT);
			this.p = new Position(this.p.x,this.p.y);
		}
		else if(direction==2) {
			this.p.y = ((this.p.y+(Params.WORLD_HEIGHT-1))%Params.WORLD_HEIGHT);
			this.p = new Position(this.p.x,this.p.y);
		}
		else if(direction==3) {
			this.p.x=((this.p.x+(Params.WORLD_WIDTH-1))%Params.WORLD_WIDTH);
			this.p.y =((this.p.y+(Params.WORLD_HEIGHT-1))%Params.WORLD_HEIGHT);
			this.p = new Position(this.p.x,this.p.y);
		}
		else if(direction==4) {
			this.p.x = ((this.p.x+(Params.WORLD_WIDTH-1))%Params.WORLD_WIDTH);
			this.p = new Position(this.p.x,this.p.y);
		}
		else if(direction==5) {
			this.p.x=((this.p.x+(Params.WORLD_WIDTH-1))%Params.WORLD_WIDTH);
			this.p.y =((this.p.y+1)%Params.WORLD_HEIGHT);
			this.p = new Position(this.p.x,this.p.y);
		}
		else if(direction==6) {
			this.p.y =((this.p.y+1)%Params.WORLD_HEIGHT);
			this.p = new Position(this.p.x,this.p.y);
		}
		else if(direction==7) {
			this.p.x=((this.p.x+1)%Params.WORLD_WIDTH);
			this.p.y =((this.p.y+1)%Params.WORLD_HEIGHT);
			this.p = new Position(this.p.x,this.p.y);
		}
		if((this.energy<=0)&&(!(dead.contains(this)))){
			dead.add(this);
		}

	}

	protected final void run(int direction) {
		// TODO: Complete this method
		this.energy = this.energy-Params.RUN_ENERGY_COST;
		if(direction==0) {
			int x = (this.p.x+2)%Params.WORLD_WIDTH;
			this.p = new Position(x,this.p.y);
		}
		else if(direction==1) {
			this.p.x=(this.p.x+2)%Params.WORLD_WIDTH;
			this.p.y =(this.p.y+(Params.WORLD_HEIGHT-2))%Params.WORLD_HEIGHT;
			this.p = new Position(this.p.x,this.p.y);
		}
		else if(direction==2) {
			this.p.y = (this.p.y+(Params.WORLD_HEIGHT-2))%Params.WORLD_HEIGHT;
			this.p = new Position(this.p.x,this.p.y);
		}
		else if(direction==3) {
			this.p.x=(this.p.x+(Params.WORLD_WIDTH-2))%Params.WORLD_WIDTH;
			this.p.y =(this.p.y+(Params.WORLD_HEIGHT-2))%Params.WORLD_HEIGHT;
			this.p = new Position(this.p.x,this.p.y);
		}
		else if(direction==4) {
			this.p.x = (this.p.x+(Params.WORLD_WIDTH-2))%Params.WORLD_WIDTH;
			this.p = new Position(this.p.x,this.p.y);
		}
		else if(direction==5) {
			this.p.x=(this.p.x+(Params.WORLD_WIDTH-2))%Params.WORLD_WIDTH;
			this.p.y =(this.p.y+2)%Params.WORLD_HEIGHT;
			this.p = new Position(this.p.x,this.p.y);
		}
		else if(direction==6) {
			this.p.y =(this.p.y+2)%Params.WORLD_HEIGHT;
			this.p = new Position(this.p.x,this.p.y);
		}
		else if(direction==7) {
			this.p.x=(this.p.x+2)%Params.WORLD_WIDTH;
			this.p.y =(this.p.y+2)%Params.WORLD_HEIGHT;
			this.p = new Position(this.p.x,this.p.y);
		}
		if((this.energy<=0)&&(!(dead.contains(this)))){
			dead.add(this);
		}
	}

	protected final void reproduce(Critter offspring, int direction) {
		// TODO: Complete this method
		if(this.energy<Params.MIN_REPRODUCE_ENERGY) {
			return;
		}
		else {
			this.energy /= 2;
			offspring.energy = this.energy;
			offspring.walk(direction);
			offspring.energy = Params.WALK_ENERGY_COST;
			babies.add(offspring);
		}
	}

	//simulate the encounter for Critters

	/**
	 * The TestCritter class allows some critters to "cheat". If you
	 * want to create tests of your Critter model, you can create
	 * subclasses of this class and then use the setter functions
	 * contained here.
	 * <p>
	 * NOTE: you must make sure that the setter functions work with
	 * your implementation of Critter. That means, if you're recording
	 * the positions of your critters using some sort of external grid
	 * or some other data structure in addition to the x_coord and
	 * y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {

		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}

		protected void setX_coord(int new_x_coord) {

			super.p.x = new_x_coord;
			super.p = new Position(super.p.x,super.p.y);
		}

		protected void setY_coord(int new_y_coord) {

			super.p.y = new_y_coord;
			super.p = new Position(super.p.x,super.p.y);
		}

		protected int getX_coord() {
			return super.p.x;
		}

		protected int getY_coord() {
			return super.p.y;
		}

		/**
		 * This method getPopulation has to be modified by you if you
		 * are not using the population ArrayList that has been
		 * provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}

		/**
		 * This method getBabies has to be modified by you if you are
		 * not using the babies ArrayList that has been provided in
		 * the starter code.  In any case, it has to be implemented
		 * for grading tests to work.  Babies should be added to the
		 * general population at either the beginning OR the end of
		 * every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}
}
