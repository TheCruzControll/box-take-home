package game;

import piece.Piece;
import utils.Direction;
import java.util.*;

public class Player {
    private static String name;
    private final boolean isUpper;
    private final List<Piece> capturedPieces;
    private final Direction direction;


    public Player(boolean isUpper){
        this.isUpper = isUpper;
        capturedPieces = new LinkedList<Piece>();
        if(isUpper){
            direction = Direction.DOWN;
            name = "UPPER";
        }else{
            direction = Direction.UP;
            name = "lower";
        }
    }

    public char getSymbol(char originalSymbol) {
        if (isUpper) return Character.toUpperCase(originalSymbol);
        else return Character.toLowerCase(originalSymbol);
    }

    public void addCapturedPiece(Piece piece){
        capturedPieces.add(piece);
    }

    public void addCapturedPiece(Piece piece, int index){
        if (index < capturedPieces.size()) {
            capturedPieces.add(index, piece);
        }
        else {
            capturedPieces.add(piece);
        }
    }

    public int getPieceIndex(char c){
        for(int i = 0; i < capturedPieces.size(); i++){
            Piece currPiece = capturedPieces.get(i);
            if(Character.toLowerCase(currPiece.getSymbol()) == c){
                return i;
            }
        }
        return -1;
    }

    public Piece getPiece(char c){
        for(Piece piece : capturedPieces){
            if(Character.toLowerCase(piece.getSymbol()) == c){
                capturedPieces.remove(piece);
                return piece;
            }
        }
        return null;
    }

    public static String getName() {
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
