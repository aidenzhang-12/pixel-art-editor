package pixelArt;
import java.awt.*;
import javax.swing.*;


public class EraserTool extends JButton implements Tool {
	// attributes
    private Color backgroundColor; 
    
    public EraserTool(Color bg) { // constructor
    	super("Eraser");
    	backgroundColor = bg; 
    }
    // on release method
    public void onRelease(PixelCanvas canvas, int row, int col) {}
    public String getName() { return "Eraser"; } // get name of the tool
    // on click
    public void onClick(PixelCanvas c, int r, int l) {
    	// creates  a larger area to erase 2x2
    	for (int i = 0; i < 2; i++) {
    		for (int j = 0; j < 2; j++) {
    			c.setColorAt(r + i, l + j , backgroundColor); // set the color to white (background color)
    		}
    	}
    	
    }
    public void onDrag(PixelCanvas c, int r, int l) {
    	// larger area when dragging to erase
    	for (int i = 0; i < 2; i++) {
    		for (int j = 0; j < 2; j++) {
    			c.setColorAt(r + i, l + j , backgroundColor);
    		}
    	}

    	
    }
    
}
