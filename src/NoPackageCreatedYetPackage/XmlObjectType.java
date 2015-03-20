package NoPackageCreatedYetPackage;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Tim on 20.03.2015.
 */
@XmlType(name = "xmlObjectType")
@XmlEnum
public enum XmlObjectType {

  PLAYER,
  RAM,
  LIBRARY,
  ENEMY;

  public String value() {
    return name();
  }

  public static XmlObjectType fromValue(String v) {
    return valueOf(v);
  }

}