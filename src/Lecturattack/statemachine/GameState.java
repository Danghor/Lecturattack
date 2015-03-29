package Lecturattack.statemachine;/*
 * Copyright (c) 2015.
 */

import Lecturattack.entities.*;
import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.Level;
import Lecturattack.utilities.LevelGenerator;
import Lecturattack.utilities.PhysicsEngine;
import Lecturattack.utilities.xmlHandling.configLoading.PlayerStandard;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelElement;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tim Adamek
 * @author Andreas Geis
 * @author Nick Steyer
 */

public class GameState extends BasicGameState implements InputListener {
  private static int stateID;
  private StateBasedGame stateBasedGame;
  private int currentLevel;
  private float wind;
  private ArrayList<Player> players;
  private int currentPlayer;
  private Level level;
  private Projectile projectile;
  private Flag flag;
  private InformationField score;
  private InformationField playerName;
  private Image background;

  public GameState(int stateID) {
    GameState.stateID = stateID;
  }

  public void loadLevel(int level) {
    background = FileHandler.loadImage("background");
    //TODO see if this can be done somwhere else
    try {//TODO see if exeption can be dealt with somewhere else
      List<LevelElement> levelElements = FileHandler.getLevelData(level);
      this.level = LevelGenerator.getGeneratedLevel(levelElements);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void resetLevel() {
    loadLevel(currentLevel);
  }

  @Override
  public int getID() {
    return stateID;
  }

  @Override
  public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    this.stateBasedGame = stateBasedGame;

    players = new ArrayList<>();

    List<PlayerStandard> playerStandards = FileHandler.getPlayerData();
    for (PlayerStandard meta : playerStandards) {
      //TODO dont't create the image here
      players.add(new Player(meta.getBodyImageAsImage(),meta.getArmImageAsImage(),meta.getProjectileMeta()));
    }
    currentPlayer = 0;
    currentLevel = 1; //default
    resetLevel();
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    graphics.drawImage(background, 0, 0);
    players.get(currentPlayer).render(gameContainer, stateBasedGame, graphics);
    for (Target target : level.getTargets()) {
      target.render(gameContainer, stateBasedGame, graphics);
    }

    if (projectile != null) {
      projectile.render(gameContainer, stateBasedGame, graphics);
    }

  }


  @Override
  public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
    wind = (float) ((Math.random() * 10) % 5);
    if (gameContainer.getInput().isKeyDown(Input.KEY_RIGHT)) {
      players.get(currentPlayer).moveArm(1);//TODO constants for angle
    } else if (gameContainer.getInput().isKeyDown(Input.KEY_LEFT)) {
      players.get(currentPlayer).moveArm(-1);
    } else if (gameContainer.getInput().isKeyDown(Input.KEY_UP)) {
      projectile = players.get(currentPlayer).throwProjectile(1);
    } else if (gameContainer.getInput().isKeyDown(Input.KEY_DOWN)) {
      projectile = players.get(currentPlayer).throwProjectile(-1);
    }

    PhysicsEngine.calculateStep(null, null, wind, delta);//TODO real values
  }


  @Override
  public void keyPressed(int key, char c) {
    if (key == Input.KEY_ESCAPE) {
      stateBasedGame.enterState(Lecturattack.PAUSESTATE);
    }
  }
}
