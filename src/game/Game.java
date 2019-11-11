package game;
import piece.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Skeleton of a BoxShogi game.
 */
public abstract class Game {
    protected static final int moveLimit = 400;
    protected int numTurns;
    protected boolean gameOver;
    protected Queue<Player> playerQueue;
    protected Player currentPlayer;
    protected Player upper;
    protected Player lower;
    protected Board board;

    public Game(){
        this.numTurns = 0;
        this.gameOver = false;
        playerQueue = new LinkedList<>();
        this.upper = new Player(true);
        this.lower = new Player(false);
        playerQueue.add(lower);
        playerQueue.add(upper);
        this.board = new Board();
    }

    abstract void nextTurn();

    /**
     * Move piece from "from" to "to"
     * @param from : start coordinate
     * @param to : end coordinate
     * @param promote : true if needed to be promoted
     * @return true if move is valid, false if else
     */
    abstract boolean move(String from, String to, boolean promote);

    /**
     * Drop piece at location
     * @param piece : piece to be dropped
     * @param addr : location to be dropped to
     * @return true if drop is valid, false if else
     */
    abstract boolean drop(char piece, String addr);

    /**
     * Outputs board state, upper captures, and lower captures
     */
    abstract void getGameState();

    /**
     * Checks and outputs if player is in check/checkmate or made an illegal move
     * @param isLegal : true if move is legal
     * @param isCheck : true if current player is in check
     * @param strategies : list of strategies to escape check
     */
    abstract void getResult(boolean isLegal, boolean isCheck, List<String> strategies);

    public boolean isGameOver(){
        return gameOver;
    }

    public Player getOpponent(){
        return playerQueue.peek();
    }
}
