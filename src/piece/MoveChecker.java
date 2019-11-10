package piece;

import game.Board;
import utils.Direction;

public class MoveChecker {
    public static boolean BoxDrive(int startRow, int endRow, int startCol, int endCol){
        return Math.abs(endRow-startRow) <= 1 && Math.abs(endCol-startCol) <= 1;
    }

    public static boolean BoxNotes(int startRow, int endRow, int startCol, int endCol, Board board){
        //if we did not move along the same row and same col
        if(startRow != endRow && startCol != endCol)return false;
        int offset;

        //check rows first
        if(startRow != endRow){
            offset = startRow < endRow ? 1 : -1;
            for(int currRow = startRow + offset; currRow != endRow; currRow += offset){
                if(board.isOccupied(currRow, startCol)){
                    return false;
                }
            }
        }

        if(startCol != endCol){
            offset = startCol < endCol ? 1 : -1;
            for(int currCol = startCol + offset; currCol != endCol; currCol += offset){
                if(board.isOccupied(startRow, currCol)){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean BoxGovernance(int startRow, int endRow, int startCol, int endCol, Board board){
        if(startRow == endRow || startCol == endCol)return false;
        if(Math.abs(endRow - startRow) != Math.abs(endCol - startCol))return false;

        int rowOffset = startRow < endRow ? 1 : -1;
        int colOffset = startCol < endCol ? 1 : -1;

        int currCol = startCol + colOffset;
        for(int currRow = startRow + rowOffset; currRow != endRow; currRow += rowOffset){
            if(board.isOccupied(currRow, currCol)){
                return false;
            }
            currCol += colOffset;
        }
        return true;
    }

    public static boolean BoxShield(int startRow, int endRow, int startCol, int endCol, Direction direction){
        int rowChange = endRow - startRow;
        int colChange = endCol - startCol;

        if(rowChange == 0 && Math.abs(colChange) == 1)return true;
        else if(colChange == 0 && Math.abs(rowChange) == 1)return true;
        else if(direction == Direction.DOWN){
            return (rowChange == 1 && Math.abs(colChange) == 1);
        }else{
            return (rowChange == -1 && Math.abs(colChange) == 1);
        }
    }

    public static boolean BoxRelay(int startRow, int endRow, int startCol, int endCol, Direction direction){
        int rowChange = endRow - startRow;
        int colChange = endCol - startCol;


        //if it goes in any diagonal return true;
        if(Math.abs(rowChange) == 1 && Math.abs(colChange) == 1)return true;
        else if(direction == Direction.DOWN){
            return (rowChange == 1 && colChange == 0);
        }else{
            return (rowChange == -1 && colChange == 0);
        }
    }

    public static boolean BoxPreview(int startRow, int endRow, int startCol, int endCol, Direction direction){
        if(direction == Direction.DOWN){
            return ((endRow - startRow) == 1 && (endCol - startCol) == 0);
        }else{
            return ((endRow - startRow) == -1 && (endCol - startCol) == 0);
        }
    }
}



















