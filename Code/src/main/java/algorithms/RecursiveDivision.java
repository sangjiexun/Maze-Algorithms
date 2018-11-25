package algorithms;

import sample.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RecursiveDivision {

    private static ArrayList<Edge> edges;
    private static int Size;

    /**
     * This method is used to call the private GenerateRandomWall() which is a recursive method
     * So we declare and initializes the data in this method , and the GenerateRandomWall() will run the
     * actual algorithm and chance the data in our variables
     *
     * @param size the size of the maze (size*size)
     * @return List<Edge> , the list of edges that will be drawn to the screen
     */
    public static ArrayList<Edge> GenerateMaze(int size)
    {
        Size = size;
        edges = new ArrayList<Edge>();

        GenerateRandomWall(new Point(0, 0), new Point(size, size),false);
        return edges;
    }

    /**
     * The actual Recursive Division Algorithm.
     * This method takes in two points, which can be thought of as two points making up a rectangle
     * The area of the rectangle, is the area where we divide and continuously create edges that in the end
     * will make up what looks like a perfect maze
     *
     * See: http://weblog.jamisbuck.org/2011/1/12/maze-generation-recursive-division-algorithm
     *
     * @param from The from point, could also be thought of as start point of the rect
     * @param too The too point, could also be thought of as the end Point of the rect
     * @param d A bool that determines whether we start by slicing the area horizontally or vertically
     */
    private static void GenerateRandomWall(Point from, Point too, boolean d)
    {
        if (Math.abs(too.X-from.X) == 1 || Math.abs(too.Y - from.Y) == 1)
        {
            return;
        }

        if (d == false)
        {
            //slice horizontally
            int rnd_y = RandomNum.Next(from.Y+1, too.Y);
            int rnd_x = RandomNum.Next(from.X, too.X);

            for (int x = from.X; x < too.X; x++)
            {
                if (x != rnd_x)
                {
                    Edge edge = new Edge(new Point(x, rnd_y - 1), new Point(x, rnd_y));
                    edge.Direction = Edge.EdgeDirection.Horizontal;
                    edges.add(edge);
                }

            }

            //upper
            GenerateRandomWall(new Point(from.X, from.Y), new Point(too.X, rnd_y), true);
            //lower
            GenerateRandomWall(new Point(from.X, rnd_y), new Point(too.X, too.Y), true);
        }
        else
        {
            //slice vertically
            int rnd_x = RandomNum.Next(from.X + 1, too.X);
            int rnd_y = RandomNum.Next(from.Y, too.Y);

            for (int y = from.Y; y < too.Y; y++)
            {
                if (y != rnd_y)
                {
                    Edge edge = new Edge(new Point(rnd_x - 1, y), new Point(rnd_x, y));
                    edge.Direction = Edge.EdgeDirection.Vertical;
                    edges.add(edge);
                }
            }

            //left
            GenerateRandomWall(new Point(from.X, from.Y), new Point(rnd_x, too.Y), false);
            //right
            GenerateRandomWall(new Point(rnd_x, from.Y), new Point(too.X, too.Y), false);
        }
    }
}
