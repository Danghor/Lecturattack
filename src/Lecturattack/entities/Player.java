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

  //todo  comment on use of amplifier
  public static final float amplifier = 3f;
  //this constant is the translation of the throw angle, so that the angle is tangential
  private static final double THROW_ANGLE_TRANSLATION = Math.PI / 6;
  //this constant is the translation of the angle, which is used in calculating the middle of the player hand
  private static final double PROJECTILE_ANGLE_TRANSLATION = Math.PI / 4;
  //this constant specifies a scale, it is scaling up the value of the trigonometric calculation, for the projectile in the hand of the player,
  //this is needed, because these calculations are for a circle with a radius of 1, in this case the radius is bigger so it must be scaled
  private static final float projectileOnHandScale = 54;
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
  private PlayerState playerState;
  private String name;

  public Player(Image bodyImage, Image armImage, ProjectileMeta projectileMeta, String name) {
    this.positionX = 0f;
    this.positionY = 0f;
    this.bodyImage = bodyImage;
    this.armImage = armImage;
    this.projectileMeta = projectileMeta;
    this.name = name;
    this.powerSlider = new PowerSlider();
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
    setHandCenterPosition();
  }

  public float getAngle() {
    return directionAngle;
  }

  public void setAngle(float angleInDegrees) {
    float difference = angleInDegrees - directionAngle;
    moveArm(difference);
  }

  public String getName() {
    return name;
  }

  public PlayerState getPlayerState() {
    return playerState;
  }

  public void reset() {
    playerState = PlayerState.ANGLE_SELECTION;
    directionAngle = 0;
    projectile = new Projectile(projectileMeta, 0f, 0f);
    setHandCenterPosition();
    powerSlider.reset();
  }

  public void moveArm(float degreeDifference) {
    if (playerState == PlayerState.ANGLE_SELECTION) {
      // the player can only move his arm in a certain angle,
      // this checks if the player can still move his arm
      if (degreeDifference > 0 && directionAngle < 20) {
        this.directionAngle += degreeDifference;
      } else if (degreeDifference < 0 && directionAngle > -180) {
        this.directionAngle += degreeDifference;
      }
      setHandCenterPosition();
    }
  }

  /**
   * lock the current Selection (setAngle or setPower) and increment the
   * playerState do nothing if the player already threw the projectile
   *
   * @return projectile
   */
  public final Projectile throwProjectile() {
    Projectile projectileReturned = null;

    if (playerState == PlayerState.ANGLE_SELECTION) {
      playerState = PlayerState.POWER_SLIDER;
    } else if (playerState == PlayerState.POWER_SLIDER) {
      playerState = PlayerState.THROWING;
      float velocityY = ((float) Math.cos(Math.toRadians(directionAngle) + THROW_ANGLE_TRANSLATION) * powerSlider.getSelectedForce());
      float velocityX = -((float) Math.sin(Math.toRadians(directionAngle) + THROW_ANGLE_TRANSLATION) * powerSlider.getSelectedForce());
      projectile.applyForce(velocityX * amplifier, velocityY * amplifier);
      projectileReturned = projectile;
    }

    return projectileReturned;
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
    graphics.drawLine(armShoulderX + (float) Math.cos(Math.toRadians(directionAngle) + PROJECTILE_ANGLE_TRANSLATION) * projectileOnHandScale,
            armShoulderY + (float) Math.sin(Math.toRadians(directionAngle) + PROJECTILE_ANGLE_TRANSLATION) * projectileOnHandScale,
            armShoulderX + (float) Math.cos(Math.toRadians(directionAngle) + PROJECTILE_ANGLE_TRANSLATION) * projectileOnHandScale - (float) Math.sin(Math.toRadians(directionAngle) + THROW_ANGLE_TRANSLATION) * projectileOnHandScale,
            armShoulderY + (float) Math.sin(Math.toRadians(directionAngle) + PROJECTILE_ANGLE_TRANSLATION) * projectileOnHandScale + (float) Math.cos(Math.toRadians(directionAngle) + THROW_ANGLE_TRANSLATION) * projectileOnHandScale);
  }

  public void updatePowerSlider(int delta) {
    if (playerState == PlayerState.POWER_SLIDER) {
      powerSlider.update(delta);
    }
  }

  private void setHandCenterPosition() {
    this.handCenterPositionX = ((float) Math.cos(Math.toRadians(directionAngle) + PROJECTILE_ANGLE_TRANSLATION) * projectileOnHandScale) + armShoulderX;
    this.handCenterPositionY = ((float) Math.sin(Math.toRadians(directionAngle) + PROJECTILE_ANGLE_TRANSLATION) * projectileOnHandScale) + armShoulderY;
  }

  public enum PlayerState {
    ANGLE_SELECTION, POWER_SLIDER, THROWING
  }
}
