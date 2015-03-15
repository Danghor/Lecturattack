package entities;/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.Graphics;

/**
 * Created by Nick Steyer on 10/03/2015
 */
public class Stroehtie extends Player {
  private static Stroehtie instance;

  static {
    instance = new Stroehtie();
  }

  private Stroehtie() {

  }

  public static Stroehtie getInstance() {
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
