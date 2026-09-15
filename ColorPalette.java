package pixelArt;

// import classes
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;


public class ColorPalette extends JPanel implements ActionListener {
	// create a new array list of colors 
    private ArrayList<Color> colors = new ArrayList<>();
    // selected index
    private int selectedIndex = 0;
    // current color the user has clicked
    private Color currentColor; 
    // has a button for current color, is displayed on the UI
    private JButton currentColorButton;
    
    // methods
    public void addColor(Color c) { colors.add(c); }
    public Color getSelectedColor() { return colors.get(selectedIndex); }
    public void setSelectedIndex(int i) { if(i>=0 && i<colors.size()) selectedIndex=i; }
    public List<Color> getColors() { return Collections.unmodifiableList(colors); }
	    
    public ColorPalette() { // constructor
    	setLayout(new BorderLayout()); // layout is a border layout
    	setSize(350, 50); // size of 350 x 50
    	
    	JPanel leftPanel = new JPanel(); // left panel for the color buttons and label
    	
    	leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // left panel is a box layout
    	
    	// add all the colors into the arraylist
    	 colors = new ArrayList<Color>();
         colors.add(Color.BLACK);
         colors.add(Color.WHITE);
         colors.add(Color.RED);
         colors.add(Color.GREEN);
         colors.add(Color.BLUE);
         colors.add(Color.YELLOW);
         colors.add(Color.ORANGE);
         colors.add(Color.PINK);
         currentColor = Color.BLACK; // black is the default color
         selectedIndex = 0;
         // button panel for colors
         JPanel buttonPanel = new JPanel();
         buttonPanel.setLayout(new GridLayout(2, 4)); // grid layout for all colors
         
         // loop through the colors to add an action listener and create borders between colors
         for(int i = 0; i < colors.size(); i++) {
        	 JButton button = new JButton();
        	 button.setBackground(colors.get(i));
        	 button.setSize(50, 30);
        	 //https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/javax/swing/BorderFactory.html
        	 // create a border for the buttons color palette used this link
        	 button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        	 button.addActionListener(this);
        	 buttonPanel.add(button);
         }
         // color palette label
         JLabel colorsLabel = new JLabel("Color Palette");
         // set the label to the center
         colorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
         // add both the label and the buttons
         leftPanel.add(colorsLabel);
         leftPanel.add(buttonPanel);
         // right panel for the selected color
         JPanel rightPanel = new JPanel();
         // set the layout as box layout
         rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

         // create a current color button, that shows the current color
         currentColorButton = new JButton();
         currentColorButton.setSize(200, 100);
        
         // set the background as the current color
         currentColorButton.setBackground(currentColor);
         currentColorButton.setEnabled(false);
         currentColorButton.setAlignmentX(Component.CENTER_ALIGNMENT);

         // add the label to the center
         JLabel selectedLabel = new JLabel("Selected Color");
         selectedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

         rightPanel.add(selectedLabel);
         // used the previous border factory reference to create the gap in between panels
         rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 20));
         // add in the current color
         rightPanel.add(currentColorButton);
         // add in the left and right panels 
         add(leftPanel, BorderLayout.WEST);
         add(rightPanel, BorderLayout.EAST);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// get source of the action event
		Object obj = e.getSource();
		
		//https://www.baeldung.com/java-instanceof
		// used the above link for instanceof
		// used to determine if the button is clicked
        if (obj instanceof JButton) {
            JButton button = (JButton) obj;
            // get the background color
            Color color = button.getBackground();
            for (int i = 0; i < colors.size(); i++) {
                if (color.equals(colors.get(i))) {
                	// change the current color and the index 
                    selectedIndex = i;
                    // set current color
                    currentColor = color;
                    currentColorButton.setBackground(currentColor);
                    break;
                }
            }
        }
		
	}
}
