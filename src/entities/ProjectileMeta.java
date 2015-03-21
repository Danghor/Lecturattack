package entities;

import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Tim Adamek
 * @author Nick Steyer
 */
public class ProjectileMeta {

  private static HashMap<ProjectileType, ProjectileMeta> projectileMetaInstances;
  private Image image;
  private float mass;
  private TargetMeta.TargetType destroys;
  private ArrayList<float[]> projectileOutline;

  static {
    //todo: initialize instances with data from config file
  }

  private ProjectileMeta() {
  }

  public static ProjectileMeta getInstance(ProjectileType type) {
    return projectileMetaInstances.get(type);
  }

  public Image getImage() {
    return image;
  }

  public TargetMeta.TargetType getTargetType() {
    return destroys;
  }

  public ArrayList<float[]> getProjectileOutline() {
    return projectileOutline;
  }

  public float getMass() {
    return mass;
  }

  private enum ProjectileType {
    EXAM,
    ROBOT,
    POINTER
  }

}
