package Lecturattack.utilities.xmlHandling.levelLoading;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Tim Adamek
 */
@XmlType(name = "xmlObjectType")
@XmlEnum
public enum XmlObjectType {
  //This Enum is for differentiating between the possible LevelElements, which can be loaded in a level XML
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