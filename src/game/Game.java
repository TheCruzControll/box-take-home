package game;
import piece.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class Game {
    protected static final int moveLimit = 200;
    protected int numTurns;
    protected boolean gameOver;
    //false for lower, true for UPPER
    protected Queue<Player> playerQueue;
    protected Player currentPlayer;
    protected Player upper;
    protected Player lower;
    protected Board board;
//    private GameHelper helper;

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

//    public Game(GameHelper helper){
//        this.helper = helper;
//        newGame();
//    }

    abstract void nextTurn();

    abstract boolean move(String from, String to, boolean promote);

    abstract boolean drop(char piece, String addr);

    abstract void getGameState();

    abstract void getResult(boolean isLegal, boolean isCheck, List<String> strategies);

    public boolean isGameOver(){
        return gameOver;
    }
}
