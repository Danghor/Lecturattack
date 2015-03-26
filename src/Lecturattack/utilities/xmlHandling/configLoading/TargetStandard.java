package Lecturattack.utilities.xmlHandling.configLoading;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @author Tim Adamek
 */
public class TargetStandard {
  private String imageIntact;
  private String imageSlightlyBroken;
  private String imageAlmostBroken;
  private int maxHits;
  private String targetType;
  private String positioning;
  private List<XmlVertex> vertices;

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
  public String getTargetType() {
    return targetType;
  }

  public void setTargetType(String targetType) {
    this.targetType = targetType;
  }

  @XmlElement
  public String getPositioning() {
    return positioning;
  }

  public void setPositioning(String positioning) {
    this.positioning = positioning;
  }

  @XmlElement(name = "vertice")
  public List<XmlVertex> getVertices() {
    return vertices;
  }

  public void setVertices(List<XmlVertex> vertices) {
    this.vertices = vertices;
  }

}
