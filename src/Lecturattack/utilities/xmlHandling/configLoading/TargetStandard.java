package Lecturattack.utilities.xmlHandling.configLoading;

import Lecturattack.entities.TargetMeta;
import Lecturattack.entities.TargetMetaType;
import Lecturattack.utilities.xmlHandling.Positioning;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by Tim on 23.03.2015.
 */
public class TargetStandard {
  /**
   * Created by Tim on 20.03.2015.
   */
  private String imageIntact;
  private String imageSlightlyBroken;
  private String imageAlmostBroken;
  private int maxHits;
  private TargetMeta.TargetType targetType;
  private TargetMetaType targetMetaType;
  private Positioning positioning;
  private List<XmlVertice> vertices;

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

  public void setMaxHits(int maxHits) {
    this.maxHits = maxHits;
  }

  @XmlElement
  public TargetMeta.TargetType getTargetType() {
    return targetType;
  }

  public void setTargetType(TargetMeta.TargetType targetType) {
    this.targetType = targetType;
  }


  @XmlElement
  public TargetMetaType getTargetMetaType() {
    return targetMetaType;
  }

  public void setTargetMetaType(TargetMetaType targetMetaType) {
    this.targetMetaType = targetMetaType;
  }

  @XmlElement
  public Positioning getPositioning() {
    return positioning;
  }

  public void setPositioning(Positioning positioning) {
    this.positioning = positioning;
  }

  @XmlElement
  public List<XmlVertice> getVertices() {
    return vertices;
  }

  public void setVertices(List<XmlVertice> vertices) {
    this.vertices = vertices;
  }

}
