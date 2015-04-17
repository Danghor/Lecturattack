package Lecturattack.entities;

/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
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

  // the decrees the arm is rotated very update
  private static final int DEGREE_ARM_MOVE = 1;

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

  public Projectile update(GameContainer gameContainer, int delta) {
    updatePowerSlider(delta);
    // a local variable is necessary because the player only returns the projectile when he throws it otherwise he returns null
    Projectile projectileReturned = null;
    if (gameContainer.getInput().isKeyDown(Input.KEY_RIGHT)) {
      moveArm(DEGREE_ARM_MOVE);
    } else if (gameContainer.getInput().isKeyDown(Input.KEY_LEFT)) {
      moveArm(-DEGREE_ARM_MOVE);
    } else if (gameContainer.getInput().isKeyPressed(Input.KEY_SPACE)) {

      // the player enters the POWER_SLIDER state (where he can choose the force) when he presses space in the ANGLE_SELECTION state
      // the player enters the THROWING state when he presses  space in  the POWER_SLIDER state, than he throws the projectile
      // the player enters the ANGLE_SELECTION state with the reset() method
      if (playerState == PlayerState.ANGLE_SELECTION) {
        playerState = PlayerState.POWER_SLIDER;
      } else if (playerState == PlayerState.POWER_SLIDER) {
        playerState = PlayerState.THROWING;

        // the velocity of the projectile is tangential to the arm movement (the animation when throwing not selecting the angle)
        // the vector necessary for this has the same direction as an line which is orthogonal to the line from the shoulder of the player to the hand of the player
        // with this in mind the following first caculates the the directions for x and y and than scales it with the force of the powerSlider
        float velocityX = -((float) Math.sin(Math.toRadians(directionAngle) + THROW_ANGLE_TRANSLATION) * powerSlider.getSelectedForce());
        float velocityY = ((float) Math.cos(Math.toRadians(directionAngle) + THROW_ANGLE_TRANSLATION) * powerSlider.getSelectedForce());
        projectile.applyForce(velocityX * forceAmplifier, velocityY * forceAmplifier);
        projectile.applyTorque(powerSlider.getSelectedForce() * torqueAmplifier);
        projectileReturned = projectile;
      }
    }
    return projectileReturned;
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    armImage.setRotation(directionAngle);
    graphics.drawImage(bodyImage, positionX, positionY);
    graphics.drawImage(armImage, armImageX, armImageY);
    if (playerState != PlayerState.THROWING) {
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
    this.handCenterPositionX = ((float) Math.cos(Math.toRadians(directionAngle) + PROJECTILE_ANGLE_TRANSLATION) * projectileOnHandScale) + armShoulderX;
    this.handCenterPositionY = ((float) Math.sin(Math.toRadians(directionAngle) + PROJECTILE_ANGLE_TRANSLATION) * projectileOnHandScale) + armShoulderY;
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

  public enum PlayerState {
    ANGLE_SELECTION, POWER_SLIDER, THROWING
  }
}
