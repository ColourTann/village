package tann.village.gameplay.effect;


import static tann.village.gameplay.effect.EffAct.ActivationType.*;

public class EffAct {

    public enum ActivationType{NOW, IN_TURNS, FOR_TURNS, UPKEEP, PASSIVE}

    public static EffAct now = new EffAct(NOW, 0);
    public ActivationType type;
    public int value;
    public EffAct(ActivationType type, int value){
        this.type=type;
        this.value=value;
    }

    public EffAct(ActivationType type){
        this(type, 0);
    }

    public String toString(){
        switch(type){
            case NOW:
                return "now";
            case IN_TURNS:
                return "in "+value+" turns";
            case FOR_TURNS:
                return "for "+value+" turns";
            case UPKEEP:
                break;
            case PASSIVE:
                break;
        }
        return "";
    }

    public String toWriterString(){
        return "";
    }

    public boolean equiv(EffAct other){
        return this.type==other.type && this.value==other.value;
    }
}
