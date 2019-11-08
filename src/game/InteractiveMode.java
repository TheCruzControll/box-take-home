package game;

import piece.*;

public class InteractiveMode extends Game {
    private GameHelper helper;

    public InteractiveMode(GameHelper helper){
        super();
        this.helper = helper;
    }

    @Override
    void nextTurn() {
        numTurns++;
        if(numTurns > moveLimit && !gameOver){
            gameOver = true;
            helper.tie();
            return;
        }
        if(gameOver)return;
        currentPlayer = playerQueue.poll();
        playerQueue.add(currentPlayer);
        helper.getTurn(currentPlayer);
    }

    public void newGame() {
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
        helper.boardState(board.toString());

    }
}
