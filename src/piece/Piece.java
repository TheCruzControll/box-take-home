package piece;

import utils.Coordinate;
import utils.Direction;
import game.Player;
import game.Board;

import java.util.*;

public abstract class Piece {
    private char symbol;
    private Player player;
    protected boolean promoted;
    protected Direction direction;

    public Piece(char symbol, Player player){
        this.player = player;
        this.symbol = symbol;
        this.direction = player.getDirection();
        this.promoted = false;
    }

    public void capture(Player p){
        player = p;
        symbol = p.getSymbol(symbol);
        direction = p.getDirection();
        demote();
    }

    public Player getPlayer(){
        return this.player;
    }

    public char getSymbol(){
        return this.symbol;
    }

    public abstract boolean promote(int startRow, int endRow, Board board );

    protected abstract void demote();

    protected boolean canPromote(int startRow, int endRow, Board board){
        if (promoted) return false;
        return (startRow == board.getPromoteRow(direction) || endRow == board.getPromoteRow(direction));
    }

    public void promote(){
        promoted = true;
    }

    public boolean isValidMove(int startRow, int endRow, int startCol, int endCol, Board board){
        if(startRow == endRow && startCol == endCol)return false;
        Piece piece = board.getPiece(endRow, endCol);
        if(board.isOccupied(endRow,endCol) && piece.getPlayer() == player)return false;
        return isInMoveRange(startRow, endRow, startCol, endCol, board);
    }

    protected abstract boolean isInMoveRange(int startRow, int endRow, int startCol, int endCol, Board board);

    public abstract boolean isLegalDrop(int row, int col, Board board);

    public Set<Coordinate> validMoves(int start, int end, Board board){
        Set<Coordinate> moves = new HashSet<>();
        for(int endRow = 0; endRow < board.getBoardSize(); endRow++){
            for(int endCol = 0; endCol < board.getBoardSize(); endCol++){
                if(isValidMove(start, end, endRow, endCol, board)){
                    moves.add(new Coordinate(endRow, endCol));
                }
            }
        }
        return moves;
    }

    public String toString() {
        if (promoted) {
            return "+" + symbol;
        }
        return Character.toString(symbol);
    }
}
