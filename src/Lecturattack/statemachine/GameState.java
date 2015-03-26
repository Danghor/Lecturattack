package Lecturattack.statemachine;/*
 * Copyright (c) 2015.
 */

import Lecturattack.entities.Flag;
import Lecturattack.entities.InformationField;
import Lecturattack.entities.Projectile;
import Lecturattack.entities.Target;
import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.Level;
import Lecturattack.utilities.LevelGenerator;
import Lecturattack.utilities.PhysicsEngine;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelElement;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.List;

/**
 * @author Tim Adamek, Andreas Geis
 */

public class GameState extends BasicGameState implements InputListener {
  private static int stateID;
  private StateBasedGame stateBasedGame;
  private int currentLevel;
  private float wind;

  private Level level;
  private Projectile projectile;
  private Flag flag;
  private InformationField score;
  private InformationField playerName;

  public GameState(int stateID) {
    GameState.stateID = stateID;
  }

  public void loadLevel(int level) {
    try {//TODO see if exeption can be dealt with somewhere else
      List<LevelElement> levelElements = FileHandler.getLevelData(level);
      FileHandler.loadTargetConfig();
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

    currentLevel = 1; //default
    resetLevel();
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

    level.getPlayer().render(gameContainer, stateBasedGame, graphics);

    for (Target target : level.getTargets()) {
      target.render(gameContainer, stateBasedGame, graphics);
    }

  }

  @Override
  public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    PhysicsEngine.calculateStep(null, null, 0, 0);//TODO real values
  }

  @Override
  public void keyPressed(int key, char c) {
    if (key == Input.KEY_ESCAPE) {
      stateBasedGame.enterState(Lecturattack.PAUSESTATE);
    }
  }

}
