package logic.converters;

import logic.Exceptions.BoardRandomException;
import logic.Exceptions.BoardSizeException;
import logic.Exceptions.GameTypeException;
import logic.Exceptions.SqureException;
import logic.Exceptions.MarkerException;
import logic.JAXBcreation.GameDescriptor;

import java.math.BigInteger;

/**
 * Created by erez on 16/12/2016.
 */
public class Validator {

    public static final int MAX_BOARD_SIZE = 50;
    public static final int MIN_BOARD_SIZE = 5;

    public static boolean numberiadaValidator(GameDescriptor descriptor) throws Exception {
      return  (validateGameType(descriptor)) && (validateBoard(descriptor.getBoard()) && (validatePlayers(descriptor.getPlayers()) && (validateDynamicPlayers(descriptor.getDynamicPlayers()))));

    }
    private static boolean validateGameType(GameDescriptor descriptor) throws GameTypeException{

        String GameType = descriptor.getGameType().toLowerCase();
        if (GameType.equals("basic") || GameType.equals("advance") || GameType.equals("advancedynamic") )
        {
            return true;
        }
        throw new GameTypeException();
    }

    private static boolean validateBoard(GameDescriptor.Board board) throws Exception {
        //int MarkerCounter = 0;
        BigInteger BoardSize = board.getSize();

        if (BoardSize.intValue() > MAX_BOARD_SIZE || BoardSize.intValue() < MIN_BOARD_SIZE) {
            throw new BoardSizeException();
        }
        String gameStructure = board.getStructure().getType().toLowerCase();
        if (gameStructure.equals("explicit")){
            GameDescriptor.Board.Structure.Squares.Marker marker = board.getStructure().getSquares().getMarker();
            int SizeOfListSquares = board.getStructure().getSquares().getSquare().size();
            if (SizeOfListSquares > BoardSize.intValue()*BoardSize.intValue() - 1 ){ throw new SqureException();}

            for (int i = 0; i < board.getStructure().getSquares().getSquare().size(); i++) {
                GameDescriptor.Board.Structure.Squares.Square CurrSquare = board.getStructure().getSquares().getSquare().get(i);
                if (CurrSquare.getColumn().intValue() > BoardSize.intValue() || CurrSquare.getRow().intValue() >BoardSize.intValue() || CurrSquare.getColumn().intValue() < 1 || CurrSquare.getRow().intValue() < 1){
                    throw new SqureException();
                }
                if (CurrSquare.getColor() > 6) {throw new SqureException();}
            }
            if (marker.getRow().intValue() > BoardSize.intValue() || marker.getColumn().intValue() > BoardSize.intValue() ||marker.getRow().intValue() < 1 || marker.getColumn().intValue() < 1){
                    throw new MarkerException();
            }

        } else if (gameStructure.equals("random")) {
            GameDescriptor.Board.Structure.Range range = board.getStructure().getRange();
            if (range.getFrom() > range.getTo()) {
                throw new BoardRandomException("Make sure that your random range values are valid");
            }
            int rangeNum = range.getTo() - range.getFrom() + 1;
            if (Math.pow(board.getSize().doubleValue(), 2) <= rangeNum) {
                throw new BoardRandomException();
            }
        }

        return true;
    }

    private static boolean validatePlayers(GameDescriptor.Players players) {
        return true;
    }

    private static boolean validateDynamicPlayers(GameDescriptor.DynamicPlayers dynamicPlayers) {
        return true;
    }
}
