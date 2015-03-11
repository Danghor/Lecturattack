package entities;/*
 * Copyright (c) 2015.
 */

/**
 * Created by Nick Steyer on 10/03/2015
 */
public class Speedy extends Player {
  private static Speedy instance = new Speedy();

  private Speedy() {

  }

  public static Speedy getInstance() {
    return instance;
  }
}
