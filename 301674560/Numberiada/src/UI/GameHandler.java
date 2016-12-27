package UI;

import logic.Game.GameManager;
import logic.Player.Player;

import java.util.ArrayList;
import java.util.Scanner;

import static UI.Utils.boardPrinting;
import static logic.Game.GameManager.setIsGameLoaded;

/**
 * Created by erez on 10/12/2016.
 */
public class GameHandler {

    private GameManager manager;
    boolean firstEnter = true;
    public void run() {
        manager = new GameManager();
        startGame();
    }

    private void startGame() {
        // Print all menus

        boolean userWantsToPlay = true;

        setIsGameLoaded(false);

        Utils.clearMyScreen();
        System.out.println("\nWelcome to Numberiada game!");
        Utils.printMainMenu();
        GameManager.GAME_OPTION option = getOption();
        while (option != GameManager.GAME_OPTION.LOAD_GAME && option != GameManager.GAME_OPTION.EXIT_GAME) {
            System.out.println("You need to load game first. Please choose command number 1");
            option = getOption();
        }
        if (option == GameManager.GAME_OPTION.LOAD_GAME) {
            getConfigFromUser();

        }
        if (option == GameManager.GAME_OPTION.EXIT_GAME) {
            userWantsToPlay = false;
            Utils.getOutOfGameMsg();
            System.exit(1);
        }
        //manager.doOption(option);

        while (userWantsToPlay)  {
            Utils.printMainMenu();
            option = getOption();
            if (Utils.isValidOption(option)) {
                switch (option) {
                    case LOAD_GAME:
                        getConfigFromUser();
                        break;
                    case START_GAME:
                        System.out.println("First Player is Humen? y for and n for Computer");
                        boolean CheakfirstPlayer = CheakComOrHumen();
                        System.out.println("Second Player is Humen? y for and n for Computer");
                        boolean CheaksecondPlayer = CheakComOrHumen();
                        manager.playGame(CheakfirstPlayer,CheaksecondPlayer);
                        printPlayerTurn();
                        firstEnter = false;
                        break;
                    case SHOW_GAME_SITUATION:
                        boardPrinting(manager.getBoard(), manager.getPossibleSquares());
                        printPlayerTurn();
                        break;
                    case DO_ITERATION:
                        boolean notValidMove = true;
                        while (notValidMove)
                        try {
                           manager.doIteration(getNumber());
                            notValidMove = false;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        boardPrinting(manager.getBoard(), manager.getPossibleSquares());
                        if (!manager.isGameActive())
                        {
                            ShowStats();
                            Player winner = manager.getLeader();
                            if (winner == null) {
                                System.out.println("It's a draw!!");
                            }
                            else
                            {
                                printWinner(winner.getPlayerName());
                            }
                            userWantsToPlay = false;
                        } else {
                             printPlayerTurn();
                            }

                        break;
                    case SHOW_STATS:
                        ShowStats();
                        break;
                    case EXIT_GAME:
                        userWantsToPlay = false;
                        System.out.println(manager.whosTurn().getPlayerName() + " has quit the game!");
                        manager.doOption(option);
                        printWinner(manager.whosTurn().getPlayerName());
                        boardPrinting(manager.getBoard(), new ArrayList<>());
                        ShowStats();
                        break;
                    default:
                        break;

                }

            }
        }

    }

    public boolean CheakComOrHumen() {
        boolean isPlayerHuman = true;
        Scanner scanner = new Scanner(System.in);
        char Answer = scanner.findInLine(".").charAt(0);
        if (Answer == 'y' ||Answer == 'Y' ) {isPlayerHuman = true;}
        else if (Answer =='n' ||Answer =='N') {isPlayerHuman = false;}
        else
        {
            System.out.println("invalid pick - try again");
            CheakComOrHumen();
        }
        return isPlayerHuman;

    }

    private void getConfigFromUser() {
        boolean isConfigValid = false;
        while (!isConfigValid) {
            try {
                String path = Utils.getFileFromUser();
                manager.loadGame(path);
                System.out.println("\nGame file loaded successfully! :)\n");
                isConfigValid = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printWinner(String winnerName) {
        System.out.println(winnerName + " has won the game");
    }

    private void ShowStats() {
        System.out.println("Time of play - Minutes:Seconds");
        int seconds = (int) ((System.currentTimeMillis() - manager.getStartTimeOfGame()) / 1000) % 60 ;
        int minutes = (int) ((System.currentTimeMillis() - manager.getStartTimeOfGame()) / (1000*60) % 60);
        System.out.println(minutes + ":" + (seconds > 9 ? seconds : "0" + seconds));
        System.out.println("Number of moves: " +manager.turnCounter);
        manager.doOption(GameManager.GAME_OPTION.SHOW_STATS);
    }

    private void printPlayerTurn() {
        System.out.println(manager.whosTurn().getPlayerName() + " Turn.");
    }

    private GameManager.GAME_OPTION getOption() {
        Integer returnVal = null;
        if (!firstEnter )
        {
            if (manager.whosTurn().getPlayerName().equals("PlayerRow") || manager.whosTurn().getPlayerName().equals("PlayerCol")) {
                returnVal = HumenPick(returnVal);
            } else returnVal = 4;
        }
        else returnVal = HumenPick(returnVal);

        GameManager.GAME_OPTION option = null;

        switch (returnVal) {
            case 1:
                option = GameManager.GAME_OPTION.LOAD_GAME;
                break;
            case 2:
                option = GameManager.GAME_OPTION.START_GAME;
                break;
            case 3:
                option = GameManager.GAME_OPTION.SHOW_GAME_SITUATION;
                break;
            case 4:
                option = GameManager.GAME_OPTION.DO_ITERATION;
                break;
            case 5:
                option = GameManager.GAME_OPTION.SHOW_STATS;
                break;
            case 6:
                option = GameManager.GAME_OPTION.EXIT_GAME;
                break;
            default:
                option = null;
                break;

        }
        return option;
    }


    public int getNumber() {
        System.out.println("The Marker is in " + (manager.getBoard().getMarkerRow()+1) +" , " + (manager.getBoard().getMarkerCol()+1)  + " position");
        if(manager.turnCounter%2 == 0) {
            System.out.println("Select your Col");
        } else {
            System.out.println("Select your Row");
        }
        if (manager.whosTurn().getPlayerName().equals("ComputerRow") || manager.whosTurn().getPlayerName().equals("ComputerCol"))
        {
            return 1;
        }

        Scanner scanner = new Scanner(System.in);
        Integer returnVal = null;
        while (returnVal == null) {
            if (scanner.hasNextInt())
                returnVal = scanner.nextInt();
            else {
                System.out.println("Your input is not a number. Please try again");
                scanner.next();
            }
        }
        return returnVal;
    }

    public Integer HumenPick(Integer returnVal){

        Scanner scanner = new Scanner(System.in);
        while (returnVal == null) {
            if (scanner.hasNextInt()) {
                returnVal = scanner.nextInt();
                if (returnVal > 6 || returnVal < 1) {
                    System.out.println("Your input need to be between 1 to 6 . Please try again");
                    returnVal = null;
                }
            }
            else
            {
                System.out.println("Your input is not a number. Please try again");
                scanner.next();
            }
        }
        return  returnVal;
    }
}