package pixelArt;
// PixelCanvas starter

// import classes
import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

public class PixelCanvas {
	// attributes for the pixels 
    private int width;
    private int height;
    private Color backgroundColor;
    private Color[][] pixels;
    private Color currentColor;
    private int cellSize = 6;
    
    // constructor
    public PixelCanvas(int width, int height, Color backgroundColor) {
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        // clear the canvas 
        clear();
        
    }
    // getter method for cell size
    public int getCellSize() { return cellSize; }
    // getter methods for width and height
    public int getWidthInCells() { return width; }
    public int getHeightInCells() { return height; }
    
    // getter and setter methods for the color of the pixel
    public Color getColorAt(int row, int col) { return pixels[row][col]; }
    public void setColorAt(int row, int col, Color color) {
    	if (row >= height || col >= width || row < 0 || col < 0) { // check if its in bounds
    		return;
    	}
    	pixels[row][col] = color;
    }
    
    // current color on the tool bar
    public void setCurrentColor(Color bgColor) {
    	this.currentColor = bgColor;
    }
    // get current color
    public Color getCurrentColor() {
    	return this.currentColor;
    }
    // clear function
    public void clear() {
    	this.pixels = new Color[height][width];
    	for (int i = 0; i < height; i++) {
    		
    		for (int j = 0; j < width; j++) {
    			this.pixels[i][j] = Color.WHITE; // change all colors of pixels to white
    		}
    	}
    }
    // gets a copy of current canvas, duplicate of the pixel grid
    public PixelCanvas getCopy() {
    	PixelCanvas newCanvas = new PixelCanvas(height, width, backgroundColor); // create a copy of the canvas
    	
    	for (int i = 0; i < height; i++) {
    		for (int j = 0; j < width; j++) {
    			newCanvas.setColorAt(i, j, pixels[i][j]); // set the color of the new canvas 
    		}
    	}
    	return newCanvas; // return a copy of the canvas
    }
    
    // implement later
    public void saveToFile(String fileName) throws IOException {
    	PrintWriter pw = new PrintWriter(new FileWriter(fileName)); // create a new printwriter
    	
    	for (int i = 0; i < height; i++) {
    		//https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html
    		// getRGB() method used this
    		for (int j = 0; j < width; j++) {
    			pw.print(pixels[i][j].getRGB() + ", "); // get rgb of the colors and add it with comma
    		}
    		pw.println(); // new line after each row 
    	}
    	
    	pw.close(); // close 
    	
    }
    
    public void loadFromFile(String fileName) throws IOException {
    	File file = new File (fileName); 
    	Scanner sc = new Scanner(file); // scan from the file
    	for (int i = 0; i < height; i++) {
    		String line = sc.nextLine(); // read the next line
    		// split the line into an array based on the commas
    		String[] parts = line.split(",");
    		
    		for (int j = 0; j < width; j++) {
    			// trim extra spaces
    			String s = parts[j].trim();
    			int rgb = Integer.parseInt(s); // parse into an integer 
    			
    			//https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html#Color-int-boolean 
    			// used for parameters of color
    			pixels[i][j] = new Color(rgb, true); // has parameters of rgb and is true, alpha bits are valid
    			
    		}
    	}
    	sc.close(); // close scanner

    }

   // target color is the original color of pixel that is clicked on, replacement is the new one you want to replace with
    public void floodFill(int row, int col, Color targetColor, Color replacementColor) {
    	
    	// check out of bounds of canvas (base case)
    	if (row < 0 || row >= height || col < 0 || col >= width) {
    		return;
    	}
    	// stops if the target and replacement color are same
    	if (targetColor.equals(replacementColor)) {
    		return;
    	}
    	// stops if the starting pixel does not match, only fill pixels that match original clicked color
    	if (!pixels[row][col].equals(targetColor)) {
    		return;
    	}
    	// change color of current pixel
    	pixels[row][col] = replacementColor;
    	// recursive calls, 1 row above, 1 row below, 1 col right, 1 col left, fills all of the pixels in 4 directions
    	floodFill(row + 1, col, targetColor, replacementColor);

    	floodFill(row - 1, col, targetColor, replacementColor);

    	floodFill(row, col + 1, targetColor, replacementColor);

    	floodFill(row, col -1, targetColor, replacementColor);
    }
    
    
}
