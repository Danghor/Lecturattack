package Lecturattack.statemachine;/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.menu.AnimatedMenuButton;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Andreas Geis
 */
public class MainMenuState extends BasicGameState implements InputListener {
  private final int stateID;
  private StateBasedGame stateBasedGame;
  private Image background;
  private Image logo;
  private AnimatedMenuButton[] menuButton;
  private int currentSelection;
  // this boolean indicates, if the "Spiel starten" button has be set to
  // "Spiel fortsetzen" already
  private boolean continueGameButton;

  /**
   * Constructor for MainMenuState
   * Set the ID of this state to the given stateID
   *
   * @param stateID
   */
  public MainMenuState(int stateID) {
    this.stateID = stateID;
  }

  @Override
  public int getID() {
    return stateID;
  }

  @Override
  public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    this.stateBasedGame = stateBasedGame;
    background = FileHandler.loadImage("backgroundMenu");
    logo = FileHandler.loadImage("logo");
    FileHandler.getSystem();
    FileHandler.getLastLevelNumber();
    menuButton = new AnimatedMenuButton[3];
    int lastLevelNumber = FileHandler.getLastLevelNumber();
    if (lastLevelNumber > 1) {
      menuButton[0] = new AnimatedMenuButton(245, 500, FileHandler.loadImage("continueGame_down"), FileHandler.loadImage("continueGame"));
      continueGameButton = true;
    } else {
      menuButton[0] = new AnimatedMenuButton(245, 500, FileHandler.loadImage("startGame_down"), FileHandler.loadImage("startGame"));
      continueGameButton = false;
    }
    menuButton[1] = new AnimatedMenuButton(495, 500, FileHandler.loadImage("levelSelect_down"), FileHandler.loadImage("levelSelect"));
    menuButton[2] = new AnimatedMenuButton(745, 500, FileHandler.loadImage("endGame_down"), FileHandler.loadImage("endGame"));
    currentSelection = 0;
  }

  @Override
  public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    int lastLevelNumber = FileHandler.getLastLevelNumber();
    if (continueGameButton) {
      // make sure to change the button back to "Spiel Starten", if the progress
      // is reset
      if (lastLevelNumber == 1) {
        menuButton[0] = new AnimatedMenuButton(245, 500, FileHandler.loadImage("startGame_down"), FileHandler.loadImage("startGame"));
        continueGameButton = false;
      }
    } else {
      if (lastLevelNumber > 1) {
        menuButton[0] = new AnimatedMenuButton(245, 500, FileHandler.loadImage("continueGame_down"), FileHandler.loadImage("continueGame"));
        continueGameButton = true;
      }
    }
  }

  @Override
  public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {

  }

  @Override
  public void keyPressed(int key, char c) {
    if (key == Input.KEY_LEFT) {
      if (currentSelection > 0) {
        currentSelection--;
      }
    } else if (key == Input.KEY_RIGHT) {
      if (currentSelection < 2) {
        currentSelection++;
      }
    } else if (key == Input.KEY_ENTER) {
      if (currentSelection == 0) {
        int lastLevelNumber = FileHandler.getLastLevelNumber();
        ((GameState) stateBasedGame.getState(Lecturattack.GAMESTATE)).loadLevel(lastLevelNumber);
        stateBasedGame.enterState(Lecturattack.GAMESTATE);
      }
      if (currentSelection == 1) {
        stateBasedGame.enterState(Lecturattack.LEVELSELECTSTATE);
      }
      if (currentSelection == 2) {
        System.exit(0);
      }
    }
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    graphics.drawImage(background, 0, 0);
    graphics.drawImage(logo, 250, 70);
    for (int i = 0; i < menuButton.length; i++) {
      // check if the button currently has focus
      if (currentSelection == i) {
        menuButton[i].setActive();
      }
      menuButton[i].render(gameContainer, stateBasedGame, graphics);
    }
  }

}
