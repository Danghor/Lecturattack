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
public class MainMenuState extends BasicGameState implements InputListener {

  private int iStateID;
  private StateBasedGame stateBasedGame;
  private Image background;
  private Image logo;
  private AnimatedButton[] menuButton;
  private int iMenuSelector;

  public MainMenuState(int iStateID) {
    this.iStateID = iStateID;
  }

  @Override
  public int getID() {
    return iStateID;
  }

  @Override
  public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    this.stateBasedGame = stateBasedGame;
    background = FileHandler.createMenuBackground();
    logo = FileHandler.createMenuLogo();
    menuButton = FileHandler.createMainMenuButtons();
    iMenuSelector = 0;
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
      if (iMenuSelector > 0) {
        iMenuSelector--;
      }
    } else if (key == Input.KEY_RIGHT) {
      if (iMenuSelector < 2) {
        iMenuSelector++;
      }
    } else if (key == Input.KEY_ENTER) {
      if (iMenuSelector == 0) {
        stateBasedGame.enterState(2);
      }
      if (iMenuSelector == 1) {
        stateBasedGame.enterState(1);
      }
      if (iMenuSelector == 2) {
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
      menuButton[i].render(graphics, iMenuSelector == i);
    }
  }

}
