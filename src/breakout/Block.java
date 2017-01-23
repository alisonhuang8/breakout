package breakout;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {
	private Rectangle block;
	private int type;
	private double xloc, yloc;
	private Boolean hit = false;
	private Boolean hasPU = false;
	
	public Block(int t, double x, double y){
		type = t;
		xloc = x;
		yloc = y;
	}
	
	public Block(int t){
		type = t;
	}
	
	public Block(double x, double y){
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
		} else if (type == 5){
			block = createType5();
		}
		return block;
	}
	public Rectangle createType1(){
		Rectangle rec = new Rectangle();
		rec.setX(xloc);
		rec.setY(yloc);
		rec.setWidth(500/9);
		rec.setHeight(30);
		rec.setFill(Color.PURPLE);
		return rec;
	}
	public Rectangle createType2(){
		Rectangle rec = new Rectangle();
		rec.setX(xloc);
		rec.setY(yloc);
		rec.setWidth(500/9);
		rec.setHeight(30);
		rec.setFill(Color.BLUE);
		
		return rec;
	}
	public Rectangle createType3(){ // moving block
		Rectangle rec = new Rectangle();
		rec.setX(xloc);
		rec.setY(yloc);
		rec.setWidth(500/9);
		rec.setHeight(30);
		rec.setFill(Color.PINK);
		return rec;
	}
	public Rectangle createType4(){ // power ups
		Rectangle rec = new Rectangle();
		rec.setX(xloc);
		rec.setY(yloc);
		rec.setWidth(500/9);
		rec.setHeight(30);
		rec.setFill(Color.GREEN);
		return rec;
	}
	public Rectangle createType5(){ // power ups
		Rectangle rec = new Rectangle();
		rec.setX(xloc);
		rec.setY(yloc);
		rec.setWidth(500/9);
		rec.setHeight(30);
		rec.setFill(Color.ORANGE);
		return rec;
	}
	
	
	
	
	public int returnType(){
		return type;
	}
	
	public double returnX(){
		return xloc;
	}
	
	public double returnY(){
		return yloc;
	}
	
	public void decType(){
		type = type - 1;
	}
	
	
}