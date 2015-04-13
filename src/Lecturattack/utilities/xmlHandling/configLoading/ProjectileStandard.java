package Lecturattack.utilities.xmlHandling.configLoading;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tim Adamek
 */
public class ProjectileStandard {
  //the objects of this class hold the information about the ProjectileMetaObjects, which is read from the configs
  private String image;
  private String destroys;
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
  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @XmlElement
  public String getDestroys() {
    return destroys;
  }

  public void setDestroys(String destroys) {
    this.destroys = destroys;
  }

  @XmlElement(name = "vertex")
  public List<XmlVertex> getVertices() {
    return vertices;
  }

  public void setVertices(List<XmlVertex> vertices) {
    this.vertices = vertices;
  }

  public ArrayList<float[]> getVerticesAsFloats() {//TODO maybe a interface/superclass which lets Projectile and Target Standard get this method
    ArrayList<float[]> vertices = new ArrayList<>();
    for (XmlVertex vertex : this.vertices) {
      //the array constructor is not allowed when adding to a list
      float[] points = {vertex.getX(), vertex.getY()};
      vertices.add(points);
    }
    return vertices;
  }

}

