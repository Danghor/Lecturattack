package Lecturattack.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Tim on 23.03.2015.
 */
@XmlType(name = "targetMetaType")
@XmlEnum
public enum TargetMetaType {
  LIBRARYH,
  LIBRARYV,
  RAMH,
  RAMV,
  ENEMY;

  public static TargetMetaType fromValue(String v) {
    return valueOf(v);
  }

  public String value() {
    return name();
  }
}