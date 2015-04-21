package Lecturattack.entities;

/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Tim Adamek
 * @author Nick Steyer
 * @author Andreas Geis
 */
public class Player implements Renderable {

  //amplifies the force returned by the powerSlider so the player actually throws harder
  private static final float forceAmplifier = 3f;

  //amplifies the force returned by the powerSlider so that the spin of the projectile thrown is much faster
  private static final float torqueAmplifier = 15f;

  //this constant is the translation of the throw angle, so that the angle is tangential
  private static final double THROW_ANGLE_TRANSLATION = Math.PI / 6;
  //this constant is the translation of the angle, which is used in calculating the middle of the player hand
  private static final double PROJECTILE_ANGLE_TRANSLATION = Math.PI / 4;
  //this constant specifies a scale, it is scaling up the value of the trigonometric calculation, for the projectile in the hand of the player,
  //this is needed, because these calculations are for a circle with a radius of 1, in this case the radius is bigger so it must be scaled
  private static final float projectileOnHandScale = 54;
  // the decrees the arm is rotated very update
  private static final int DEGREE_ARM_MOVE = 1;

  private Image bodyImage;
  private Image armImage;
  private Projectile projectile;
  private PowerSlider powerSlider;
  private Point playerPosition;   //the top left position of the player
  private Point handCenterPosition;   //the center of the hand;
  private Point armImagePosition;   //the top left of the arm image
  private ProjectileMeta projectileMeta;
  private float directionAngle;
  private PlayerState playerState;
  private String name;

  public Player(Image bodyImage, Image armImage, ProjectileMeta projectileMeta, String name) {
    playerPosition = new Point(0, 0);
    handCenterPosition = new Point(0, 0);
    armImagePosition = new Point(0, 0);
    this.bodyImage = bodyImage;
    this.armImage = armImage;
    this.projectileMeta = projectileMeta;
    this.name = name;
    this.powerSlider = new PowerSlider();
    reset();
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    armImage.setRotation(directionAngle);
    graphics.drawImage(bodyImage, playerPosition.getX(), playerPosition.getY());
    graphics.drawImage(armImage, armImagePosition.getX(), armImagePosition.getCenterY());
    if (playerState != PlayerState.THROWING) {
      projectile.setCenterPosition(handCenterPosition.getX(), handCenterPosition.getCenterY());
      projectile.render(gameContainer, stateBasedGame, graphics);
    }
    if (playerState == PlayerState.POWER_SLIDER || playerState == PlayerState.THROWING) {
      powerSlider.render(gameContainer, stateBasedGame, graphics);
    }
  }

  public void moveArm(float degreeDifference) {
    if (playerState == PlayerState.ANGLE_SELECTION) {
      // the player can only move his arm in a certain angle,
      // this checks if the player can still move his arm
      if ((degreeDifference > 0 && directionAngle < 20) || (degreeDifference < 0 && directionAngle > -180)) {
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

      projectile.applyForce(velocityX * forceAmplifier, velocityY * forceAmplifier);
      projectile.applyTorque(powerSlider.getSelectedForce() * torqueAmplifier);

      projectileReturned = projectile;
    }
    return projectileReturned;
  }

  public void updatePowerSlider(int delta) {
    if (playerState == PlayerState.POWER_SLIDER) {
      powerSlider.update(delta);
    }
  }

  public void reset() {
    playerState = PlayerState.ANGLE_SELECTION;
    projectile = new Projectile(projectileMeta, 0f, 0f);
    setHandCenterPosition();
    powerSlider.reset();
  }

  private void setHandCenterPosition() {
    handCenterPosition.setX(((float) Math.cos(Math.toRadians(directionAngle) + PROJECTILE_ANGLE_TRANSLATION) * projectileOnHandScale) + armImagePosition.getX() + armImage.getWidth() / 2);
    handCenterPosition.setY(((float) Math.sin(Math.toRadians(directionAngle) + PROJECTILE_ANGLE_TRANSLATION) * projectileOnHandScale) + armImagePosition.getY() + armImage.getHeight() / 2);
  }

  public void setPosition(float x, float y) {
    playerPosition.setX(x);
    playerPosition.setY(y);

    //the position of the arm image must be set in relation to the player,
    //the shoulder is NOT the top left corner but the middle of the image
    armImagePosition.setX(x - 42);
    armImagePosition.setY(y + 9);

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

  public enum PlayerState {
    ANGLE_SELECTION, POWER_SLIDER, THROWING
  }
}
