package game;

import piece.Piece;

/**
 * Series of print statements that each game
 * mode should contain. Communicates game state to the Player.
 */
public interface GameMode {
    void boardState(String board);

    void upperCaptured(Player player);

    void lowerCaptured(Player player);

    void moveMade(Player player, String from, String to, boolean promote);

    void dropMade(Player player, Piece piece, String address);

    void inCheck(Player player);
}
