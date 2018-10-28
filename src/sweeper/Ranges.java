package sweeper;

import java.util.ArrayList;
import java.util.Random;

/*
 *  Хранит размеры всего, что используется
 */

public class Ranges
{
   private static Coord size;
   private static ArrayList<Coord> allCoords; //перебор всех координат по списку
   private static Random random = new Random(); //генератор случайных координат


   public static void setSize (Coord _size) // установка размеров поля
   {
       size = _size; // _ используется для static
       allCoords = new ArrayList<Coord>();
       for (int y = 0; y < size.y; y++)
           for (int x = 0; x < size.x; x++)
               allCoords.add(new Coord(x,y));
   }

    public static Coord getSize()
    {
        return size;
    }

    public static ArrayList<Coord> getAllCoords () //для построения поля
    {
       return allCoords;
    }

    static boolean inRange (Coord coord) //определяет, находится ли клетка в пределах нашего поля
    {
        return coord.x >= 0 && coord.x < size.x &&
                coord.y >= 0 && coord.y < size.y;
    }

    static Coord getRandomCoord ()
    {
        return new Coord(random.nextInt(size.x), random.nextInt(size.y));
    }

    static ArrayList<Coord> getCoordsArround (Coord coord) //получение координат вокруг нашей клетки
    {
       Coord around;
       ArrayList<Coord> list = new ArrayList<Coord>();
       for (int x = coord.x - 1; x <= coord.x + 1; x++)
           for (int y = coord.y - 1; y <= coord.y + 1; y++)
               if (inRange(around = new Coord(x, y)))
                   if (!around.equals(coord))
                       list.add(around);
       return list;
    }


}
