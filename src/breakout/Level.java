package breakout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
public class Level {
	public Group root = new Group();
	public int[][] matrix;
	public Scanner scanner;
	public double x = 0;
	public double y = 20;
	public ArrayList<Rectangle> blocks = new ArrayList<Rectangle>();
	public ArrayList<Rectangle> type3 = new ArrayList<Rectangle>();
	
	public Level(Group r, int[][] m){
		root = r;
		matrix = m;
	}
	
	public Level(Group r, Scanner s){
		root = r;
		scanner = s;
	}
	
	public void makeLevel(int level){
		for (int i=0; i<matrix.length; i++){
			x = 0;
			for (int j=0; j<matrix[i].length; j++) {
				blocks.add(new Block(matrix[i][j], x + 1000/9*j, y).createBlock());

			}
			y = y + 50;
		}
		if (level == 1){
			Rectangle r = new Block(3, 0, 170).createBlock();
			blocks.add(r);
		}
		for (int i=0; i<blocks.size(); i++){
			root.getChildren().add(blocks.get(i));
		}
	}
	
	public void readLevel(){
		int j = 0;
		while (scanner.hasNext()){
			int type = scanner.nextInt();
			if (type == 8){
				y = y + 100;
				j = 0;
			}
			if (type == 9){
				return;
			} else {
				blocks.add(new Block(type, x + 100*j, y).createBlock());
				j++;
			}
		}
	}
}