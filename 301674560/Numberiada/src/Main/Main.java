package Main;

import UI.GameHandler;
import logic.Game.BasicGame;
import logic.Game.Game;
import logic.Game.GameManager;

import javax.xml.bind.JAXBException;

/**
 * Created by erez on 05/12/2016.
 */
public class Main {
    public static void main(String[] args) throws JAXBException {
        GameHandler gameHandler = new GameHandler();
        gameHandler.run();
    }
}
