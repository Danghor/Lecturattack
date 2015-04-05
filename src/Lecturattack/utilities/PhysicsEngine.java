package Lecturattack.utilities;

import Lecturattack.entities.Projectile;
import Lecturattack.entities.RigidBody;
import Lecturattack.entities.Target;

import java.util.ArrayList;

/**
 * @author Nick Steyer
 */
public class PhysicsEngine {
  private static final float GRAVITATION_ACCELERATION = 9.81f;

  public static void calculateStep(Projectile projectile, ArrayList<Target> targets, float wind, int deltaInMilliseconds, float groundLevel) {
    float scaledDelta = (float) deltaInMilliseconds / 100;
    EnhancedVector oldPosition;

    if (projectile != null) {
      projectile.applyForce(wind, GRAVITATION_ACCELERATION * projectile.getMass());
      projectile.update(scaledDelta);
      moveAboveGround(projectile, groundLevel);
    }

    for (Target target : targets) {
      target.applyForce(0f, GRAVITATION_ACCELERATION * target.getMass());
      oldPosition = target.getCenter();

      target.update(scaledDelta);
      moveAboveGround(target, groundLevel);

      boolean intersectionDetected = false;
      for (Target otherTarget : targets) {
        if (!target.equals(otherTarget) && target.collidesWith(otherTarget)) {
          intersectionDetected = true;
        }
      }

      if (intersectionDetected) {
        EnhancedVector newPosition = target.getCenter();
        target.move((EnhancedVector) oldPosition.sub(newPosition));
      }
    }

  }

  private static void moveAboveGround(RigidBody body, float groundLevel) {
    if (body.getBiggestY() >= groundLevel) {
      body.move(new EnhancedVector(0f, groundLevel - body.getBiggestY()));
    }
  }
}
