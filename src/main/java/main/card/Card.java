package main.card;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import main.Element;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class Card {
    protected Element type;
    protected String name;
    protected double damage;
    private static Random random = new Random();
    private String weakness = "";
    private Element typeWeakness;
    private List<String> monsterCardNames = new LinkedList<>();
    private String nameAndType;

    public Card(String name) {

        int rnd = random.nextInt(3);
        if (rnd == 0) {
            type = Element.WATER;
            typeWeakness = Element.NORMAL;
        } else if (rnd == 1) {
            type = Element.FIRE;
            typeWeakness = Element.WATER;
        } else {
            type = Element.NORMAL;
            typeWeakness = Element.FIRE;
        }

        try {
            Scanner scanner = new Scanner(new FileInputStream("C:\\GitHub\\MonsterCardGame\\src\\main\\java\\main\\db\\CardNames.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                monsterCardNames.add(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (name.equals("Monster")) {
            Collections.shuffle(monsterCardNames);
            String[] parts = monsterCardNames.get(0).split(",");
            if (parts.length > 1) {
                setWeakness(parts[1]);
            } else {
                setWeakness("NO");
            }
            setName(parts[0]);
        } else {
            setName(name);
        }

        setNameAndType((type + "" + name).toLowerCase(Locale.ROOT));


//      damage = random.nextInt(101);
        damage = 100;
    }

}
