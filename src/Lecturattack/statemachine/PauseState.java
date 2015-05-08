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
class PauseState extends BasicGameState implements InputListener {
  private static int stateID;
  private final FileHandler fileHandler;
  private StateBasedGame stateBasedGame;
  private Image background;
  private AnimatedMenuButton[] menuButton;
  private int currentSelection;

  /**
   * Constructor for PauseState
   * Set the ID of this state to the given stateID
   *
   * @param stateID The stateID to be set.
   */
  public PauseState(int stateID) {
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
    menuButton = new AnimatedMenuButton[2];
    menuButton[0] = new AnimatedMenuButton(245, 500, fileHandler.loadImage("continue_down"), fileHandler.loadImage("continue"));
    menuButton[1] = new AnimatedMenuButton(745, 500, fileHandler.loadImage("backToMenu_down"), fileHandler.loadImage("backToMenu"));
  }

  @Override
  public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    graphics.drawImage(background, 0, 0);
    for (int i = 0; i < menuButton.length; i++) {
      // check if the button currently has focus
      if (currentSelection == i) {
        menuButton[i].setActive();
      }
      menuButton[i].render(gameContainer, stateBasedGame, graphics);
    }
  }

  @Override
  public void keyPressed(int key, char c) {
    if (key == Input.KEY_LEFT || key == Input.KEY_RIGHT) {
      if (currentSelection == 0) {
        currentSelection++;
      } else if (currentSelection == 1) {
        currentSelection--;
      }
    } else if (key == Input.KEY_ENTER) {
      if (currentSelection == 0) {
        // continue the game
        stateBasedGame.enterState(2);
      } else if (currentSelection == 1) {
        // go back to MainMenu
        stateBasedGame.enterState(0);
      }
    }
  }

  // reset the selection every time the state is entered
  @Override
  public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    currentSelection = 0;
  }

}
