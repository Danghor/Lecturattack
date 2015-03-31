package Lecturattack.utilities.xmlHandling.configLoading;

import Lecturattack.entities.ProjectileMeta;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

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


  public Image getArmImageAsImage() throws SlickException {
    return new Image(imageArm);
  }

  public Image getBodyImageAsImage() throws SlickException {
    return new Image(imageBody);
  }

  //TODO see if this is really a good idea because there might be a lot of dependencys
  public ProjectileMeta getProjectileMeta() {
    ProjectileMeta projectileMeta = null;//If the wrong projectie is specifed in playerStandart ProjectileMeta.getInstance() will also return null
    if (projectile.equals("ROBOT")) {
      projectileMeta = ProjectileMeta.getInstance(ProjectileMeta.ProjectileType.ROBOT);
    } else if (projectile.equals("EXAM")) {
      projectileMeta = ProjectileMeta.getInstance(ProjectileMeta.ProjectileType.EXAM);
    } else if (projectile.equals("POINTER")) {
      projectileMeta = ProjectileMeta.getInstance(ProjectileMeta.ProjectileType.POINTER);
    }
    return projectileMeta;
  }
}
