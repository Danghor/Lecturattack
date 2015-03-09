/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.Image;

/**
 * Created by Nick Steyer on 08/03/2015
 */
interface GameEntity {
  Image image;

  void init();

  void update();

  void render();
}
