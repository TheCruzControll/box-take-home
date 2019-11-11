package game;

import piece.BoxDrive;
import piece.BoxPreview;
import piece.Piece;
import utils.Coordinate;
import utils.Direction;
import utils.Utils;

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


    private final Map<Player, Coordinate> driveLocations;
    private Piece[][] board;


    public Board() {
    	this.board = new Piece[BOARD_SIZE][BOARD_SIZE];
    	this.driveLocations = new HashMap<>();
    }

    /**
     * Get board size
     * @return board size
     */
    public int getBoardSize(){
        return BOARD_SIZE;
    }

    /**
     *
     * @param direction : UP or DOWN
     * @return index of promotion row based on direction
     */
    public int getPromoteRow(Direction direction) {
        if (direction == Direction.UP) return UPPER_PROMOTION_ROW;
        else return LOWER_PROMOTION_ROW;
    }

    public boolean isOccupied(int row, int col) {
        return board[row][col] != null;
    }

    public Piece getPiece(int row, int col){return board[row][col];}

    /**
     * Goes to every single opponent piece and sees if there's a possible
     * move to capture the drive.
     * @param currentPlayer : the player that is in check
     * @return true if currentPlayer is in check
     */
    public boolean isInCheck(Player currentPlayer){
        for(int row = 0; row < getBoardSize(); row++){
            for(int col = 0; col < getBoardSize(); col++){
                Piece piece = getPiece(row, col);
                if(isOccupied(row, col) && piece.getPlayer() == getOpponent(currentPlayer)){
                    Coordinate driveLoc = getDriveLocation(currentPlayer);
                    if(piece.isValidMove(row, driveLoc.getRow(), col, driveLoc.getCol(), this)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check to see if the current player is in checkmate
     * @param currentPlayer : Player who is in checkmate
     * @return true if current player is in checkmate
     */
    public boolean isCheckMate(Player currentPlayer){
        if(!isInCheck(currentPlayer))return false;
        ArrayList<String> strats = unCheckPossibilities(currentPlayer);
        return strats.isEmpty();
    }

    /**
     * Goes through every possible move and drop the current player has
     * to uncheck itself
     * @param currentPlayer : player that is trying to uncheck
     * @return ArrayList of possible moves in string format
     */
    public ArrayList<String> unCheckPossibilities(Player currentPlayer){
        ArrayList<String> strategies = new ArrayList<>();

        //Try every move of each piece the currentPlayer has
        for(int row = 0; row < getBoardSize(); row++){
            for(int col = 0; col < getBoardSize(); col++){
                Piece piece = getPiece(row, col);
                if(isOccupied(row, col) && piece.getPlayer() == currentPlayer){
                    for(Coordinate move : piece.validMoves(row, col, this)){
                        if(moveUncheck(row, move.getRow(), col, move.getCol(), currentPlayer)){
                            strategies.add(Utils.moveToString(row, move.getRow(), col, move.getCol(), this));
                        }
                    }
                }
            }
        }

        //Try every possible drop
        List<Piece> captured = new ArrayList<>(currentPlayer.getCapturedPieces());
        for(Piece piece : captured){
            for(int row = 0; row < getBoardSize(); row++){
                for(int col = 0; col < getBoardSize(); col++){
                    boolean uncheck = dropUncheck(piece, row, col, currentPlayer);
                    if(uncheck){
                        strategies.add(Utils.dropToString(piece, row, col, this));
                    }
                }
            }
        }
        return strategies;
    }


    /**
     * Checks if it is possible to uncheck the current player with the move specified.
     * @param startRow : start row index for move
     * @param endRow : end row index for move
     * @param startCol : start column index for move
     * @param endCol : end column index for move
     * @param currentPlayer : player that wants to uncheck
     * @return true if it is possible to uncheck the current player with the move specified.
     */
    private boolean moveUncheck(int startRow, int endRow, int startCol, int endCol, Player currentPlayer){
        //only gets called when there's a piece so no need to check for null piece
        if(!isValidMove(startRow, endRow, startCol, endCol, false, currentPlayer))return false;

        Piece startPiece = getPiece(startRow, startCol);
        Piece endPiece = getPiece(endRow, endCol);

        //try move
        removePiece(endRow, endCol);
        removePiece(startRow, startCol);
        placePiece(startPiece, endRow, endCol);
        boolean isInCheck = isInCheck(currentPlayer);

        //undo move
        placePiece(startPiece, startRow, startCol);
        placePiece(endPiece, endRow, endCol);

        return !isInCheck;
    }

    /**
     *
     * @param piece : piece to drop
     * @param row : row to drop to
     * @param col : column to drop to
     * @param currentPlayer : player that wants to drop
     * @return : True if it is possible to drop at a specific coordinate
     */
    private boolean dropUncheck(Piece piece, int row, int col, Player currentPlayer){
        //can't drop on occupied spot
        if(isOccupied(row,col))return false;
        if(!piece.isLegalDrop(row, col, this))return false;

        //try the drop to see if it results in a check
        placePiece(piece, row, col);
        boolean isInCheck = isInCheck(currentPlayer);
        removePiece(row, col);
        return !isInCheck;
    }

    /**
     * Place a piece without check.
     * @param piece : piece to be placed
     * @param row : row to be placed in
     * @param col : col to be placed in
     */
    public void placePiece(Piece piece, int row, int col){
        if(piece instanceof BoxDrive) updateDriveLocations(piece, row, col);
        board[row][col] = piece;
    }

    /**
     * Moves the piece as long as it is not an illegal move.
     * @param from : start coordinate of move in string form
     * @param to : end coordinate of move in string form
     * @param promote : true if current piece is promoted
     * @param currentPlayer : player moving the piece
     * @return true : if a legal move
     */
    public boolean move(String from, String to, boolean promote, Player currentPlayer){
        //If any of the addresses dont match the address pattern return false
        String addressPattern = "[a-e][1-5]";
        if(!from.matches(addressPattern) || !to.matches(addressPattern))return false;
        Coordinate fromCoordinate = Utils.addressToIndex(from, this);
        Coordinate toCoordinate = Utils.addressToIndex(to, this);
        int startRow = fromCoordinate.getRow();
        int startCol = fromCoordinate.getCol();
        int endRow = toCoordinate.getRow();
        int endCol = toCoordinate.getCol();

        //If it's not a valid move return false
        if(!isValidMove(startRow, endRow, startCol, endCol, promote, currentPlayer))return false;
        Piece piece = getPiece(startRow, startCol);
        Piece endPiece = getPiece(endRow, endCol);

        //make move
        removePiece(startRow,startCol);
        placePiece(piece,endRow,endCol);

        //always try to promote if its a box preview
        if (piece instanceof BoxPreview) piece.promote(startRow, endRow, this);

        //if the move puts current player in check. It is an illegal move
        if(isInCheck(currentPlayer)){
            removePiece(endRow,endCol);
            placePiece(piece, startRow, startCol);
            return false;
        }

        //If there was a piece at the end address. Add it to captured pieces.
        if(endPiece != null){
            endPiece.capture(currentPlayer);
            currentPlayer.addCapturedPiece(endPiece);
        }
        return true;
    }

    /**
     * Drops the specified piece in the appropriate coordinate
     * @param piece : piece to drop
     * @param to : coordinate to drop to in string format
     * @return true if valid drop
     */
    public boolean drop(Piece piece, String to){
        String addressPattern = "[a-e][1-5]";

        if(!to.matches(addressPattern))return false;
        Coordinate toCoordinate = Utils.addressToIndex(to, this);
        int row = toCoordinate.getRow();
        int col = toCoordinate.getCol();

        //can't drop on an occupied space
        if(getPiece(row, col) != null)return false;
        if(!piece.isLegalDrop(row, col, this))return false;
        placePiece(piece, row, col);
        return true;
    }

    /**
     * helper method to check if a move is valid without knowing
     * if there is a piece or not
     * @param startRow : start row of move
     * @param endRow : end row of move
     * @param startCol : start column of move
     * @param endCol : end column of move
     * @param promote : true if moving piece is asked to be promoted
     * @param currentPlayer : current player that wants to be promoted
     * @return true if valid move
     */
    private boolean isValidMove(int startRow, int endRow, int startCol, int endCol, boolean promote, Player currentPlayer){
        Piece piece = getPiece(startRow, startCol);
        if(piece == null || piece.getPlayer() != currentPlayer || !piece.isValidMove(startRow , endRow, startCol, endCol, this))return false;
        if(promote){
            if(!piece.promote(startRow, endRow, this))return false;
        }

        Piece endPiece = getPiece(endRow, endCol);
        if(endPiece != null && endPiece.getPlayer() == currentPlayer)return false;
        return true;
    }

    public void removePiece(int row, int col){
        board[row][col] = null;
    }

    /**
     * updates location of BoxDrive piece to specified row and col
     * @param piece : box drive
     * @param row : destination row
     * @param col : destination column
     */
    private void updateDriveLocations(Piece piece, int row, int col){
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

        for (int row = 0; row < board.length; row++) {

            str += Integer.toString(board.length - row ) + " |";
            for (int col = 0; col < board[row].length; col++) {
                str += stringifySquare(board[row][col]);
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

