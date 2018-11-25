package sample;

public class Point {

    public int X;
    public int Y;

    /**
     * Constructor method for the Point which takes in each coordinate
     * @param x
     * @param y
     */
    public Point (int x, int y)
    {
        this.X = x;
        this.Y = y;
    }

    /**
     * Used for debugging purposes
     * @return
     */
    public String ToString()
    {
        return String.format("(%s, %s)", X,Y);
    }

}
