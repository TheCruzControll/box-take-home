package game;

import piece.Piece;
import utils.Coordinate;

import java.util.List;

/**
 * Series of print statements that each game
 * mode should contain. Communicates game state to the Player but does not keep track of state.
 */
public interface GameHelper {
    /**
     * Broadcasts board state
     * @param board : string representation of the board
     */
    void boardState(String board);

    /**
     * Outputs all captured pieces of upper player
     * @param player : upper player
     */
    void upperCaptured(Player player);

    /**
     * Outputs all captures pieces of lower player
     * @param player
     */
    void lowerCaptured(Player player);

    /**
     * Ouputs the move made
     * @param player : player making the move
     * @param from : starting address
     * @param to : ending address
     * @param promote : true if wants to promote
     */
    void moveMade(Player player, String from, String to, boolean promote);

    /**
     * Outputs the drop made
     * @param player : player making the move
     * @param piece : piece to be dropped
     * @param address : address to be dropped to
     */
    void dropMade(Player player, String piece, String address);

    /**
     * Outputs if check happens
     * @param player : player in check
     * @param strategies : list of strategies to uncheck
     */
    void inCheck(Player player, List<String> strategies);

    /**
     * Outputs if Checkmate
     * @param player : Player who checkmates
     */
    void checkMate(Player player);

    /**
     * Outputs tie
     */
    void tie();

    /**
     * Outputs turn of current player
     * @param player
     */
    void getTurn(Player player);

    /**
     * Outputs string for illegal move
     * @param player
     */
    void illegalMove(Player player);
}
