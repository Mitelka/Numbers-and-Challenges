package UI;

import logic.Board.Board;
import logic.Board.Square;
import logic.Game.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;



/**
 * Created by erez on 07/12/2016.
 */

public class Utils {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    private static final int MARKER_SIGN = -100;


    private static Scanner in = new Scanner(System.in);

    public Utils() {}

    public static void clearMyScreen()
    {
        for (int i = 0; i < 1; ++i)
        {
            System.out.println();
        }
    }

    public static void printMainMenu()
    {
        System.out.println("\nChoose one from the following:");
        System.out.println("==============================");
        System.out.println("1.  Load game details from user");
        System.out.println("2.  Start game");
        System.out.println("3.  Show Game Situation");
        System.out.println("4.  Making a move");
        System.out.println("5.  Receive statistics");
        System.out.println("6.  Retirement / end game");
    }

    public static boolean isValidOption(GameManager.GAME_OPTION option) throws IllegalStateException
    {
        boolean checkAgain = true;

        while(checkAgain)
        {
            checkAgain = false;

            if (GameManager.getIsGameStarted() && (option == GameManager.GAME_OPTION.LOAD_GAME || option == GameManager.GAME_OPTION.START_GAME))
            {
                System.out.println("You can't choose this option because you're in the middle of a game. Please insert a valid option");
                return false;
            }
            if (!GameManager.getIsGameStarted() && GameManager.getIsGameLoaded() && option != GameManager.GAME_OPTION.START_GAME && option != GameManager.GAME_OPTION.EXIT_GAME && option != GameManager.GAME_OPTION.LOAD_GAME)
            {
                System.out.println ("You need to start your game before choosing show details about it. Please insert a valid option");
                return false;
            }
            if (option != GameManager.GAME_OPTION.EXIT_GAME && option != GameManager.GAME_OPTION.LOAD_GAME && option != GameManager.GAME_OPTION.START_GAME && option != GameManager.GAME_OPTION.SHOW_GAME_SITUATION && option != GameManager.GAME_OPTION.SHOW_STATS && option != GameManager.GAME_OPTION.DO_ITERATION) {
                System.out.println("Please insert a valid option");
                return false;
            }
        }

        return true;
    }

    public static String getFileFromUser() {
        boolean validPath = false;
        String path = "";
        while (!validPath) {
            System.out.println("Please insert your config path: ");
            path = in.nextLine();
            Path pathFormat = null;
            try {
                pathFormat = Paths.get(path);
            } catch (Exception e) {
                System.out.println("Invalid path");
                continue;
            }
            validPath = true;
            boolean isXml = true;
            boolean isNotExist = false;

            if (Files.notExists(pathFormat)) {
                System.out.println("File not exists");
                isNotExist = true;
            }

            if (!path.endsWith(".xml")) {
                isXml = false;
            }

            if (isNotExist || !isXml) {
                validPath = false;
            }
        }

        return  path;
    }

    public static String getFilePathFromUser()
    {
        String path = null;
        Path pathFormat = null;

        boolean checkAgain = true;

        while (checkAgain) {
            System.out.println("Insert your game path: ");
            try {
                path = in.nextLine();
                pathFormat = Paths.get(path);
                Path ending = Paths.get(".xml");
            } catch (Exception ex) {
                System.out.println("Invalid path");
            }
            checkAgain = false;
            boolean isXml = true;
            boolean isNotExist = false;

            if (Files.notExists(pathFormat)) {
                System.out.println("File not exists");
                path = in.nextLine();
                isNotExist = true;
            }

            if (!path.endsWith(".xml")) {
                isXml = false;
            }

            if (isNotExist || !isXml) {
                checkAgain = true;
            }
        }
        return path;
    }

    public static void boardPrinting(Board board, List<Square> possibleSquares)
    {
        Square toPrint;
        int boardSize = Board.getBoardSize();
        System.out.print("   ");
        for (int i = 0; i < boardSize; i++) {
            printNumber(i + 1);
            System.out.print(" ");
        }
        System.out.println();
        // print all columns numbers
        for (int i = 0; i < boardSize; i++) {
            // print row number
            printNumber(i + 1);
            for (int j = 0; j < boardSize; j++) {
                System.out.print("|");
                toPrint = board.getSquares()[i][j];
                if (!toPrint.isEmptySquare()) {
                    if (possibleSquares.contains(toPrint)) {
                        //System.out.print(ANSI_GREEN);
                        printNumber(toPrint.getVal());
                        //System.out.print(ANSI_RESET);
                    } else {
                        printNumber(toPrint.getVal());
                    }
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println("|");
        }
    }

    public static void printNumber(int i) {
        if (i == MARKER_SIGN) {
            System.out.print("  M" );
            return;
        }
        else if ((i < 0 && i > -10) || i >= 10)  {
            System.out.print(" ");
        } else if (i >= 0 && i < 10){
            System.out.print("  ");
        }
        System.out.print(i);
    }

    public static Boolean lastChanceToContinue()
    {
        Boolean backToMainMenu = false;
        Boolean validChoice;
        String playerChoice;

        do {
            System.out.println("Do you wants to continue playing? (go back to main menu) Press 'Y' for yes or 'N' otherwise");
            playerChoice = Utils.in.nextLine();
            validChoice = playerChoice.equalsIgnoreCase("Y") || playerChoice.equalsIgnoreCase("N");
        }while(!validChoice);

        if(playerChoice.equalsIgnoreCase("Y"))
        {
            backToMainMenu = true;
        }

        return  backToMainMenu;
    }

    public static void getOutOfGameMsg()
    {
        System.out.println("See you next time");
        System.out.println("Bye Bye!");
    }

}


