package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block {
	private Rectangle block;
	private int type, xloc, yloc;

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
	
	

}
