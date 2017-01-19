// TO-Do
// dont let paddle go off screen
// make main screen
// connect levels

package breakout;

import java.util.ArrayList;
import java.util.Scanner;

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
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {
	private Scene myScene;
	private ImageView myBall;
	private Rectangle myPaddle;

	public static final int SIZE = 500;
	public static final Paint BACKGROUND = Color.WHITE;
	public static final int FRAMES_PER_SECOND = 240;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final String TITLE = "Breakout";
	public static final String BALL_IMAGE = "ball.gif";
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final int KEY_INPUT_SPEED = 10;
	public static final double GROWTH_RATE = 1.1;
	public static final int BOUNCER_SPEED = 60;

	private Timeline animation = new Timeline();

	static int deltaX = 1;
	static int deltaY = 1;
	static int level = 1;
	private int[][] L1 = new int[2][2];
	private int[][] L2 = new int[2][2];
	private ArrayList<Rectangle> remBlocks = new ArrayList<Rectangle>();
	
	
	@Override
	public void start(Stage stage) {
		
		fillMatrices();

		Scene scene_homePage = setupHP(SIZE, SIZE, BACKGROUND, stage); //set up scene for home page
		stage.setScene(scene_homePage);
		stage.show();


	}
	
	private void fillMatrices(){
		L1[0][0] = 1;
		L1[0][1] = 2;
		L1[1][0] = 2;
		L1[1][1] = 1;
		L2[0][0] = 3;
		L2[0][1] = 4;
		L2[1][0] = 4;
		L2[1][1] = 3;
		
	}

	private Scene setupHP (int width, int height, Paint background, Stage stage) {


		VBox root = new VBox(); //create root for home page scene
		root.setAlignment(Pos.CENTER); //center the things in the root
		Scene scene_HP = new Scene (root, width, height, background); //establish the root that will be returned

		Label lb = new Label("BREAKOUT!");
		lb.setFont(Font.font("Comic Sans", FontWeight.BOLD, 70));
		root.getChildren().add(lb); //make label and add it
		Button btn_Play = new Button("Start");
		btn_Play.setFont(Font.font("Verdana", FontPosture.ITALIC, 15));
		btn_Play.setOnAction(new EventHandler<ActionEvent>() { //if the button is clicked
			public void handle(ActionEvent arg){
				Scene scene = setupLevel(SIZE, SIZE, BACKGROUND, stage); //make a scene for level 1 
				stage.setScene(scene);
			}
		});

		root.getChildren().add(btn_Play);

		return scene_HP;
	}

	private Scene setupLevel (int width, int height, Paint background, Stage stage) {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> step(SECOND_DELAY, stage));
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
		}

		Level l = new Level(root, mat); //create object
		l.makeLevel(); //make the level
		remBlocks = l.blocks;
		//root should have 
		
		Scene scene = new Scene(root, width, height, background);
		ballAndPaddle(root, width, height, scene);
		
		Boolean[] removes = blocksHit(l.blocks);
		
		for (int i=0; i<l.blocks.size(); i++){
			if (removes[i]){
				l.blocks.remove(i);
			}
		}
		
		if (l.blocks.size() == 0){
			level++;
			scene = setupLevel(width, height, background, stage);
			stage.setScene(scene);
		}

		


		return scene;
	}

	private void ballAndPaddle(Group root, int width, int height, Scene s){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
		myBall = new ImageView(image);
		myBall.setTranslateX(width / 2 - myBall.getBoundsInLocal().getWidth() / 2);
		myBall.setTranslateY(height / 2 - myBall.getBoundsInLocal().getHeight() / 2);
		myPaddle = new Rectangle(215,400,70,10);
		myPaddle.setFill(Color.BLUE);
		root.getChildren().addAll(myBall, myPaddle);
		s.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
	}

	private void step (double elapsedTime, Stage stage) {
		// update attributes
		ballBounce(elapsedTime, stage);
		

	}

	private Boolean[] blocksHit(ArrayList<Rectangle> array){
		Boolean[] ret = new Boolean[array.size()];
		
		for (int i=0; i<array.size(); i++){
			ret[i] = blockWasHit(array.get(i));
		}
		
		return ret;
	}
	
	private Boolean blockWasHit(Rectangle rec){
		Boolean ballBelow = (myBall.getBoundsInParent().getMinY() < rec.getBoundsInParent().getMaxY()) && 
				(myBall.getBoundsInParent().getMaxY() > rec.getBoundsInParent().getMaxY());
		Boolean ballAbove = (myBall.getBoundsInParent().getMaxY() > rec.getBoundsInParent().getMinY()) && 
				(myBall.getBoundsInParent().getMinY() < rec.getBoundsInParent().getMinY());
		Boolean ballLeft = (myBall.getBoundsInParent().getMaxX() > rec.getBoundsInParent().getMinX()) && 
				(myBall.getBoundsInParent().getMinX() < rec.getBoundsInParent().getMinX());
		Boolean ballRight = (myBall.getBoundsInParent().getMinX() < rec.getBoundsInParent().getMaxX()) && 
				(myBall.getBoundsInParent().getMaxX() > rec.getBoundsInParent().getMaxX());
		
		
		if (ballBelow) {
			return true;
		} else if (ballAbove) {
			return true;
		} else if (ballLeft) {
			return true;
		} else if (ballRight){
			return true;
		}
		
		return false;
	}

	private void ballBounce(double elapsedTime, Stage s){
		if (myPaddle != null && myBall != null){
			Boolean A = myPaddle.getBoundsInParent().getMinY() < myBall.getBoundsInParent().getMinY();
			Boolean B = myBall.getBoundsInParent().getMaxY() < myPaddle.getBoundsInParent().getMaxY();
			Boolean C = myBall.getBoundsInParent().getMaxX() > myPaddle.getBoundsInParent().getMinX();
			Boolean D = myBall.getBoundsInParent().getMinX() < myPaddle.getBoundsInParent().getMaxX();
			Boolean E = myBall.getTranslateX() < myPaddle.getX();
			Boolean F = myBall.getTranslateX() > myPaddle.getX();

			if ( (C && E) || (D && F) ) {
				if (A && B) {
					deltaX = deltaX * -1;
				}
			} 


			if (myBall.getBoundsInParent().getMinX() < 0 || 
					myBall.getBoundsInParent().getMaxX() > SIZE){
				deltaX = deltaX * -1;
			} 
			myBall.setTranslateX(myBall.getTranslateX() + deltaX * BOUNCER_SPEED * elapsedTime);


			Boolean Aa = myPaddle.getBoundsInParent().getMinX() < myBall.getBoundsInParent().getMinX();
			Boolean Bb = myBall.getBoundsInParent().getMaxX() < myPaddle.getBoundsInParent().getMaxX();
			Boolean Cc = myBall.getBoundsInParent().getMaxY() > myPaddle.getBoundsInParent().getMinY();
			Boolean Dd = myBall.getBoundsInParent().getMinY() < myPaddle.getBoundsInParent().getMaxY();
			Boolean Ee = myBall.getTranslateY() < myPaddle.getY();
			Boolean Ff = myBall.getTranslateY() > myPaddle.getY();
			if ( (Cc && Ee) || (Dd && Ff) ) {
				if (Aa && Bb) {
					deltaY = deltaY * -1;
				}
			}
			if (myBall.getBoundsInParent().getMinY() < 0){
				deltaY = deltaY * -1;
			} 
			myBall.setTranslateY(myBall.getTranslateY() + deltaY * BOUNCER_SPEED * elapsedTime);

			if (myBall.getBoundsInParent().getMinY() > SIZE){
				Group rt = new Group();
				Scene scene_END = new Scene(rt, SIZE, SIZE);
				Label t = new Label("Game Over");
				rt.getChildren().add(t);
				s.setScene(scene_END);
			}
		}
	}

	private void handleKeyInput (KeyCode code) {

		if (code == KeyCode.RIGHT & myPaddle.getBoundsInParent().getMaxX() < SIZE ) {
			myPaddle.setX(myPaddle.getX() + KEY_INPUT_SPEED);
		}
		else if (code == KeyCode.LEFT & myPaddle.getBoundsInParent().getMinX() > 0) {
			myPaddle.setX(myPaddle.getX() - KEY_INPUT_SPEED);
		}
		else if (code == KeyCode.UP & myPaddle.getBoundsInParent().getMinY() > 0) {
			myPaddle.setY(myPaddle.getY() - KEY_INPUT_SPEED);
		}
		else if (code == KeyCode.DOWN & myPaddle.getBoundsInParent().getMaxY() < SIZE) {
			myPaddle.setY(myPaddle.getY() + KEY_INPUT_SPEED);
		}
	}


	public static void main(String[] args){
		launch(args);
	}


}
