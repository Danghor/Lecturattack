/*
 * Copyright (c) 2015.
 */

package Lecturattack.utilities.xmlHandling.configLoading;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Steyer on 19/03/2015
 */
@XmlRootElement(name = "targets")
public class TargetConfig {
  private List<TargetStandard> target = new ArrayList<TargetStandard>() {
  };

  public TargetConfig() {
  }

  public TargetConfig(int id, List<TargetStandard> target) {
    this.target = target;
  }

  @XmlElement(name = "target")
  public List<TargetStandard> getTargetStandards() {
    return target;
  }

  public void setTargetStandards(List<TargetStandard> targetStandards) {
    this.target = target;
  }

}
