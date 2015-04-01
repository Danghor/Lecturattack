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
 * @author Andreas Geis
 */
public class Player implements Renderable {

  //TODO remove
  private float strength = 54;
  private Image bodyImage;
  private Image armImage;
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

  private enum PlayerState {
    ANGLE_SELECTION, POWER_SLIDER, THROWING
  }

  private PlayerState playerState;

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

    armShoulderX = armImageX + armImage.getWidth() / 2;
    armShoulderY = armImageY + armImage.getHeight() / 2;


    //set the position of the projectile to be on the hand
    // +Math.PI/4 reduces changes the angle, the hand is not at the position where it wouldbe
    setProjectilePosition();
  }

  public float getAngle() {
    return directionAngle;
  }

  public void setAngle(float angleInDegrees) {
    float difference = angleInDegrees - directionAngle;
    moveArm(difference);
  }

  public void reset() {
    playerState = PlayerState.ANGLE_SELECTION;
    directionAngle = 0;
    projectile = new Projectile(projectileMeta, 0f, 0f);
    powerSlider = new PowerSlider();
    setProjectilePosition();
  }

  public void moveArm(float degreeDifference) {
    // todo: check if movement possible, turn arm etc.
    if (playerState == PlayerState.ANGLE_SELECTION) {
      this.directionAngle += degreeDifference;
      setProjectilePosition();
    }
  }

  /**
   * lock the current Selection (setAngle or setPower) and increment the
   * playerState do nothing if the player already threw the projectile
   *
   * @return projectile
   */
  public final Projectile throwProjectile() {
    if (playerState == PlayerState.ANGLE_SELECTION) {
      playerState = PlayerState.POWER_SLIDER;
      return null;
    } else if (playerState == PlayerState.POWER_SLIDER) {
      playerState = PlayerState.THROWING;
      float velocityX = (float) Math.cos(Math.toRadians(directionAngle) + Math.PI / 4) + powerSlider.getForce();
      float velocityY = (float) Math.sin(Math.toRadians(directionAngle) + Math.PI / 4) + powerSlider.getForce();
      projectile.applyForce(velocityX, velocityY);
      return projectile;
    } else {
      playerState = PlayerState.THROWING;
      return null;
    }
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    armImage.setRotation(directionAngle);
    graphics.drawImage(bodyImage, positionX, positionY);
    graphics.drawImage(armImage, armImageX, armImageY);
    if (playerState != PlayerState.THROWING) {
      //todo: set position to middle of the player's hand
      projectile.setCenterPosition(handCenterPositionX, handCenterPositionY);
      projectile.render(gameContainer, stateBasedGame, graphics);
    }
    if (playerState == PlayerState.POWER_SLIDER || playerState == PlayerState.THROWING) {
      powerSlider.render(gameContainer, stateBasedGame, graphics);
    }
  }

  public void update(int delta) {
    if (playerState == PlayerState.POWER_SLIDER) {
      powerSlider.update(delta);
    }
  }

  private void setProjectilePosition() {
    this.handCenterPositionX = ((float) Math.cos(Math.toRadians(directionAngle) + Math.PI / 4) * strength) + armShoulderX;
    this.handCenterPositionY = ((float) Math.sin(Math.toRadians(directionAngle) + Math.PI / 4) * strength) + armShoulderY;
  }
}
