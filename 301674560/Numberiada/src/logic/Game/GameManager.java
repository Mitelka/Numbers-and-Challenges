package logic.Game;

import logic.Board.Board;
import logic.Board.Square;
import logic.Exceptions.*;
import logic.Player.Player;

import java.util.List;

import static UI.Utils.boardPrinting;


public class GameManager {

    public enum GAME_OPTION {
        LOAD_GAME,
        START_GAME,
        SHOW_GAME_SITUATION,
        DO_ITERATION,
        SHOW_STATS,
        EXIT_GAME
    }

    private static boolean isGameLoaded;
    private static boolean isGameStarted;
    private boolean isGameActive;
    public int turnCounter;
    private List<Square> possibleSquares;
    public Long startTimeOfGame;

    private Game game;

    public Board getBoard() {
        return game.getBoard();
    }

    public void setBoard(Board board) {
        game.setBoard(board);
    }

    public GameManager() {
        setIsGameLoaded(false);
        setIsGameStarted(false);
    }

    public boolean isGameActive() {
        return isGameActive;
    }

    public static boolean getIsGameLoaded() {
        return isGameLoaded;
    }

    public static void setIsGameLoaded(boolean isLoaded) {
        isGameLoaded = isLoaded;
    }

    public static boolean getIsGameStarted() {
        return isGameStarted;
    }

    public static void setIsGameStarted(boolean isStarted) {
        isGameStarted = isStarted;
    }

    public Long getStartTimeOfGame() {
        return startTimeOfGame;
    }

    public void setStartTimeOfGame(Long newStartTime) {
        startTimeOfGame = newStartTime;
    }

    public void doOption(GAME_OPTION option)   {
        switch (option) {
            case START_GAME:
                //playGame();
                break;
            case SHOW_STATS:
                Stats();
                break;
            case EXIT_GAME:
                exitApplication();
                break;
            default:
                break;
        }
    }

    private void exitApplication() {
        isGameActive = false;
        if (game instanceof BasicGame) {
            turnCounter++;
        }
    }

    public void doIteration(int Num) throws PlayerErrorException, PlayerOutOfBoundsException {
        Player currPlayer = whosTurn();
        if (currPlayer.getPlayerName().equals("ComputerRow") || currPlayer.getPlayerName().equals("ComputerCol"))
        {
            if (currPlayer.getPlayerName().equals("ComputerRow")) {
                for (int i = 0; i < Board.getBoardSize() ; i++) {
                    if (!game.getBoard().getSquares()[game.getBoard().getMarkerRow()][i].isEmptySquare() && !game.getBoard().getSquares()[game.getBoard().getMarkerRow()][i].isSignSquare()) {
                        currPlayer.makeMove(i, game.getBoard());
                        break;
                    }
                }
            }
            else
            {
                for (int i = 0; i < Board.getBoardSize() ; i++) {
                    if (!game.getBoard().getSquares()[i][game.getBoard().getMarkerCol()].isEmptySquare() && !game.getBoard().getSquares()[i][game.getBoard().getMarkerCol()].isSignSquare()) {
                        currPlayer.makeMove(i, game.getBoard());
                        break;
                    }
                }
            }
        }
        else currPlayer.makeMove(Num - 1, game.getBoard());

        turnCounter++;
        possibleSquares = fillPossibleSquares(whosTurn());
       if (possibleSquares.isEmpty()) {
           isGameActive = false;
       }
    }

    public List<Square> fillPossibleSquares(Player activePlayer) {
        if(activePlayer.getPlayerName().equals(BasicGame.PLAYER_ROW))
            return ((BasicGame) game).getPossibleSquares(activePlayer.getPlayerName().equals(BasicGame.PLAYER_ROW));
        else
            return ((BasicGame) game).getPossibleSquares(activePlayer.getPlayerName().equals(BasicGame.COMPUTER_ROW));
    }

    public List<Square> getPossibleSquares() {
        return possibleSquares;
    }

    public void playGame(boolean isPlayerRowHuman, boolean isPlayerColHuman) {
        isGameActive = true;
        isGameStarted = true;
        setStartTimeOfGame(System.currentTimeMillis());
        game.addPlayers(isPlayerRowHuman,isPlayerColHuman);
        turnCounter = 0;
        Player active = whosTurn();
        possibleSquares = fillPossibleSquares(active);
        boardPrinting(getBoard(), getPossibleSquares());

    }

    public void loadGame(String path) throws Exception {
        game = new BasicGame(path);
        setIsGameLoaded(true);
    }

    public Player whosTurn() {
        return game.players.get(turnCounter % 2);
    }

    public void Stats() {
        for (int i = 0; i < game.players.size(); i++) {
            System.out.println(game.players.get(i).getPlayerName() + " Score is: " + game.players.get(i).getTotalScoreOfPlayer());
        }
    }
    public Player getLeader() {
        return  game.getLeader();
    }
}