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
class MainMenuState extends BasicGameState implements InputListener {
  private final int stateID;
  private final FileHandler fileHandler;
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
   * @param stateID The stateID to be set.
   */
  public MainMenuState(int stateID) {
    this.stateID = stateID;
    fileHandler = new FileHandler();
  }

  @Override
  public int getID() {
    return stateID;
  }

  @Override
  public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    this.stateBasedGame = stateBasedGame;
    background = fileHandler.loadImage("backgroundMenu");
    logo = fileHandler.loadImage("logo");
    fileHandler.getLastLevelNumber();//TODO return value not used
    menuButton = new AnimatedMenuButton[3];
    // check if the first button has to say "Spiel starten" or "Spiel fortsetzen" depending on player progress
    int lastLevelNumber = fileHandler.getLastLevelNumber();
    if (lastLevelNumber > 1) {
      menuButton[0] = new AnimatedMenuButton(245, 500, fileHandler.loadImage("continueGame_down"), fileHandler.loadImage("continueGame"));
      continueGameButton = true;
    } else {
      menuButton[0] = new AnimatedMenuButton(245, 500, fileHandler.loadImage("startGame_down"), fileHandler.loadImage("startGame"));
      continueGameButton = false;
    }
    menuButton[1] = new AnimatedMenuButton(495, 500, fileHandler.loadImage("levelSelect_down"), fileHandler.loadImage("levelSelect"));
    menuButton[2] = new AnimatedMenuButton(745, 500, fileHandler.loadImage("endGame_down"), fileHandler.loadImage("endGame"));
    currentSelection = 0;
  }

  @Override
  public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    int lastLevelNumber = fileHandler.getLastLevelNumber();
    if (continueGameButton) {
      // make sure to change the button back to "Spiel Starten", if the progress
      // is reset
      if (lastLevelNumber == 1) {
        menuButton[0] = new AnimatedMenuButton(245, 500, fileHandler.loadImage("startGame_down"), fileHandler.loadImage("startGame"));
        continueGameButton = false;
      }
    } else {
      // if the player unlocked a new level and the first button was set to "Spiel Starten", then change the button to "Spiel Fortsetzen"
      if (lastLevelNumber > 1) {
        menuButton[0] = new AnimatedMenuButton(245, 500, fileHandler.loadImage("continueGame_down"), fileHandler.loadImage("continueGame"));
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
        int lastLevelNumber = fileHandler.getLastLevelNumber();
        ((GameState) stateBasedGame.getState(Lecturattack.GAME_STATE)).loadLevel(lastLevelNumber);
        stateBasedGame.enterState(Lecturattack.GAME_STATE);
      }
      if (currentSelection == 1) {
        stateBasedGame.enterState(Lecturattack.LEVELSELECT_STATE);
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
