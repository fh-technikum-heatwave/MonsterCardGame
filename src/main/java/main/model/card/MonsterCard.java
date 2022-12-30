package main.model.card;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Element;

@Getter
@Setter(AccessLevel.PROTECTED)
public class MonsterCard extends Card {

    @JsonCreator
    public MonsterCard(@JsonProperty("Id") String id, @JsonProperty("Name") String name, @JsonProperty("Damage") int damage) {
        super(name,damage,id);
        setMonsterCardWeakness();
    }


    private void setMonsterCardWeakness() {
        if (name.contains("Goblin")) {
            setWeakness("Dragon");
        } else if (name.contains("Knight")) {
            setWeakness("WaterSpell");
        } else if (name.contains("Dragon")) {
            setWeakness("FireElve");
        } else if (name.contains("Ork")) {
            setWeakness("Wizard");
        }
    }
}
