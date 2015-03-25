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

  public Player() {
  }

  public Player(Image playerImage, Image armImage, ProjectileMeta projectileMeta) {
    this.bodyImage = playerImage;
    this.armImage = armImage;
    this.projectile = new Projectile(projectileMeta, 0f, 0f); //todo: set actual position for the projectile
  }

  public float getPositionY() {
    return positionY;
  }

  public void setPositionY(float positionY) {
    this.positionY = positionY;
  }

  public float getPositionX() {
    return positionX;
  }

  public void setPositionX(float positionX) {
    this.positionX = positionX;
  }

  public Image getBodyImage() {
    return bodyImage;
  }

  public void setBodyImage(Image bodyImage) {
    this.bodyImage = bodyImage;
  }

  public Image getArmImage() {
    return armImage;
  }

  public void setArmImage(Image armImage) {
    this.armImage = armImage;
  }

  public void reset() {
    isThrowing = false;
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
  }


}
