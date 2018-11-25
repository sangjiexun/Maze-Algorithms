package sample;

public class Edge {

    public enum EdgeDirection
    {
        Vertical,
        Horizontal
    }

    public Point A;

    public Point B;

    public EdgeDirection Direction;

    /**
     * Constructor which initializes the two points, A and B
     * @param a
     * @param b
     */
    public Edge(Point a, Point b)
    {
        this.A = a;
        this.B = b;
    }

    /**
     * A method I only called for debugging. redundant
     * @return Returns a string with the coordinates of the two points "(x1,y1)-(x2,y2)"
     */
    public String ToString()
    {
        return String.format("%s-%s", A.toString(), B.toString());
    }

}

