package Lecturattack.utilities.xmlHandling.configLoading;

import Lecturattack.utilities.FileHandler;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tim Adamek
 */
public class TargetStandard {
  //the objects of this class hold the information about the TargetMetaObjects, which is read from the configs
  private String imageIntact;
  private String imageSlightlyBroken;
  private String imageAlmostBroken;
  private int maxHits;
  private String targetType;
  private String positioning;
  private List<XmlVertex> vertices;
  private float mass;
  private float hitScore;
  private String soundPath1;
  private String soundPath2;
  private String soundPath3;

  @XmlElement(name = "sound1")
  public String getSoundPath1() {
    return soundPath1;
  }

  public void setSoundPath1(String soundPath1) {
    this.soundPath1 = soundPath1;
  }

  @XmlElement(name = "sound2")
  public String getSoundPath2() {
    return soundPath2;
  }

  public void setSoundPath2(String soundPath2) {
    this.soundPath2 = soundPath2;
  }

  @XmlElement(name = "sound3")
  public String getSoundPath3() {
    return soundPath3;
  }

  public void setSoundPath3(String soundPath3) {
    this.soundPath3 = soundPath3;
  }

  @XmlElement
  public float getHitScore() {
    return hitScore;
  }

  public void setHitScore(float hitScore) {
    this.hitScore = hitScore;
  }

  @XmlElement
  public float getMass() {
    return mass;
  }

  public void setMass(float mass) {
    this.mass = mass;
  }

  @XmlElement
  public String getImageIntact() {
    return imageIntact;
  }

  public void setImageIntact(String imageIntact) {
    this.imageIntact = imageIntact;
  }

  @XmlElement
  public String getImageSlightlyBroken() {
    return imageSlightlyBroken;
  }

  public void setImageSlightlyBroken(String imageSlightlyBroken) {
    this.imageSlightlyBroken = imageSlightlyBroken;
  }

  @XmlElement
  public String getImageAlmostBroken() {
    return imageAlmostBroken;
  }

  public void setImageAlmostBroken(String imageAlmostBroken) {
    this.imageAlmostBroken = imageAlmostBroken;
  }

  @XmlElement
  public int getMaxHits() {
    return maxHits;
  }

  public void setMaxHits(int maxHits) {
    this.maxHits = maxHits;
  }

  @XmlElement
  public String getTargetType() {
    return targetType;
  }

  public void setTargetType(String targetType) {
    this.targetType = targetType;
  }

  @XmlElement
  public String getPositioning() {
    return positioning;
  }

  public void setPositioning(String positioning) {
    this.positioning = positioning;
  }

  @XmlElement(name = "vertex")
  public List<XmlVertex> getVertices() {
    return vertices;
  }

  public void setVertices(List<XmlVertex> vertices) {
    this.vertices = vertices;
  }


  public Sound getSound1AsSound() throws SlickException {
    FileHandler fileHandler = new FileHandler();
    return fileHandler.loadSound(soundPath1);
  }

  public Sound getSound2AsSound() throws SlickException {
    FileHandler fileHandler = new FileHandler();
    return fileHandler.loadSound(soundPath2);
  }

  public Sound getSound3AsSound() throws SlickException {
    FileHandler fileHandler = new FileHandler();
    return fileHandler.loadSound(soundPath3);
  }

  public ArrayList<float[]> getVerticesAsFloats() {
    ArrayList<float[]> vertices = new ArrayList<>();
    for (XmlVertex vertex : this.vertices) {
      //the array constructor is not allowed when adding to a list
      float[] points = {vertex.getX(), vertex.getY()};
      vertices.add(points);
    }
    return vertices;
  }
}
