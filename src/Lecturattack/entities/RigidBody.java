package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.EnhancedVector;

import java.util.ArrayList;

/**
 * @author Nick Steyer
 */
public abstract class RigidBody implements Renderable {
  protected final double area; //area is not expected to change
  protected ArrayList<EnhancedVector> vertices;
  protected EnhancedVector linearVelocity;
  protected EnhancedVector force;

  protected RigidBody(MetaObject meta, float x, float y) {
    this.vertices = new ArrayList<>();

    for (float[] vertexPosition : meta.getOutline()) {
      vertices.add(new EnhancedVector(vertexPosition[0], vertexPosition[1]));
    }

    move(new EnhancedVector(x, y));

    area = getArea();

    linearVelocity = new EnhancedVector(0f, 0f);
    force = new EnhancedVector(0f, 0f);
  }

  public EnhancedVector getLinearVelocity() {
    return linearVelocity;
  }

  public void setLinearVelocity(EnhancedVector linearVelocity) {
    this.linearVelocity = linearVelocity;
  }

  public EnhancedVector getCenter() {
    int n = vertices.size(); //number of vertices on the polygon
    double centerXSum = 0; //the summation part of calculating the x-axis of the center
    double centerYSum = 0;

    float centerX;
    float centerY;

    double prefix;
    double appendix;

    for (int i = 0; i < n - 1; i++) {
      //use appendix to slightly speed up calculation
      appendix = (vertices.get(i).x * vertices.get(i + 1).y - vertices.get(i + 1).x * vertices.get(i).y);
      centerXSum += (vertices.get(i).x + vertices.get(i + 1).x) * appendix;
      centerYSum += (vertices.get(i).y + vertices.get(i + 1).y) * appendix;
    }

    //Xn, Yn is to be assumed the same as X0, Y0
    appendix = (vertices.get(n - 1).x * vertices.get(0).y - vertices.get(0).x * vertices.get(n - 1).y);
    centerXSum += (vertices.get(n - 1).x + vertices.get(0).x) * appendix;
    centerYSum += (vertices.get(n - 1).y + vertices.get(0).y) * appendix;

    prefix = (1d / (6 * area));

    centerX = (float) (prefix * centerXSum);
    centerY = (float) (prefix * centerYSum);

    return new EnhancedVector(centerX, centerY);
  }

  private double getArea() {
    double area;

    double areaSum = 0; //the summation part of the area-equation
    int n = vertices.size(); //number of vertices in the polygon

    for (int i = 0; i < n - 1; i++) {
      areaSum += (vertices.get(i).x * vertices.get(i + 1).y - vertices.get(i + 1).x * vertices.get(i).y);
    }

    //Xn, Yn is to be assumed the same as X0, Y0
    areaSum += (vertices.get(n - 1).x * vertices.get(0).y - vertices.get(0).x * vertices.get(n - 1).y);

    area = 0.5 * areaSum;
    return area;
  }

  public abstract float getMass();

  public void applyForce(float x, float y) {
    force.add(new EnhancedVector(x, y));
  }

  public void move(EnhancedVector direction) {
    for (EnhancedVector vertex : vertices) {
      vertex.add(direction);
    }
  }

  public void update(float delta) {
    EnhancedVector acceleration;

    acceleration = new EnhancedVector(force.x * (1 / getMass()), force.y * (1 / getMass()));
    linearVelocity.add(acceleration.scale(delta/1000));
    move(new EnhancedVector(linearVelocity.x * delta, linearVelocity.y * delta));

    force = new EnhancedVector(0, 0);
  }

  public void setCenterPosition(float x, float y) {
    EnhancedVector destination = new EnhancedVector(x, y);
    EnhancedVector center = getCenter();

    EnhancedVector direction = (EnhancedVector) destination.sub(center);

    move(direction);
  }

}
