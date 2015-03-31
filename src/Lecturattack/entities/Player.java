package Lecturattack.entities;

/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.xmlHandling.configLoading.PlayerStandard;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Tim Adamek
 * @author Nick Steyer
 * @author Andreas Geis
 */
public class Player implements Renderable {

  // TODO remove
  float strength = 70;
  private Image bodyImage;
  private Image armImage;

  public enum PlayerState {
    ANGLE_SELECTION, POWER_SLIDER, THROWING
  }

  private Projectile projectile;
  private PowerSlider powerSlider;
  private float positionX;
  private float positionY;
  private float projectilePositionX;
  private float projectilePositionY;
  private ProjectileMeta projectileMeta;
  private float directionAngle;

  private PlayerState playerState;

  private float armShoulderX;// must be set in relation to player
  private float armShoulderY;

  public Player(Image bodyImage, Image armImage, ProjectileMeta projectileMeta) {
    this.positionX = 0f;
    this.positionY = 0f;
    this.bodyImage = bodyImage;
    this.armImage = armImage;
    this.projectileMeta = projectileMeta;
    reset();

    // set the position of the projectile to be on the hand
    this.projectilePositionX = 80;
    this.projectilePositionY = 195;

  }

  public void setPosition(float x, float y) {
    positionX = x;
    positionY = y;
    // the position of the arm must be set in relation to the player
    armShoulderX = x - 36;
    armShoulderY = y + 10;
  }

  public void reset() {
    playerState = PlayerState.ANGLE_SELECTION;
    projectile = new Projectile(projectileMeta, 0f, 0f);
    powerSlider = new PowerSlider();
  }

  public void moveArm(float degreeDifference) {
    // todo: check if movement possible, turn arm etc.
    if (playerState == PlayerState.ANGLE_SELECTION) {
      this.directionAngle += degreeDifference;
      projectilePositionX = ((float) Math.cos(Math.toRadians(directionAngle) + Math.PI / 4) * strength) + armShoulderX + 85;
      projectilePositionY = ((float) Math.sin(Math.toRadians(directionAngle) + Math.PI / 4) * strength) + armShoulderY + 135;
    }
  }

  /**
   * lock the current Selection (setAngle or setPower) and increment the
   * playerState do nothing if the player already threw the projectile
   * @return
   */
  public final Projectile throwProjectile() {
    if (playerState == PlayerState.ANGLE_SELECTION) {
      playerState = PlayerState.POWER_SLIDER;
      return null;
    } else {
      playerState = PlayerState.THROWING;
      // TODO apply force to projectile using powerSlider.getForce();
      return projectile;
    }
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    armImage.setRotation(directionAngle);
    double directionRad = Math.toRadians(directionAngle);
    graphics.drawImage(bodyImage, positionX, positionY);
    graphics.drawImage(armImage, armShoulderX, armShoulderY);

    if (playerState != PlayerState.THROWING) {
      // todo: set position to middle of the player's hand
      projectile.setCenterPosition(projectilePositionX, projectilePositionY);
      projectile.render(gameContainer, stateBasedGame, graphics);
    }
    
    if(playerState == PlayerState.POWER_SLIDER || playerState == PlayerState.THROWING){
      powerSlider.render(gameContainer, stateBasedGame, graphics);
    }
  }

  public void update(int delta) {
    if (playerState == PlayerState.POWER_SLIDER) {
      powerSlider.update(delta);
    }
  }
}
