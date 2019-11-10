package piece;

import game.Board;
import game.Player;

public class BoxShield extends Piece {
    private static char symbol = 'S';

    public BoxShield(Player player){
        super(symbol, player);
    }

    @Override
    public void promote(){
        promoted = false;
    }

    @Override
    public boolean promote(int startRow, int endRow, Board board) {
        return false;
    }

    @Override
    protected void demote() {
        promoted = false;
    }

    @Override
    protected boolean isInMoveRange(int startRow, int endRow, int startCol, int endCol, Board board) {
        return MoveChecker.BoxShield(startRow, endRow, startCol, endCol, direction);
    }

    @Override
    public boolean isLegalDrop(int row, int col, Board board) {
        return !board.isOccupied(row,col);
    }
}
