package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.FileHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Andreas Geis
 */
public class PowerSlider implements Renderable {
  // powerslide.png has 255px and powserslideLine is 5px width
  private static final int MAX_FORCE = 250;
  private static final float SCALE = 0.5f;
  private float force;
  private boolean movingRight;
  private Image powerslide;
  private Image powerslideLine;

  public PowerSlider() {
    powerslide = FileHandler.loadImage("powerslide");
    powerslideLine = FileHandler.loadImage("powerslideLine");
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    graphics.drawImage(powerslide, 10, 380);
    graphics.drawImage(powerslideLine, 10 + force, 368);
  }

  /**
   * updates the PowerSlider, depending on direction it either moves right or left
   *
   * @param delta The time-difference compared to the previous step.
   */
  public void update(int delta) {
    
    if (movingRight) {
      force += SCALE * delta;
      if (force > MAX_FORCE) {
        force = MAX_FORCE;
        movingRight = false;
      }
    } else {
      force -= SCALE * delta;
      if (force < 0) {
        force = 0;
        movingRight = true;
      }
    }
  }

  public float getForce() {
    return force;
  }

  public void reset() {
    force = 0;
    movingRight = true;
  }
}
