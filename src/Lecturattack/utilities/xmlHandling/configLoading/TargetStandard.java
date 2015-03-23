package Lecturattack.utilities.xmlHandling.configLoading;

import Lecturattack.utilities.xmlHandling.Positioning;
import Lecturattack.utilities.xmlHandling.levelLoading.XmlObjectType;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Tim on 23.03.2015.
 */
public class TargetStandard {
  /**
   * Created by Tim on 20.03.2015.
   */
  private String imageIntact;
  private String imageSlightlyBroken;
  String imageAlmostBroken;
  private int maxHits;
  private XmlObjectType targetType;
  private Positioning positioning;

  @XmlElement
  public String getImageIntact() {
    return imageIntact;
  }

  public void setImageIntact(String imageIntact) {
    this.imageIntact = imageIntact;
  }

  @XmlElement
  public String getImageSlightlyBroken() {
    return imageSlightlyBroken;
  }

  public void setImageSlightlyBroken(String imageSlightlyBroken) {
    this.imageSlightlyBroken = imageSlightlyBroken;
  }

  @XmlElement
  public String getImageAlmostBroken() {
    return imageAlmostBroken;
  }

  public void setImageAlmostBroken(String imageAlmostBroken) {
    this.imageAlmostBroken = imageAlmostBroken;
  }

  @XmlElement
  public int getMaxHits() {
    return maxHits;
  }

  public void setPositionX(int maxHits) {
    this.maxHits = maxHits;
  }

  @XmlElement
  public XmlObjectType getTargetType() {
    return targetType;
  }

  public void setTargetType(XmlObjectType targetType) {
    this.targetType = targetType;
  }

  @XmlElement
  public Positioning getPositioning() {
    return positioning;
  }

  public void setPositioning(Positioning positioning) {
    this.positioning = positioning;
  }


}
