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

        //Can't drop on an occupied spot
        if(board.isOccupied(row, col))return false;

        //can't drop if there's a preview in the same col
        for(int r = 0; r < board.getBoardSize(); r++){
            Piece piece = board.getPiece(r, col);
            if(piece != null && piece instanceof BoxPreview && piece.getPlayer() == player){
                return false;
            }
        }
        //Cannot be dropped into promotion zone
        if(board.getPromoteRow(player.getDirection()) == row)return false;

        //simulate drop
        int index = player.getPieceIndex(getSymbol());
        Piece p = player.getPiece(getSymbol());
        board.placePiece(this, row, col);

        //check to see if drop results in a checkmate
        boolean checkMate = board.isCheckMate(opponent);
        board.removePiece(row, col);
        if (p != null) player.addCapturedPiece(p, index);
        return !checkMate;
    }
}
