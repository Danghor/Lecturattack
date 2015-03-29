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

  private Image bodyImage;
  private Image armImage;
  private float angle; //the current angle of the player's arm
  private boolean isThrowing;
  private Projectile projectile;
  private PowerSlider powerSlider;
  private float positionX;
  private float positionY;
  private ProjectileMeta projectileMeta;


  private float directionAngle;

  public Player(Image bodyImage, Image armImage, ProjectileMeta projectileMeta) {
    this.positionX = 0f;
    this.positionY = 0f;
    this.bodyImage = bodyImage;
    this.armImage = armImage;
    this.projectileMeta = projectileMeta;
  }

  public void setPositionY(float positionY) {
    this.positionY = positionY;
  }

  public void setPositionX(float positionX) {
    this.positionX = positionX;
  }

  public void reset() {
    isThrowing = false;
    projectile = new Projectile(projectileMeta, 0f, 0f); //todo: set actual position for the projectile
  }


  public void moveArm(float degreeDifference) {
    //todo: check if movement possible, turn arm etc.
    if (!isThrowing) {
      this.directionAngle += degreeDifference;
    }
  }

  /**
   * @param strength
   */

  //TODO remove
  float strength = 70;

  public final Projectile throwProjectile(float strength) {

    //isThrowing = true;
//    projectile = new Projectile(projectileMeta,1,1);
    projectile = new Projectile(projectileMeta, 110f, 110f);//TODO position
    return projectile;
  }


  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    graphics.drawImage(bodyImage, positionX, positionY);

//    if (isThrowing) {
//      //todo: place projectile on hand and rotate correctly, this is just for testing
//       projectile.render(gameContainer, stateBasedGame, graphics);
//    }


    double directionRad = Math.toRadians(directionAngle);
    graphics.drawLine(this.positionX + 50, this.positionY + 150, this.positionX + 50 + ((float) Math.cos(directionRad) * strength), this.positionY + 150 + ((float) Math.sin(directionRad) * strength));

  }
}
