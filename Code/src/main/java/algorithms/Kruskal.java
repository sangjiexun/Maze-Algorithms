package algorithms;

import sample.Cell;
import sample.Edge;
import sample.RandomNum;

import java.util.ArrayList;
import java.util.List;

public class Kruskal {

    private static int Size;
    private static List<Edge> edges;
    private static List<String> sets;
    private static Cell[][] cells;

    /**
     * This static method is where we run our Kruskal algorithm to generate the maze
     * @param size is the size of the maze (size*size)
     * @return List<Edge> , the list of edges we will draw to the screen
     */
    public static List<Edge> GenerateMaze(int size)
    {
        Size = size;

        InitCells();
        InitEdges();
        InitSets();


        while (sets.size() > 1)
        {
            int rnd_edge = RandomNum.Next(0, edges.size());
            Edge e = edges.get(rnd_edge);

            if (IsCellSetsDifferent(e))
            {
                int rnd_choice = RandomNum.Next(0, 2);
                String chosen_set = null;

                if (rnd_choice == 0)
                {
                    chosen_set = cells[e.A.X][e.A.Y].Set;
                }
                else
                {
                    chosen_set = cells[e.B.X][e.B.Y].Set;
                }

                for (Cell[] arr : cells)
                {
                    for (Cell c : arr)
                    {
                        if (c.Set == chosen_set)
                        {
                            if (rnd_choice == 0)
                            {
                                c.Set = cells[e.B.X][e.B.Y].Set;
                            }
                            else
                            {
                                c.Set = cells[e.A.X][e.A.Y].Set;
                            }
                        }
                    }
                }
                sets.remove(chosen_set);
                edges.remove(edges.get(rnd_edge));
            }
        }

        return edges;
    }

    /**
     * This method checks whether or not the two cells names are different between a chosen edge
     * @param edge The edge that holds the data about the two points between it.
     * @return bool
     */
    private static boolean IsCellSetsDifferent(Edge edge)
    {
        if (cells[edge.A.X][edge.A.Y].Set == cells[edge.B.X][edge.B.Y].Set)
        {
            return false;
        }
        return true;
    }

    /**
     * This method initializes the Cell[][] cells
     */
    private static void InitCells()
    {
        cells = new Cell[Size][Size];

        int counter = 0;

        for (int x = 0; x < Size; x++)
        {
            for (int y = 0; y < Size; y++)
            {
                cells[x][y] = new Cell(x, y);
                cells[x][y].Set = Integer.toString(counter);
                counter++;
            }
        }
    }

    /**
     * This method initializes the list<Edge> edges
     */
    private static void InitEdges()
    {
        edges = new ArrayList<Edge>();

        for (int i = 0; i < Size-1; i++)
        {
            for (int j = 0; j < Size; j++)
            {
                edges.add(new Edge(cells[i][j].Point, cells[i + 1][ j].Point));
                edges.add(new Edge(cells[j][i].Point, cells[j] [i + 1].Point));
            }
        }

        for(Edge edge : edges)
        {
            if ((edge.A.X - edge.B.X) == -1)
            {
                edge.Direction = Edge.EdgeDirection.Vertical;
            }
            else
            {
                edge.Direction = Edge.EdgeDirection.Horizontal;
            }
        }
    }

    /**
     * This method initializes the List<String> sets
     */
    private static void InitSets()
    {
        sets = new ArrayList<String>();

        for (Cell[] arr : cells)
        {
            for (Cell c : arr)
            {
                sets.add(c.Set);
            }
        }
    }
}


