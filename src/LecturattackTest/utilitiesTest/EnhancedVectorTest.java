///*
// * Copyright (c) 2015.
// */
//
//package LecturattackTest.utilitiesTest;
//
//import Lecturattack.utilities.EnhancedVector;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * @author Nick Steyer
// */
//public class EnhancedVectorTest { //Convention: ClassNameTest
//
//  @Test
//  public void rotate_clockwise90Degrees_vectorCorrectlyRotated() { //Convention: testedMethod_testedScenarion_expectedResult
//    //Arrange
//    EnhancedVector sut; //sut: SystemUnderTest
//    EnhancedVector center;
//    float angle;
//
//    sut = new EnhancedVector(5f, 7f);
//    center = new EnhancedVector(3f, 4f);
//    angle = -90f;
//
//    //Act
//    sut.rotate(angle, center);
//
//    //Assert
//    assertEquals(6f, sut.getX(), 0.01);
//    assertEquals(2f, sut.getY(), 0.01);
//  }
//
//  @Test
//  public void rotate_counterclockwise90Degrees_vectorCorrectlyRotated() {
//    //Arrange
//    EnhancedVector sut;
//    EnhancedVector center;
//    float angle;
//
//    sut = new EnhancedVector(5f, 8f);
//    center = new EnhancedVector(2f, 4f);
//    angle = 90f;
//
//    //Act
//    sut.rotate(angle, center);
//
//    //Assert
//    assertEquals(-2f, sut.getX(), 0.01);
//    assertEquals(7f, sut.getY(), 0.01);
//  }
//
//  @Test
//  public void rotate_halfTurn_vectorCorrectlyRotated() {
//    //Arrange
//    EnhancedVector sut;
//    EnhancedVector center;
//    float angle;
//
//    sut = new EnhancedVector(5f, 8f);
//    center = new EnhancedVector(1f, 1f);
//    angle = 180f;
//
//    //Act
//    sut.rotate(angle, center);
//
//    //Assert
//    assertEquals(-3f, sut.getX(), 0.01);
//    assertEquals(-6f, sut.getY(), 0.01);
//  }
//
//  @Test
//  public void rotate_fullTurn_vectorCorrectlyRotated() {
//    //Arrange
//    EnhancedVector sut;
//    EnhancedVector center;
//    float angle;
//
//    sut = new EnhancedVector(5f, 8f);
//    center = new EnhancedVector(8f, 3f);
//    angle = 360f;
//
//    //Act
//    sut.rotate(angle, center);
//
//    //Assert
//    assertEquals(5f, sut.getX(), 0.01);
//    assertEquals(8f, sut.getY(), 0.01);
//  }
//}
//
