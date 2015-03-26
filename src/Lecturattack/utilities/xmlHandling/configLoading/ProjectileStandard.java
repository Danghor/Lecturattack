package Lecturattack.utilities.xmlHandling.configLoading;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
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

  public ArrayList<float[]> getVerticesAsFloats(){//TODO maybe a interface/superclass which lets Projectile and Target Standard get this method
    ArrayList<float[]> vertexes = new ArrayList<>();
    for(XmlVertex vertice: vertices){
      //the array constructor is not allowed when adding to a list
      float[] points = {vertice.getX(),vertice.getY()};
      vertexes.add(points);
    }
    return vertexes;
  }

}

