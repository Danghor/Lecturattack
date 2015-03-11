package entities;/*
 * Copyright (c) 2015.
 */

/**
 * Created by Nick Steyer on 10/03/2015
 */
public class Muse extends Player {
  private static Muse instance = new Muse();

  private Muse() {

  }

  public static Muse getInstance() {
    return instance;
  }
}
