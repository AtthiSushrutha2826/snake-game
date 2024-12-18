package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Snakes extends JPanel implements ActionListener {
    
    private Image apple;
    private Image dot;
    private Image head;
    
    private final int x_dots = 900;
    private final int size = 10;
    private final int Rand_position = 29;
    
    private int apple_x_dirn;
    private int apple_y_dirn;
    
    private final int x[] = new int[x_dots];
    private final int y[] = new int[x_dots];
    
    private boolean left_Direction = false;
    private boolean right_Direction = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private boolean inGame = true;
    
    private int dots;
    private Timer time;
    
    Snakes() {
        addKeyListener(new TAdapter());
        
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300, 300));
        setFocusable(true);
        
        loadImages();
        initGame();
    }
    
    public void loadImages() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
        apple = i1.getImage();
        
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));
        dot = i2.getImage();
        
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
        head = i3.getImage();
    }
    
    public void initGame() {
        dots = 3;
        
        for (int i = 0; i < dots; i++) {
            y[i] = 50;
            x[i] = 50 - i * size;
        }
        
        locateApple();
        
        time = new Timer(140, this);
        time.start();
    }
    
    public void locateApple() {
        int r = (int)(Math.random() * Rand_position);
        apple_x_dirn = r * size;
                
        r = (int)(Math.random() * Rand_position);
        apple_y_dirn = r * size;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        draw(g);
    }
    
    public void draw(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x_dirn, apple_y_dirn, this);

            for (int i = 0 ; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }
    
    public void gameOver(Graphics g) {
        String msg = "Game Over!";
        Font font = new Font("SAN_SERIF", Font.BOLD, 14);
        FontMetrics metrices = getFontMetrics(font);
        
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (300 - metrices.stringWidth(msg)) / 2, 300/2);
    }
    
    public void move() {
        for (int i = dots ; i > 0 ; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        
        if (left_Direction) {
            x[0] = x[0] - size;
        }
        if (right_Direction) {
            x[0] = x[0] + size;
        }
        if (upDirection) {
            y[0] = y[0] - size;
        }
        if (downDirection) {
            y[0] = y[0] + size;
        }
    }
    
    public void checkApple() {
        if ((x[0] == apple_x_dirn) && (y[0] == apple_y_dirn)) {
            dots++;
            locateApple();
        }
    }
    
    public void checkCollision() {
        for(int i = dots; i > 0; i--) {
            if (( i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }
        
        if (y[0] >= 300) {
            inGame = false;
        }
        if (x[0] >= 300) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        
        if (!inGame) {
            time.stop();
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        
        repaint();
    }
    
    public class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if (key == KeyEvent.VK_LEFT && (!right_Direction)) {
                left_Direction = true;
                upDirection = false;
                downDirection = false;
            }
            
            if (key == KeyEvent.VK_RIGHT && (!left_Direction)) {
                right_Direction = true;
                upDirection = false;
                downDirection = false;
            }
            
            if (key == KeyEvent.VK_UP && (!downDirection)) {
                upDirection = true;
                left_Direction = false;
                right_Direction = false;
            }
            
            if (key == KeyEvent.VK_DOWN && (!upDirection)) {
                downDirection = true;
                left_Direction = false;
                right_Direction = false;
            }
        }
    }
    
}

