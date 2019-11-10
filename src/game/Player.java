package game;

import piece.Piece;
import utils.Direction;
import java.util.*;

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
