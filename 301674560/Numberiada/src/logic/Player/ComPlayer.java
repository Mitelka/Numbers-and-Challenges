package logic.Player;

import logic.Board.Board;
import logic.Board.Marker;
import logic.Board.Square;
import logic.Exceptions.PlayerErrorException;
import logic.Exceptions.PlayerOutOfBoundsException;
import logic.Game.BasicGame;

/**
 * Created by erez on 20/11/2016.
 */
public class ComPlayer extends Player{

    public ComPlayer(String name) {
        super(name);
    }
    @Override
    public boolean makeMove(int selectedNum, Board board) throws PlayerErrorException, PlayerOutOfBoundsException {
        Marker marker = board.getMarker(); // Save the Marker pos
        int row = board.getMarkerRow();
        int col = board.getMarkerCol();
        Square selectedSquare;

        if (selectedNum >= Board.getBoardSize() || selectedNum < 0)
            throw new PlayerOutOfBoundsException();

        if (getPlayerName().equals(BasicGame.COMPUTER_ROW)) {
            selectedSquare = board.getSquares()[row][selectedNum];
            if (selectedSquare == null || selectedSquare.isEmptySquare() || selectedSquare.isSignSquare()) {
                throw new PlayerErrorException();
            }
            else
            {
                addScoreToPlayer(board.getSquares()[row][selectedNum].getVal());
                //updateSteps();
                board.updateMarker(row,selectedNum);
            }
        }
        else
        {
            selectedSquare = board.getSquares()[selectedNum][col];
            if (selectedSquare == null || selectedSquare.isEmptySquare() || selectedSquare.isSignSquare())
                throw new PlayerErrorException();

            else {
                addScoreToPlayer(board.getSquares()[selectedNum][col].getVal());
                //updateSteps();
                board.updateMarker(selectedNum, col);
            }
        }
        board.getSquares()[row][col].setVal(100);
        board.getSquares()[board.getMarkerRow()][board.getMarkerCol()].setVal(-100);
        return true;

    }
}
