package Lecturattack.entities;
/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @Author Tim Adamek
 *
 */
public class Player implements Renderable {
  private Image bodyImage;
  private Image armImage;
  private float angle; //the current angle of the player's arm
  private boolean isThrowing;
  private Projectile projectile;
  private PowerSlider powerSlider;

  public Player(Image playerImage, Image armImage, ProjectileMeta projectileMeta) {
    this.bodyImage = playerImage;
    this.armImage = armImage;
    this.projectile = new Projectile(projectileMeta);
  }

  public void reset() {
    isThrowing = false;
  }


  public void moveArm(float degreeDifference) {
    //todo: check if movement possible, turn arm etc.
  }

  /**
   *
   *
   * @param strength
   */
  public final Projectile throwProjectile(float strength) {
    isThrowing = true;
    return null;
  }


  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {

  }
}
