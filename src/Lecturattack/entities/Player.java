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
  private ProjectileMeta projectileMeta;
  private float directionAngle;

  public Player(PlayerStandard playerStandard) {
    this.positionX = 0f;
    this.positionY = 0f;

    try {
      bodyImage = new Image(playerStandard.getImageBody());//TODO don'T do this here --> filehandler
      armImage = new Image(playerStandard.getImageArm());
    } catch (SlickException e) {
      e.printStackTrace();
    }

    //todo: this belongs in the fileHandler
    ProjectileMeta.ProjectileType projectileType = null;//If the wrong projectie is specifed in playerStandart ProjectileMeta.getInstance() will also return null
    switch (playerStandard.getProjectile()) {
      case "ROBOT":
        projectileType = ProjectileMeta.ProjectileType.ROBOT;
        break;
      case "EXAM":
        projectileType = ProjectileMeta.ProjectileType.EXAM;
        break;
      case "POINTER":
        projectileType = ProjectileMeta.ProjectileType.POINTER;
        break;
    }
    projectileMeta = ProjectileMeta.getInstance(projectileType);

    reset();
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
    }
  }

  public final Projectile throwProjectile(float strength) {
    isThrowing = true;
    return projectile;
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    armImage.setRotation(directionAngle);

    graphics.drawImage(bodyImage, positionX, positionY);
    graphics.drawImage(armImage, positionX, positionY);

    if (!isThrowing) {
      //todo: set position to middle of the player's hand
      projectile.setCenterPosition(100, 100);
      projectile.render(gameContainer, stateBasedGame, graphics);
    }

    double directionRad = Math.toRadians(directionAngle);
    graphics.drawLine(this.positionX + 50, this.positionY + 150, this.positionX + 50 + ((float) Math.cos(directionRad) * strength), this.positionY + 150 + ((float) Math.sin(directionRad) * strength));

  }
}
