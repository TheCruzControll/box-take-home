package game;

import piece.Piece;

import java.util.List;

public class FileHelper implements GameHelper {
    private int moves;
    private int currentMove;
    private Game game;

    FileHelper(Game game, int moves){
        this.moves = moves;
        this.game = game;
    }

    @Override
    public void boardState(String board) {
        if (currentMove == moves || game.isGameOver()) {
            System.out.println(board);
        }
    }

    @Override
    public void upperCaptured(Player player) {
        if (currentMove == moves || game.isGameOver()) {
            System.out.print("Captures UPPER: ");
            StringBuilder pieces = new StringBuilder();

            for (Piece piece : player.getCapturedPieces()) {
                pieces.append(" " + piece.toString());
            }
            System.out.println(pieces.toString().trim());
        }
    }

    @Override
    public void lowerCaptured(Player player) {
        if (currentMove == moves || game.isGameOver()) {
            System.out.print("Captures lower: ");
            StringBuilder pieces = new StringBuilder();
            for (Piece piece : player.getCapturedPieces()) {
                pieces.append(" " + piece.toString());
            }
            System.out.println(pieces.toString().trim());
            System.out.println();
        }
    }

    @Override
    public void moveMade(Player player, String from, String to, boolean promote) {
        if (currentMove == moves || game.isGameOver()) {
            System.out.print(player.getName() + " player action: move " + from + " " + to);
            if (promote) {
                System.out.println(" promote");
            } else {
                System.out.println();
            }
        }
    }

    @Override
    public void dropMade(Player player, String piece, String address) {
        if (currentMove == moves || game.isGameOver()) {
            System.out.println(player.getName() + " player action: drop " + piece.toLowerCase() + " " + address);
        }
    }


    public void dropMadeFail(Player player, char piece, String address){
        System.out.println(player.getName() + " player action: drop " + Character.toLowerCase(piece) + " " + address);
    }

    @Override
    public void inCheck(Player player, List<String> strats) {
        if (currentMove == moves || game.isGameOver()) {
            System.out.println(player.getName() + " player is in check!");
            System.out.println("Available moves: ");
            strats.sort(String::compareToIgnoreCase);
            for (String strat : strats) {
                System.out.println(strat);
            }
        }
    }

    @Override
    public void checkMate(Player player) {
        System.out.println(player.getName() + " player wins.  Checkmate.");
    }

    @Override
    public void tie() {
        System.out.println("Tie game.  Too many moves.");
    }

    @Override
    public void getTurn(Player player){
        currentMove++;
        if (currentMove == moves+1 && !game.isGameOver()) {
            System.out.println(player.getName() + "> ");
        }
    }

    @Override
    public void illegalMove(Player player){
        System.out.println(player.getName() + " player wins.  Illegal move.");
    }

}
