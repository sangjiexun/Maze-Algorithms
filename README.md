# Maze Algorithms
A *JavaFX* applet showcasing different algorithms to generate &amp; solve mazes!

## Generating algorithms
* **Kruskal´s algorithm**

    *[Kruskal´s algorithm](https://en.wikipedia.org/wiki/Kruskal's_algorithm) is a method for producing a minimal spanning tree from a weighted graph. The algorithm used in this application is actually a randomized version of Kruskal´s.*

* **Recursive backtracking**

    *This is a fast, easy to understand, and straightforward to implement. However for
    exceptionally large mazes it can be fairly inefficient, due to stack space proportional to the maze size.*

    **General steps in algorithm:**
        
    1. Choose a starting point in the field.

    2. Randomly choose a wall at that point and carve a passage through to the adjacent cell, but only if the adjacent cell has not been visited yet. This becomes the new current cell.
    3. If all adjacent cells have been visited, back up to the last cell that has uncarved walls and repeat.
    4. The algorithm ends when the process has backed all the way up to the starting point.

    

* **Recursive division**

    *The recursive division algorithm is one that must be implemented as a wall adder. This algorithm is particularly fascinating because of it's fractal nature.*

    **General steps in algorithm:**

    1. Begin with an empty field.

    2. Bisect the field with a wall, either horizontally or vertically. Add a single passage through the wall.
    3. Repeat step #2 with the areas on either side of the wall.
    4. Continue, recursively, until the maze reaches the desired resolution.

## Solving algorithms
* **A*** **(A Star)**
    
    >*A** *is an informed search algorithm, or a best-first search, meaning that it is formulated in terms of weighted graphs: starting from a specific starting node of a graph, it aims to find a path to the given goal node having the smallest cost (least distance travelled, shortest time, etc.). It does this by maintaining a tree of paths originating at the start node and extending those paths one edge at a time until its termination criterion is satisfied.* 
    ~ [Wikipedia article](https://en.wikipedia.org/wiki/A*_search_algorithm)

    