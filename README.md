## Objective
Solve the 8 puzzle problem in under 5 seconds using the A* search algorithm. 
## Language Used
Java 7

## Implementation
The project contains 3 classes: Node, Astar, and Main.
The Node class contains all the necessary information about a particular node.
It has the F cost which is the addition of the G cost(amount of steps to get to the current node) 
and the H cost(Heuristic function). It knows what the goal state is and the current state.
It has a parent Node to know where it came from (1 direction Linked list) and functionality to 
get all of the possible moves(children) from it's current position.

Astar class is contains the implementation of the A* algorithm as well as figuring out 
if a given input is solvable(being able to reach the goal state)

Main class is responsible for reading in the input and solving the probelm, if solvable.

## A star
A* algorithm is a pathfinding and graph traversal algorithm. It's goal is to find the optimal or most effecient path to 
the goal state. A* orders a priority queue based on the F(n) which is the combination of the G(n), path cost, and the H(n), Heuristic cost.
The Heuristic function that i defined is the combination of the Manhattan Distance plus Linear conflict.

## Inputs outputs
Sample input is as follows:

```
7 2 4
5 0 6
8 3 1

1 2 3
4 5 6
7 8 0
```

First set is the start state and second set is the goal state.

## Compiling
```
javac Main.java
java Main *Path to text file*
