package entities;/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.Graphics;

/**
 * Created by Nick Steyer on 10/03/2015
 */
public class Muse extends Player {
  private static Muse instance;

  static {
    instance = new Muse();
  }

  private Muse() {

  }

  public static Muse getInstance() {
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
