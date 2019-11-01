import utils.Coordinate;
import utils.Direction;

import java.util.*;

public abstract class Piece {
    private char symbol;
    private Player owner;
    private boolean promoted;
    private Direction direction;

    public Piece(char symbol, Player owner){
        this.owner = owner;
        this.symbol = symbol;
        this.direction = owner.getDirection();
        this.promoted = false;
    }

    public void capture(Player p){
        owner = p;
        symbol = p.getSymbol(symbol);
        direction = p.getDirection();
        demote();
    }

    public Player getOwner(){
        return this.owner;
    }

    public char getSymbol(){
        return this.symbol;
    }

    public abstract void promote(int startRow, int endRow, Board board );

    public void demote(){
        promoted = false;
    }

    public boolean canPromote(int startRow, int endRow, Board board){
        if (promoted) return false;
        return (startRow == board.getPromoteRow(direction) || endRow == board.getPromoteRow(direction));
    }

    boolean isValidMove(int startRow, int endRow, int startCol, int endCol, Board board);

    boolean isLegalDrop(int row, int col, Board board);

    Set<Coordinate> validMoves(int start, int end, Board board);
}
