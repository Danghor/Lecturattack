package Lecturattack.utilities.xmlHandling.configLoading;

import Lecturattack.entities.ProjectileMeta;
import Lecturattack.entities.types.ProjectileType;
import Lecturattack.utilities.FileHandler;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Tim Adamek
 * @author Andreas Geis
 */
public class PlayerStandard {
  //the objects of this class hold the information about the Players, which is read from the configs
  private String name;
  private String imageArm;
  private String imageBody;
  private String projectile;
  private String sound;

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

  @XmlElement
  public String getSound() {
    return sound;
  }

  public void setSound(String sound) {
    this.sound = sound;
  }

  public Image getArmImageAsImage() throws SlickException {
    return new Image(imageArm);
  }

  public Image getBodyImageAsImage() throws SlickException {
    return new Image(imageBody);
  }

  public Sound getSoundAsSound() {
    return new FileHandler().loadSound(sound);
  }

  public ProjectileMeta getProjectileMeta() {
    ProjectileMeta projectileMeta = null;//If the wrong projectile is specified in playerStandard ProjectileMeta.getInstance() will also return null
    switch (projectile) {
      case "ROBOT":
        projectileMeta = ProjectileMeta.getInstance(ProjectileType.ROBOT);
        break;
      case "EXAM":
        projectileMeta = ProjectileMeta.getInstance(ProjectileType.EXAM);
        break;
      case "POINTER":
        projectileMeta = ProjectileMeta.getInstance(ProjectileType.POINTER);
        break;
    }
    return projectileMeta;
  }

}
