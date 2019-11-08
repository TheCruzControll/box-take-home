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
        if(promoted){
            return MoveChecker.BoxShield(startRow, endRow, startCol, endCol,direction);
        }else{
            return MoveChecker.BoxPreview(startRow, endRow, startCol, endCol, direction);
        }
    }

    @Override
    public boolean isLegalDrop(int row, int col, Board board) {
        Player player = getPlayer();
        Player opponent = board.getOpponent(player);
        if(board.isOccupied(row, col))return false;
        //can't drop if there's a preview in the same col
        for(int r = 0; r < board.getBoardSize(); r++){
            Piece piece = board.getPiece(r, col);
            if(piece != null && piece instanceof BoxPreview && piece.getPlayer() == player){
                return false;
            }
        }

        if(board.getPromoteRow(player.getDirection()) == row)return false;
        board.placePiece(this, row, col);
        boolean isCheckMate = board.isCheckMate(opponent);
        board.removePiece(row, col);
        return !isCheckMate;
    }
}
