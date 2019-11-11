package game;

import piece.Piece;
import utils.Direction;
import java.util.*;

/**
 * Class to represent a player in a game of BoxShogi
 */
public class Player {
    private String name;
    private final boolean isUpper;
    private final List<Piece> capturedPieces;
    private final Direction direction;


    public Player(boolean isUpper){
        this.isUpper = isUpper;
        this.capturedPieces = new LinkedList<Piece>();
        if(isUpper){
            this.direction = Direction.DOWN;
            this.name = "UPPER";
        }else{
            this.direction = Direction.UP;
            this.name = "lower";
        }
    }

    /**
     * Get the symbol of a piece depending on the player
     * @param originalSymbol : piece
     * @return correct symbol based on the player
     */
    public char getSymbol(char originalSymbol) {
        if (isUpper) return Character.toUpperCase(originalSymbol);
        else return Character.toLowerCase(originalSymbol);
    }

    /**
     * Add piece to list of captures pieces
     * @param piece : piece to drop
     */
    public void addCapturedPiece(Piece piece){
        capturedPieces.add(piece);
    }

    /**
     * Add captured piece to a certain index.
     * Used when checking for valid drop
     * @param piece : piece to drop
     * @param index : index in captured pieces array
     */
    public void addCapturedPiece(Piece piece, int index){
        if (index < capturedPieces.size()) {
            capturedPieces.add(index, piece);
        }
        else {
            capturedPieces.add(piece);
        }
    }

    /**
     * Get index of captured piece
     * @param c : character of wanted piece
     * @return index of wanted piece
     */
    public int getPieceIndex(char c){
        for(int i = 0; i < capturedPieces.size(); i++){
            Piece currPiece = capturedPieces.get(i);
            if(Character.toLowerCase(currPiece.getSymbol()) == c){
                return i;
            }
        }
        return -1;
    }

    /**
     * Get piece by symbol
     * @param c
     * @return
     */
    public Piece getPiece(char c){
        for(Piece piece : capturedPieces){
            if(Character.toLowerCase(piece.getSymbol()) == c){
                capturedPieces.remove(piece);
                return piece;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public boolean isUpper() {
        return isUpper;
    }

    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public Direction getDirection() {
        return direction;
    }
}
