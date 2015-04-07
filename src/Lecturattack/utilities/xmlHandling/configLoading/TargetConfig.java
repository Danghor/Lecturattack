package Lecturattack.utilities.xmlHandling.configLoading;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tim Adamek
 */
@XmlRootElement(name = "targets")
public class TargetConfig {
  //this class is a wrapping class, which holds multiple TargetStandards,
  //this is needed because JAXP can only return a singe object when unmarshalling, not a list of objects
  private List<TargetStandard> target = new ArrayList<TargetStandard>() {
  };

  public TargetConfig() {
  }

  public TargetConfig(List<TargetStandard> target) {
    this.target = target;
  }

  @XmlElement(name = "target")
  public List<TargetStandard> getTargetStandards() {
    return target;
  }

  public void setTargetStandards(List<TargetStandard> targetStandards) {
    this.target = targetStandards;
  }

}
