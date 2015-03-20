package entities;

import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tim on 19.03.2015.
 */
public class ProjectileMeta {

  private Image image;
  private float mass;
  private Target.TargetType destroys;
  private ArrayList<float[]> projectileOutline;
  private HashMap<ProjectileType, ProjectileMeta> projectileMetaInstances;

  private ProjectileMeta() {

  }

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

  public ProjectileMeta getProjectileMeta(ProjectileType projectileType) {
    return projectileMetaInstances.get(projectileType);
  }

  private enum ProjectileType {
    EXAM,
    ROBOT,
    POINTER
  }

}
