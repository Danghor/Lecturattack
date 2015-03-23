package Lecturattack.entities;

import org.newdawn.slick.Image;

import java.util.HashMap;

/**
 * @author Tim Adamek
 * @author Nick Steyer
 */
public class ProjectileMeta extends MetaObject {
  private Image image;
  private TargetMeta.TargetType destroys;

  static {
    instances = new HashMap<ProjectileType, ProjectileMeta>();
    //todo: initialize instances with data from config file
  }

  private ProjectileMeta(Image image, TargetMeta.TargetType destroys) {
    this.image = image;
    this.destroys = destroys;
  }

  public static ProjectileMeta getInstance(ProjectileType type) {
    return (ProjectileMeta) instances.get(type);
  }

  TargetMeta.TargetType getTargetType() {
    return destroys;
  }

  Image getImage() {
    return image;
  }

  private enum ProjectileType {
    EXAM,
    ROBOT,
    POINTER
  }

}
