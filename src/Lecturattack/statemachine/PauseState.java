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
public class PauseState extends BasicGameState implements InputListener {
  private static int stateID;
  private StateBasedGame stateBasedGame;
  private Image background;
  private AnimatedButton[] menuButton;
  private int menuSelector;

  public PauseState(int iStateID) {
    stateID = iStateID;
  }

  @Override
  public int getID() {
    return stateID;
  }

  @Override
  public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    this.stateBasedGame = stateBasedGame;
    background = FileHandler.createMenuBackground();
    menuButton = FileHandler.createPauseMenuButtons();
    menuSelector = 0;
  }

  @Override
  public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    graphics.drawImage(background, 0, 0);
    for (int i = 0; i < menuButton.length; i++) {
      menuButton[i].render(graphics, menuSelector == i);
    }
  }

  @Override
  public void keyPressed(int key, char c) {
    if (key == Input.KEY_LEFT || key == Input.KEY_RIGHT) {
      if (menuSelector == 0) {
        menuSelector++;
      } else if (menuSelector == 1) {
        menuSelector--;
      }
    } else if (key == Input.KEY_ENTER) {
      if (menuSelector == 0) {
        // continue the game
        stateBasedGame.enterState(2);
      } else if (menuSelector == 1) {
        // go back to MainMenu
        stateBasedGame.enterState(0);
      }
    }
  }

}
