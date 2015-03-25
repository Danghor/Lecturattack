package Lecturattack.utilities.xmlHandling.levelLoading;

/**
 * Created by Tim on 20.03.2015.
 */

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Tim on 20.03.2015.
 */
@XmlType(name = "positioningType")
@XmlEnum
public enum Positioning {

  VERTICAL,
  HORIZONTAL;

  public static Positioning fromValue(String v) {
    return valueOf(v);
  }

  public String value() {
    return name();
  }

}