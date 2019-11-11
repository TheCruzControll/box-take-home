package game;

import piece.*;

import java.util.ArrayList;
import java.util.List;

public class InteractiveMode extends Game {
    private GameHelper helper;

    public InteractiveMode(GameHelper helper){
        super();
        this.helper = helper;
        newGame();
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

        getGameState();
        nextTurn();
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

    @Override
    boolean move(String from, String to, boolean promote) {
        if(gameOver) return false;
        List<String> strategies = new ArrayList<>();
        boolean isLegalMove = board.move(from, to, promote, currentPlayer);
        boolean isInCheck = false;
        Player opponent = board.getOpponent(currentPlayer);
        if(isLegalMove){
            if(board.isInCheck(opponent)){
                isInCheck = true;
                strategies = board.unCheckPossibilities(opponent);
                if(strategies.size() == 0){
                    gameOver = true;
                }
            }
        }else{
            gameOver = true;
        }

        helper.moveMade(currentPlayer, from, to, promote);
        getGameState();
        getResult(isLegalMove, isInCheck, strategies);
        nextTurn();
        return isLegalMove;
    }

    @Override
    boolean drop(char piece, String addr) {
        if(gameOver)return false;
        int index = currentPlayer.getPieceIndex(piece);
        Piece currPiece = currentPlayer.getPiece(piece);
        boolean isLegal;
        if(currPiece == null){
            gameOver = true;
            isLegal = false;
        }else{
            isLegal = board.drop(currPiece, addr);
        }
        if(!isLegal && currPiece != null){
            currentPlayer.addCapturedPiece(currPiece, index);
        }

        List<String> strategies = new ArrayList<>();
        boolean isInCheck = false;
        Player opponent = board.getOpponent(currentPlayer);
        if(isLegal){
            if(board.isInCheck(opponent)){
                isInCheck = true;
                strategies = board.unCheckPossibilities(opponent);
                if(strategies.size() == 0){
                    gameOver = true;
                }
            }
        }else{
            gameOver = true;
        }
        helper.dropMade(currentPlayer, currPiece.toString(), addr);
        getGameState();
        getResult(isLegal, isInCheck, strategies);
        nextTurn();
        return isLegal;
    }

    @Override
    void getGameState() {
        helper.boardState(board.toString());
        helper.upperCaptured(upper);
        helper.lowerCaptured(lower);
    }

    @Override
    void getResult(boolean isLegal, boolean isCheck, List<String> strategies) {
        Player opponent = getOpponent();
        if(isLegal){
            if(isCheck && !gameOver){
                helper.inCheck(opponent, strategies);
            }else if(isCheck && gameOver){
                helper.checkMate(currentPlayer);
            }
        }else{
            helper.illegalMove(opponent);
        }
    }
}
