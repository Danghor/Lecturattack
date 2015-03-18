package entities;/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.Graphics;

/**
 * Created by Nick Steyer on 10/03/2015
 */
public class Speedy extends Player {
  private static Speedy instance;

  static {
    instance = new Speedy();
  }

  private Speedy() {

  }

  public static Speedy getInstance() {
    return instance;
  }

  @Override
  public Projectile throwSpecificProjectile(float strength) {
    return null;
  }

  @Override
  public void render(Graphics graphics) {

  }
}
