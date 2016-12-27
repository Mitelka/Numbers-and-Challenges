package logic.Board;

import logic.JAXBcreation.GameDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by erez on 20/11/2016.
 */
public class Board {
    private static final int MIN_RANGE = -99;
    private static final int MAX_RANGE = 99;
    private static final int MARKER_VALUE = -100;
    private Square[][] squares;
    private Marker marker;
    private int markerCol;
    private int markerRow;

    public int getMarkerCol() {
        return markerCol;
    }

    public void setMarkerCol(int markerCol) {
        this.markerCol = markerCol;
    }

    public int getMarkerRow() {
        return markerRow;
    }

    public void setMarkerRow(int markerRow) {
        this.markerRow = markerRow;
    }

    private static int boardSize;
    private static final int MINBOARDSIZE = 5;
    private static final int MAXBOARDSIZE = 50;
    public static final String ROW = "row";
    public static final String COL = "column";

    public Board(GameDescriptor.Board xmlBoard) {
        boardSize = xmlBoard.getSize().intValue();
        squares = new Square[boardSize][boardSize];
        initializeBoard(xmlBoard);
    }

    public void initializeBoard(GameDescriptor.Board xmlBoard) {
        boolean isRandom = xmlBoard.getStructure().getType().toLowerCase().equals("random");
        GameDescriptor.Board.Structure.Range range = xmlBoard.getStructure().getRange();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                squares[i][j] = new Square();
            }
        }
        Random random = new Random();
        if (isRandom && range == null) {
            range = new GameDescriptor.Board.Structure.Range();
            range.setFrom(MIN_RANGE);
            range.setTo(MAX_RANGE);
        }
        if (isRandom){
            int numRange = range.getTo() - range.getFrom() + 1;
            markerCol = showRandomInteger(0, boardSize - 1, random);
            markerRow = showRandomInteger(0, boardSize - 1, random);
            int totalPossible = (int)(Math.pow(boardSize, 2) - 1);
            List<List<Square>> possibleSquares = new ArrayList<>(boardSize);
            for (int i = 0; i < boardSize; i++) {
                possibleSquares.add(new ArrayList<>(boardSize));
                for (int j = 0; j < boardSize; j++) {
                    if (i != markerRow || j != markerCol) {
                        possibleSquares.get(i).add(squares[i][j]);
                    }
                }
            }
            while (totalPossible >= numRange) {
                for (int i = range.getFrom(); i <= range.getTo(); i++) {
                    int randomRow = possibleSquares.size() == 1 ? 0 : showRandomInteger(0, possibleSquares.size() - 1, random);
                    int randomCol = possibleSquares.get(randomRow).size() == 1 ? 0 : showRandomInteger(0, possibleSquares.get(randomRow).size() - 1, random);
                    Square pick = possibleSquares.get(randomRow).remove(randomCol);
                    if (possibleSquares.get(randomRow).size() == 0) {
                        possibleSquares.remove(randomRow);
                    }
                    pick.setVal(i);
                    totalPossible--;
                }
            }
        }
        else // explicit
        {
            List<GameDescriptor.Board.Structure.Squares.Square> allGivenSquares = xmlBoard.getStructure().getSquares().getSquare();
            int numSquares = allGivenSquares.size();
            for (int i = 0; i < numSquares; i++) {
               GameDescriptor.Board.Structure.Squares.Square CurrSquare = allGivenSquares.get(i);
                int givenRow = CurrSquare.getRow().intValue() - 1;
                int givenCol = CurrSquare.getColumn().intValue() - 1;
                squares[givenRow][givenCol] = new Square();
                squares[givenRow][givenCol].setVal(CurrSquare.getValue().intValue());
            }
//            marker = new Marker(xmlBoard.getStructure().getSquares().getMarker().getRow().intValue() - 1,xmlBoard.getStructure().getSquares().getMarker().getColumn().intValue() - 1);
            markerCol = xmlBoard.getStructure().getSquares().getMarker().getColumn().intValue() - 1;
            markerRow = xmlBoard.getStructure().getSquares().getMarker().getRow().intValue() - 1;
        }
        marker = new Marker();
        squares[markerRow][markerCol] = new Square(MARKER_VALUE);
        /*for(int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                if (squares[i][j] == null) {
                    squares[i][j] = new Square(i,j);
                    if (isRandom) {
                        squares[i][j].setVal(showRandomInteger(range.getFrom(), range.getTo(), random));
                    }
                }
            }
        }*/
    }

    public Square[][] getSquares() {
        return squares;
    }

    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public static int getBoardSize() {
        return boardSize;
    }

    public static void setBoardSize(int boardSize) {
        Board.boardSize = boardSize;
    }

    private static int showRandomInteger(int aStart, int aEnd, Random aRandom){
        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long)aEnd - (long)aStart + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * aRandom.nextDouble());
        int randomNumber =  (int)(fraction + aStart);
        return randomNumber;
    }

    public void updateMarker(int row, int col) {
        markerCol = col;
        markerRow = row;
    }

}
