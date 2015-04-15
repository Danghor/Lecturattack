package Lecturattack.utilities;

import Lecturattack.entities.Projectile;
import Lecturattack.entities.RigidBody;
import Lecturattack.entities.Target;
import Lecturattack.entities.types.TargetType;

import java.util.ArrayList;

/**
 * @author Nick Steyer
 */
public class PhysicsEngine {
  private static final float GRAVITATION_ACCELERATION = 9.81f;

  public static int calculateStep(Projectile projectile, ArrayList<Target> targets, ArrayList<Target> deadTargets, float wind, int deltaInMilliseconds, float groundLevel) {
    float scaledDelta = (float) deltaInMilliseconds / 100;
    EnhancedVector oldPosition;
    boolean intersectionBetweenTargetsDetected;
    Target targetCollidedWith;
    ArrayList<Integer> targetsToBeRemovedIndices = new ArrayList<>();
    // if the player hits any target in this step, add them to the score
    int scoreIncrement = 0;

    //----------projectile operations----------
    if (projectile != null) {
      oldPosition = projectile.getCenter();

      //update projectile
      projectile.applyForce(wind, GRAVITATION_ACCELERATION * projectile.getMass());
      projectile.update(scaledDelta);
      reflectOnGround(projectile, groundLevel);

      //detect if projectile collided with target
      targetCollidedWith = null;
      for (Target target : targets) {
        if (projectile.collidesWith(target)) {
          targetCollidedWith = target;
          break; //todo: avoid break
        }
      }

      //if projectile collided with target, perform collision response
      if (targetCollidedWith != null) {
        scoreIncrement += targetCollidedWith.hit(projectile);
        projectile.reflect(targetCollidedWith);
      }
    }

    //----------target operations----------

    //remove destroyed targets from list
    for (int i = 0; i < targets.size(); i++) {
      if (targets.get(i).isDestroyed()) {
        targetsToBeRemovedIndices.add(i);
      }
    }

    /**
     * Add dead ENEMYs to the deadTarget list, to make them fall out of frame.
     * Remove the target from the targets list.
     */
    for (int index : targetsToBeRemovedIndices) {
      Target targetToBeRemoved = targets.get(index);
      if (targetToBeRemoved.getType() == TargetType.ENEMY) {
        deadTargets.add(targetToBeRemoved);
      }
      targets.remove(index);
    }

    //make remaining targets fall and collide
    for (Target target : targets) {
      target.applyForce(0f, GRAVITATION_ACCELERATION * target.getMass());
      oldPosition = target.getCenter();

      target.update(scaledDelta);

      moveAboveGround(target, groundLevel);

      intersectionBetweenTargetsDetected = false;
      for (Target otherTarget : targets) {
        if (!target.equals(otherTarget) && target.collidesWith(otherTarget)) {
          intersectionBetweenTargetsDetected = true;
        }
      }

      if (intersectionBetweenTargetsDetected) {
        EnhancedVector newPosition = target.getCenter();
        target.move((EnhancedVector) oldPosition.sub(newPosition));
        target.setLinearVelocity(new EnhancedVector(0f, 0f));
      }

    }

    updateDeadTargets(deadTargets, scaledDelta);

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
      body.setLinearVelocity(new EnhancedVector(body.getLinearVelocity().x, 0f));
    }
  }

  private static void reflectOnGround(Projectile projectile, float groundLevel) {
    if (projectile.getBiggestY() >= groundLevel) {
      projectile.move(new EnhancedVector(0f, groundLevel - projectile.getBiggestY()));
      projectile.setLinearVelocity(new EnhancedVector(projectile.getLinearVelocity().x, -projectile.getLinearVelocity().y));
      projectile.invertRotation();
    }
  }

  /**
   * Processes the deadTargets List:
   * 1. Makes reachable targets fall down.
   * 2. Removes unreachable targets (that fell out of the visible frame) fro mthe list.
   *
   * @param deadTargets Targets that are dead and are not part of the simulation anymore.
   *                    They are still visible and therefore have to be rendered until they fell out of the visible frame.
   */
  private static void updateDeadTargets(ArrayList<Target> deadTargets, float scaledDelta) {
    //update the targets
    for (Target deadTarget : deadTargets) {
      deadTarget.applyForce(0f, GRAVITATION_ACCELERATION * deadTarget.getMass());
      deadTarget.update(scaledDelta);
    }

    //remove unreachable targets from the list
    ArrayList<Integer> targetsToBeRemovedIndices = new ArrayList<>();

    for (int i = 0; i < deadTargets.size(); i++) {
      if (deadTargets.get(i).isUnreachable()) {
        targetsToBeRemovedIndices.add(i);
      }
    }

    for (int index : targetsToBeRemovedIndices) {
      deadTargets.remove(index);
    }
  }
}
