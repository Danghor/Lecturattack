package utilities;/*
 * Copyright (c) 2015.
 */

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
   * @return The scalar value of the cross product.
   */
  public float crossProductScalar(EnhancedVector partner) {
    return (this.x * partner.y - this.y * partner.x);
  }

  /**
   * Rotates the vector by a specified angle around a specified center.
   *
   * @param angle  The angle used to rotate the vector. Positive angle: clockwise rotation; Negative angle: counterclockwise rotation
   * @param center The center around which the vector is to be rotated.
   */
  public void rotate(float angle, EnhancedVector center) {

    //move center to origin of coordinate system
    double tmpX = this.x - center.x;
    double tmpY = this.y - center.y;

    //rotate x and y using the origin as the center
    double rotatedTmpX = Math.cos(angle) * tmpX - Math.sin(angle) * tmpY;
    double rotatedTmpY = Math.sin(angle) * tmpX + Math.cos(angle) * tmpY;

    //move center to its original position
    double newX = rotatedTmpX + center.x;
    double newY = rotatedTmpY + center.y;

    //apply the calculated values, i.e. rotate the vector
    this.x = (float) newX;
    this.y = (float) newY;
  }

  public EnhancedVector getScaled(float factor) {
    return (EnhancedVector) super.scale(factor);
  }
}
