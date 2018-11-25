package sample;

public class Cell {

    public String Set;
    public Point Point;
    public boolean Visited = false;

    /**
     * The constructor method for the Cell class
     *
     * @param x the x coordinate in Point
     * @param y the y coordinate in Point
     */
    public Cell(int x, int y)
    {
        Point = new Point(x,y);
    }

    /**
     * Another constructor method that takes a point param instead of two ints
     *
     * This method is probably going to be redundant in prospective, because i'm only going
     * to call the other constructor method
     *
     * @param p the point which we set this.Point to
     */
    public Cell(Point p)
    {
        this.Point = p;
    }

}
