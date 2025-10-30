
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class SnakeGame {

    public static void main(String[] argss) {
        // Create the main window frame
        JFrame frame = new JFrame("Classic Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create the game panel, where all the action happens
        GamePanel gamePanel = new GamePanel();
        
        frame.add(gamePanel);
        
        // Automatically adjust the window size to fit the panel's preferred size
        frame.pack();
        
        // Prevent resizing, as it could mess up the grid
        frame.setResizable(false);
        
        // Center the window on the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - frame.getWidth()) / 2;
        int y = (dim.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        // Make the window visible
        frame.setVisible(true);
        
        // Ensure the game panel has focus to receive key events
        gamePanel.requestFocusInWindow();
    }
}