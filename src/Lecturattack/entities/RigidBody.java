package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.statemachine.Lecturattack;
import Lecturattack.utilities.EnhancedVector;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;

import java.util.ArrayList;

/**
 * @author Nick Steyer
 */
public abstract class RigidBody implements Renderable {
  static final float BOUNCINESS = 0.8f;
  private static final String CONSISTS_OF_NO_VERTICES_EXCEPTION_TEXT = "This RigidBody does not consist of any vertices.";

  final ArrayList<EnhancedVector> vertices;
  private final double area; //area is not expected to change
  private EnhancedVector linearVelocity;
  private EnhancedVector force;

  RigidBody(MetaObject meta, float x, float y) {
    this.vertices = new ArrayList<>();

    for (float[] vertexPosition : meta.getOutline()) {
      vertices.add(new EnhancedVector(vertexPosition[0], vertexPosition[1]));
    }

    move(new EnhancedVector(x, y));

    area = getArea();

    linearVelocity = new EnhancedVector(0f, 0f);
    force = new EnhancedVector(0f, 0f);
  }

  protected abstract float getMass();

  public void update(float scaledDelta) {
    EnhancedVector acceleration;

    acceleration = new EnhancedVector(force.getX() / getMass(), force.getY() / getMass());
    linearVelocity.add(acceleration.scale(scaledDelta));
    move(new EnhancedVector(linearVelocity.getX() * scaledDelta, linearVelocity.getY() * scaledDelta));

    force = new EnhancedVector(0f, 0f);
  }

  /**
   * This method will reflect the linearVelocity of this object on the given partner body, i.e. it will "rebounce" this object on the side of the partner.
   * This method only works if the two bodies do indeed collide.
   *
   * @param partner The RigidBody this object is colliding with each other.
   */
  void reflect(RigidBody partner) {
    Line intersectingLine = null;
    Polygon thisPolygon = new Polygon();

    for (EnhancedVector vertex : vertices) {
      EnhancedVector toCenter = getCenter();
      toCenter.sub(vertex);
      toCenter.normalise();
      thisPolygon.addPoint(vertex.getX() - toCenter.getX(), vertex.getY() - toCenter.getY());
    }

    ArrayList<Line> partnerLines = new ArrayList<>();

    //get ArrayList of all Lines of the partner body
    ArrayList<EnhancedVector> pv = partner.vertices; //for comprehension purposes
    int partnerSize = pv.size();

    for (int i = 0; i < partnerSize - 1; i++) {
      partnerLines.add(new Line(pv.get(i).getX(), pv.get(i).getY(), pv.get(i + 1).getX(), pv.get(i + 1).getY()));
    }

    partnerLines.add(new Line(pv.get(partnerSize - 1).getX(), pv.get(partnerSize - 1).getY(), pv.get(0).getX(), pv.get(0).getY()));

    ArrayList<Line> potentialCollisionLines = new ArrayList<>(); //lines to be in question for collision response

    for (Line line : partnerLines) {
      if (thisPolygon.intersects(line)) {
        potentialCollisionLines.add(line);
      }
    }

    ArrayList<Integer> penetrationSizes = new ArrayList<>();
    int currentSizeArrayIndex = -1;

    if (potentialCollisionLines.size() > 1) {
      //determine, which line on the target has the longest segment "penetrating" the projectile
      for (Line partnerLine : potentialCollisionLines) {
        currentSizeArrayIndex++;
        penetrationSizes.add(0);
        Point start = new Point(partnerLine.getX1(), partnerLine.getY1());
        Point end = new Point(partnerLine.getX2(), partnerLine.getY2());
        Point currentPoint;
        if (thisPolygon.contains(start) && thisPolygon.contains(end)) { //this object contains both edges of the partnerLine
          intersectingLine = partnerLine;
          break; //todo: avoid break;
        } else {
          EnhancedVector direction;
          if (thisPolygon.contains(start)) { //this object contains the start point of the partnerLine
            currentPoint = start;
            direction = new EnhancedVector(end.getX() - start.getX(), end.getY() - start.getY());
          } else { //this object contains the end point of the partnerLine
            currentPoint = end;
            direction = new EnhancedVector(start.getX() - end.getX(), start.getY() - end.getY());
          }
          while (thisPolygon.contains(currentPoint)) {
            int previousSize = penetrationSizes.get(currentSizeArrayIndex);
            penetrationSizes.set(currentSizeArrayIndex, previousSize + 1);
            currentPoint.setX(currentPoint.getX() + direction.getX());
            currentPoint.setY(currentPoint.getY() + direction.getY());
          }
        }
      }

      int biggestFoundSize = 0;
      for (int i = 0; i < penetrationSizes.size(); i++) {
        if (penetrationSizes.get(i) > biggestFoundSize) {
          intersectingLine = potentialCollisionLines.get(i);
        }
      }
    } else if (potentialCollisionLines.size() == 1) {
      intersectingLine = potentialCollisionLines.get(0);
    }

    if (intersectingLine != null) {
      EnhancedVector startPoint = new EnhancedVector(intersectingLine.getX1(), intersectingLine.getY1());
      EnhancedVector lineVector = (EnhancedVector) (new EnhancedVector(intersectingLine.getX2(), intersectingLine.getY2())).sub(startPoint);
      EnhancedVector perpendicularToTarget = lineVector.getPerpendicular();

      perpendicularToTarget.normalise();

      EnhancedVector intersectionTestVector = (EnhancedVector) startPoint.sub(perpendicularToTarget);
      Point intersectionTestPoint = new Point(intersectionTestVector.getX(), intersectionTestVector.getY());
      Polygon partnerPolygon = new Polygon();

      for (EnhancedVector vertex : partner.vertices) {
        partnerPolygon.addPoint(vertex.getX(), vertex.getY());
      }

      if (partnerPolygon.contains(intersectionTestPoint)) {
        perpendicularToTarget.negateLocal();
      }

      float dx = linearVelocity.getX();
      float dy = linearVelocity.getY();
      float nx = perpendicularToTarget.getX();
      float ny = perpendicularToTarget.getY();

      linearVelocity = new EnhancedVector(dx - 2 * nx * (dx * nx + dy * ny), dy - 2 * ny * (dx * nx + dy * ny));
      linearVelocity.scale(BOUNCINESS);

      while (this.collidesWith(partner)) {
        EnhancedVector direction = (EnhancedVector) this.getCenter().sub(partner.getCenter());
        direction.normalise();
        this.move(direction);
      }
    } else {
      throw new IllegalArgumentException("The given partner body does not intersect with this object.");
    }

  }

  public boolean collidesWith(RigidBody partner) {
    Polygon polygon1 = new Polygon();
    Polygon polygon2 = new Polygon();

    for (EnhancedVector vertex : vertices) {
      polygon1.addPoint(vertex.getX(), vertex.getY());
    }

    for (EnhancedVector vertex : partner.vertices) {
      polygon2.addPoint(vertex.getX(), vertex.getY());
    }

    return polygon1.intersects(polygon2);
  }

  public void move(EnhancedVector direction) {
    for (EnhancedVector vertex : vertices) {
      vertex.add(direction);
    }
  }

  public void applyForce(float x, float y) {
    force.add(new EnhancedVector(x, y));
  }

  /**
   * Calculates the center point of this object based on it's vertices.
   *
   * @return An EnhancedVector object representing the center point.
   */
  EnhancedVector getCenter() {
    int n = vertices.size(); //number of vertices on the polygon
    double centerXSum = 0; //the summation part of calculating the x-axis of the center
    double centerYSum = 0;

    float centerX;
    float centerY;

    double prefix;
    double appendix;

    for (int i = 0; i < n - 1; i++) {
      //use appendix to slightly speed up calculation
      appendix = (vertices.get(i).getX() * vertices.get(i + 1).getY() - vertices.get(i + 1).getX() * vertices.get(i).getY());
      centerXSum += (vertices.get(i).getX() + vertices.get(i + 1).getX()) * appendix;
      centerYSum += (vertices.get(i).getY() + vertices.get(i + 1).getY()) * appendix;
    }

    //Xn, Yn is to be assumed the same as X0, Y0
    appendix = (vertices.get(n - 1).getX() * vertices.get(0).getY() - vertices.get(0).getX() * vertices.get(n - 1).getY());
    centerXSum += (vertices.get(n - 1).getX() + vertices.get(0).getX()) * appendix;
    centerYSum += (vertices.get(n - 1).getY() + vertices.get(0).getY()) * appendix;

    prefix = (1d / (6 * area));

    centerX = (float) (prefix * centerXSum);
    centerY = (float) (prefix * centerYSum);

    return new EnhancedVector(centerX, centerY);
  }

  /**
   * This method is only executed once in order to calculate and save the area of this object.
   *
   * @return The calculated area of this object based on it's vertices.
   */
  private double getArea() {
    double area;

    double areaSum = 0; //the summation part of the area-equation
    int n = vertices.size(); //number of vertices in the polygon

    for (int i = 0; i < n - 1; i++) {
      areaSum += (vertices.get(i).getX() * vertices.get(i + 1).getY() - vertices.get(i + 1).getX() * vertices.get(i).getY());
    }

    //Xn, Yn are to be assumed the same as X0, Y0
    areaSum += (vertices.get(n - 1).getX() * vertices.get(0).getY() - vertices.get(0).getX() * vertices.get(n - 1).getY());

    area = 0.5 * Math.abs(areaSum);
    return area;
  }

  private float getBiggestX() {
    if (vertices.size() < 1) {
      throw new IllegalStateException(CONSISTS_OF_NO_VERTICES_EXCEPTION_TEXT);
    } else {
      float biggestX = vertices.get(0).getX();

      for (EnhancedVector vertex : vertices) {
        if (vertex.getX() > biggestX) {
          biggestX = vertex.getX();
        }
      }

      return biggestX;
    }
  }

  public float getBiggestY() {
    if (vertices.size() < 1) {
      throw new IllegalStateException(CONSISTS_OF_NO_VERTICES_EXCEPTION_TEXT);
    } else {
      float biggestY = vertices.get(0).getY();

      for (EnhancedVector vertex : vertices) {
        if (vertex.getY() > biggestY) {
          biggestY = vertex.getY();
        }
      }

      return biggestY;
    }
  }

  /**
   * This method only works correctly if the RigidBody is not fully or partially outside of the visible frame.
   *
   * @return The ordinate of the vertex with the smallest y-value.
   */
  float getSmallestY() {
    if (vertices.size() < 1) {
      throw new IllegalStateException(CONSISTS_OF_NO_VERTICES_EXCEPTION_TEXT);
    } else {
      float smallestY = vertices.get(0).getY();

      for (EnhancedVector vertex : vertices) {
        if (vertex.getY() < smallestY) {
          smallestY = vertex.getY();
        }
      }

      return smallestY;
    }
  }

  /**
   * @return The abscissa of the vertex with the smallest x-value.
   */
  float getSmallestX() {
    if (vertices.size() < 1) {
      throw new IllegalStateException(CONSISTS_OF_NO_VERTICES_EXCEPTION_TEXT);
    } else {
      float smallestX = vertices.get(0).getX();

      for (EnhancedVector vertex : vertices) {
        if (vertex.getX() < smallestX) {
          smallestX = vertex.getX();
        }
      }

      return smallestX;
    }
  }

  /**
   * @return A new EnhancedVector object representing the top right corner of this body.
   */
  public EnhancedVector getPosition() {
    EnhancedVector returnedVector;

    try {
      returnedVector = new EnhancedVector(vertices.get(0).getX(), vertices.get(0).getY());
    } catch (NullPointerException ex) {
      throw new IllegalStateException(CONSISTS_OF_NO_VERTICES_EXCEPTION_TEXT);
    }

    return returnedVector;
  }

  public void setCenterPosition(float x, float y) {
    EnhancedVector destination = new EnhancedVector(x, y);
    EnhancedVector center = getCenter();

    EnhancedVector direction = (EnhancedVector) destination.sub(center);

    move(direction);
  }

  /**
   * Determines whether or not this object is outside of the visible frame and therefore cannot be reached anymore, except for the top.
   * The top is ignored, since the Object will eventually fall down again.
   *
   * @return A boolean value indicating whether or not the current object flew out of the frame sideways or down.
   */
  public boolean isUnreachable() {
    return getBiggestX() < 0 || getSmallestX() > Lecturattack.WIDTH || getSmallestY() > Lecturattack.HEIGHT;
  }

  public EnhancedVector getLinearVelocity() {
    return linearVelocity;
  }

  public void setLinearVelocity(EnhancedVector linearVelocity) {
    this.linearVelocity = linearVelocity;
  }
}
