package logic.Game;

import logic.Board.Board;
import logic.Board.Square;
import logic.Exceptions.BadXmlException;
import logic.Exceptions.BoardSizeException;
import logic.Exceptions.GameTypeException;
import logic.Exceptions.SqureException;
import logic.Player.ComPlayer;
import logic.Player.HumanPlayer;
import logic.Player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erez on 02/12/2016.
 */
public class BasicGame extends Game {

    public static final String PLAYER_ROW = "PlayerRow";
    public static final String PLAYER_COL = "PlayerCol";
    public static final String COMPUTER_ROW = "ComputerRow";
    public static final String COMPUTER_COL = "ComputerCol";

    public BasicGame(String xmlPath) throws Exception {
        super(xmlPath);
    }

    public void addPlayers(boolean isPlayerRowHuman, boolean isPlayerColHuman) {
        players = new ArrayList<>(2);
        Player playerRow = isPlayerRowHuman ? new HumanPlayer(PLAYER_ROW) : new ComPlayer(COMPUTER_ROW);
        Player playerCol = isPlayerColHuman ? new HumanPlayer(PLAYER_COL) : new ComPlayer(COMPUTER_COL);
        players.add(0,playerRow);
        players.add(1,playerCol);
    }

    public void addPlayers()
    {
        players = new ArrayList<>(2);
        Player playerRow = new HumanPlayer(PLAYER_ROW);
        Player playerCol = new HumanPlayer(PLAYER_COL);
        players.add(0,playerRow);
        players.add(1,playerCol);
        // TODO - Comp player
    }

    public List<Square> getPossibleSquares(boolean isRow) {
        List<Square> possibleSquares = new ArrayList<>();
        int col = getBoard().getMarkerCol();
        int row = getBoard().getMarkerRow();
        Square suspected;
        for (int i = 0; i < Board.getBoardSize(); i++) {
            if (isRow) {
                suspected = getBoard().getSquares()[row][i];
            } else {
                suspected = getBoard().getSquares()[i][col];
            }
            if (suspected.getVal() != 100 && suspected.getVal() != -100) {
                possibleSquares.add(suspected);
            }
        }

        return possibleSquares;
    }

}
