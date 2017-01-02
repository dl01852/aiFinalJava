import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by davle on 11/29/2016.
 */
public class aStar {

    public aStar(){}

    public void solve(Node start)
    {
        ArrayList<Node> allNodes = new ArrayList<>(); // hold all the nodes(Frontier)
        ArrayList<Node> childrenList = new ArrayList<>(); // hold all the children of the current node
        ArrayList<int[]> exploredNodes = new ArrayList<>(); // all the nodes that's been explored.
        int iterations = 0;
        Node current = start;
        allNodes.add(current);

        // A* star search.
        while(!allNodes.isEmpty())
        {
            iterations++;
            current = getLowestNode(allNodes); // grab the lowestNode(lowest F cost)
            if(isGoalState(current)) {
                 Node[] path = reconstructPath(current);
                 printPath(path);
                System.out.print( current.getG() + " optimal moves.");
                break;
            }
            else {
                exploredNodes.add(current.getBoard()); // add it to the explored set.
                childrenList.clear();
                childrenList.addAll(current.Children()); // generate it's children and put it in child's list.

                // iterate over children. If the child has been explored, move on. else put it in the allNodes(Frontier)
                for(int i = 0; i < childrenList.size(); i++)
                {
                    if(beenExplored(childrenList.get(i).getBoard(),exploredNodes))
                        continue;
                    else
                    {
                        allNodes.add(childrenList.get(i));
                    }
                }
            }
        }
    }

    public boolean isGoalState(Node n)
    {
        int[] currentBoard = n.getBoard();
        int[] goalBoard = n.getGoal_state();
        for(int i = 0; i < currentBoard.length; i++)
        {
            if(currentBoard[i] != goalBoard[i])
                return false;
        }
        return true;
    }
    public boolean beenExplored(int[] current_board, ArrayList<int[]> exploredNodes)
    {
        boolean output = false;
        for(int i = 0; i < exploredNodes.size(); i++)
        {
            for(int j = 0; j < current_board.length; j++)
            {
                // as long as the the values equal, keep it true.
                if(exploredNodes.get(i)[j] == current_board[j])
                    output = true;
                else
                {   // the minute one of the values is not equal, change to false and break out.
                    output = false;
                    break;
                }
            }
            // after checking one iteration, if output is true(meaning it's been explored then return true)
            if(output)
                return true;
        }

        return false;
    }
    public Node getLowestNode(ArrayList<Node> allNodes)
    {
        Node lowestNode = allNodes.get(0);
        int index = 0; // need this to remove after looping.

        for(int i = 0; i < allNodes.size(); i++)
        {
            if(allNodes.get(i).getF() < lowestNode.getF())
            {
                index = i;
                lowestNode = allNodes.get(i);
            }
        }
        allNodes.remove(index);
        return lowestNode;
    }

    public Node[] reconstructPath(Node endNode)
    {
        Node node = endNode;
        Node[] path = new Node[endNode.getG() + 1];
        int start = path.length - 1;
        for(int i = start; i >= 0; i--)
        {
            path[i] = node;
            node = node.getParent();
        }

        return path;
    }
    public void printPath(Node[] path)
    {
        for(int i = 0; i < path.length; i++)
        {
            System.out.println("=== Move " + i + "===");
            System.out.println(path[i].toString());
        }
    }


    // calculates the inversion of the start state.
    // part of determining if a puzzle is solvable.
    public int calculateInversions(int[] start, int[] goal)
    {
        int inversionCount = 0;
        for(int i = 0; i < goal.length; i ++)
        {
            int iIndex = findIndex(start,goal[i]);
            for(int j = i + 1; j < goal.length; j++)
            {
                int jIndex = findIndex(start,goal[j]);
                if(iIndex > jIndex)
                    inversionCount++;
            }
        }
        return inversionCount;
    }

    // calculates the manhantttan distance for '0' used to determine if a puzzle is solvable.
    public int ZeromanhattanDistance(int[] start, int[] goal)
    {
        int zeroStart = findIndex(start,0);
        int zeroGoal = findIndex(goal,0);

        int currentRow = zeroStart / 3;
        int currentCol = zeroStart % 3;
        int goalRow = zeroGoal / 3;
        int goalCol = zeroGoal % 3;
        int val1 = currentRow - goalRow;
        int val2 = currentCol - goalCol;
        return Math.abs(val1) + Math.abs(val2);
    }
    public boolean isSolvable(int[] start, int[] goal)
    {
        int zDistance = ZeromanhattanDistance(start, goal);
        int inversions = calculateInversions(start,goal);

        // if both even or both odd then solvable.
        if(((zDistance % 2 == 0) && inversions % 2 == 0) || (zDistance % 2 == 1) && (inversions % 2 == 1))
            return true;
        else
            return false;
    }
    public int findIndex(int[] array,int value)
    {
        int returnVal = -2;
        for(int i = 0; i < array.length; i++)
        {
            if(array[i] == value)
            {
                returnVal = i;
                break;
            }
        }
        return returnVal;
    }

}
