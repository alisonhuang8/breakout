/*
 *Refactored code in this class. Originally had a method to make each type of block, which resulted in a lot of
 *duplicated code. Changes made were so that there is only one method that is needed, as long as the constructor passes
 *in a type value. The type helps to determine what color to set the fill of the rectangle, but the rest of the 
 *specifications are left the same. This, however, simplifies the block parameters so that if the user wants to change up
 *the sizes of the blocks then other changes would need to be made.
 */

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
		Rectangle rec = new Rectangle();
		if (type == 1){
			rec.setFill(Color.PURPLE);
		} else if (type == 2){
			rec.setFill(Color.BLUE);
		} else if (type == 3){
			rec.setFill(Color.PINK);
		} else if (type == 4){
			rec.setFill(Color.GREEN);
		} else if (type == 5){
			rec.setFill(Color.ORANGE);
		}
		
		rec.setX(xloc);
		rec.setY(yloc);
		rec.setWidth(500/9);
		rec.setHeight(30);
		
		return rec;
	}
}