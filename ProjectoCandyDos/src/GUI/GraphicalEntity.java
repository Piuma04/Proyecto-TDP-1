package GUI;


import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

public class GraphicalEntity extends JComponent {

    GraphicalEntity () {
        this.setPreferredSize(new Dimension(500, 500));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw your shapes here using the Graphics object
        g.setColor(Color.RED); // Set the color for the shape
        g.fillRect(0, 0, 2000, 2000); // Example: Draw a red rectangle
        g.setColor(Color.BLUE);
        g.fillOval(75, 75, 50, 50); // Example: Draw a blue oval
        // Draw other shapes as needed
    }
}
