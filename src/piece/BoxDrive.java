package piece;

import game.Board;
import game.Player;

public class BoxDrive extends Piece{
    private static final char symbol = 'D';

    public BoxDrive(Player player){
        super(symbol, player);
    }

    @Override
    public boolean promote(int startRow, int endRow, Board board) {
        return false;
    }

    @Override
    public void demote() {}

    @Override
    public boolean isInMoveRange(int startRow, int endRow, int startCol, int endCol, Board board) {
        return MoveChecker.BoxDrive(startRow, endRow, startCol, endCol);
    }

    @Override
    public boolean isLegalDrop(int row, int col, Board board) {
        return false;
    }
}
