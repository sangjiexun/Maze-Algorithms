package Algorithms;

import sample.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RecursiveBacktracking {

    private static int Size;
    private static Cell[][] cells;
    private static ArrayList<Edge> edges;
    private static Stack<Cell> stack;


    /**
     * This method generates the list of edges with the Recursive Backtracking Algorithm
     * @param size The size of the maze (size*size)
     * @return ArrayList<Edge> , the list of edges we are going to draw to the screen
     */
    public static ArrayList<Edge> GenerateMaze(int size)
        {
            //Initialize
            Size = size;
            InitializeCells();
            InitializeEdges();
            stack = new Stack<Cell>();

            Cell current;

            //Algorithm

            //STEP 1: Choose 'random' cell and make it the current cell, and push it to the stack

            int rndX = RandomNum.Next(0, size);
            int rndY = RandomNum.Next(0, size);

            current = cells[rndX][rndY];
            stack.push(cells[rndX][rndY]);
            cells[current.Point.X][current.Point.Y].Visited = true;

            //STEP 2: While all cells hasn't been visited
            while (!AllCellsVisited())
            {
                //STEP 3: Get a random unvisited neighbour from current cell.
                //If cell doesn't have any valid neighbours it will return null
                Point neighbour = GetRandomNeighbour(current.Point);

                //If the current cell has a unvisited neighbour
                if (neighbour != null)
                {
                    RemoveEdge(current.Point, neighbour);
                    cells[neighbour.X][neighbour.Y].Visited = true;
                    current = cells[neighbour.X][neighbour.Y];
                    stack.push(current);

                }

                //If the current cell doesn't have any unvisited neighbours
                else
                {
                    while (true)
                    {
                        Cell pop_cell = stack.pop();
                        Point pop_neighbour = GetRandomNeighbour(pop_cell.Point);

                        //If popped cell has a unvisited neighbour, break while loop
                        if (pop_neighbour != null)
                        {
                            RemoveEdge(pop_cell.Point, pop_neighbour);
                            current = cells[pop_neighbour.X][pop_neighbour.Y];
                            stack.push(cells[pop_neighbour.X][pop_neighbour.Y]);
                            cells[pop_neighbour.X][pop_neighbour.Y].Visited = true;

                            break;
                        }
                    }
                }
            }

            return edges;
        }

    /**
     * Checks whether or not all cells have been visited
     * @return bool
     */
    private static boolean AllCellsVisited()
        {
            for (Cell arr[] : cells){
                for (Cell c : arr)
                {
                    if (!c.Visited)
                    {
                        return false;
                    }
                }
            }
            return true;
        }

    /**
     * Creates a list of neighbour points to the param current, and returns one random of those
     * @param current the point from which we are going to get the neighbours from
     * @return Point, a 'randomly' chosen point in the neighbours list
     */
    private static Point GetRandomNeighbour(Point current)
        {
            List<Cell> neighbours = new ArrayList<Cell>();

            int cX = current.X;
            int cY = current.Y;

            //right
            if (cX + 1 <= Size - 1)
            {
                neighbours.add(cells[cX + 1][cY]);
            }
            //left
            if (cX - 1 >= 0)
            {
                neighbours.add(cells[cX - 1][ cY]);
            }
            //Upper
            if (cY - 1 >= 0)
            {
                neighbours.add(cells[cX][cY - 1]);
            }
            //Lower
            if (cY + 1 <= Size - 1)
            {
                neighbours.add(cells[cX][cY + 1]);
            }


            //Declare and initialize a new list called toRemove
            //We then run a foreach loop that iterates over every Cell in neighbours
            //If Cell n is already visited, add it to toRemove list
            List<Cell> toRemove = new ArrayList<>();

            for (Cell n : neighbours)
            {
                if (n.Visited)
                {
                    toRemove.add(n);
                }
            }
            //After the foreach loop, remove all cells in neighbours that matches the cells in toRemove
            //We need to do it this way because Java doesn't like to change a list while we iterate over it
            neighbours.removeAll(toRemove);

            //Check if neighbours list is empty, if not then return a randomly chosen neighbour
            if (neighbours.size() > 0)
            {
                return neighbours.get(RandomNum.Next(0, neighbours.size())).Point;
            }

            return null;
        }

    /**
     * Takes in two point parameters, then searches through List<Edge> to see if there is a match.
     * If there is a match, remove that edge from the list
     * @param a the first point to compare edge.A to
     * @param b the second point to compare edge.B to
     */
        private static void RemoveEdge(Point a, Point b)
        {
            //Here we use a lambda expression/predicate to express that we're looking for a match in list
            //Either if (n.A == a && n.B == b) or if (n.A == b && n.B == a)
            edges.removeIf(n -> n.A == a && n.B == b || n.B == a && n.A == b);
        }

    /**
     * Initializes the cell[][] cells
     */
    private static void InitializeCells()
        {
            cells = new Cell[Size][Size];

            for (int i = 0; i < Size; i++)
            {
                for (int j = 0; j < Size; j++)
                {
                    cells[i][j] = new Cell(i, j);
                }
            }
        }

    /**
     * Initializes the List<Edge> edges and sets the direction of the EdgeDirection variable
     */
    private static void InitializeEdges()
        {
            edges = new ArrayList<Edge>();

            for (int i = 0; i < Size -1; i++)
            {
                for (int j = 0; j < Size; j++)
                {
                    edges.add(new Edge(cells[i][j].Point, cells[i + 1][ j].Point));
                    edges.add(new Edge(cells[j][i].Point, cells[j][ i + 1].Point));
                }
            }

            for (Edge e : edges)
            {
                if ((e.A.X - e.B.X) == -1)
                {
                    e.Direction = Edge.EdgeDirection.Vertical;
                }
                else
                {
                    e.Direction = Edge.EdgeDirection.Horizontal;
                }
            }
        }

    }

