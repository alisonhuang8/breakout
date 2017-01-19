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

	public Group root;
	public int[][] matrix;
	public int x = 100;
	public int y = 0;
	public ArrayList<Rectangle> blocks = new ArrayList<Rectangle>();

	public Level(Group r, int[][] m){
		root = r;
		matrix = m;
	}

	public void makeLevel(){
		for (int i=0; i<matrix.length; i++){
			for (int j=0; j<matrix[0].length; j++) {
				blocks.add(new Block(matrix[i][j], x + 100*j, y).createBlock());
			}
			y = y + 50;
		}
		
		for (int i=0; i<blocks.size(); i++){
			root.getChildren().add(blocks.get(i));
		}

	}
}
