/*
 * Copyright (c) 2015.
 */

package utilities;

import utilities.xmlHandling.LevelElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Steyer on 19/03/2015
 */
@XmlRootElement(name="LevelData")
public class LevelData {
  private int id;
  private List<LevelElement> levelElements= new ArrayList<LevelElement>() {
  };

  public LevelData() {
  }

  public LevelData(int id, List<LevelElement> levelElements) {
    this.id = id;
    this.levelElements = levelElements;
  }

  @XmlAttribute
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @XmlElement
  public List<LevelElement> getLevelElements() {
    return levelElements;
  }

  public void setAnswers(List<LevelElement> levelElements) {
    this.levelElements = levelElements;
  }

}
