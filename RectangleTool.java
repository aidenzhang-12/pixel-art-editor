package pixelArt;
import java.awt.*;
import javax.swing.*;
public class RectangleTool extends JButton implements Tool {
	// attributes
    private ColorPalette palette;
    private boolean dragging; // know if user is dragging the rectangle
    private int startRow; // start row (row which user starts rectangle
    private int startCol;
    private int currentRow; // row and col when user releases 
    private int currentCol;
    // constructor
    public RectangleTool(ColorPalette palette) { 
    	super("Rectangle"); // super as it inherits JButton
    	this.palette = palette; 
    	dragging = false;
    }
    public String getName() { return "Rectangle"; } // return name, getter method
    // on click method
    public void onClick(PixelCanvas c, int r, int l) {
    	dragging = false; // user isn't dragging when click
    	// debug
    	System.out.println("clicked");
    	// set the start row to the clicked pixel and current row as well, multiple by cell size to get the mouse position
    	startRow = r * c.getCellSize();
    	startCol = l * c.getCellSize();
    	currentRow = r * c.getCellSize();
    	currentCol = l * c.getCellSize();
    }
    public void onDrag(PixelCanvas c, int r, int l) {
    	dragging = true; // set dragging to true
    	System.out.println("dragged");
    	// set current row to current mouse position
    	currentRow = r * c.getCellSize();
    	currentCol = l * c.getCellSize();
    }
    
    public void onRelease(PixelCanvas c, int r, int l) {
    	System.out.println("released"); // debug
    	dragging = false; // set dragging to false when released
    	int starterY = getStartRow() / c.getCellSize(); // start y is the start row, in 2d array
    	int starterX = getStartCol() / c.getCellSize(); // start x is the start col, in 2d array
    	int height = getCurrentHeight() / c.getCellSize(); // get current height and width
    	int width = getCurrentWidth() / c.getCellSize();
    	Color currentColor = c.getCurrentColor(); // get the current color
    	// set the color of the border from start row to the width
    	for (int i = starterX; i < starterX + width; i++) {
    		c.setColorAt(starterY, i, currentColor); 
    		c.setColorAt(starterY + height, i, currentColor);
    	}
    	// set color of the border of the rectangle from start column
    	for (int i = starterY; i < starterY + height; i++) {
    		c.setColorAt(i, starterX, currentColor);
    		c.setColorAt(i, starterX + width, currentColor);
    	}
    	
    }
    public boolean isDragging() { // return whether mouse is dragging
    	return dragging;
    }
    // get the start row, min between start and current row
    public int getStartRow() {
        return Math.min(startRow, currentRow);
    }
    // min between start and current col is start col
    public int getStartCol() {
        return Math.min(startCol, currentCol);
    }
    // find the difference between current and start col, width
    public int getCurrentWidth() {
        return Math.abs(currentCol - startCol);
    }
    // find difference between current and start col, the height
    public int getCurrentHeight() {
        return Math.abs(currentRow - startRow);
    }

}
