package breakout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Splash {
	public Button btn_exit;

	
	public Splash(){
		
	}
	
	public Scene createInstr (int width, int height, Paint background){
		Group root = new Group();
		Scene s = new Scene (root, width, height, background);
		Label t = new Label("How to Play:\n\n"
				+ " 1. Hit the blocks with your ball to break them.\n"
				+ " 2. Careful: some blocks are tricky! The pink blocks move, and the blue blocks \n speed up your ball.\n"
				+ " 3. If your ball falls off the bottom of the screen, you lose a life. You only \n have 3 lives until it's game over.\n"
				+ " 4. There are some power ups hidden throughout some of the levels! Hitting the \n green ones will release them, "
				+ " but make sure you catch them with your paddle.\n"
				+ "      a. Level 1: extra life\n"
				+ "      b. Level 2: longer paddle\n"
				+ "      c. Level 3: slower ball\n"
				+ " 5. Here are some cheat codes to make life easier:\n"
				+ "      a. Press X to exit back to the home page\n"
				+ "      b. Press 1, 2, or 3 to go to that specific level\n"
				+ "      c. Press R to reset the level you're on\n"
				+ "      d. Press L for more lives\n"
				+ "      e. Press S to slow down the ball and blocks\n"
				+ "      f. Press F to speed up to ball and blocks\n"
				+ "      g. Press W to go to the end of the game and see your results\n"
				+ " 6. Here are the point breakdowns:\n"
				+ "      a. A purple or green block = 10 points\n"
				+ "      b. A blue block = 20 points\n"
				+ "      c. A pink block = 30 points\n"
				+ " 7. The orange block on level three randomly hopes around!\n"
				+ " 8. Be careful: the ball gets faster and faster as time goes on.\n\n"
				+ " Good luck!");
		
		
		
		
		btn_exit = new Button("Done");
		btn_exit.setLayoutX(225);
		btn_exit.setLayoutY(470);
		
		root.getChildren().addAll(t, btn_exit);
		
		return s;
	}
}
