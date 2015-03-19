package NoPackageCreatedYetPackage;

import entities.Projectile;
import entities.Target;
import org.newdawn.slick.Image;
import utilities.EnhancedVector;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tim on 19.03.2015.
 */
public class ProjectileMeta {

  private enum ProjectileType {
    EXAM,
    ROBOT,
    POINTER;
  }

  private Image image;
  private float mass;
  private Target.TargetType destroys;
  private ArrayList<float[]> projectileOutline;
  private HashMap<ProjectileType, ProjectileMeta> projectileMetaInstances;


  public Image getImage() {
    return null;
  }

  public Target.TargetType getTargetType() {
    return null;
  }

  public ArrayList<float[]> getProjectileOutline() {
    return null;
  }

  public float getMass() {
    return 0;
  }

  private ProjectileMeta() {

  }

  public ProjectileMeta getProjectileMeta(ProjectileType projectileType) {
    return projectileMetaInstances.get(projectileType);
  }

}
