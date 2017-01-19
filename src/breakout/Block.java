package breakout;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {
	private Rectangle block;
	private int type, xloc, yloc;
	private Boolean hit = false;

	public Block(int t, int x, int y){
		type = t;
		xloc = x;
		yloc = y;
	}

	public Rectangle createBlock(){

		if (type == 1){
			block = createType1();
		} else if (type == 2){
			block = createType2();
		} else if (type == 3){
			block = createType3();
		} else if (type == 4){
			block = createType4();
		}

		return block;
	}

	public Rectangle createType1(){
		Rectangle rec = new Rectangle();
		rec.setX(xloc);
		rec.setY(yloc);
		rec.setWidth(50);
		rec.setHeight(10);
		rec.setFill(Color.PURPLE);
		return rec;
	}
	public Rectangle createType2(){
		Rectangle rec = new Rectangle();
		rec.setX(xloc);
		rec.setY(yloc);
		rec.setWidth(50);
		rec.setHeight(10);
		rec.setFill(Color.CYAN);
		
		return rec;
	}
	public Rectangle createType3(){
		Rectangle rec = new Rectangle();
		rec.setX(xloc);
		rec.setY(yloc);
		rec.setWidth(50);
		rec.setHeight(10);
		rec.setFill(Color.PINK);
		return rec;
	}
	public Rectangle createType4(){
		Rectangle rec = new Rectangle();
		rec.setX(xloc);
		rec.setY(yloc);
		rec.setWidth(50);
		rec.setHeight(10);
		rec.setFill(Color.CORAL);
		return rec;
	}
	
	public Boolean beenHit(ImageView myBall){
		Boolean ballBelow = (myBall.getBoundsInParent().getMinY() < block.getBoundsInParent().getMaxY()) && 
				(myBall.getBoundsInParent().getMaxY() > block.getBoundsInParent().getMaxY());
		Boolean ballAbove = (myBall.getBoundsInParent().getMaxY() > block.getBoundsInParent().getMinY()) && 
				(myBall.getBoundsInParent().getMinY() < block.getBoundsInParent().getMinY());
		Boolean ballLeft = (myBall.getBoundsInParent().getMaxX() > block.getBoundsInParent().getMinX()) && 
				(myBall.getBoundsInParent().getMinX() < block.getBoundsInParent().getMinX());
		Boolean ballRight = (myBall.getBoundsInParent().getMinX() < block.getBoundsInParent().getMaxX()) && 
				(myBall.getBoundsInParent().getMaxX() > block.getBoundsInParent().getMaxX());
		
		
		if (ballBelow) {
			hit = true;
		} else if (ballAbove) {
			hit = true;
		} else if (ballLeft) {
			hit = true;
		} else if (ballRight){
			hit = true;
		}
		
		return hit;

	}

}
