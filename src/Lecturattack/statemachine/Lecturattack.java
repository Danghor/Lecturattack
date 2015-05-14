package Lecturattack.statemachine;/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.FileHandler;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author Andreas Geis
 * @author Tim Adamek
 */
public class Lecturattack extends StateBasedGame {
  public static final int MAINMENU_STATE = 0;
  public static final int LEVELSELECT_STATE = 1;
  public static final int GAME_STATE = 2;
  public static final int PAUSE_STATE = 3;
  public static final int WIDTH = 1280;
  public static final int HEIGHT = 720;
  private static final boolean DEBUG = false; //toggle debug mode with console output
  private static final boolean TOGGLE_FULLSCREEN = false;
  private static final int TARGET_FPS = 80;

  private Lecturattack() {
    super("Lecturattack");
  }

  /**
   * Main method. Disables the printStream in production, plays the background music and starts the game.
   *
   * @param args Not used.
   */
  public static void main(String[] args) {

    //disable the printStream in the release version so no console output is produced in production
    if (!DEBUG) {
      PrintStream nullStream = new PrintStream(new OutputStream() {
        @Override
        public void write(int b) throws IOException {
        }
      });

      System.setErr(nullStream);
      System.setOut(nullStream);
    }

    try {
      AppGameContainer appGameContainer = new AppGameContainer(new Lecturattack());
      appGameContainer.setTargetFrameRate(TARGET_FPS);
      appGameContainer.setDisplayMode(WIDTH, HEIGHT, TOGGLE_FULLSCREEN);
      appGameContainer.setShowFPS(false);
      startBackgroundMusic();
      appGameContainer.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }

  /**
   * Starts playing the background music in a loop.
   */
  private static void startBackgroundMusic() {
    FileHandler fh = new FileHandler();
    Music bgMusic = fh.getBackgroundMusic();
    bgMusic.loop(1f, 0.5f);
  }

  /**
   * Initializes the different states the game can be in.
   *
   * @param gameContainer Not used.
   *
   * @throws SlickException
   */
  @Override
  public void initStatesList(GameContainer gameContainer) throws SlickException {
    addState(new MainMenuState(MAINMENU_STATE));
    addState(new LevelSelectState(LEVELSELECT_STATE));
    addState(new GameState(GAME_STATE));
    addState(new PauseState(PAUSE_STATE));
  }
}
