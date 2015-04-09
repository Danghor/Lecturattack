package Lecturattack.utilities.xmlHandling.levelLoading;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Tim Adamek
 */
@XmlType(name = "positioningType")
@XmlEnum
public enum Positioning {
  //this is for loading the positioning of the targets in the level elements
  VERTICAL,
  HORIZONTAL;

  public static Positioning fromValue(String v) {
    return valueOf(v);
  }

  public String value() {
    return name();
  }

}