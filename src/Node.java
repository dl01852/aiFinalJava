import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by davle on 11/28/2016.
 */
public class Node {

    private int f; // F(n)
    private int g; // path cost
    private int h;
    private Node parent;
    private int[] board;
    private int[] goal_state;

    public Node(int[] board, int[] goal_state, Node parent)
    {
        this.board = board;
        this.goal_state = goal_state;
        this.parent = parent;
        //hashGoal();
        //flattenGoal();

        if(parent == null) // if you're the start.
            this.g = 0; // path  cost is 0.
        else
            this.g = parent.g + 1;

        CalculateH1();
        setF();
    }


    // getters
    public int getF(){return this.f;}
    public int getG(){return this.g;}
    public int getH(){return this.h;}
    public int[] getBoard(){return this.board;}
    public int[] getGoal_state(){return this.goal_state;}
    public Node getParent(){return this.parent;}
    //setters

    private void setF(){
        f = getG() + getH();
    }

    // distance forumula without square root.
    private void CalculateH1()
    {
        int h1 = 0;

       for(int i = 0; i < goal_state.length; i++)
       {
           if(goal_state[i] == 0) // don't calculate for the blank spot or '0'
               continue;
           int goalPosition = i; // grab position of value in goal state and it's value.
           int goalValue = goal_state[i];
           int currentPosition = -1;
           for(int j = 0; j < board.length; j++)
           {
               if(board[j] == goalValue) { // search the board looking for the value(goal value).
                   currentPosition = j;
                   break;
               }

           }
           // given an x by x board in the form of a flat array(1D array),
           // we can emulate the 2D version of the board using 1 loop.
           // for any x by x board, dividing by x gives you the row and modulo x gives you the column.
           // below i'm grabbing the row/col for each the goalBoard and the currentBoard. Then i calculate the distance between the 2.
           int currentRow = currentPosition / 3;
           int currentCol = currentPosition % 3;
           int goalRow = goalPosition / 3;
           int goalCol = goalPosition % 3;
           int val1 = currentRow - goalRow;
           int val2 = currentCol - goalCol;
           h1 += Math.pow(val1,2) + Math.pow(val2,2);
       }
       h = h1;
    }

    public ArrayList<Node> Children()
    {
        int zero_index = 0; // value to hold the index of where the 0 is located.
        int zero_swap; // index to swap with the zero.
        int zero_holder; // temp value to do swapping.
        ArrayList<Node> children = new ArrayList<>();
        for(int i = 0; i < board.length; i++)
        {
            if(board[i] == 0)
            {
                zero_index = i;
                break;
            }
        }

        // i can move up.
        if(zero_index > 2)
        {
            zero_swap = zero_index - 3;
            int[] swappedArray = new int[board.length];
            System.arraycopy(board,0,swappedArray,0,board.length);


            // basic value swapping with temp variables etc...
            zero_holder = swappedArray[zero_index];
            swappedArray[zero_index] = swappedArray[zero_swap];
            swappedArray[zero_swap] = zero_holder;

            // create new node and add it to children list.
            Node tempNode = new Node(swappedArray,this.goal_state,this);
            children.add(tempNode);
        }
        // I can move down!
        if(zero_index < 6)
        {
            zero_swap = zero_index + 3;
            int[] swappedArray = new int[board.length];
            System.arraycopy(board,0,swappedArray,0,board.length);


            // basic value swapping with temp variables...
            zero_holder = swappedArray[zero_index];
            swappedArray[zero_index] = swappedArray[zero_swap];
            swappedArray[zero_swap] = zero_holder;

            Node tempNode = new Node(swappedArray,this.goal_state,this);
            children.add(tempNode);
        }

        // I can move LEFT
        if(zero_index % 3 > 0)
        {
            zero_swap = zero_index - 1;
            int[] swappedArray = new int[board.length];
            System.arraycopy(board,0,swappedArray,0,board.length);

            // basic value swapping with temp variables...
            zero_holder = swappedArray[zero_index];
            swappedArray[zero_index] = swappedArray[zero_swap];
            swappedArray[zero_swap] = zero_holder;

            Node tempNode = new Node(swappedArray,this.goal_state,this);
            children.add(tempNode);

        }

        // i can move RIGHT
        if(zero_index % 3 < 2)
        {
            zero_swap = zero_index + 1;
            int[] swappedArray = new int[board.length];
            System.arraycopy(board,0,swappedArray,0,board.length);

            // basic value swapping with temp variables..
            zero_holder = swappedArray[zero_index];
            swappedArray[zero_index] = swappedArray[zero_swap];
            swappedArray[zero_swap] = zero_holder;

            Node tempNode = new Node(swappedArray,this.goal_state,this);
            children.add(tempNode);
        }

        return children;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        //sb.append(String.format("G:%d Heuristic:%d F:%d\n",this.getG(),this.getH(),this.getF()));
        for(int i = 0; i < board.length; i++)
        {
            if(i % 3 == 2) { // it's the end of the row.
                sb.append(board[i]);
                sb.append('\n');
            }
            else {
                sb.append(board[i]);
                sb.append(' ');
            }
        }
        return sb.toString();
    }
}
