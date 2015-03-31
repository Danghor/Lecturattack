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

  //TODO remove
  float strength = 54;
  private Image bodyImage;
  private Image armImage;
  private boolean isThrowing;
  private Projectile projectile;
  private PowerSlider powerSlider;
  private float positionX;
  private float positionY;
  private float handCenterPositionX;
  private float handCenterPositionY;
  private ProjectileMeta projectileMeta;
  private float directionAngle;

  private float armImageX;//must be set in relation to player
  private float armImageY;

  private float armShoulderX;//must be set in relation to player
  private float armShoulderY;


  public Player(Image bodyImage, Image armImage, ProjectileMeta projectileMeta) {
    this.positionX = 0f;
    this.positionY = 0f;
    this.bodyImage = bodyImage;
    this.armImage = armImage;
    this.projectileMeta = projectileMeta;
    reset();
  }



  public void setPosition(float x, float y) {
    positionX = x;
    positionY = y;

    //the position of the arm image must be set in relation to the player,
    //the shoulder is NOT the top left corner but the middle of the image
    armImageX = x - 42;
    armImageY = y + 9;

    armShoulderX=armImageX+armImage.getWidth()/2;
    armShoulderY=armImageY+armImage.getHeight()/2;


    //set the position of the projectile to be on the hand
    // +Math.PI/4 reduces changes the angle, the hand is not at the position where it wouldbe
    this.handCenterPositionX = ((float) Math.cos(Math.toRadians(directionAngle) + Math.PI / 4)* strength) + armShoulderX;
    this.handCenterPositionY = ((float) Math.sin(Math.toRadians(directionAngle) + Math.PI / 4) * strength) + armShoulderY;
  }

  public void reset() {
    isThrowing = false;
    projectile = new Projectile(projectileMeta, 0f, 0f);
  }

  public void moveArm(float degreeDifference) {
    //todo: check if movement possible, turn arm etc.
    if (!isThrowing) {
      this.directionAngle += degreeDifference;
      handCenterPositionX = ((float) Math.cos(Math.toRadians(directionAngle) + Math.PI / 4)* strength) + armShoulderX;
      handCenterPositionY = ((float) Math.sin(Math.toRadians(directionAngle) + Math.PI / 4) * strength) + armShoulderY;
    }
  }

  public final Projectile throwProjectile(float strength) {
    isThrowing = true;
    //TODO apply force to projectile
    return projectile;
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    armImage.setRotation(directionAngle);
    graphics.drawImage(bodyImage, positionX, positionY);
    graphics.drawImage(armImage, armImageX, armImageY);

    if (!isThrowing) {
      //todo: set position to middle of the player's hand
      graphics.drawRect(handCenterPositionX, handCenterPositionY, 5, 5);
      projectile.setCenterPosition(handCenterPositionX, handCenterPositionY);
      projectile.render(gameContainer, stateBasedGame, graphics);
    }
  }
}
