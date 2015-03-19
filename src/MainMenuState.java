/*
 * Copyright (c) 2015.
 */

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Andreas Geis on 18/03/2015
 */
public class MainMenuState extends BasicGameState {

  private int iStateID;

  private AnimatedButton[] menuButton = new AnimatedButton[3];
  private Image placeholderImage;
  private Image placeholderImage2;

  private TextField oTitle;
  private int iMenuSelector = 0;

  private java.awt.Font oAwtFont = new java.awt.Font("Sanserif", java.awt.Font.BOLD, 24);
  private TrueTypeFont oTrueTypeFont = new TrueTypeFont(oAwtFont, false);

  private boolean bInputPossible;

  private int iIntervall;

  public MainMenuState(int iStateID) {
    this.iStateID = iStateID;
  }

  @Override
  public int getID() {
    return iStateID;
  }

  @Override
  public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    // TODO: check if the player is in level 2 or higher and replace the
    // "Spiel Starten" image with "Spiel fortsetzen" for Button 1
    placeholderImage = new Image("resources/images/placeholder.png");
    placeholderImage2 = new Image("resources/images/placeholder2.png");
    for (int i = 0; i < menuButton.length; i++) {
      menuButton[i] = new AnimatedButton(200, 300 + (i * 60), placeholderImage, placeholderImage2);
    }
    TrueTypeFont oTitlefont = new TrueTypeFont(new Font("Sanserif", Font.BOLD, 40), true);

    oTitle = new TextField(gameContainer, oTitlefont, 180, 100, 300, 60);

    oTitle.setBorderColor(Color.black);
    oTitle.setText("Lecturattack");
  }

  @Override
  public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int dt) throws SlickException {

    keyboardNavigation(gameContainer);
    // has to be set other�wise it is possible to change text while in
    // GameState (playstate)
    oTitle.setFocus(false);
    initiateGameStat(gameContainer, stateBasedGame);
  }

  private void keyboardNavigation(GameContainer oContainer) {
    if (!bInputPossible) {
      if (iIntervall == 10) {
        iIntervall = 0;
        bInputPossible = true;
      } else {
        iIntervall++;
      }
    } else {

      if (oContainer.getInput().isKeyDown(Input.KEY_DOWN)) {
        if (iMenuSelector < 2) {
          iMenuSelector++;
          bInputPossible = false;
        } else {
          iMenuSelector = 0;
          bInputPossible = false;
        }
      }
      if (oContainer.getInput().isKeyDown(Input.KEY_UP)) {
        if (iMenuSelector > 0) {
          iMenuSelector--;
          bInputPossible = false;
        } else {
          iMenuSelector = 2;
          bInputPossible = false;
        }
      }
    }
  }

  private void initiateGameStat(GameContainer gameContainer, StateBasedGame stateBasedGame) {
    // if (gameContainer.getInput().isKeyPressed(Input.KEY_ENTER)) {
    if (gameContainer.getInput().isKeyPressed(Input.KEY_ENTER)) {
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
    oTitle.render(gameContainer, graphics);
    boolean focus;
    for (int i = 0; i < menuButton.length; i++) {
      if (iMenuSelector == i) {
        focus = true;
      } else {
        focus = false;
      }
      menuButton[i].render(graphics, focus);
    }
  }

}
