package piece;

import game.Board;
import game.Player;

public class BoxPreview extends Piece{
    private static char symbol = 'P';

    public BoxPreview(Player player){
        super(symbol, player);
    }

    @Override
    public boolean promote(int startRow, int endRow, Board board) {
        if(!canPromote(startRow, endRow,board))return false;
        promoted = true;
        return true;
    }

    @Override
    protected void demote() {
        promoted = false;
    }

    @Override
    protected boolean isInMoveRange(int startRow, int endRow, int startCol, int endCol, Board board) {
        return false;
    }

    @Override
    public boolean isLegalDrop(int row, int col, Board board) {
        Player player = getPlayer();
        if(board.isOccupied(row, col))return false;

        if(board.getPromoteRow(player.getDirection()) == row)return false;

    }
}
