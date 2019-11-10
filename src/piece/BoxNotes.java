package piece;

import game.Board;
import game.Player;

public class BoxNotes extends Piece{
    private static final char symbol = 'N';

    public BoxNotes(Player player){
        super(symbol, player);
    }

    @Override
    public boolean promote(int startRow, int endRow, Board board) {
        if(!canPromote(startRow, endRow, board))return false;
        promoted = true;
        return true;
    }

    @Override
    public void demote() {
        promoted = false;
    }

    @Override
    public boolean isInMoveRange(int startRow, int endRow, int startCol, int endCol, Board board) {
        if(promoted){
            return MoveChecker.BoxDrive(startRow, endRow, startCol, endCol) || MoveChecker.BoxNotes(startRow, endRow, startCol, endCol, board);
        }else{
            return MoveChecker.BoxNotes(startRow, endRow, startCol, endCol, board);
        }
    }

    @Override
    public boolean isLegalDrop(int row, int col, Board board) {
        return !board.isOccupied(row, col);
    }
}
