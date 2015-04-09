package Lecturattack.utilities.xmlHandling.levelLoading;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Tim Adamek
 */
public class LevelElement {
  //each object of this class represent one element (player, target) which is loaded from the xml
  private float positionX;
  private float positionY;
  private XmlObjectType type;
  private Positioning positioning;
  private String image;

  @XmlElement
  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
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
