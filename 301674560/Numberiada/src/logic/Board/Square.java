package logic.Board;

/**
 * Created by erez on 20/11/2016.
 */
public class Square {
    public static final int EMPTY_SQUARE_VALUE = 100;
    private int Val;

    public Square(int val) {
        Val = val;
    }

    public Square() {
        setVal(EMPTY_SQUARE_VALUE);
    }

    public void setEmptySquare(boolean emptySquare) {
        isEmptySquare = emptySquare;
    }

    boolean isEmptySquare;
    public int getVal() {return Val;}
    public boolean isEmptySquare() { return (Val == EMPTY_SQUARE_VALUE);}
    public boolean isSignSquare() {return (Val == -100);}
    public void setVal(int val) {
        Val = val;
    }
}
