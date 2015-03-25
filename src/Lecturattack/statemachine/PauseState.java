package Lecturattack.statemachine;/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.menu.AnimatedButton;

/**
 * @author Andreas Geis
 */
public class PauseState extends BasicGameState implements InputListener {
  private StateBasedGame stateBasedGame;
  private static int stateID;
  private Image background;
  private AnimatedButton[] menuButton;
  private int iMenuSelector;

  public PauseState(int iStateID) {
    this.stateID = iStateID;
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
    iMenuSelector = 0;
  }

  @Override
  public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    graphics.drawImage(background, 0, 0);
    for (int i = 0; i < menuButton.length; i++) {
      menuButton[i].render(graphics, iMenuSelector == i);
    }
  }

  @Override
  public void keyPressed(int key, char c) {
    if (key == Input.KEY_LEFT || key == Input.KEY_RIGHT) {
      if (iMenuSelector == 0) {
        iMenuSelector++;
      } else if (iMenuSelector == 1) {
        iMenuSelector--;
      }
    } else if (key == Input.KEY_ENTER) {
      if (iMenuSelector == 0) {
        // continue the game
        stateBasedGame.enterState(2);
      } else if (iMenuSelector == 1) {
        // go back to MainMenu
        stateBasedGame.enterState(0);
      }
    }
  }

}
