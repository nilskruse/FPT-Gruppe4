package controller;

import interfaces.SerializableStrategy;
import model.Model;

public class Serialization {
    private SerializableStrategy strat;

    public Serialization(){

    }
    public Serialization(SerializableStrategy strat){
        this.strat = strat;
    }

    public void setStrat(SerializableStrategy strat) {
        this.strat = strat;
    }

    public void load(Model model){
        strat.load(model);
    }
    public void save(Model model){
        strat.save(model);
    }
}
