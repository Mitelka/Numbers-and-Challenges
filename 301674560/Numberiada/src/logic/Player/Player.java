package logic.Player;

import logic.Board.Board;
import logic.Exceptions.PlayerErrorException;
import logic.Exceptions.PlayerOutOfBoundsException;

/**
 * Created by erez on 20/11/2016.
 */
public abstract class Player {
    protected final String playerName;
    private int currRoworCol;
    private int totalScoreOfPlayer;
    private Integer totalStepsCount = 0;
    private boolean isMyTurn;

    public boolean isMyTurn() {
        return isMyTurn;
    }

    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    public Player(String newName)
    {
        playerName = newName;
        totalScoreOfPlayer = 0;
    }
    public String getPlayerName() {return playerName;}

    /*public void updateSteps() {
        totalStepsCount = totalStepsCount++;
    }*/

    public void addScoreToPlayer(int score) {
        totalScoreOfPlayer = totalScoreOfPlayer + score;
    }
    public int getTotalScoreOfPlayer() {return totalScoreOfPlayer;}
    public void setTotalScoreOfPlayer(int updatedScore) {totalScoreOfPlayer = totalScoreOfPlayer+ updatedScore;}
    public abstract boolean makeMove(int selectedNum, Board board) throws PlayerErrorException, PlayerOutOfBoundsException;
}
