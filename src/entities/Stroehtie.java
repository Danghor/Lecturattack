package entities;/*
 * Copyright (c) 2015.
 */

/**
 * Created by Nick Steyer on 10/03/2015
 */
public class Stroehtie extends Player {
  private static Stroehtie instance = new Stroehtie();

  private Stroehtie() {
  }

  public static Stroehtie getInstance() {
    return instance;
  }
}
