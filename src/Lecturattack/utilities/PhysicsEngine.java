package Lecturattack.utilities;

import Lecturattack.entities.Projectile;
import Lecturattack.entities.RigidBody;
import Lecturattack.entities.Target;
import Lecturattack.entities.TargetMeta;
import Lecturattack.entities.TargetMeta.TargetType;

import org.newdawn.slick.geom.Line;

import java.util.ArrayList;

/**
 * @author Nick Steyer
 */
public class PhysicsEngine {
  private static final float GRAVITATION_ACCELERATION = 9.81f;

  public static int calculateStep(Projectile projectile, ArrayList<Target> targets, float wind, int deltaInMilliseconds, float groundLevel) {
    float scaledDelta = (float) deltaInMilliseconds / 100;
    EnhancedVector oldPosition;
    boolean intersectionDetected;
    Target targetCollidedWith;
    ArrayList<Integer> targetsToBeRemoved = new ArrayList<>();
    // if the player hits any target in this step, add them to the score
    int scoreIncrement = 0;

    //----------projectile operations----------
    if (projectile != null) {
      oldPosition = projectile.getCenter();

      projectile.applyForce(wind, GRAVITATION_ACCELERATION * projectile.getMass());
      projectile.update(scaledDelta);
      moveAboveGround(projectile, groundLevel);

      targetCollidedWith = null;
      for (Target target : targets) {
        if (projectile.collidesWith(target)) {
          targetCollidedWith = target;
          break; //todo: avoid break
        }
      }

      if (targetCollidedWith != null) {
        //change projectile velocity in other direction
        //destroy target
        TargetMeta.TargetType type = targetCollidedWith.getType();
        if (projectile.getDestroys().contains(type)) {
          targetCollidedWith.hit();
          if(type == TargetType.ENEMY){
            scoreIncrement += 100;
          } else {
            scoreIncrement += 10;
          }
        }

        //todo: rebounce vv
        Line lineCollidedWith = projectile.getFirstIntersectingLine(targetCollidedWith);
        EnhancedVector targetLine = new EnhancedVector(lineCollidedWith.getX2() - lineCollidedWith.getX1(), lineCollidedWith.getY2() - lineCollidedWith.getY1());

        float intersectionAngle = projectile.getLinearVelocity().getAngle(targetLine);
        if (intersectionAngle > 90) {
          intersectionAngle = 180 - intersectionAngle;
        }
      }

    }

    //----------target operations----------

    //remove destroyed targets from list
    for (int i = 0; i < targets.size(); i++) {
      if (targets.get(i).isDestroyed()) {
        targetsToBeRemoved.add(i);
      }
    }

    for (int index : targetsToBeRemoved) {
      targets.remove(index);
    }

    //make remaining targets fall
    for (Target target : targets) {
      target.applyForce(0f, GRAVITATION_ACCELERATION * target.getMass());
      oldPosition = target.getCenter();

      target.update(scaledDelta);

      moveAboveGround(target, groundLevel);

      intersectionDetected = false;
      for (Target otherTarget : targets) {
        if (!target.equals(otherTarget) && target.collidesWith(otherTarget)) {
          intersectionDetected = true;
        }
      }

      if (intersectionDetected) {
        EnhancedVector newPosition = target.getCenter();
        target.move((EnhancedVector) oldPosition.sub(newPosition));
        target.setLinearVelocity(new EnhancedVector(0f, 0f));
      }

    }
    
    return scoreIncrement;

  }

  /**
   * Moves the given RigidBody just above the given groundLevel.
   *
   * @param body        The RigidBody to be moved.
   * @param groundLevel The floating-point number indicating the ground level. Notice that the ordinate rises from top to bottom.
   */
  private static void moveAboveGround(RigidBody body, float groundLevel) {
    if (body.getBiggestY() >= groundLevel) {
      body.move(new EnhancedVector(0f, groundLevel - body.getBiggestY()));
      body.setLinearVelocity(new EnhancedVector(0f, 0f));
    }
  }
}
