package Lecturattack.utilities.xmlHandling.configLoading;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Tim on 23.03.2015.
 */
public class XmlVertice {
  private float x;
  private float y;

  @XmlElement
  public float getX() {
    return x;
  }

  public void setX(float x) {
    this.x = x;
  }

  @XmlElement
  public float getY() {
    return y;
  }

  public void setY(float y) {
    this.y = y;
  }
}
