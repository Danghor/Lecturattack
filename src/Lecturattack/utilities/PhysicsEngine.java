package Lecturattack.utilities;

import Lecturattack.entities.Projectile;
import Lecturattack.entities.Target;

import java.util.ArrayList;

/**
 * @author Nick Steyer
 */
public class PhysicsEngine {
  public static void calculateStep(Projectile projectile, ArrayList<Target> targets, float wind, int deltaInMilliseconds) {
    float scaledDelta = (float) deltaInMilliseconds / 100;

    if (projectile != null) {
      projectile.update(scaledDelta);
    }
  }
}
