package entities;
/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by Nick Steyer on 08/03/2015
 */
public abstract class Player {
  private Image bodyImage;
  private Image armImage;
  private float angle; //the current angle of the player's arm
  private boolean isThrowing;

  public void reset() {
    isThrowing = false;
  }

  public void moveArm(float degreeDifference) {
    //todo: check if movement possible, turn arm etc.
  }

  /**
   * This method enforces setting isThrowing to true when the player throws a projectile.
   *
   * @param strength
   */
  public final Projectile throwProjectile(float strength) {
    isThrowing = true;
    return throwSpecificProjectile(strength);
  }

  protected abstract Projectile throwSpecificProjectile(float strength);

  public void render(Graphics graphics) {

  }
}
