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
        this.symbol = player.getDirection() == Direction.DOWN ? Character.toUpperCase(symbol) : Character.toLowerCase(symbol);
        this.direction = player.getDirection();
        this.promoted = false;
    }

    /**
     * Capture current piece
     * @param player : Player who captures the piece
     */
    public void capture(Player player){
        this.player = player;
        symbol = player.getSymbol(symbol);
        direction = player.getDirection();
        demote();
    }

    public Player getPlayer(){
        return this.player;
    }

    public char getSymbol(){
        return this.symbol;
    }

    /**
     * Promote depending on start and end row
     * @param startRow : start row of move
     * @param endRow : end row of move
     * @param board : board of the game
     * @return true if current piece can be promoted with this move
     */
    public abstract boolean promote(int startRow, int endRow, Board board );


    protected abstract void demote();

    /**
     * helper method for promote. Checks to see if end point
     * or starting point is in the correct promotion zone
     * @param startRow : start row of move
     * @param endRow : end row of move
     * @param board : current board
     * @return true if piece can promote
     */
    protected boolean canPromote(int startRow, int endRow, Board board){
        if (promoted) return false;
        return (startRow == board.getPromoteRow(direction) || endRow == board.getPromoteRow(direction));
    }

    /**
     * Promote without check
     */
    protected void promote(){
        promoted = true;
    }

    /**
     * Check to see if the current move is valid with the current piece
     * @param startRow : start row of move
     * @param endRow : end row of move
     * @param startCol : start column of move
     * @param endCol : end column of move
     * @param board : current board
     * @return true if move is valid
     */
    public boolean isValidMove(int startRow, int endRow, int startCol, int endCol, Board board){
        //If you don't move. Move is invalid
        if(startRow == endRow && startCol == endCol)return false;

        Piece piece = board.getPiece(endRow, endCol);

        //If the endpoint of the move is occupied by one of your pieces. Move is invalid
        if(board.isOccupied(endRow,endCol) && piece.getPlayer() == player)return false;
        return isInMoveRange(startRow, endRow, startCol, endCol, board);
    }

    /**
     * Checks if the move that is input is within the range of the current piece
     * @param startRow : start row of move
     * @param endRow : end row of move
     * @param startCol : start column of move
     * @param endCol : end column of move
     * @param board : current board
     * @return true if move is in move range
     */
    protected abstract boolean isInMoveRange(int startRow, int endRow, int startCol, int endCol, Board board);

    /**
     * Checks if drop is legal
     * @param row : row to be dropped
     * @param col : column to be dropped
     * @param board : current board
     * @return true if valid drop
     */
    public abstract boolean isLegalDrop(int row, int col, Board board);

    /**
     * Creates a set of all possible moves from an initial position
     * @param startRow : start row of move
     * @param startCol : start column of move
     * @param board : current board
     * @return a set of all possible moves from the initial position
     */
    public Set<Coordinate> validMoves(int startRow, int startCol, Board board){
        Set<Coordinate> moves = new HashSet<>();
        for(int endRow = 0; endRow < board.getBoardSize(); endRow++){
            for(int endCol = 0; endCol < board.getBoardSize(); endCol++){
                if(isValidMove(startRow, endRow, startCol, endCol, board)){
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
