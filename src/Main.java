import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by davle on 11/28/2016.
 */
public class Main {

    static int[] startState;
    static int[] goal_State;
    public static void main(String args[])
    {
        if(args.length > 0) {
            aStar solver = new aStar();
            List<Integer> numbers = new ArrayList<>();
            try {
                for (String line : Files.readAllLines(Paths.get(args[0]))) {
                    for (String part : line.split(" ")) {
                        if (part.equals(""))
                            continue;
                        Integer i = Integer.valueOf(part);
                        numbers.add(i);
                    }
                }
                startState = new int[numbers.size() / 2]; // half the numbers are the start and the other is the goal.
                goal_State = new int[numbers.size() / 2];
                filterNumbers(numbers);
                Node startNode = new Node(startState, goal_State, null);
                if (solver.isSolvable(startState, goal_State))
                    solver.solve(startNode);
                else
                    System.out.println("Not solveable!");

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else
        {
            System.out.println("specify a txt file to be read.");
        }
    }

    public static void filterNumbers(List<Integer> allValues)
    {
        int j = 0; // used as counter to populate goalState
        for(int i = 0; i < allValues.size(); i++)
        {
            if(i < 9) // end of the start State.
            {
                startState[i] = allValues.get(i);
            }
            else
            {
                goal_State[j] = allValues.get(i);
                j++;
//                if(i >= 9 && i < 12)
//                    goal_State[0][i % 3] = allValues.get(i);
//                else if(i >=12 && i < 15)
//                    goal_State[1][i % 3] = allValues.get(i);
//                else if(i >=15)
//                    goal_State[2][i % 3] = allValues.get(i);
            }
        }
    }

    
}
