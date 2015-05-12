package Lecturattack.entities;

import Lecturattack.entities.types.ProjectileType;
import Lecturattack.entities.types.TargetType;
import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.xmlHandling.configLoading.ProjectileStandard;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Tim Adamek
 * @author Nick Steyer
 */
public class ProjectileMeta extends MetaObject {
  static {
    instances = new HashMap<>();
    FileHandler fileHandler = new FileHandler();
    // the xml config files are loaded
    List<ProjectileStandard> projectileStandards = fileHandler.loadProjectileStandards();

    for (ProjectileStandard projectileStandard : projectileStandards) {
      ProjectileType type = null;
      ArrayList<float[]> outline;
      Image image = null;
      ArrayList<TargetType> destroys = new ArrayList<>();

      //for every type that can be destroyed (except enemies) there is one
      //target metaobject for the horizontal and one for the vertical, so
      //the projectiles can destroy 2 projectiles
      switch (projectileStandard.getDestroys()) {
        case "ENEMY":
          destroys.add(TargetType.ENEMY);
          type = ProjectileType.EXAM; //todo: read type from XML files
          break;
        case "RAM":
          destroys.add(TargetType.RAMH);
          destroys.add(TargetType.RAMV);
          type = ProjectileType.POINTER;
          break;
        case "LIBRARY":
          destroys.add(TargetType.LIBRARYH);
          destroys.add(TargetType.LIBRARYV);
          type = ProjectileType.ROBOT;
          break;
      }

      try {
        image = new Image(projectileStandard.getImage());
      } catch (SlickException e) {
        e.printStackTrace();
      }

      outline = projectileStandard.getVerticesAsFloats();
      ProjectileMeta projectileMeta = new ProjectileMeta(outline, image, destroys);
      projectileMeta.mass = projectileStandard.getMass();
      //the possible metaObjects are mapped to the projectile type
      instances.put(type, projectileMeta);
    }
  }

  private final Image image;
  private final ArrayList<TargetType> destroys;

  private ProjectileMeta(ArrayList<float[]> outline, Image image, ArrayList<TargetType> destroys) {
    this.outline = outline;
    this.image = image;
    this.destroys = destroys;
  }

  public static ProjectileMeta getInstance(ProjectileType type) {
    return (ProjectileMeta) instances.get(type);
  }

  ArrayList<TargetType> getDestroys() {
    return destroys;
  }

  Image getImage() {
    //copying is necessary for rotating, otherwise instances could not be rotated individually
    return image.copy();
  }
}
