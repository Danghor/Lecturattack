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
  private List<ProjectileStandard> projectile = new ArrayList<ProjectileStandard>();

  public ProjectileConfig() {
  }

  public ProjectileConfig(List<ProjectileStandard> projectile) {
    this.projectile = projectile;
  }

  @XmlElement(name = "projectile")
  public List<ProjectileStandard> getProjectileStandards() {
    return projectile;
  }

  public void setTargetStandards(List<ProjectileStandard> projectileStandard) {
    this.projectile = projectileStandard;
  }

}
