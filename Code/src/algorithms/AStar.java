package Algorithms;

import sample.Cell;
import sample.Edge;
import sample.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


class Node
{
    public int X;
    public int Y;

    public boolean Visited = false;

    public float GCost;
    public float TravelCost;

    public Node Parent;

    /**
     * A method that returns the total cost, which is the gCost value plus travelCost value
     * @return float (GCost + TravelCost)
     */
    public float GetTotalCost() {return GCost + TravelCost;}

    public Node (int x, int y)
    {
        this.X = x;
        this.Y = y;
    }
}

public class AStar {

    private int Size;
    private List<Edge> edges;
    private Node[][] nodes;

    private List<Node> openList;
    private List<Node> closedList;

    private List<Point> path;


    /**
     *
     * @param edges This is the list of 'walls'(edges) which tells our algorithm where it can go
     * @param size The size of the maze
     * @return List of points, that is the path from start point to goal point
     */
    public List<Point> GetPath(List<Edge> edges, int size)
    {
        //Initialize objects
        Size = size;
        path = new ArrayList<Point>();
        this.edges = edges;

        openList = new ArrayList<Node>();
        closedList = new ArrayList<Node>();

        InitializeCells();

        //Declare and Initialize the start and goal point
        Point startPoint = new Point(0,0);
        Point goalPoint = new Point(size-1,size-1);


        //Algorithm

        //Start by calculating the heuristic cost for each node

        CalculateHeuristics(goalPoint);

        //Add start node to closedList
        closedList.add(nodes[startPoint.X][startPoint.Y]);

        //Populate the openList with the start node's neighbours
        openList.addAll(GetNeighbours(nodes[startPoint.X][startPoint.Y]));

        //Search for the cheapest node in openList and remove it, repeat until openList is empty
        while (openList.size() > 0)
        {
            Node cheapestNode = GetCheapestNode();

            //Add the cheapestNode neighbours that isn't already in the openList and not in closedList either
            openList.addAll(GetNeighbours(cheapestNode).stream().filter(n -> !openList.contains(n) && !closedList.contains(n)).collect(Collectors.toList()));

            //Check if cheapestNode has already explored neighbours that can be travelled to cheaper from here
            for (Node n : GetNeighbours(cheapestNode).stream().filter(n -> n.Visited == true).collect(Collectors.toList()))
            {
                if (cheapestNode.GetTotalCost() + 1 < n.GetTotalCost())
                {
                    //If so, then we set the parent node to the cheapestNode and recalculate the travelCost
                    //n.Parent = nodes[cheapestNode.X][cheapestNode.Y];
                    n.TravelCost = cheapestNode.TravelCost+1;
                }
            }

            //If the openList now contains our target node, set the parent of the target node to cheapest node, and break the while loop
            if (openList.contains(nodes[goalPoint.X][goalPoint.Y]))
            {
                nodes[goalPoint.X][goalPoint.Y].Parent = nodes[cheapestNode.X][cheapestNode.Y];
                break;
            }

            openList.remove(cheapestNode);
            closedList.add(cheapestNode);
        }



        //Time to backtrack from our targetPoint to GoalPoint
        if (nodes[goalPoint.X][goalPoint.Y].Parent != null)
        {
            Node lastAddedNode = null;

            while(true)
            {

                if (path.size() == 0)
                {
                    path.add(new Point(goalPoint.X, goalPoint.Y));
                    lastAddedNode = nodes[goalPoint.X][goalPoint.X];
                }

                //If we added start node, break while loop
                if (lastAddedNode == nodes[startPoint.X][startPoint.Y])
                {
                    break;
                }
                else
                {
                    path.add(new Point(lastAddedNode.Parent.X, lastAddedNode.Parent.Y));
                    lastAddedNode = lastAddedNode.Parent;
                }

            }
        }

        return path;
    }

    /**
     *
     * @return The node that has the lowest gCost of all the nodes in list<nodes>
     */
    private Node GetCheapestNode()
    {
        int cheapestIndex = 0;

        for (int i = 0; i < openList.size(); i++)
        {
            if (openList.get(i).GetTotalCost() < openList.get(cheapestIndex).GetTotalCost())
            {
                cheapestIndex = i;
            }
        }

        //If openList has multiple nodes with the same totalCost as cheapest node
        int finalCheapestIndex = cheapestIndex;
        if (openList.stream().filter(n -> n.GetTotalCost() == openList.get(finalCheapestIndex).GetTotalCost()).count() > 1)
        {
            return openList.stream().min((n1,n2) -> Float.compare(n1.GCost, n2.GCost)).get();
        }

        return openList.get(cheapestIndex);
    }

    /**
     *
     * @param node The node to get the neighbours from
     * @return the list of neighbours (List<nodes>)
     */
    private List<Node> GetNeighbours(Node node) {

        List<Node> neighbours = new ArrayList<Node>();

        int n_X = node.X;
        int n_Y = node.Y;

        //Left neighbour
        if (n_X - 1 >= 0)
        {
            if (!DoesEdgeExistBetween(new Point(n_X, n_Y), new Point(n_X - 1, n_Y)))
            {
                neighbours.add(nodes[n_X-1][n_Y]);
            }
        }

        //Right neighbour
        if (n_X + 1 < Size)
        {
            if (!DoesEdgeExistBetween(new Point(n_X, n_Y), new Point(n_X + 1, n_Y)))
            {
                neighbours.add(nodes[n_X+1][n_Y]);
            }
        }

        //Lower Neighbour
        if (n_Y + 1 < Size)
        {
            if (!DoesEdgeExistBetween(new Point(n_X, n_Y), new Point(n_X, n_Y + 1)))
            {
                neighbours.add(nodes[n_X][n_Y+1]);
            }
        }

        //Upper Neighbour
        if (n_Y - 1 >= 0)
        {
            if (!DoesEdgeExistBetween(new Point(n_X, n_Y), new Point(n_X, n_Y - 1)))
            {
                neighbours.add(nodes[n_X][n_Y-1]);
            }
        }

        for (Node n : neighbours)
        {
            if (n.Parent == null)
            {
                n.Parent = nodes[n_X][n_Y];
            }
            if (node.TravelCost == 0)
            {
                n.TravelCost = nodes[n_X][n_Y].TravelCost + 1;
            }

            n.Visited = true;
        }

        return neighbours;
    }

    /**
     * This method  
     *
     * @param a The point a
     * @param b The point b
     * @return true or false depending on whether the edge exists in edges
     */
    private boolean DoesEdgeExistBetween(Point a, Point b)
    {
        for (Edge e : edges)
        {
            if (e.A.X == a.X && e.A.Y == a.Y && e.B.X == b.X && e.B.Y == b.Y)
            {
                return true;
            }
            else if (e.A.X == b.X && e.A.Y == b.Y && e.B.X == a.X && e.B.Y == a.Y)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * This method calculates the heuristics for every node in nodes
     * We use the manhattan distance approach
     * See: https://en.wiktionary.org/wiki/Manhattan_distance
     *
     * @param goalP The point from where the heuristics is calculated
     */
    private void CalculateHeuristics(Point goalP) {

        for (int x = 0; x < Size; x++)
        {
            for (int y = 0; y < Size; y++)
            {
                int xC = Math.abs(x - goalP.X);
                int yC = Math.abs(y - goalP.Y);

                nodes[x][y].GCost = xC + yC;
            }
        }
    }

    /** This method initializes the two dimensional array nodes[][],
     * We also run a nested for-loop and give each node it's coordinate, according to a 2D grid
     */
    private void InitializeCells() {

        nodes = new Node[Size][Size];

        for (int x = 0; x < Size; x++)
        {
            for (int y = 0; y < Size; y++)
            {
                nodes[x][y] = new Node(x,y);
            }
        }
    }
}
