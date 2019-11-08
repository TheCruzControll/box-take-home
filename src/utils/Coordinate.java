package utils;

public class Coordinate {
    private int row;
    private int col;

    public Coordinate(){ }

    public Coordinate(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String colStr = String.valueOf((char) this.col + 65);
        sb.append(colStr).append(row);
        return sb.toString();
    }
}
