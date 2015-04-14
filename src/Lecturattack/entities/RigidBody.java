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
  private static final float DAMPING = 0.8f;
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

  /**
   * Calculates the center point of this object based on it's vertices.
   *
   * @return An EnhancedVector object representing the center point.
   */
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

  //todo: fix, sometimes a negative area is calculated

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
      areaSum += (vertices.get(i).x * vertices.get(i + 1).y - vertices.get(i + 1).x * vertices.get(i).y);
    }

    //Xn, Yn are to be assumed the same as X0, Y0
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

  public void update(float scaledDelta) {
    EnhancedVector acceleration;

    acceleration = new EnhancedVector(force.x / getMass(), force.y / getMass());
    linearVelocity.add(acceleration.scale(scaledDelta));
    move(new EnhancedVector(linearVelocity.x * scaledDelta, linearVelocity.y * scaledDelta));

    force = new EnhancedVector(0, 0);
  }

  public float getBiggestY() {
    float biggestY = 0f;

    for (EnhancedVector vertex : vertices) {
      if (vertex.y > biggestY) {
        biggestY = vertex.y;
      }
    }

    return biggestY;
  }

  /**
   * This method only works correctly if the RigidBody is not fully or partially outside of the visible frame.
   *
   * @return The abscissa of the vertex with the smallest x-value.
   */
  public float getSmallestX() {
    float smallestX = Lecturattack.WIDTH;

    for (EnhancedVector vertex : vertices) {
      if (vertex.x < smallestX) {
        smallestX = vertex.x;
      }
    }

    return smallestX;
  }

  /**
   * This method only works correctly if the RigidBody is not fully or partially outside of the visible frame.
   *
   * @return The ordinate of the vertex with the smallest y-value.
   */
  public float getSmallestY() {
    float smallestY = Lecturattack.HEIGHT;

    for (EnhancedVector vertex : vertices) {
      if (vertex.y < smallestY) {
        smallestY = vertex.y;
      }
    }

    return smallestY;
  }

  public void setCenterPosition(float x, float y) {
    EnhancedVector destination = new EnhancedVector(x, y);
    EnhancedVector center = getCenter();

    EnhancedVector direction = (EnhancedVector) destination.sub(center);

    move(direction);
  }

  public boolean collidesWith(RigidBody partner) {
    Polygon polygon1 = new Polygon();
    Polygon polygon2 = new Polygon();

    for (EnhancedVector vertex : vertices) {
      polygon1.addPoint(vertex.x, vertex.y);
    }

    for (EnhancedVector vertex : partner.vertices) {
      polygon2.addPoint(vertex.x, vertex.y);
    }

    return polygon1.intersects(polygon2);
  }

  /**
   * This method will reflect the linearVelocity of this object on the given partner body, i.e. it will "rebounce" this object on the side of the partner.
   * This method only works if the two bodies do indeed collide.
   *
   * @param partner The RigidBody this object is colliding with.
   */
  public void reflect(RigidBody partner) {
    Line intersectingLine = null;
    Polygon thisPolygon = new Polygon();

    for (EnhancedVector vertex : vertices) {
      EnhancedVector toCenter = getCenter();
      toCenter.sub(vertex);
      toCenter.normalise();
      thisPolygon.addPoint(vertex.x - toCenter.x, vertex.y - toCenter.y);
    }

    ArrayList<Line> partnerLines = new ArrayList<>();

    //get ArrayList of all Lines of the partner body
    ArrayList<EnhancedVector> pv = partner.vertices; //for comprehension purposes
    int partnerSize = pv.size();

    for (int i = 0; i < partnerSize - 1; i++) {
      partnerLines.add(new Line(pv.get(i).x, pv.get(i).y, pv.get(i + 1).x, pv.get(i + 1).y));
    }

    partnerLines.add(new Line(pv.get(partnerSize - 1).x, pv.get(partnerSize - 1).y, pv.get(0).x, pv.get(0).y));

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
        if (thisPolygon.contains(start) && thisPolygon.contains(end)) {
          intersectingLine = partnerLine;
          break;
        } else if (thisPolygon.contains(start)) {
          currentPoint = start;
          EnhancedVector direction = new EnhancedVector(end.getX() - start.getX(), end.getY() - start.getY());
          while (thisPolygon.contains(currentPoint)) {
            int previousSize = penetrationSizes.get(currentSizeArrayIndex);
            penetrationSizes.set(currentSizeArrayIndex, previousSize + 1);
            currentPoint.setX(currentPoint.getX() + direction.x);
            currentPoint.setY(currentPoint.getY() + direction.y);
          }
        } else if (thisPolygon.contains(end)) {
          currentPoint = end;
          EnhancedVector direction = new EnhancedVector(start.getX() - end.getX(), start.getY() - end.getY());
          while (thisPolygon.contains(currentPoint)) {
            int previousSize = penetrationSizes.get(currentSizeArrayIndex);
            penetrationSizes.set(currentSizeArrayIndex, previousSize + 1);
            currentPoint.setX(currentPoint.getX() + direction.x);
            currentPoint.setY(currentPoint.getY() + direction.y);
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
      Point intersectionTestPoint = new Point(intersectionTestVector.x, intersectionTestVector.y);
      Polygon partnerPolygon = new Polygon();

      for (EnhancedVector vertex : partner.vertices) {
        partnerPolygon.addPoint(vertex.x, vertex.y);
      }

      if (partnerPolygon.contains(intersectionTestPoint)) {
        perpendicularToTarget.negateLocal();
      }

      float dx = linearVelocity.x;
      float dy = linearVelocity.y;
      float nx = perpendicularToTarget.x;
      float ny = perpendicularToTarget.y;

      linearVelocity = new EnhancedVector(dx - 2 * nx * (dx * nx + dy * ny), dy - 2 * ny * (dx * nx + dy * ny));
      linearVelocity.scale(DAMPING);

      while (this.collidesWith(partner)) {
        EnhancedVector direction = (EnhancedVector) this.getCenter().sub(partner.getCenter());
        direction.normalise();
        this.move(direction);
      }
    } else {
      throw new IllegalArgumentException("The given partner body does not intersect with this object.");
    }

  }

}
