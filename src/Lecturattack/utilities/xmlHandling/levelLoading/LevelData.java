/*
 * Copyright (c) 2015.
 */

package Lecturattack.utilities.xmlHandling.levelLoading;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tim Adamek
 */
@XmlRootElement(name = "LevelData")
public class LevelData {
  //this class is a wrapping class, which holds multiple LevelObjects,
  //this is needed because JAXP can only return a single object when unmarshalling, not a list of objects
  private int score;

  private List<LevelElement> levelElements = new ArrayList<LevelElement>() {
  };

  public LevelData() {
  }

  public LevelData(List<LevelElement> levelElements) {
    this.levelElements = levelElements;
  }

  @XmlAttribute
  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  @XmlElement
  public List<LevelElement> getLevelElements() {
    return levelElements;
  }

  public void setLevelElements(List<LevelElement> levelElements) {
    this.levelElements = levelElements;
  }

}
