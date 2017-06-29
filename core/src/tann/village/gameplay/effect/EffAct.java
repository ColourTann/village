package tann.village.gameplay.effect;


import static tann.village.gameplay.effect.EffAct.ActivationType.*;

public class EffAct {

    public enum ActivationType{NOW, in_turns, for_turns, passive}

    public static EffAct now = new EffAct(NOW, 0);
    ActivationType type;
    int value;
    int value2;
    public EffAct(ActivationType type, int value){
        this.type=type;
        this.value=value;
    }
}
