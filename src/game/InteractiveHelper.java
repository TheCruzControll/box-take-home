package game;

import piece.Piece;
import utils.Coordinate;
import utils.Utils;

import java.util.Comparator;
import java.util.List;

/**
 * Helper class that outputs all information out to the user.
 */
public class InteractiveHelper implements GameHelper{

    @Override
    public void boardState(String board) {
        System.out.println(board);
    }

    @Override
    public void upperCaptured(Player player) {
        System.out.print("Captures UPPER: ");
        StringBuilder pieces = new StringBuilder();

        for(Piece piece : player.getCapturedPieces()){
            pieces.append(" " + piece.toString());
        }
        System.out.println(pieces.toString().trim());
    }

    @Override
    public void lowerCaptured(Player player) {
        System.out.print("Captures lower: ");
        StringBuilder pieces = new StringBuilder();
        for(Piece piece : player.getCapturedPieces()){
            pieces.append(" " + piece.toString());
        }
        System.out.println(pieces.toString().trim());
    }

    @Override
    public void moveMade(Player player, String from, String to, boolean promote) {
        System.out.print(player.getName() + " player action: move " + from + " " + to);
        if(promote){
            System.out.println(" promote");
        }else{
            System.out.println();
        }
    }

    @Override
    public void dropMade(Player player, Piece piece, String address) {
        System.out.println(player.getName() + " player action: drop " + piece.toString() + " " + address);

    }

    @Override
    public void inCheck(Player player, List<String> strats) {
        System.out.println(player.getName() + " player is in check!");
        System.out.println("Available moves: ");
        strats.sort(String::compareToIgnoreCase);
        for(String strat : strats){
            System.out.println(strat);
        }
    }

    @Override
    public void checkMate(Player player) {
        System.out.println(player + " player wins. Checkmate");
    }

    @Override
    public void tie() {
        System.out.println("Tie game. Too many moves.");
    }

    @Override
    public void getTurn(Player player){
        System.out.print(player.getName() + "> ");
    }

    @Override
    public void illegalMove(Player player){
        System.out.println(player.getName() + " player wins. Illegal Move");
    }
}
