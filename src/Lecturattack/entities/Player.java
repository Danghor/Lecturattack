package Lecturattack.entities;

/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Tim Adamek
 * @author Nick Steyer
 * @author Andreas Geis
 * @author Stefanie Raschke
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
  // the degrees the arm is rotated every update
  private static final int DEGREE_ARM_MOVE = 1;

  private final Point playerPosition;   //the top left position of the player
  private final Point handCenterPosition;   //the center of the hand;
  private final Point armImagePosition;   //the top left of the arm image

  private final Image bodyImage;
  private final Image armImage;
  private final PowerSlider powerSlider;
  private final ProjectileMeta projectileMeta;
  private final String name;
  private final Sound sound;
  private Projectile projectile;
  private float directionAngle;
  private PlayerState playerState;
  private float animationAngle;  //the angle that the player has to move his arm for the animation (in degree)
  private boolean returnProjectile = false;  //defines if player can return projectile or not
  private boolean moveArmBack = false;  //the flag if the player has to move his arm back to throw
  private boolean moveArmForward;// flag if arm for animation cannot be moved any further back
  private long throwStart;

  public enum PlayerState {
    ANGLE_SELECTION, POWER_SLIDER, ANIMATION, THROWING
  }

  public Player(Image bodyImage, Image armImage, ProjectileMeta projectileMeta, String name, Sound sound) {
    playerPosition = new Point(0, 0);
    handCenterPosition = new Point(0, 0);
    armImagePosition = new Point(0, 0);
    this.bodyImage = bodyImage;
    this.armImage = armImage;
    this.projectileMeta = projectileMeta;
    this.name = name;
    this.sound = sound;
    this.powerSlider = new PowerSlider();
    reset();
  }

  /**
   * this method is called to update the arm animation
   * it can be called every calculation step because it is handled internally when the animation is visible
   */
  public void updateArmAnimation() {
    if (moveArmBack) {
      // the arm is moved back to -200 degrees
      if (directionAngle - animationAngle > -200) {
        // the speed of the arm back animation is based on the selected force + a fixed value
        // when the selected force is really low
        animationAngle += powerSlider.getSelectedForce() / 100 + 1;
      } else if (directionAngle - animationAngle <= -200) {
        //the arm has reached the end --> no longer needed to move back
        moveArmBack = false;
        moveArmForward = true;
      }
    } else if (moveArmForward) {
      if (animationAngle > 0) {
        // the speed of the arm forward animation is based on the selected force + a fixed value
        // when the selected force is really low
        animationAngle -= powerSlider.getSelectedForce() / 50 + 2;
      } else if (animationAngle <= 0) {
        moveArmForward = false; // animation finished
        returnProjectile = true;//projectile can now be returned because animation finished
        setThrowStart(System.currentTimeMillis());
        playerState = PlayerState.THROWING;
      }
    }
    setHandCenterPosition();
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    armImage.setRotation(directionAngle - animationAngle);
    graphics.drawImage(bodyImage, playerPosition.getX(), playerPosition.getY()); //draw body
    graphics.drawImage(armImage, armImagePosition.getX(), armImagePosition.getCenterY()); // draw arm

    if (playerState != PlayerState.THROWING) {
      projectile.setRotation(directionAngle - animationAngle);
      projectile.setCenterPosition(handCenterPosition.getX(), handCenterPosition.getCenterY());
      projectile.render(gameContainer, stateBasedGame, graphics); //draw projectile
    }
    if (playerState != PlayerState.ANGLE_SELECTION) {
      powerSlider.render(gameContainer, stateBasedGame, graphics); //draw PowerSlider
    }
  }

  /**
   * counter clock-wise rotation of the arm
   */
  public void moveArmRight() {
    moveArm(DEGREE_ARM_MOVE);
  }

  /**
   * clock-wise rotation of the arm
   */
  public void moveArmLeft() {
    moveArm(-DEGREE_ARM_MOVE);
  }

  /**
   * Rotates the arm
   *
   * @param degreeDifference The angle the arm is rotated, in decree
   */
  private void moveArm(float degreeDifference) {
    if (playerState == PlayerState.ANGLE_SELECTION) {
      // the player can only move his arm in a certain angle,
      // this checks if the player can still move his arm
      if ((degreeDifference > 0 && directionAngle < 20) || (degreeDifference < 0 && directionAngle > -180)) {
        this.directionAngle += degreeDifference;
      }
    }
  }

  /**
   * lock the current Selection (setAngle or setPower) and increment the
   * playerState do nothing if the player already threw the projectile
   */
  public final void startArmAnimation() {
    if (playerState == PlayerState.ANGLE_SELECTION) {
      playerState = PlayerState.POWER_SLIDER;
    } else if (playerState == PlayerState.POWER_SLIDER) {
      moveArmBack = true;
      playerState = PlayerState.ANIMATION;
    }
  }

  public void updatePowerSlider(int delta) {
    if (playerState == PlayerState.POWER_SLIDER) {
      powerSlider.update(delta);
    }
  }

  /**
   * Brings the player back into the default state, where he can throw a new projectile
   */
  public void reset() {
    playerState = PlayerState.ANGLE_SELECTION;
    projectile = new Projectile(projectileMeta);
    setHandCenterPosition();
    powerSlider.reset();
  }

  /**
   * Throw the projectile
   *
   * @return return the thrown projectile
   */
  public Projectile throwProjectile() {
    if (returnProjectile) {
      returnProjectile = false;
      float velocityY = ((float) Math.cos(Math.toRadians(directionAngle) + THROW_ANGLE_TRANSLATION) * powerSlider.getSelectedForce());
      float velocityX = -((float) Math.sin(Math.toRadians(directionAngle) + THROW_ANGLE_TRANSLATION) * powerSlider.getSelectedForce());

      projectile.applyForce(velocityX * forceAmplifier, velocityY * forceAmplifier);
      projectile.applyTorque(powerSlider.getSelectedForce() * torqueAmplifier);
      return projectile;
    }
    return null;
  }

  /**
   * This set the center position of the hand, needed after moving the arm ("moveArm" method)
   */
  private void setHandCenterPosition() {
    //The position of the hand is on a circle around the shoulder.
    //This position is defined by direction angle (the angle in the circle)
    //animationAngle is needed for the animation, it is increased when the arm moves back (counter clock-wise rotation)
    //PROJECTILE_ANGLE_TRANSLATION is in radians, and is a translation, because the arm doesn't start at 0°
    //projectileOnHandScale is a scaling, because else the calculation would be for a radius of 1
    handCenterPosition.setX(((float) Math.cos(Math.toRadians(directionAngle - animationAngle) + PROJECTILE_ANGLE_TRANSLATION) * projectileOnHandScale) + armImagePosition.getX() + armImage.getWidth() / 2);
    handCenterPosition.setY(((float) Math.sin(Math.toRadians(directionAngle - animationAngle) + PROJECTILE_ANGLE_TRANSLATION) * projectileOnHandScale) + armImagePosition.getY() + armImage.getHeight() / 2);
  }

  public void setPosition(float x, float y) {
    playerPosition.setX(x);
    playerPosition.setY(y);

    //the position of the arm image must be set in relation to the player,
    //the shoulder is NOT the top left corner but the middle of the image
    armImagePosition.setX(x - 42);
    armImagePosition.setY(y + 9);

    //set the position of the projectile to be on the hand
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

  public long getThrowStart() {
    return throwStart;
  }

  private void setThrowStart(long throwStart) {
    this.throwStart = throwStart;
  }

  public void playSound() {
    sound.play();
  }

  public void stopSound() {
    sound.stop();
  }

}
