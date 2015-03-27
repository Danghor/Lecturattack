package Lecturattack.entities;
/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Tim Adamek
 * @author Nick Steyer
 */
public class Player implements Renderable {

  private Image bodyImage;
  private Image armImage;
  private float angle; //the current angle of the player's arm
  private boolean isThrowing;
  private Projectile projectile;
  private PowerSlider powerSlider;
  private float positionX;
  private float positionY;
  private ProjectileMeta projectileMeta;

  public Player(/*ProjectileMeta projectileMeta, */ Image playerImage/*, Image armImage*/) {
    //this.projectileMeta = projectileMeta;
    this.positionX = 0f;
    this.positionY = 0f;
    this.bodyImage = playerImage;
    //this.armImage = armImage;
  }

  public void setPositionY(float positionY) {
    this.positionY = positionY;
  }

  public void setPositionX(float positionX) {
    this.positionX = positionX;
  }

  public void reset() {
    isThrowing = false;
    projectile = new Projectile(projectileMeta, 0f, 0f); //todo: set actual position for the projectile
  }


  public void moveArm(float degreeDifference) {
    //todo: check if movement possible, turn arm etc.
  }

  /**
   * @param strength
   */
  public final Projectile throwProjectile(float strength) {
    isThrowing = true;
    return null;
  }


  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    graphics.drawImage(bodyImage, positionX, positionY);

    if (!isThrowing) {
//todo: place projectile on hand and rotate correctly, this is just for testing
      //projectile = new Projectile(ProjectileMeta.getInstance(ProjectileMeta.ProjectileType.POINTER), 0f, 0f);
      //projectile.render(gameContainer, stateBasedGame, graphics);
    }

  }
}
