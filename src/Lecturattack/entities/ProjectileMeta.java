package Lecturattack.entities;

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
    instances = new HashMap<ProjectileType, ProjectileMeta>();
    //todo: initialize instances with data from config file
    List<ProjectileStandard> projectileStandards = FileHandler.loadProjectileStandards();

    for (ProjectileStandard projectileStandard : projectileStandards) {

      ArrayList<float[]> outline;
      Image image = null;//TODO from FileHandler
      ArrayList<TargetMeta.TargetType> destroys = new ArrayList<>();

      switch (projectileStandard.getDestroys()) {
        case "ENEMY":
          TargetMeta.TargetType ENEMY = TargetMeta.TargetType.ENEMY;
          destroys.add(ENEMY);

          break;
        case "RAM":
          TargetMeta.TargetType RAMH = TargetMeta.TargetType.RAMH;
          TargetMeta.TargetType RAMV = TargetMeta.TargetType.RAMV;
          destroys.add(RAMH);
          destroys.add(RAMV);
          break;
        case "LIBRARY":
          TargetMeta.TargetType LIBRARYH = TargetMeta.TargetType.LIBRARYH;
          TargetMeta.TargetType LIBRARYV = TargetMeta.TargetType.LIBRARYV;
          destroys.add(LIBRARYH);
          destroys.add(LIBRARYV);
          break;
      }

      try {
        image = new Image(projectileStandard.getImage());
      } catch (SlickException e) {
        e.printStackTrace();
      }

      outline = projectileStandard.getVerticesAsFloats();

      instances.put(ProjectileType.EXAM, new ProjectileMeta(outline, image, destroys));

    }
  }

  private Image image;
  private ArrayList<TargetMeta.TargetType> destroys;

  //todo: add vertices
  private ProjectileMeta(ArrayList<float[]> outline, Image image, ArrayList<TargetMeta.TargetType> destroys) {
    this.outline = outline;
    this.image = image;
    this.destroys = destroys;
  }

  public static ProjectileMeta getInstance(ProjectileType type) {
    return (ProjectileMeta) instances.get(type);
  }

  ArrayList<TargetMeta.TargetType> getDestroyableTargetTypes() {
    return destroys;
  }

  Image getImage() {
    return image;
  }

  public enum ProjectileType {
    EXAM,
    ROBOT,
    POINTER
  }

}
