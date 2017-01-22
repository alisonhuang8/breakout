package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PowerUp extends Rectangle {
	private int type;
	private int xloc;
	private int yloc;
	private Rectangle powerup;
	
	
	public PowerUp(int t, int x, int y){
		type = t;
		xloc = x;
		yloc = y;
	}
	
	public PowerUp createPU(){
		if (type == 11){
			powerup = createType1();
		} else if (type == 12){
			powerup = createType2();
		} else if (type == 13){
			powerup = createType3();
		} else if (type == 14){
			powerup = createType4();
		}
		return (PowerUp) powerup;
	}
	public Rectangle createType1(){
		Rectangle pu = new Rectangle();
		pu.setX(xloc);
		pu.setY(yloc);
		pu.setWidth(50);
		pu.setHeight(30);
		pu.setFill(Color.SILVER);
		return pu;
	}
	public Rectangle createType2(){
		Rectangle pu = new Rectangle();
		pu.setX(xloc);
		pu.setY(yloc);
		pu.setWidth(50);
		pu.setHeight(30);
		pu.setFill(Color.GOLD);
		
		return pu;
	}
	public Rectangle createType3(){
		Rectangle pu = new Rectangle();
		pu.setX(xloc);
		pu.setY(yloc);
		pu.setWidth(50);
		pu.setHeight(30);
		pu.setFill(Color.PALEGREEN);
		
		return pu;
	}
	public Rectangle createType4(){
		Rectangle pu = new Rectangle();
		pu.setX(xloc);
		pu.setY(yloc);
		pu.setWidth(50);
		pu.setHeight(30);
		pu.setFill(Color.RED);
		
		return pu;
	}
	
}
