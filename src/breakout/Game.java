// TO-Do
// dont let paddle go off screen
// make main screen
// connect levels
package breakout;
import java.util.ArrayList;
import java.util.Random;
import java.text.DecimalFormat;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
public class Game extends Application {
	private ImageView myBall; 
	private Rectangle myPaddle;
	private Stage theStage; //so its easy to set the scene no matter where you are in the code
	public static final int SIZE = 500;
	public static final Paint BACKGROUND = Color.WHITE;


	public static final String TITLE = "Breakout";
	public static final String BALL_IMAGE = "ball.gif";
	public static final int KEY_INPUT_SPEED = 10;
	public static final double GROWTH_RATE = 1.1;

	private Timeline animation = new Timeline();
	private int deltaX = 1; //direction of the ball
	private int deltaY = 1; 
	private int deltaRX = 1; //direction of moving pink blocks

	private double BOUNCER_SPEED = 60;
	private int FRAMES_PER_SECOND = 240;
	private int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private double SECOND_DELAY = .75 / FRAMES_PER_SECOND;

	private int[][] L1 = new int[3][5];
	private int[][] L2 = new int[3][5];
	private int[][] L3 = new int[3][3];

	private ArrayList<Rectangle> currentBlocks = new ArrayList<Rectangle>(); //list of the blocks in the root
	private ArrayList<Rectangle> currentPU = new ArrayList<Rectangle>(); //list of any power ups in the root
	private Scene currentScene; //vessel for the various scenes created during the game

	private int level = 1; //each level has different layout, power ups, blocks, etc.
	private Boolean aLevel = false; //some methods should only be done if in a method
	private int lives = 3; //start off the game with 3 lives
	private double score = 0;
	private double time = 0;
	private double lastTime = 0; //to make sure the random movement of the orange blocks only happen every X seconds

	private Label info_level; //i only want to make the label once and just delete and reload an edited version
	private Label info_lives; //otherwise i would overlay a large number of the same labels
	private Label info_score;
	private Label info_time;

	@Override
	public void start(Stage stage) {
		fillMatrices();
		theStage = stage;
		Scene scene_homePage = setupHP(SIZE, SIZE, BACKGROUND); //set up scene for home page
		currentScene = scene_homePage;
		theStage.setScene(currentScene);
		theStage.show();
	}

	private void fillMatrices(){
		L1[0][0] = 1; L1[0][1] = 2; L1[0][2] = 1; L1[0][3] = 2; L1[0][4] = 1;
		L1[1][0] = 2; L1[1][1] = 1; L1[1][2] = 2; L1[1][3] = 1; L1[1][4] = 2;
		L1[2][0] = 4; L1[2][1] = 4; L1[2][2] = 4; L1[2][3] = 4; L1[2][4] = 4;

		L2[0][0] = 1; L2[0][1] = 1; L2[0][2] = 1; L2[0][3] = 1; L2[0][4] = 1;
		L2[1][0] = 1; L2[1][1] = 1; L2[1][2] = 1; L2[1][3] = 1; L2[1][4] = 1;
		L2[2][0] = 1; L2[2][1] = 1; L2[2][2] = 1; L2[2][3] = 1; L2[2][4] = 1;

		L3[0][0] = 1; L3[0][1] = 1; L3[0][2] = 1;
		L3[1][0] = 1; L3[1][1] = 2; L3[1][2] = 1;
		L3[2][0] = 1; L3[2][1] = 1; L3[2][2] = 1;
	}

	private Scene setupHP (int width, int height, Paint background) { //scene for home page!
		time = 0;
		score = 0;
		VBox root = new VBox(); //create root for home page scene
		root.setAlignment(Pos.CENTER); //center the things in the root
		Scene scene_HP = new Scene (root, width, height, background); //establish the root that will be returned
		currentScene = scene_HP;
		
		Label lb = new Label("BREAKOUT!");
		lb.setFont(Font.font("Comic Sans", FontWeight.BOLD, 70));
		root.getChildren().add(lb); //make label and add it
		Button btn_Play = new Button("Start");
		btn_Play.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
		btn_Play.setOnAction(new EventHandler<ActionEvent>() { //if the button is clicked
			public void handle(ActionEvent arg){
				currentScene = setupLevel(SIZE, SIZE, BACKGROUND);
				theStage.setScene(currentScene);
			}
		});
		Button btn_instr = new Button("Help");
		btn_instr.setOnAction(new EventHandler<ActionEvent>() { //if the button is clicked
			public void handle(ActionEvent arg){
				Splash s = new Splash();

				currentScene = s.createInstr(width, height, background);

				s.btn_exit.setOnAction(new EventHandler<ActionEvent>() { //if the button is clicked
					public void handle(ActionEvent arg){
						currentScene = setupHP(SIZE, SIZE, BACKGROUND);
						theStage.setScene(currentScene);
					}
				});

				theStage.setScene(currentScene);
			}
		});
		root.getChildren().addAll(btn_Play, btn_instr);
		return scene_HP;
	}

	private Scene setupLevel (int width, int height, Paint background) { //returns the scene given what level we're on

		aLevel = true;
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> step(SECOND_DELAY, theStage));
		if (animation != null){
			animation.stop();
		}
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();

		Group root = new Group();
		int[][] mat = new int[2][2]; //make a matrix
		if (level == 1){ //check what level to make
			mat = L1;
		} else if (level == 2){
			mat = L2;
		} else if (level == 3){
			mat = L3;
		}
		Level l = new Level(root, mat); //create object with matrix
		l.makeLevel(level); //make the level
		currentBlocks = l.blocks;
		//root should currently have all the Blocks as rectangles and only that
		info_level = new Label("Level: " + level);
		info_lives = new Label("Lives remaining: " + lives);
		info_lives.setLayoutX(60);
		root.getChildren().addAll(info_level, info_lives);		
		Scene scene = new Scene(root, width, height, background);
		ballAndPaddle(root, width, height, scene);

		return scene;
	}
	private void ballAndPaddle(Group root, int width, int height, Scene s){ //initializing the ball and paddle
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
		myBall = new ImageView(image);
		myBall.setTranslateX(250);
		myBall.setTranslateY(250);
		myPaddle = new Rectangle(215,450,70,10);
		myPaddle.setFill(Color.BLUE);
		root.getChildren().addAll(myBall, myPaddle);
		s.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
	}


	private void step (double elapsedTime, Stage stage) { //different methods that are performed at each step
		// update attributes
		newLevel(SIZE, SIZE, BACKGROUND);

		if (aLevel){ //only do this if we're in a level
			ballBounce(elapsedTime);
			removeBlocksHit(elapsedTime);
			movingBlocks(elapsedTime);
			updateInfo();
			updateTime(elapsedTime);

		}
	}

	private void updateTime(double elapsedTime){ // update the time elapsed
		time = time + elapsedTime;
	}

	private void updateInfo(){ //update the labels at the top
		Group root = (Group) currentScene.getRoot();
		root.getChildren().remove(info_level);
		root.getChildren().remove(info_lives);
		root.getChildren().remove(info_score);
		root.getChildren().remove(info_time);
		info_level = new Label("Level: " + level);
		info_lives = new Label("Lives remaining: " + lives);
		info_score = new Label("Score: " + score);
		info_time = new Label("Seconds elapsed: " + new DecimalFormat("#.#").format(time));
		info_lives.setLayoutX(60);
		info_score.setLayoutX(180);
		info_time.setLayoutX(270);
		root.getChildren().addAll(info_level, info_lives, info_score, info_time);	
		theStage.setScene(currentScene);
	}

	private void removeBlocksHit(double elapsedTime){ //check if any blocks have been hit and then remove them in one time step
		Group root = (Group) currentScene.getRoot();

		for (int i=0; i<currentBlocks.size(); i++){
			if (blockWasHit(currentBlocks.get(i))){
				if (currentBlocks.get(i).getFill() == Color.BLUE){
					BOUNCER_SPEED = BOUNCER_SPEED * 1.25;
				}
				if (currentBlocks.get(i).getFill() == Color.GREEN){
					Rectangle newLife = new Rectangle();
					newLife.setFill(Color.BLACK);
					newLife.setWidth(20);
					newLife.setHeight(20);
					newLife.setX(currentBlocks.get(i).getX()+15);
					newLife.setY(currentBlocks.get(i).getY());
					currentPU.add(newLife);
					root.getChildren().add(newLife);
				}

				root.getChildren().remove(currentBlocks.get(i));
				currentBlocks.remove(i);
			}
		}

		for (int i=0; i<currentPU.size(); i++){
			if (catchPU(currentPU.get(i))){
				if (level == 1){
					lives++;
				} else if (level == 2){
					myPaddle.setWidth(myPaddle.getWidth() + 20);
				} else if (level == 3){
					BOUNCER_SPEED = 0.75 * BOUNCER_SPEED;
				}
				root.getChildren().remove(currentPU.get(i));
				currentPU.remove(i);
			}
		}

	}

	private void newLevel(int width, int height, Paint background){ //make the new level with the scene returned from setupLevel method
		if (currentBlocks.size() == 0){
			level++;
			if (level == 4){
				VBox root = new VBox();
				root.setAlignment(Pos.CENTER);
				Label lb = new Label("YOU WIN!");
				score = score - (Math.floor(time) / 2);
				Label finalScore = new Label("Score: " + score);
				lb.setFont(Font.font(20));
				Label finalTime = new Label("Score: " + time);
				lb.setFont(Font.font(20));
				root.getChildren().addAll(lb, finalScore, finalTime);
				currentScene.setRoot(root);
				theStage.setScene(currentScene);
				aLevel = false;
				animation.stop();
			} else {
				currentScene = setupLevel(width, height, background);
				theStage.setScene(currentScene);
			}
		}
	}
	private Boolean blockWasHit(Rectangle rec){ //check if each of the balls are hit
		double midBallX = (myBall.getBoundsInParent().getMaxX() + myBall.getBoundsInParent().getMinX())/2;
		double midBallY = (myBall.getBoundsInParent().getMaxY() + myBall.getBoundsInParent().getMinY())/2;
		double midRecX = (rec.getBoundsInParent().getMaxX() + rec.getBoundsInParent().getMinX())/2;
		double midRecY = (rec.getBoundsInParent().getMaxY() + rec.getBoundsInParent().getMinY())/2;
		Boolean onSides = midBallX < midRecX || midBallX > midRecX;
		Boolean withinY = rec.getBoundsInParent().getMinY() < midBallY && midBallY < rec.getBoundsInParent().getMaxY();
		Boolean onTopBot = midBallY < midRecY || midBallY > midRecY;
		Boolean withinX = rec.getBoundsInParent().getMinX() < midBallX && midBallX < rec.getBoundsInParent().getMaxX();
		if (myBall.getBoundsInParent().intersects(rec.getBoundsInParent())){
			if (rec.getFill() == Color.PURPLE || rec.getFill() == Color.GREEN){
				score = score + 10;
			} else if (rec.getFill() == Color.BLUE){
				score = score + 20;
			} else if (rec.getFill() == Color.PINK){
				score = score + 30;
			}
			if (onSides && withinY){ //ball on right or left, change X
				deltaX = deltaX * -1;
			} 
			if (onTopBot && withinX){
				deltaY = deltaY * -1;
			}

			return true;
		}
		return false;
	}


	private void ballBounce(double elapsedTime){ //bounce the ball off the paddle
		if (myPaddle != null && myBall != null){

			if (myBall.getBoundsInParent().intersects(myPaddle.getBoundsInParent())){
				double midBallX = (myBall.getBoundsInParent().getMaxX() + myBall.getBoundsInParent().getMinX())/2;
				double midBallY = (myBall.getBoundsInParent().getMaxY() + myBall.getBoundsInParent().getMinY())/2;
				double midRecX = (myPaddle.getBoundsInParent().getMaxX() + myPaddle.getBoundsInParent().getMinX())/2;
				double midRecY = (myPaddle.getBoundsInParent().getMaxY() + myPaddle.getBoundsInParent().getMinY())/2;
				Boolean onSides = midBallX < midRecX || midBallX > midRecX;
				Boolean withinY = myPaddle.getBoundsInParent().getMinY() < midBallY && midBallY < myPaddle.getBoundsInParent().getMaxY();
				Boolean onTopBot = midBallY < midRecY || midBallY > midRecY;
				Boolean withinX = myPaddle.getBoundsInParent().getMinX() < midBallX && midBallX < myPaddle.getBoundsInParent().getMaxX();

				if (onSides && withinY){ //ball on right or left, change X
					deltaX = deltaX * -1;
				} 
				if (onTopBot && withinX){
					deltaY = deltaY * -1;
				}
			}
			if (myBall.getBoundsInParent().getMinX() < 0 || 
					myBall.getBoundsInParent().getMaxX() > SIZE){
				deltaX = deltaX * -1;
			} 
			if (myBall.getBoundsInParent().getMinY() < 0){
				deltaY = deltaY * -1;
			} 

			myBall.setTranslateX(myBall.getTranslateX() + deltaX * BOUNCER_SPEED * elapsedTime);
			myBall.setTranslateY(myBall.getTranslateY() + deltaY * BOUNCER_SPEED * elapsedTime);

			if (myBall.getBoundsInParent().getMinY() > SIZE){
				lives--;

				if (lives == 0){
					aLevel = false;
					VBox rt = new VBox();
					rt.setAlignment(Pos.CENTER);
					Scene scene_LOSE = new Scene(rt, SIZE, SIZE);
					Label lb = new Label("GAME OVER :(");
					lb.setFont(Font.font("Comic Sans", FontWeight.BOLD, 50));
					score = score - (Math.floor(time) / 2);
					Label finalScore = new Label("Score: " + Math.floor(score));
					lb.setFont(Font.font(20));
					Label finalTime = new Label("Score: " + new DecimalFormat("#.#").format(time));
					lb.setFont(Font.font(20));

					Button btn_SO = new Button("Start Over");
					btn_SO.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
					btn_SO.setOnAction(new EventHandler<ActionEvent>() { //if the button is clicked
						public void handle(ActionEvent arg){
							animation.playFromStart();
							currentScene = setupHP(SIZE, SIZE, BACKGROUND);
							theStage.setScene(currentScene);

						}
					});
					rt.getChildren().addAll(lb, finalScore, finalTime, btn_SO);
					currentScene = scene_LOSE;
					theStage.setScene(currentScene);
					animation.stop();
				} else {
					time = 0;
					SECOND_DELAY = 0.75 * SECOND_DELAY;
					currentScene = setupLevel(SIZE, SIZE, BACKGROUND);
					theStage.setScene(currentScene);
				}
			}
		}
	}

	private void movingBlocks(double elapsedTime){ //move the pink and orange blocks
		for (int i=0; i<currentBlocks.size(); i++){
			Rectangle r = currentBlocks.get(i);
			if (r.getFill() == Color.PINK){
				if (r.getBoundsInParent().getMaxX() > SIZE || r.getBoundsInParent().getMinX() < 0){
					deltaRX = deltaRX * -1;
				}
				r.setTranslateX(r.getTranslateX() + deltaRX * BOUNCER_SPEED * elapsedTime);
			}
			if (r.getFill() == Color.ORANGE){
				if (time > lastTime + 2){
					Random rand = new Random();
					int xloc = rand.nextInt(450);
					int yloc = rand.nextInt(200) + 20;
					r.setX(xloc);
					r.setY(yloc);
					lastTime = time;
				}

			}

		}
		for (int i=0; i<currentPU.size(); i++){
			Rectangle r = currentPU.get(i);
			if (r.getFill() == Color.BLACK){
				r.setTranslateY(r.getTranslateY() +  BOUNCER_SPEED * elapsedTime);
			}
			if (r.getY() > SIZE){
				currentPU.remove(i);
			}
		}


	}

	private Boolean catchPU(Rectangle r){ //check if the power ups were caught!
		if (myPaddle.getBoundsInParent().intersects(r.getBoundsInParent())){
			return true;
		}

		return false;

	}

	private void handleKeyInput (KeyCode code) { //any key inputs?
		if (code == KeyCode.RIGHT & myPaddle.getBoundsInParent().getMaxX() < SIZE ) {
			myPaddle.setX(myPaddle.getX() + KEY_INPUT_SPEED);
		}
		if (code == KeyCode.LEFT & myPaddle.getBoundsInParent().getMinX() > 0) {
			myPaddle.setX(myPaddle.getX() - KEY_INPUT_SPEED);
		}
		if (code == KeyCode.UP & myPaddle.getBoundsInParent().getMinY() > 0) {
			myPaddle.setY(myPaddle.getY() - KEY_INPUT_SPEED);
		}
		if (code == KeyCode.DOWN & myPaddle.getBoundsInParent().getMaxY() < SIZE) {
			myPaddle.setY(myPaddle.getY() + KEY_INPUT_SPEED);
		}
		if (code == KeyCode.DIGIT1){
			level = 1;
			currentScene = setupLevel(SIZE, SIZE, BACKGROUND);

		}
		if (code == KeyCode.DIGIT2){
			level = 2;
			currentScene = setupLevel(SIZE, SIZE, BACKGROUND);

		}
		if (code == KeyCode.DIGIT3){
			level = 3;
			currentScene = setupLevel(SIZE, SIZE, BACKGROUND);

		}
		if (code == KeyCode.X){
			level = 1;
			lives = 3;
			currentScene = setupHP(SIZE, SIZE, BACKGROUND);
			aLevel = false;
			theStage.setScene(currentScene);
		}
		if (code == KeyCode.L){
			lives++;
		}
		if (code == KeyCode.R){
			currentScene = setupLevel(SIZE, SIZE, BACKGROUND);
		}
		if (code == KeyCode.S){
			BOUNCER_SPEED = 0.75 * BOUNCER_SPEED;
		}
		if (code == KeyCode.F){
			BOUNCER_SPEED = 1.25 * BOUNCER_SPEED;
		}
		if (code == KeyCode.W){
			VBox root = new VBox();
			root.setAlignment(Pos.CENTER);
			Label lb = new Label("YOU WIN!");
			score = score - (Math.floor(time) / 2);
			Label finalScore = new Label("Score: " + score);
			lb.setFont(Font.font(20));
			Label finalTime = new Label("Time: " + new DecimalFormat("#.#").format(time));
			lb.setFont(Font.font(20));
			root.getChildren().addAll(lb, finalScore, finalTime);
			currentScene.setRoot(root);
			theStage.setScene(currentScene);
			aLevel = false;
			animation.stop();
		}
		if (code == KeyCode.P){
			animation.pause();
		}
		if (code == KeyCode.G){
			animation.play();
		}
	}
	public static void main(String[] args){
		launch(args);
	}
}