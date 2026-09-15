package pixelArt;
import java.awt.*;
import javax.swing.*;
public class PencilTool extends JButton implements Tool {
    private ColorPalette palette;
    // used to remember where the last position of the mouse was during last mouseevent 
    // draw a connecting line between the two points, last row and col are used for this
    private int lastRow = -1;
    private int lastCol = -1;
    
    public PencilTool(ColorPalette palette) { // constructor
    	super("Pencil");
    	this.palette = palette; 
    }
    public void onRelease(PixelCanvas canvas, int row, int col) {}
    public String getName() { return "Pencil"; } // get name method, returns pencil
    // onclick, sets color of the pixel
    public void onClick(PixelCanvas c, int row, int col)  {
        c.setColorAt(row, col, c.getCurrentColor());
        // set last row to the new click
        lastRow = row;
        lastCol = col;
    }
    // on drag method
    public void onDrag(PixelCanvas c, int r, int l) {
    	if (lastRow != -1 && lastCol != -1) { // draw a line if the last row and col are not default
    		drawLine(c, lastRow, lastCol, r, l, c.getCurrentColor()); // call the draw line method

    	}
    	c.setColorAt(r, l, c.getCurrentColor()); // set the color of the canvas at the new drag position
    	// set the last row to the current
    	lastRow = r; 
    	lastCol = l;
    }
    // draw a line between the previous points and the current point on drag
    // because the drag event has gaps
    private void drawLine(PixelCanvas canvas, int lastRow, int lastCol, int row, int col, Color color) {
    	// amount of points connected
    	int fillSize = 10;
    	for (int i = 1; i < fillSize; i++) {
    		// set up the connecting points
    		//  find connecting points of the current row and previous row
    		// basically trying to draw a line between previous point and new point you are trying to draw a line too
    		canvas.setColorAt(row * i / fillSize + lastRow *( fillSize - i ) / fillSize, col * i / fillSize + lastCol * (fillSize-i) / fillSize, canvas.getCurrentColor());
    	}
    }
    
    
}
