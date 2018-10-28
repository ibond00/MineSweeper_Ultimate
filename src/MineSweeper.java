import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;


public class MineSweeper extends JFrame
{
    private Game game;
    private JPanel panel;
    private JLabel label;

    /*
        everything is set for novice level
     */
    private final int COLS = 9; // столбцы
    private final int ROWS = 9; // строки
    private final int BOMBS = 10; // количество бомб
    private final int IMAGE_SIZE = 40; // размер картинки симметричный по x и y


    public static void main(String[] args)
    {
        new MineSweeper();
    }

    private MineSweeper ()
    {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages(); //установка картинок
        initLabel(); //установка подписи
        initPanel(); //создание окна
        initFrame(); //создание контейнера
    }

    private void initLabel ()
    {
        label = new JLabel("Get rid of the f*in bombs!");
        add (label, BorderLayout.SOUTH);
    }

    private void initPanel (){
        panel = new JPanel() // при инициализации выводим картинки
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g); //рисует все
                for (Coord coord : Ranges.getAllCoords())
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE, this);

            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) // левая кнопка мыши
                    game.pressLeftButton (coord);
                if (e.getButton() == MouseEvent.BUTTON3) // правая кнока мыши
                    game.pressRightButton (coord);
                if (e.getButton() == MouseEvent.BUTTON2) // средняя кнока мыши
                    game.start(); //перезапуск
                label.setText(getmessage ());
                panel.repaint(); // после каждого действия мыши перерисовываем панель игры
            }
        });

        /*
        panel.addKeyListener(new KeyAdapter() { //по нажатию на r - перезапуск (не заработал)
            @Override
            public void keyPressed(KeyEvent e) {
                 if (e.getKeyCode() == 'r')
                     game.start();
            }
        });
        */

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
        add (panel);
    }

    private String getmessage()
    {
        switch (game.getState())
        {
            case PLAYED: return "Some bombs still ticking!";
            case BOMBED:return "BOOM - right in the head..";
            case WINNER: return "Congrats, you won!";
            default: return "Welcome";
        }
    }

    private void initFrame ()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MineSweeper Ultimate");
        setResizable(false); //неизменяемость размера
        setVisible(true); //видимость
        pack(); //устанавливает размер окна, достаточный для отображения всех компонентов
        setIconImage(getImage("icon"));
        setLocationRelativeTo(null); //расположение по центру

    }

    private void setImages ()
    {
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage (String name){
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename)); //подключение папки с ресурсами
        return icon.getImage();

    }

}
