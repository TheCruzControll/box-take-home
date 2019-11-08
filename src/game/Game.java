package game;
import piece.*;

public class Game {
    private int numTurns;
    private boolean gameOver;
    //false for lower, true for UPPER
    private boolean currentPlayer;
    private Player upper;
    private Player lower;
    private Board board;

    public void newGame(){
        this.numTurns = 0;
        this.gameOver = false;
        this.currentPlayer = false;
        this.upper = new Player(true);
        this.lower = new Player(false);
        this.board = new Board();
        board.placePiece(new BoxNotes(upper), 0,0);
        board.placePiece(new BoxGovernance(upper), 0, 1);
        board.placePiece(new BoxRelay(upper), 0, 2);
        board.placePiece(new BoxShield(upper), 0, 3);
        board.placePiece(new BoxDrive(upper), 0, 4);
        board.placePiece(new BoxPreview(upper), 1, 4);

        board.placePiece(new BoxNotes(lower), 4,4);
        board.placePiece(new BoxGovernance(lower), 4, 3);
        board.placePiece(new BoxRelay(lower), 4, 2);
        board.placePiece(new BoxShield(lower), 4, 1);
        board.placePiece(new BoxDrive(lower), 4, 0);
        board.placePiece(new BoxPreview(lower), 3, 0);


    }
}
