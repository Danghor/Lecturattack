package Lecturattack.statemachine;/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.menu.AnimatedButton;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Andreas Geis
 */
public class MainMenuState extends BasicGameState implements InputListener {

  private int stateID;
  private StateBasedGame stateBasedGame;
  private Image background;
  private Image logo;
  private AnimatedButton[] menuButton;
  private int currentSelection;

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
    FileHandler.getLastLevelNumber();
    menuButton = new AnimatedButton[3];
    menuButton[0] = new AnimatedButton(245, 500, FileHandler.loadImage("startGame_down"), FileHandler.loadImage("startGame"));
    menuButton[1] = new AnimatedButton(495, 500, FileHandler.loadImage("levelSelect_down"), FileHandler.loadImage("levelSelect"));
    menuButton[2] = new AnimatedButton(745, 500, FileHandler.loadImage("endGame_down"), FileHandler.loadImage("endGame"));
    currentSelection = 0;
  }

  @Override
  public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {

  }

  /*
   * listen for user input
   */
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
        // TODO load current level from file and start that level
        ((GameState) stateBasedGame.getState(GameState.stateID)).loadLevel(1);
        stateBasedGame.enterState(2);
      }
      if (currentSelection == 1) {
        stateBasedGame.enterState(1);
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
      menuButton[i].render(graphics, currentSelection == i);
    }
  }

}
