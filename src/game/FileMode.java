package game;
import java.util.*;

import piece.*;
import utils.Coordinate;
import utils.Utils;
import utils.Utils.*;

public class FileMode extends Game {
    private GameHelper helper;

    public FileMode(TestCase testCase){
        super();
        this.helper = new FileHelper(this, testCase.moves.size());
        List<InitialPosition> init = testCase.initialPieces;
        List<String> upperCaptures = testCase.upperCaptures;
        List<String> lowerCaptures = testCase.lowerCaptures;
        for(InitialPosition ip : init){
            Piece piece = PieceFactory.makePiece(ip.piece, upper, lower);
            Coordinate coordinate = Utils.addressToIndex(ip.position, board);
            board.placePiece(piece, coordinate.getRow(), coordinate.getCol());
        }
        for(String symbol : upperCaptures){
            Piece piece = PieceFactory.makePiece(symbol, upper, lower);
            upper.addCapturedPiece(piece);
        }
        for(String symbol : lowerCaptures){
            if(symbol.equals(""))continue;
            Piece piece = PieceFactory.makePiece(symbol, upper, lower);
            lower.addCapturedPiece(piece);
        }
        nextTurn();
    }

    @Override
    public void nextTurn(){
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
    public boolean move(String from, String to, boolean promote){
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
    public boolean drop(char piece, String addr){
        if(gameOver)return false;
        int index = currentPlayer.getPieceIndex(piece);
        Piece currPiece = currentPlayer.getPiece(piece);
        boolean isLegal;
        if(currPiece == null){
            gameOver = true;
            isLegal = false;
        }else{
            isLegal = board.drop(currPiece, addr, currentPlayer);
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
        helper.dropMade(currentPlayer, currPiece, addr);
        getGameState();
        getResult(isLegal, isInCheck, strategies);
        nextTurn();
        return isLegal;
    }

    @Override
    public void getGameState(){
        helper.boardState(board.toString());
        helper.upperCaptured(upper);
        helper.lowerCaptured(lower);
    }

    @Override
    public void getResult(boolean isLegal, boolean isCheck, List<String> strategies) {
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
