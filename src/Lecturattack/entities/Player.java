package Lecturattack.entities;
/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.xmlHandling.configLoading.PlayerStandard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Tim Adamek
 * @author Nick Steyer
 */
public class Player implements Renderable {

  //TODO remove
  float strength = 70;
  private Image bodyImage;
  private Image armImage;
  private boolean isThrowing;
  private Projectile projectile;
  private PowerSlider powerSlider;
  private float positionX;
  private float positionY;
  private float projectilePositionX;
  private float projectilePositionY;
  private ProjectileMeta projectileMeta;
  private float directionAngle;



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
    //the position of the arm must be set in relation to the player
    armShoulderX=x-36;
    armShoulderY=y+10;

    //set the position of the projectile to be on the hand
    this.projectilePositionX=x+80;
    this.projectilePositionY=y+195;
  }

  public void reset() {
    isThrowing = false;
    projectile = new Projectile(projectileMeta, 0f, 0f);
  }

  public void moveArm(float degreeDifference) {
    //todo: check if movement possible, turn arm etc.
    if (!isThrowing) {
      this.directionAngle += degreeDifference;
      projectilePositionX= ((float) Math.cos(Math.toRadians(directionAngle)+Math.PI/4) * strength)+armShoulderX+85;
      projectilePositionY= ((float) Math.sin(Math.toRadians(directionAngle)+Math.PI/4) * strength)+armShoulderY+135;
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
    double directionRad = Math.toRadians(directionAngle);
    graphics.drawImage(bodyImage, positionX, positionY);
    graphics.drawImage(armImage, armShoulderX, armShoulderY);

    if (!isThrowing) {
      //todo: set position to middle of the player's hand
      projectile.setCenterPosition(projectilePositionX,projectilePositionY);
      projectile.render(gameContainer, stateBasedGame, graphics);
    }
  }
}
