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
