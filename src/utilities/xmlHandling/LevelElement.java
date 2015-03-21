package utilities.xmlHandling;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Workflow on 20.03.2015.
 */
public class LevelElement {
  private int id;
  private float positionX;
  private float positionY;
  private XmlObjectType type;
  private Positioning positioning;

  @XmlElement
  public void setId(int id) {
    this.id = id;
  }

  @XmlElement
  public float getPositionX() {
    return positionX;
  }

  public void setPositionX(float positionX) {
    this.positionX = positionX;
  }

  @XmlElement
  public float getPositionY() {
    return positionY;
  }

  public void setPositionY(float positionY) {
    this.positionY = positionY;
  }

  @XmlElement
  public XmlObjectType getType() {
    return type;
  }

  public void setType(XmlObjectType type) {
    this.type = type;
  }

  @XmlElement
  public Positioning getPositioning() {
    return positioning;
  }

  public void setPositioning(Positioning positioning) {
    this.positioning = positioning;
  }

}
