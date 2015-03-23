package Lecturattack.entities;

import java.util.HashMap;

/**
 * @author Tim Adamek
 * @author Nick Steyer
 */
public class ProjectileMeta extends MetaObject {
  private TargetMeta.TargetType destroys;

  static {
    instances = new HashMap<ProjectileType, ProjectileMeta>();
    //todo: initialize instances with data from config file
  }

  private ProjectileMeta() {
  }

  public static ProjectileMeta getInstance(ProjectileType type) {
    return (ProjectileMeta) instances.get(type);
  }

  TargetMeta.TargetType getTargetType() {
    return destroys;
  }

  private enum ProjectileType {
    EXAM,
    ROBOT,
    POINTER
  }

}
