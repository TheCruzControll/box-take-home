package piece;

import game.Board;
import game.Player;

public class BoxRelay extends Piece{
    private static char symbol = 'R';

    public BoxRelay(Player player) {
        super(symbol, player);
    }

    @Override
    public boolean promote(int startRow, int endRow, Board board) {
        if(!canPromote(startRow, endRow, board))return false;
        promoted = true;
        return true;
    }

    @Override
    protected void demote() {
        promoted = false;
    }

    @Override
    protected boolean isInMoveRange(int startRow, int endRow, int startCol, int endCol, Board board) {
        if(promoted){
            return MoveChecker.BoxShield(startRow, endRow, startCol, endCol, direction) ||
                    MoveChecker.BoxRelay(startRow, endRow, startCol, endCol, direction);
        }else{
            return MoveChecker.BoxRelay(startRow, endRow, startCol, endCol, direction);
        }
    }

    @Override
    public boolean isLegalDrop(int row, int col, Board board) {
        return board.isOccupied(row, col);
    }
}
