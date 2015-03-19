package test;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

public class RigidRectangle {

  private EnhancedVector topLeft;
  private EnhancedVector topRight;
  private EnhancedVector bottomLeft;
  private EnhancedVector bottomRight;
  private float width;
  private float height;
  private float mass;

  private EnhancedVector force;
  private EnhancedVector velocity;

  private float angularVelocity;
  private float torque;

  private float inertia;

  public RigidRectangle(float x, float y, float w, float h, float m) {
    this.topLeft = new EnhancedVector(x, y);
    this.topRight = new EnhancedVector(x + w, y);
    this.bottomLeft = new EnhancedVector(x, y + h);
    this.bottomRight = new EnhancedVector(x + w, y + h);

    this.width = w;
    this.height = h;
    this.mass = m;

    velocity = new EnhancedVector(0, 0);
    angularVelocity = 0;

    inertia = 8;//(mass * height * height * width * width)/12;

    force = new EnhancedVector(0, 0);
  }

  public EnhancedVector getCenter() {
    EnhancedVector diagonal = new EnhancedVector(bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
    diagonal.scale(0.5f);
    return new EnhancedVector(topLeft.x + diagonal.x, topLeft.y + diagonal.y);
  }

  public void applyForce(float x, float y) {
    force.add(new EnhancedVector(x, y));
  }

  public void applyTorque(float t) {
    torque += t;
  }


  public void move(EnhancedVector vector) {
    topLeft.add(vector);
    topRight.add(vector);
    bottomLeft.add(vector);
    bottomRight.add(vector);
  }

  public void rotate(float angle, EnhancedVector center) {
    topLeft = topLeft.getRotated(angle, center);
    topRight = topRight.getRotated(angle, center);
    bottomLeft = bottomLeft.getRotated(angle, center);
    bottomRight = bottomRight.getRotated(angle, center);
  }

  public void update(float delta) {

    EnhancedVector acceleration;

    acceleration = new EnhancedVector(force.x * (1 / mass), force.y * (1 / mass));
    velocity.add(acceleration.scale(delta));
    move(new EnhancedVector(velocity.x * delta, velocity.y * delta));

    force = new EnhancedVector(0, 0);

    float angularAcceleration = torque / inertia;
    angularVelocity += angularAcceleration * delta;
    rotate(angularVelocity * delta, getCenter());

    torque = 0;
  }

  public void draw(Graphics g) {
    Polygon polygon = new Polygon();
    polygon.addPoint(topLeft.x, topLeft.y);
    polygon.addPoint(topRight.x, topRight.y);
    polygon.addPoint(bottomRight.x, bottomRight.y);
    polygon.addPoint(bottomLeft.x, bottomLeft.y);

    g.draw(polygon);
    g.drawRect(getCenter().x, getCenter().y, 1, 1);
  }

}
