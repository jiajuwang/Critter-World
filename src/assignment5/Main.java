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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class Main extends Application{
	private static String myPackage; // package of Critter file.

	/* Critter cannot be in default pkg. */
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	public static void main(String[] args) {
		launch(args);

	}

	public void start(Stage Controller) {
		//Controller setting
		GridPane grid = new GridPane();
		Scene scene = new Scene(grid, 600, 600);
		Controller.setX(0);
		Controller.setY(0);
		Controller.setTitle("Controller");
		Controller.setScene(scene);
		grid.setLayoutX(0);
		grid.setLayoutY(0);
		grid.setHgap(100);
		grid.setVgap(5);

		//stats displaying setting
		Set<String> CritterToDisplay = new HashSet<String>();
		
		GridPane data = new GridPane();
		ScrollPane sp = new ScrollPane();
		sp.setContent(data);
		sp.setPannable(true);
		Stage stage2 = new Stage();
		Scene secondScene = new Scene(sp, 200, 200); // creates a second scene object with the Stackpane
		stage2.setScene(secondScene); // puts the scene onto the second stage
		stage2.setX(700);
		stage2.setY(0);
		stage2.setTitle("Data");




		//welcome and set seed
		Text welcome = new Text("Welcome to Critters");
		grid.add(welcome, 0,0);
		Text seedTitle = new Text("Seed (integer):");
		grid.add(seedTitle, 0, 1);
		TextField seedText = new TextField();
		grid.add(seedText, 1, 1);
		Button seedButton = new Button();
		seedButton.setMaxSize(600, 600);
		seedButton.setText("Set Seed");
		grid.add(seedButton, 1, 2);
		try {
			seedButton.setOnAction(new EventHandler<ActionEvent>() { // what to do when butt is pressed
				@Override
				public void handle(ActionEvent event) {
					Integer seed = Integer.parseInt(seedText.getText());
					Critter.setSeed(seed);
				}
			});
		}
		catch(Exception e) {
			System.out.println("Please enter a valid number");
		}

		//create
		Text critterType = new Text("Critter type:");
		grid.add(critterType, 0, 3);
		ChoiceBox list = new ChoiceBox();
		ArrayList<String> cls = findAllClasses();
		for(String cl: cls) {
			list.getItems().add(cl);
		}
		list.setMaxSize(600, 600);
		grid.add(list, 1, 3);
		Text critterNumber = new Text("No of critters:");
		grid.add(critterNumber, 0, 4);
		TextField critterNo = new TextField();
		grid.add(critterNo, 1, 4);
		Button critterButton = new Button();
		critterButton.setMaxSize(600, 600);
		critterButton.setText("Add critter");
		grid.add(critterButton, 1, 5);
		critterButton.setOnAction(new EventHandler<ActionEvent>() { // what to do when butt is pressed
			@Override
			public void handle(ActionEvent event) {
				try {
					String name = (String)list.getValue();
					Integer count = Integer.parseInt(critterNo.getText());
					for(int i = 0;i<count;i++) {

						Critter.createCritter(name);

					}

				}
				catch(Exception e) {

				}
			}
		});





		//Display
		Button display = new Button("Display World");
		display.setMaxSize(600, 600);
		grid.add(display, 0, 8);

		Stage critWorld = new Stage();
		critWorld.setTitle("Critter World");

		display.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Critter.displayWorld(critWorld);
				display(data,CritterToDisplay);
				stage2.show();
			}
		});
		
		
		
		//runStats
		Text critterType2 = new Text("Critter type:(press Ctrl to choose multiple)");
		grid.add(critterType2, 0, 9);
		ArrayList<String> s = findAllClasses();
		ListView<String> list2 = new ListView();
		list2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		for(String ss: s) {
			list2.getItems().add(ss);
		}
		list2.setMaxSize(600, 600);
		grid.add(list2, 1, 9);

		Button statsButton = new Button();
		statsButton.setText("Run stats");
		statsButton.setMaxSize(600, 600);
		grid.add(statsButton, 1, 10);

		statsButton.setOnAction(new EventHandler<ActionEvent>() { // what to do when butt is pressed
			@Override
			public void handle(ActionEvent event) {
				try {
					CritterToDisplay.clear();
					ObservableList<String> selectedIndices = list2.getSelectionModel().getSelectedItems();
					for(String s: selectedIndices) {

						List<Critter> result = Critter.getInstances(s);
						Critter c =  (Critter)Class.forName(s).newInstance();
						String temp = (String) c.getClass().getMethod("runStats", List.class).invoke(c, result);
						CritterToDisplay.add(s);

					}
					display(data,CritterToDisplay);
					stage2.show();
				}
				catch(Exception e) {

				}
			}
		});

		//step
		Text stepNumber = new Text("No of steps:");
		grid.add(stepNumber, 0, 6);
		TextField stepNo = new TextField();
		grid.add(stepNo, 1, 6);
		Button stepButton = new Button();
		stepButton.setMaxSize(600, 600);
		stepButton.setText("Time Step");
		grid.add(stepButton, 1, 7);
		stepButton.setOnAction(new EventHandler<ActionEvent>() { // what to do when butt is pressed
			@Override
			public void handle(ActionEvent event) {
				try {
					Integer counts = Integer.parseInt(stepNo.getText());
					for(int i = 0; i<counts;i++) {
						Critter.worldTimeStep();
					}
					display(data,CritterToDisplay);
					
					stage2.show();
				}
				catch(Exception e){

				}
			}
		});


		//quit
		Button quitButton = new Button();
		quitButton.setText("Quit");
		quitButton.setMaxSize(600, 600);
		grid.add(quitButton, 1, 15);
		
		quitButton.setOnAction(new EventHandler<ActionEvent>() { // what to do when butt is pressed
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		
		//clearWrold
				Button clearButton = new Button("clear");
				grid.add(clearButton, 1, 14);
				clearButton.setOnAction(new EventHandler<ActionEvent>() { // what to do when butt is pressed
					@Override
					public void handle(ActionEvent event) {
						Critter.clearWorld();
						Critter.displayWorld(critWorld);
					}
				});
		//animation
		Text t = new Text("Animation Speed:");
		ChoiceBox c = new ChoiceBox();
		c.getItems().add("1");
		c.getItems().add("10");
		c.getItems().add("20");
		c.getItems().add("50");
		c.getItems().add("100");
		Button runA = new Button("run");
		Button stopA = new Button("stop");
		grid.add(t, 0, 12);
		grid.add(c, 0, 13);
		grid.add(runA, 0, 14);
		grid.add(stopA, 0, 15);
		/*runA.setOnAction(new EventHandler<ActionEvent>() { // what to do when butt is pressed
			@Override
			public void handle(ActionEvent event) {
				Integer count = Integer.parseInt((String)c.getValue());
				
				while(!stopA.isPressed()) {
					for(int i =0;i<count;i++) {
						
						Critter.worldTimeStep();
					}
					display(data,CritterToDisplay);
					stage2.show();
					Critter.displayWorld(critWorld);
				}
			}
		});*/
		
		
		class Movement extends AnimationTimer{
			public long Frame;
			//private long interval;
			private long last = 0;
			@Override
			/*public Movement(long f,long i) {
				Frame = f;
				interval = i;
			}*/
			public void handle(long now) {
				if((now-last)>Frame) {
					Critter.worldTimeStep();
					Critter.displayWorld(critWorld);
					display(data,CritterToDisplay);
					stage2.show();
					last = now;
				}
			}
			
		}
		Movement clock = new Movement();
		runA.setOnAction(new EventHandler<ActionEvent>() { // what to do when butt is pressed
			@Override
			public void handle(ActionEvent event) {
				Integer count = Integer.parseInt((String)c.getValue());
				long f = (long) (1000000000/count);
				//long f = (long) 1.0;
				clock.Frame = f;
				//clock.interval = i;
				clock.start();
				quitButton.setDisable(true);
				stepButton.setDisable(true);
				statsButton.setDisable(true);
				display.setDisable(true);
				critterButton.setDisable(true);
				seedButton.setDisable(true);
				clearButton.setDisable(true);
			}
		});
		
		stopA.setOnAction(new EventHandler<ActionEvent>() { // what to do when butt is pressed
			@Override
			public void handle(ActionEvent event) {
				clock.stop();
				quitButton.setDisable(false);
				stepButton.setDisable(false);
				statsButton.setDisable(false);
				display.setDisable(false);
				critterButton.setDisable(false);
				seedButton.setDisable(false);
				clearButton.setDisable(false);
			}
		});
		
		
		
		Controller.show();
	}


	public void display(GridPane b, Set<String>s) {
		try {
			for(String ss: s) {
				List<Critter> result = Critter.getInstances(ss);
				Critter c =  (Critter)Class.forName(ss).newInstance();
				String temp = (String) c.getClass().getMethod("runStats", List.class).invoke(c, result);
				b.addColumn(0, new Text(ss+temp));
			}
		}
		catch(Exception e) {

		}

	}

	public ArrayList<String> findAllClasses(){
		String f = new File("").getAbsolutePath();
		f  += "\\src\\assignment5\\";
		File ff = new File(f);
		String classes[] = ff.list();
		ArrayList<String> result = new ArrayList<String>();
		String superC = myPackage+".Critter";
		for(int i = 0; i<classes.length;i++) {
			String temp = myPackage+"."+classes[i].split(".java")[0];
			try {
				if(Class.forName(superC).isAssignableFrom(Class.forName(temp))) {
					result.add(temp);
				}
			} catch (ClassNotFoundException e) {

				continue;
			}

		}

		return result;
	}

}
