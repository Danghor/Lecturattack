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
  private static final int MAXIMUM_STEP_SIZE_IN_MILLISECONDS = 100;
  private static final float GROUND_BOUNCINESS = 0.7f;

  /**
   * This method takes the game objects as input and calculates what happens to them in the next step based on the given delta, wind and the forces that are already applied to the objects.
   * The objects themselves are updated, but not rendered here. This method will also handle collision detection and response.
   *
   * @param projectile          The projectile thrown by the player. If null, it will be ignored.
   * @param targets             The Array containing all targets that should physically interact with each other and the projectile.
   * @param deadTargets         The targets that should not physically interact with any game object, but are currently falling out of the game frame and therefore have to be updated.
   * @param wind                The wind strength that should be applied to the projectile. Value can be positive or negative. //todo: describe, which direction is negative and which positive
   * @param deltaInMilliseconds The time passed since the last step. May not be higher than 100 (milliseconds). Otherwise, the step is skipped.
   * @param groundLevel         The y-value indicating the current ground level. (Alive) targets and projectiles may never have an< vertices with a y-value bigger than this one.
   *                            A higher value indicates a lower ground.
   *
   * @return The score achieved in this step.
   */
  public static int calculateStep(Projectile projectile, ArrayList<Target> targets, ArrayList<Target> deadTargets, float wind, int deltaInMilliseconds, float groundLevel) {
    // this is the additional score returned in the end; it gets bigger for every target hit
    int scoreIncrement = 0;

    /**
     * The engine will not work properly is the given delta is too high. To avoid errors, huge steps are skipped.
     */
    if (!(deltaInMilliseconds > MAXIMUM_STEP_SIZE_IN_MILLISECONDS)) {
      float scaledDelta = (float) deltaInMilliseconds / 100; //todo: adjust values
      EnhancedVector oldTargetPosition;
      boolean intersectionBetweenTargetsDetected;
      Target targetCollidedWith;
      ArrayList<Integer> targetsToBeRemovedIndices = new ArrayList<>();

      //----------projectile operations----------
      if (projectile != null) {

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

      /**
       * Make remaining targets fall, but prevent them from falling into each other.
       */
      for (Target target : targets) {

        target.applyForce(0f, GRAVITATION_ACCELERATION * target.getMass());
        oldTargetPosition = target.getPosition();

        target.update(scaledDelta);

        moveAboveGround(target, groundLevel);

        // If the movement of the current target makes it collide with another target, undo the previous movement.
        intersectionBetweenTargetsDetected = false;
        for (Target otherTarget : targets) {
          if (!target.equals(otherTarget) && target.collidesWith(otherTarget)) {
            intersectionBetweenTargetsDetected = true;
          }
        }

        if (intersectionBetweenTargetsDetected) {
          EnhancedVector newPosition = target.getPosition();
          target.move((EnhancedVector) oldTargetPosition.sub(newPosition));
          target.setLinearVelocity(new EnhancedVector(0f, 0f));
        }
      }

      // Make "dead" targets fall out of frame (they do not interact with any other object)
      updateDeadTargets(deadTargets, scaledDelta);
    } else {
      throw new IllegalArgumentException("Given delta too high. Skipping this step.");
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
      body.setLinearVelocity(new EnhancedVector(body.getLinearVelocity().getX(), 0f));
    }
  }

  /**
   * Checks whether the projectile touches the ground or is slightly beneath it and makes it "bounce" of the ground in an angle.
   *
   * @param projectile  The projectile thrown by the player
   * @param groundLevel The y-value indicating the ground level. A higher value indicates a lower ground.
   */
  private static void reflectOnGround(Projectile projectile, float groundLevel) {
    if (projectile.getBiggestY() >= groundLevel) { //todo: check for projectile == null
      projectile.move(new EnhancedVector(0f, groundLevel - projectile.getBiggestY()));
      projectile.setLinearVelocity(new EnhancedVector(projectile.getLinearVelocity().getX() * GROUND_BOUNCINESS, -projectile.getLinearVelocity().getY() * GROUND_BOUNCINESS));
      projectile.invertRotation(GROUND_BOUNCINESS);
    }
  }

  /**
   * Processes the deadTargets List:
   * 1. Makes reachable targets fall down.
   * 2. Removes unreachable targets (that fell out of the visible frame) from the list.
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
