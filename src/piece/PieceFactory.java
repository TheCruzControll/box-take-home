package piece;
import game.Player;
public class PieceFactory {
    public static Piece makePiece(String s, Player upper, Player lower ){
        if(s.length() <= 0)return null;
        boolean promoted = false;
        Piece piece;
        if(s.length() == 2 ){
            promoted = true;
            s = s.substring(1);
        }
        char symbol = s.charAt(0);
        Player owner = Character.isUpperCase(symbol) ? upper : lower;
        switch(Character.toLowerCase(symbol)){
            case 'd':
                piece = new BoxDrive(owner);
                break;
            case 'n':
                piece = new BoxNotes(owner);
                break;
            case 'g':
                piece = new BoxGovernance(owner);
                break;
            case 's':
                piece = new BoxShield(owner);
                break;
            case 'r':
                piece = new BoxRelay(owner);
                break;
            default:
                piece = new BoxPreview(owner);
                break;
        }
        if(promoted) piece.promote();
        return piece;
    }
}
