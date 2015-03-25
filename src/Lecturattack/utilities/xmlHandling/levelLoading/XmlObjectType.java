package Lecturattack.utilities.xmlHandling.levelLoading;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Tim Adamek
 */
@XmlType(name = "xmlObjectType")
@XmlEnum
public enum XmlObjectType {

  PLAYER,
  RAM,
  LIBRARY,
  ENEMY;

  public static XmlObjectType fromValue(String v) {
    return valueOf(v);
  }

  public String value() {
    return name();
  }

}