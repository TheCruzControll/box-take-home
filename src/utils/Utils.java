package utils;

import game.Board;
import piece.Piece;

import java.io.*;
import java.util.*;

public class Utils {


    static String indexToAddress(int row, int col, Board board){
        char r = (char)(((int)'a') + col);
        int c = board.getBoardSize() - row;
        return r + String.valueOf(c);
    }

    public static String moveToString(int startRow, int endRow, int startCol, int endCol, Board board){
        String from = indexToAddress(startRow, startCol, board);
        String to = indexToAddress(endRow, endCol, board);
        return "move " + from + " " + to;
    }

    public static String dropToString(Piece piece, int endRow, int endCol, Board board){
        return "drop " + Character.toLowerCase(piece.getSymbol()) + " " + indexToAddress(endRow, endCol, board);
    }

    static class InitialPosition {
        String piece;
        String position;

        public InitialPosition(String pc, String pos) {
            piece = pc;
            position = pos;
        }

        public String toString() {
            return piece + " " + position;
        }
    }

    static class TestCase {

        List<InitialPosition> initialPieces;
        List<String> upperCaptures;
        List<String> lowerCaptures;
        List<String> moves;

        public TestCase(List<InitialPosition> ip, List<String> uc, List<String> lc, List<String> m) {
            initialPieces = ip;
            upperCaptures = uc;
            lowerCaptures = lc;
            moves = m;
        }

        public String toString() {
            String str = "";

            str += "initialPieces: [\n";
            for (InitialPosition piece : initialPieces) {
                str += piece + "\n";
            }
            str += "]\n";

            str += "upperCaptures: [";
            for (String piece : upperCaptures) {
                str += piece + " ";
            }
            str += "]\n";

            str += "lowerCaptures: [";
            for (String piece : lowerCaptures) {
                str += piece + " ";
            }
            str += "]\n";

            str += "moves: [\n";
            for (String move : moves) {
                str += move + "\n";
            }
            str += "]";

            return str;
        }
    }

    public static TestCase parseTestCase(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = br.readLine().trim();
        List<InitialPosition> initialPieces = new ArrayList<InitialPosition>();
        while (!line.equals("")) {
            String[] lineParts = line.split(" ");
            initialPieces.add(new InitialPosition(lineParts[0], lineParts[1]));
            line = br.readLine().trim();
        }
        line = br.readLine().trim();
        List<String> upperCaptures = Arrays.asList(line.substring(1, line.length() - 1).split(" "));
        line = br.readLine().trim();
        List<String> lowerCaptures = Arrays.asList(line.substring(1, line.length() - 1).split(" "));
        line = br.readLine().trim();
        line = br.readLine().trim();
        List<String> moves = new ArrayList<String>();
        while (line != null) {
            line = line.trim();
            moves.add(line);
            line = br.readLine();
        }

        return new TestCase(initialPieces, upperCaptures, lowerCaptures, moves);
    }
}
