package game;

import piece.Piece;
import utils.Coordinate;

import java.util.List;

/**
 * Series of print statements that each game
 * mode should contain. Communicates game state to the Player but does not keep track of state.
 */
public interface GameHelper {
    void boardState(String board);

    void upperCaptured(Player player);

    void lowerCaptured(Player player);

    void moveMade(Player player, String from, String to, boolean promote);

    void dropMade(Player player, Piece piece, String address);

    void inCheck(Player player, List<String> strats);

    void checkMate(Player player);

    void tie();

    void getTurn(Player player);

    void illegalMove(Player player);
}
