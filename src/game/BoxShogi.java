package game;
import utils.Utils;
import utils.Utils.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class BoxShogi {
    public static void runFileMode(String file) throws Exception{
        TestCase testCase = Utils.parseTestCase(file);
        Game game = new FileMode(testCase);
        List<String> moves = testCase.moves;
        while(!game.isGameOver() && !moves.isEmpty()){
            String[] move = moves.remove(0).split(" ");
            if(move[0].equals("move")){
                game.move(move[1], move[2], move.length == 4);
            }else{
                game.drop(move[1].charAt(0), move[2]);
            }
        }
    }
    public static void runInteractiveMode()throws Exception{
        Game game = new InteractiveMode(new InteractiveHelper());
        Scanner userInput = new Scanner(System.in);
        while(!game.isGameOver()){
            String[] move = userInput.nextLine().split(" ");
            if(move[0].equals("move")){
                game.move(move[1], move[2], move.length == 4);
            }else{
                game.drop(move[1].charAt(0), move[2]);
            }
        }
        userInput.close();
    }

    public static void main(String[] args) throws Exception{
        if(args[0].equals( "-i")){
            runInteractiveMode();
        }else if(args[0].equals("-f")){
            runFileMode("testcases/"+(args[1]));
        }else{
            System.out.println(args[0]);
            System.out.println("illegal argument");
        }
    }
}
