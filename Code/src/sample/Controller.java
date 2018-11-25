package sample;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Algorithms.AStar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;



public class Controller implements Initializable {

    /**
     * The FXML variables which 'magically' will be bound to the xml objects in sample.fxml
     * given that the name is exactly the same as the fx:id="name" in the fxml file
     */
    //FXML variables
    public Button btnGenerate;
    public Button btnSolve;
    public Canvas canvas;
    public ComboBox comboBoxAlgorithm;
    public Spinner sizeUpDown;

    //Local variables
    private Integer Size;
    private GraphicsContext gfx;
    private Maze maze;
    private AStar astar;


    /**
     * The initializer method is for when you wanna run some code right after the View loads
     * So in our case we want to change and tweak some settings for variables and so on, before things are drawn to the
     * screen
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Disable buttons at start
        //We don't want the user to run code, that iterates over data, that hasn't been stored in memory yet
        //More 'could' be done to help secure crashing the program, but for our little program's case it's
        //foolproof enough
        btnGenerate.setDisable(true);
        btnSolve.setDisable(true);

        //Initialize A*
        astar = new AStar();

        //Spinner Settings
        sizeUpDown.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 40, 12));

        //ComboBox Settings
        comboBoxAlgorithm.setPromptText("Select algorithm");
        comboBoxAlgorithm.getItems().addAll("Kruskal", "Recursive Backtracking", "Recursive Division");

        //Initialize canvas graphics
        gfx = canvas.getGraphicsContext2D();

        //Initialize maze
        maze = new Maze();

        DrawCanvas();
    }


    /**
     * This method is called when we 'choose' a list item, from the spinner xml object
     * So when we choose an algorithm, it will set the enable the Generate button
     */
    @FXML
    private void InputMethodChanged()
    {
        btnGenerate.setDisable(false);
    }


    /**
     * This method is called whenever the Generate button onClick event is fired
     */
    @FXML
    private void OnGenerateBtnClick()
    {
        //Redraw Canvas
        DrawCanvas();

        //Set local variable Size to spinner.value
        Size = (int)sizeUpDown.getValue();

        //Generate maze and draw
        switch ((String)comboBoxAlgorithm.getValue())
        {
            case "Kruskal":
                maze.Generate(MazeGenerationAlgorithm.KRUSKAL, Size);
                break;
            case "Recursive Backtracking":
                maze.Generate(MazeGenerationAlgorithm.RECURSIVE_BACKTRACKING, Size);
                break;
            case "Recursive Division":
                maze.Generate(MazeGenerationAlgorithm.RECURSIVE_DIVISION, Size);
                break;

            default:
                    break;
        }
        maze.Draw(gfx);
        btnSolve.setDisable(false);
    }

    /**
     * Redraws the canvas. Fills the canvas rect with white and draws a black border
     */
    private void DrawCanvas()
    {

        //Clear canvas graphics
        gfx.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        //Redraw
        gfx.setFill(Color.WHITE);
        gfx.setStroke(Color.BLACK);
        gfx.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        gfx.strokeRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Method that will draw our path from startpoint to goalpoint
     */
    @FXML
    private void DrawPath()
    {
        //Get list of points (the path) from A* class
        List<Point> path = astar.GetPath(maze.Edges, Size);

        //Set stroke and line width
        gfx.setStroke(Color.RED);
        gfx.setLineWidth(3);

        //We calculate the length of the lines we're going to draw
        //It's also the same length as the walls
        float length = (float)gfx.getCanvas().getWidth() / (float)Size;

        //Here we iterate through the path (Collections of points)
        //So we can draw from point path[i] to path[i+1]
        for (int i = 0; i < path.size()-1; i++)
        {
            float x1 = path.get(i).X * length + (length/2);
            float y1 = path .get(i).Y * length + (length/2);

            float x2 = path.get(i+1).X * length + (length/2);
            float y2 = path.get(i+1).Y * length + (length/2);

            gfx.strokeLine(x1, y1, x2, y2);

        }

        //Resetting the graphics settings
        //?? somehow resetting the gfx.SetStroke(COLOR) here, doesn't work??
        gfx.setLineWidth(1);
        btnSolve.setDisable(true);

    }
}
