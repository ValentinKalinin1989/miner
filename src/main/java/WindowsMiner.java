import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import miner.Cell;
import miner.Coord;
import miner.Game;
import miner.Ranges;

public class WindowsMiner extends JFrame {
    private Game game;

    private JPanel panel;
    private JLabel label;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args) {
        new WindowsMiner();
    }

    private WindowsMiner() {
        game = new Game(9, 9,10);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord: Ranges.getAllCoords())
                {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.getX() * IMAGE_SIZE,
                            coord.getY() * IMAGE_SIZE, this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX()/IMAGE_SIZE;
                int y = e.getY()/IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if(e.getButton() == MouseEvent.BUTTON1)
                    game.tryToOpenCell(coord);
                if(e.getButton() == MouseEvent.BUTTON3)
                    game.flagedUnflagedCell(coord);
                if(e.getButton() == MouseEvent.BUTTON2)
                    game.start();
                label.setText(getMessage());
                panel.repaint();
            }
        });
        //РАЗМЕР ПОЛЯ
        panel.setPreferredSize(new Dimension(Ranges.getSize().getX() * IMAGE_SIZE,
                Ranges.getSize().getY() * IMAGE_SIZE));
        add(panel);
    }

    private String getMessage() {
        switch (game.getState()) {
            case PLAYED: return "Just do it!";
            case BOMBED: return "Terrorist win!";
            case WINNER: return "Win!";
            default: return "Welcome to the Game!";
        }
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Sweeper");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }

    private Image getImage (String name) {
        String filename = "img/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }

    private void setImages() {
        for (Cell cell : Cell.values()) {
            cell.image = getImage(cell.name());
        }
    }

    private void initLabel(){
        label = new JLabel("Welcome to the Game!", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);
    }

}
