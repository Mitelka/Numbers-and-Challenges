package logic.Game;

import logic.Board.Board;
import logic.Board.Marker;
import logic.Exceptions.BadXmlException;
import logic.JAXBcreation.GameDescriptor;
import logic.JAXBcreation.Read_xml;
import logic.Player.Player;
import logic.converters.Validator;

import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * Created by erez on 20/11/2016.
 */
public abstract class Game {

    protected Statistics stats;
    protected List<Player> players;
    private Marker marker;
    private Board board;
    private GameDescriptor descriptor;

    public Game (Statistics stats, Marker marker, Board board){
        this.board = board;
        this.stats = stats;
        this.marker = marker;
    }
    public Game(){}

    public Game(String xmlPath) throws Exception {
        try {
            descriptor = Read_xml.getInformation(xmlPath);
        } catch (JAXBException e) {
            throw new BadXmlException("Your xml file is not valid. Please fix");
        }
        Validator.numberiadaValidator(descriptor);
        initGame();


    }

    private void initGame() {
//        Board.setBoardSize(descriptor.getBoard().getSize().intValue());
        board = new Board(descriptor.getBoard());
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public GameDescriptor getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(GameDescriptor descriptor) {
        this.descriptor = descriptor;
    }
    public abstract void addPlayers();

    public Player getLeader() {
        int index = 0;
        int maxScore = players.get(0).getTotalScoreOfPlayer();
        for (int i = 1; i < players.size(); i++) {
            int scoreOfPlayer = players.get(i).getTotalScoreOfPlayer();
            if (scoreOfPlayer > maxScore) {
                maxScore = scoreOfPlayer;
                index = i;
            }
            else if(scoreOfPlayer == maxScore)
                index = -1;

        }
        if (index == -1) {return  null;}
        return players.get(index);
    }


    public abstract void addPlayers(boolean isPlayerRowHuman, boolean isPlayerColHuman);

}




