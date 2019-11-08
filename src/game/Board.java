package game;

import piece.BoxDrive;
import piece.Piece;
import utils.Coordinate;
import utils.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to represent Box Shogi board
 */
public class Board {
    private static final int BOARD_SIZE = 5;
    private static final int UPPER_PROMOTION_ROW = 0;
    private static final int LOWER_PROMOTION_ROW = BOARD_SIZE-1;
    private static final Map<Player, Coordinate> driveLocations;
    private Piece[][] board;


    public Board() {
    	board = new Piece[BOARD_SIZE][BOARD_SIZE];
    	driveLocations = new HashMap<>();
    }

    public int getBoardSize(){
        return BOARD_SIZE;
    }

    public int getPromoteRow(Direction direction) {
        if (direction == Direction.UP) return UPPER_PROMOTION_ROW;
        else return LOWER_PROMOTION_ROW;
    }

    public boolean isOccupied(int row, int col) {
        return board[row][col] != null;
    }

    public Piece getPiece(int row, int col){return board[row][col];}

    /**
     * Goes to every single opponent piece and sees if theres a possible
     * move to get the drive.
     * @param currentPlayer : the player that is in check
     * @return true if currentPlayer is in check
     */
    public boolean isInCheck(Player currentPlayer){
        for(int row = 0; row < getBoardSize(); row++){
            for(int col = 0; col < getBoardSize(); col++){
                Piece piece = getPiece(row, col);
                if(isOccupied(row, col) && piece.getPlayer() == getOpponent(currentPlayer)){
                    Coordinate driveLoc = getDriveLocation(currentPlayer);
                    if(piece.isValidMove(row, col, driveLoc.getRow(), driveLoc.getCol(), this)){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     *
     * @param currentPlayer : Player who is in checkmate
     * @return true if current player is in checkmate
     */
    public boolean isCheckMate(Player currentPlayer){
        if(!isInCheck(currentPlayer))return false;
        ArrayList<Coordinate> strats = unCheckPossibilities(currentPlayer);
        return strats.isEmpty();
    }

    private ArrayList<Coordinate> unCheckPossibilities(Player currentPlayer){
        List<Coordinate> strats = new ArrayList<>();
        //Try every move of each piece the currentPlayer has
        for(int row = 0; row < getBoardSize(); row++){
            for(int col = 0; col < getBoardSize(); col++){
                Piece piece = getPiece(row, col);
                if(isOccupied(row, col) && piece.getPlayer() == currentPlayer){
                    for(Coordinate move : piece.validMoves(row, col, this)){
                        if(moveUncheck(row, col, move.getRow(), move.getCol(), currentPlayer)){
                            strats.add(move);
                        }
                    }
                }
            }
        }

        //Try every possible drop
        for(Piece piece : currentPlayer.getCapturedPieces()){
            for(int row = 0; row < getBoardSize(); row++){
                for(int col = 0; col < getBoardSize(); col++){
                    if(dropUncheck(piece, row, col, currentPlayer)){
                        strats.add(new Coordinate(row, col));
                    }
                }
            }
        }

    }


    private boolean moveUncheck(int startRow, int endRow, int startCol, int endCol, Player currentPlayer){
        Piece piece = getPiece(startRow, startCol);
        //only gets called when there's a piece so no need to check for null piece
        if(!piece.isValidMove(startRow, endRow, startCol, endCol, this))return false;

        Piece startPiece = getPiece(startRow, startCol);
        Piece endPiece = getPiece(endRow, endCol);

        //try move
        removePiece(endRow, endCol);
        removePiece(startRow, startCol);
        placePiece(startPiece, endRow, endCol);
        boolean isInCheck = isInCheck(currentPlayer);

        placePiece(startPiece, startRow, startCol);
        placePiece(endPiece, endRow, endCol);

        return !isInCheck;
    }

    private boolean dropUncheck(Piece piece, int row, int col, Player currentPlayer){
        if(!piece.isLegalDrop(row, col, this))return false;
        placePiece(piece, row, col);
        boolean isInCheck = isInCheck(currentPlayer);
        removePiece(row, col);
        return !isInCheck;
    }

    public void placePiece(Piece piece, int row, int col){
        if(piece instanceof BoxDrive) updateDriveLocations(piece, row, col);
        board[row][col] = piece;
    }

    public void removePiece(int row, int col){
        board[row][col] = null;
    }

    public void updateDriveLocations(Piece piece, int row, int col){
        Player player = piece.getPlayer();
        Coordinate location = driveLocations.containsKey(player) ? driveLocations.get(player) : new Coordinate();
        location.setCol(col);
        location.setRow(row);
        driveLocations.put(player, location);
    }

    public Player getOpponent(Player currentPlayer){
        for(Player player : driveLocations.keySet()){
            if(player != currentPlayer) return player;
        }
        return null;
    }

    public Coordinate getDriveLocation(Player currentPlayer ){
        return driveLocations.get(currentPlayer);
    }

    public Coordinate getOpponentDriveLocation(Player currentPlayer){
        return driveLocations.get(getOpponent(currentPlayer));
    }

    /* Print board */
    public String toString() {
        String[][] pieces = new String[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Piece curr = (Piece) board[col][row];
                pieces[col][row] = this.isOccupied(col, row) ? board[col][row].toString() : "";
            }
        }
        return stringifyBoard(pieces);
    }

    private String stringifyBoard(String[][] board) {
        String str = "";

        for (int row = board.length - 1; row >= 0; row--) {

            str += Integer.toString(row + 1) + " |";
            for (int col = 0; col < board[row].length; col++) {
                str += stringifySquare(board[col][row]);
            }
            str += System.getProperty("line.separator");
        }

        str += "    a  b  c  d  e" + System.getProperty("line.separator");

        return str;
    }

    private String stringifySquare(String sq) {
        switch (sq.length()) {
            case 0:
                return "__|";
            case 1:
                return " " + sq + "|";
            case 2:
                return sq + "|";
        }

        throw new IllegalArgumentException("Board must be an array of strings like \"\", \"P\", or \"+P\"");
    }
}

