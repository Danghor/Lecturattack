package Lecturattack.utilities.xmlHandling.configLoading;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @author Tim Adamek
 */
public class ProjectileStandard {
  private String image;
  private String destroys;
  private List<XmlVertex> vertices;

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

  @XmlElement(name = "vertice")
  public List<XmlVertex> getVertices() {
    return vertices;
  }

  public void setVertices(List<XmlVertex> vertices) {
    this.vertices = vertices;
  }

}

