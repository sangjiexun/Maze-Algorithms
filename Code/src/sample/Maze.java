package sample;


import Algorithms.Kruskal;
import Algorithms.RecursiveBacktracking;
import Algorithms.RecursiveDivision;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;



enum MazeGenerationAlgorithm
{
    KRUSKAL,
    RECURSIVE_BACKTRACKING,
    RECURSIVE_DIVISION
}

public class Maze {

    public List<Edge> Edges;
    private int size;


    /**
     * This method is used to initialize the List<Edge> edges, which holds the edges that makes up the maze
     *
     * @param algorithm This param is the choice of algorithm the user wants build the maze with. SEE: line 13
     * @param size size of the maze (size*size)
     */
    public void Generate(MazeGenerationAlgorithm algorithm, int size)
    {
        this.size = size;

        //Calls and initializes the Edge list with the according algorithm
        //Note the algorithm classes are static, so we won't need to declare and initialize them
        switch (algorithm)
        {
            case KRUSKAL:
                Edges = Kruskal.GenerateMaze(size);
                break;
            case RECURSIVE_BACKTRACKING:
                Edges = RecursiveBacktracking.GenerateMaze(size);
                break;
            case RECURSIVE_DIVISION:
                Edges = RecursiveDivision.GenerateMaze(size);
                break;
        }
    }

    /**
     * Instead of taking care of the drawing stuff exclusively in the Controller Class i thought, i would write it here instead
     * In retro-perspective i should have just created a static class called Draw, which would have handled all drawing
     *
     * Anyway, the method draws each edge in list<Edge> edges, to the screen, making up a perfect maze.
     *
     * @param gfx the GraphicsContext from the canvas to call the graphics methods from
     */
    public void Draw(GraphicsContext gfx)
    {
        gfx.setStroke(Color.BLACK);

        //Length of the edges we will draw to canvas
        float line_length =  (float)gfx.getCanvas().getWidth() / (float)size ;

        //Draw Start and Goal nodes
        gfx.setFill(Color.RED);
        gfx.fillOval(0 + line_length / 4,0 + line_length / 4, line_length / 2, line_length / 2);
        gfx.setFill(Color.GREEN);
        gfx.fillOval(((size-1) * line_length) + line_length / 4,((size-1) * line_length) + line_length / 4, line_length / 2, line_length / 2);


        //Draw each edge in Edges
        for (Edge e : Edges)
        {
            float x0 = e.B.X * line_length;
            float y0 = e.B.Y * line_length;

            //Draw them horizontally if direction is equal to it
            if (e.Direction == Edge.EdgeDirection.Horizontal)
            {
                gfx.strokeLine(x0, y0, x0 + line_length, y0);
            }
            //Else draw them vertically
            else
            {
                gfx.strokeLine(x0, y0, x0,y0 + line_length);
            }
        }
    }

}
