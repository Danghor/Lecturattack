package Lecturattack.utilities.xmlHandling.configLoading;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Tim Adamek
 */
public class PlayerStandard {
  private String name;//TODO see fi really necessary
  private String imageArm;
  private String imageBody;
  private String projectile;

  @XmlElement
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @XmlElement
  public String getImageArm() {
    return imageArm;
  }

  public void setImageArm(String imageArm) {
    this.imageArm = imageArm;
  }

  @XmlElement
  public String getImageBody() {
    return imageBody;
  }

  public void setImageBody(String imageBody) {
    this.imageBody = imageBody;
  }

  @XmlElement
  public String getProjectile() {
    return projectile;
  }

  public void setProjectile(String projectile) {
    this.projectile = projectile;
  }
}
