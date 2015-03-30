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

  public Player(Image bodyImage, Image armImage, ProjectileMeta projectileMeta) {
    this.positionX = 0f;
    this.positionY = 0f;
    this.bodyImage = bodyImage;
    this.armImage = armImage;
    this.projectileMeta = projectileMeta;
    reset();

    this.projectilePositionX=100;
    this.projectilePositionY=250;

  }

  public void setPosition(float x, float y) {
    positionX = x;
    positionY = y;
  }

  public void reset() {
    isThrowing = false;
    projectile = new Projectile(projectileMeta, 0f, 0f);
  }

  public void moveArm(float degreeDifference) {
    //todo: check if movement possible, turn arm etc.
    if (!isThrowing) {
      this.directionAngle += degreeDifference;
      projectilePositionX= ((float) Math.cos(Math.toRadians(directionAngle)+Math.PI/4) * strength)+(60)+25;
      projectilePositionY= ((float) Math.sin(Math.toRadians(directionAngle)+Math.PI/4) * strength)+(200+25);
    }
  }

  public final Projectile throwProjectile(float strength) {
    isThrowing = true;
    return projectile;
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    armImage.setCenterOfRotation(36,146);//TODO keep updated if changes
    armImage.setRotation(directionAngle);

    double directionRad = Math.toRadians(directionAngle);
    graphics.drawImage(bodyImage, positionX, positionY);
    graphics.drawImage(armImage, positionX, positionY);

    if (!isThrowing) {
      //todo: set position to middle of the player's hand
     // float positionX = 100;
    //  float positionY = 100;

      projectile.setCenterPosition(projectilePositionX,projectilePositionY);
      projectile.render(gameContainer, stateBasedGame, graphics);
    }

    graphics.drawLine(85, 225,  ((float) Math.cos(Math.toRadians(directionAngle)+Math.PI/4) * strength)+(60)+25, ((float) Math.sin(Math.toRadians(directionAngle)+Math.PI/4) * strength)+(200+25));

  }
}
