package Lecturattack.utilities.xmlHandling.configLoading;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tim Adamek
 */
public class TargetStandard {
  //the objects of this class hold the information about the TargetMetaObjects, which is read from the configs
  private String imageIntact;
  private String imageSlightlyBroken;
  private String imageAlmostBroken;
  private int maxHits;
  private String targetType;
  private String positioning;
  private List<XmlVertex> vertices;
  private float mass;

  @XmlElement
  public float getMass() {
    return mass;
  }

  public void setMass(float mass) {
    this.mass = mass;
  }

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

  @XmlElement(name = "vertex")
  public List<XmlVertex> getVertices() {
    return vertices;
  }

  public void setVertices(List<XmlVertex> vertices) {
    this.vertices = vertices;
  }

  public ArrayList<float[]> getVerticesAsFloats() {//TODO maybe a interface/superclass which lets Projectile and Target Standard get this method
    ArrayList<float[]> vertexes = new ArrayList<>();
    for (XmlVertex vertice : vertices) {
      //the array constructor is not allowed when adding to a list
      float[] points = {vertice.getX(), vertice.getY()};
      vertexes.add(points);
    }
    return vertexes;
  }
}
