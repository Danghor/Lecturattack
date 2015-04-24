package Lecturattack.utilities;

import org.newdawn.slick.geom.Vector2f;

/**
 * @author Nick Steyer
 */
public class EnhancedVector extends Vector2f {
  public EnhancedVector(float x, float y) {
    super(x, y);
  }

  /**
   * @param partner The EnhancedVector with which the cross product is to be calculated.
   *
   * @return The scalar value of the cross product.
   */
  public float crossProductScalar(EnhancedVector partner) {
    return (this.getX() * partner.getY() - this.getY() * partner.getX());
  }

  /**
   * Rotates the vector by a specified angle around a specified center.
   *
   * @param angleInDegrees The angle used to rotate the vector. Positive angle: counter-clockwise rotation; Negative angle: clockwise rotation
   *                       (Assuming x rising from left to right and y rising from bottom to top)
   * @param center         The center around which the vector is to be rotated.
   */
  public void rotate(float angleInDegrees, EnhancedVector center) {

    double angleInRadians = Math.toRadians(angleInDegrees);

    //move center to origin of coordinate system
    double tmpX = this.getX() - center.getX();
    double tmpY = this.getY() - center.getY();

    //rotate x and y using the origin as the center
    double rotatedTmpX = Math.cos(angleInRadians) * tmpX - Math.sin(angleInRadians) * tmpY;
    double rotatedTmpY = Math.sin(angleInRadians) * tmpX + Math.cos(angleInRadians) * tmpY;

    //move center to its original position
    double newX = rotatedTmpX + center.getX();
    double newY = rotatedTmpY + center.getY();

    //apply the calculated values, i.e. rotate the vector
    this.x = (float) newX;
    this.y = (float) newY;
  }

  /**
   * Returns the angle between this object and the given EnhancedVector in degrees.
   *
   * @param partner The EnhancedVector this object is intersecting with.
   *
   * @return The angle between this object and the given partner vector.
   */
  public float getAngle(EnhancedVector partner) {
    return (float) Math.toDegrees(Math.atan2(partner.getY() - this.getY(), partner.getX() - this.getX()));
  }

  public EnhancedVector getScaled(float factor) {
    return (EnhancedVector) super.scale(factor);
  }

  public EnhancedVector getPerpendicular() {
    //noinspection SuspiciousNameCombination
    return new EnhancedVector(-this.getY(), this.getX());
  }
}
