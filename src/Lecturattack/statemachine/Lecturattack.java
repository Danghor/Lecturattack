package Lecturattack.statemachine;/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Andreas Geis
 */
public class Lecturattack extends StateBasedGame {
  public static final int MAINMENUSTATE = 0;
  public static final int LEVELSELECTSTATE = 1;
  public static final int GAMESTATE = 2;

  public static final int FPS = 60;
  public static final int WIDTH = 1280;
  public static final int HEIGHT = 720;
  public static final boolean TOGGLE_FULLSCREEN = false;

  public Lecturattack(String name) {
    super(name);
  }

  public static void main(String[] args) {
    try {
      AppGameContainer appGameContainer = new AppGameContainer(new Lecturattack("Lecturattack"));
      appGameContainer.setTargetFrameRate(FPS);
      appGameContainer.setDisplayMode(WIDTH, HEIGHT, TOGGLE_FULLSCREEN);
      // appGameContainer.setShowFPS(false);
      appGameContainer.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initStatesList(GameContainer gameContainer) throws SlickException {
    addState(new MainMenuState(MAINMENUSTATE));
    addState(new LevelSelectState(LEVELSELECTSTATE));
    addState(new GameState(GAMESTATE));
  }
}
