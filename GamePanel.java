import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.Random;

// The GamePanel is where the game is drawn and where the logic runs
public class GamePanel extends JPanel implements ActionListener {

    // --- Constants ---
    private final int TILE_SIZE = 25; // Size of a single grid cell in pixels
    private final int GRID_WIDTH = 30; // Number of tiles wide
    private final int GRID_HEIGHT = 20; // Number of tiles high
    private final int GAME_WIDTH = GRID_WIDTH * TILE_SIZE;
    private final int GAME_HEIGHT = GRID_HEIGHT * TILE_SIZE;
    private final int DELAY = 100; // Game speed (in milliseconds)

    // --- Game Variables ---
    private ArrayList<Point> snake; // Stores the (x, y) coordinates of the snake's body
    private Point food;           // Stores the (x, y) coordinate of the food
    private int score;
    private char direction = 'R'; // R=Right, L=Left, U=Up, D=Down
    private boolean isRunning = false;
    private Timer timer;
    private Random random;

    public GamePanel() {
        // Set up the panel properties
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        
        // Initialize components
        random = new Random();
        snake = new ArrayList<>();
        
        // Add Key Listener for controls
        addKeyListener(new MyKeyAdapter());
        
        startGame();
    }

    private void startGame() {
        // Initial setup for the snake
        snake.clear();
        snake.add(new Point(GRID_WIDTH / 2, GRID_HEIGHT / 2)); // Start in the center
        snake.add(new Point(GRID_WIDTH / 2 - 1, GRID_HEIGHT / 2));
        
        direction = 'R';
        score = 0;
        isRunning = true;

        // Place the first piece of food
        spawnFood();

        // Initialize and start the game timer
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void spawnFood() {
        int foodX, foodY;
        boolean onSnake;
        do {
            // Generate a random position (tile index)
            foodX = random.nextInt(GRID_WIDTH);
            foodY = random.nextInt(GRID_HEIGHT);
            food = new Point(foodX, foodY);

            // Check if food spawns on the snake
            onSnake = false;
            for (Point p : snake) {
                if (p.equals(food)) {
                    onSnake = true;
                    break;
                }
            }
        } while (onSnake);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        if (isRunning) {
            // 1. Draw Grid (Optional, for better visual)
            g.setColor(new Color(40, 40, 40)); 
            for (int i = 0; i < GRID_WIDTH; i++) {
                g.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, GAME_HEIGHT);
            }
            for (int i = 0; i < GRID_HEIGHT; i++) {
                g.drawLine(0, i * TILE_SIZE, GAME_WIDTH, i * TILE_SIZE);
            }

            // 2. Draw Food (Attractive: use an oval/circle)
            g.setColor(Color.RED);
            g.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

            // 3. Draw Snake (Attractive: different color for head)
            for (int i = 0; i < snake.size(); i++) {
                Point segment = snake.get(i);
                
                if (i == 0) {
                    // Head
                    g.setColor(Color.GREEN.darker());
                    g.fillRect(segment.x * TILE_SIZE, segment.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else {
                    // Body (a lighter shade)
                    g.setColor(Color.GREEN.brighter());
                    g.fillRect(segment.x * TILE_SIZE, segment.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }

            // 4. Draw Score
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            String scoreText = "Score: " + score;
            g.drawString(scoreText, (GAME_WIDTH - metrics.stringWidth(scoreText)) - 10, g.getFont().getSize());
        
        } else {
            // Game Over screen
            drawGameOver(g);
        }
    }
    
    // Game loop tick
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            move();
            checkFood();
            checkCollision();
        }
        repaint(); // Call paintComponent to redraw the game state
    }

    private void move() {
        // Create the new head position
        Point newHead = new Point(snake.get(0));

        // Update the head coordinates based on direction
        switch (direction) {
            case 'U': newHead.y--; break;
            case 'D': newHead.y++; break;
            case 'L': newHead.x--; break;
            case 'R': newHead.x++; break;
        }

        // Insert the new head at the front of the list
        snake.add(0, newHead);

        // Remove the tail only if food was NOT eaten in this move
        if (!newHead.equals(food)) {
            snake.remove(snake.size() - 1);
        }
    }

    private void checkFood() {
        if (snake.get(0).equals(food)) {
            score++;
            spawnFood();
            // Note: Snake already grew in the move() method by NOT removing the tail.
        }
    }

    private void checkCollision() {
        Point head = snake.get(0);

        // 1. Check if snake hits the boundary
        if (head.x < 0 || head.x >= GRID_WIDTH || head.y < 0 || head.y >= GRID_HEIGHT) {
            isRunning = false;
        }

        // 2. Check if snake hits itself (start from the second segment)
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                isRunning = false;
                break;
            }
        }

        if (!isRunning) {
            timer.stop();
        }
    }

    private void drawGameOver(Graphics g) {
        // Background overlay
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        // Game Over Text
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        String goText = "Game Over";
        g.drawString(goText, (GAME_WIDTH - metrics1.stringWidth(goText)) / 2, GAME_HEIGHT / 3);

        // Final Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        String scoreText = "Score: " + score;
        g.drawString(scoreText, (GAME_WIDTH - metrics2.stringWidth(scoreText)) / 2, GAME_HEIGHT / 2);
        
        // Restart Prompt
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        String prompt = "Press SPACE to Restart";
        g.drawString(prompt, (GAME_WIDTH - metrics3.stringWidth(prompt)) / 2, GAME_HEIGHT * 3 / 4);
    }

    // --- Key Input Handler ---
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (isRunning) {
                // Change direction only if it's not the opposite direction
                if (key == KeyEvent.VK_LEFT && direction != 'R') {
                    direction = 'L';
                } else if (key == KeyEvent.VK_RIGHT && direction != 'L') {
                    direction = 'R';
                } else if (key == KeyEvent.VK_UP && direction != 'D') {
                    direction = 'U';
                } else if (key == KeyEvent.VK_DOWN && direction != 'U') {
                    direction = 'D';
                }
            } else if (key == KeyEvent.VK_SPACE) {
                 // Restart the game on SPACE press after Game Over
                startGame();
            }
        }
    }
}