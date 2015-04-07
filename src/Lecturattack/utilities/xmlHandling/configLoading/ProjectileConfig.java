package Lecturattack.utilities.xmlHandling.configLoading;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tim Adamek
 */
@XmlRootElement(name = "projectiles")
public class ProjectileConfig {
  //this class is a wrapping class, which holds multiple ProjectileStandards,
  //this is needed because JAXP can only return a singe object when unmarshalling, not a list of objects
  private List<ProjectileStandard> projectile = new ArrayList<>();

  public ProjectileConfig() {
  }

  public ProjectileConfig(List<ProjectileStandard> projectile) {
    this.projectile = projectile;
  }

  @XmlElement(name = "projectile")
  public List<ProjectileStandard> getProjectileStandards() {
    return projectile;
  }

  public void setProjectileStandards(List<ProjectileStandard> projectileStandard) {
    this.projectile = projectileStandard;
  }

}
