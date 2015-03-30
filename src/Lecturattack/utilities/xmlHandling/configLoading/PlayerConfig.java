package Lecturattack.utilities.xmlHandling.configLoading;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tim Adamek
 */
@XmlRootElement(name = "players")
public class PlayerConfig {
  private List<PlayerStandard> players = new ArrayList<>();

  public PlayerConfig() {
  }

  public PlayerConfig(List<PlayerStandard> players) {
    this.players = players;
  }

  @XmlElement(name = "player")
  public List<PlayerStandard> getPlayerStandards() {
    return players;
  }

  public void setPlayerStandards(List<PlayerStandard> playerStandard) {
    this.players = playerStandard;
  }

}
