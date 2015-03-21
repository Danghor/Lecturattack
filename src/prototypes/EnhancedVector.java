package prototypes;

import org.newdawn.slick.geom.Vector2f;

public class EnhancedVector extends Vector2f {

  public EnhancedVector(float x, float y) {
    super(x, y);
  }

  //cross multiplication (only returns a scalar)
  public float crossProductValue(EnhancedVector partner) {
    return (this.x * partner.y - this.y * partner.x);
  }

  //rotation
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

    //rotate the vector
    this.x = (float) newX;
    this.y = (float) newY;
  }

  @Override
  public EnhancedVector scale(float a) {
    return (EnhancedVector) super.scale(a);
  }
}
