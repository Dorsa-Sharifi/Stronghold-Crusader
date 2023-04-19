package model.Building;

import model.Empire;

import java.util.HashMap;

public class Stable extends Building{
    Stable(Empire government) {
        super(government);
    }
    private int rate;
    private Names names;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Names getNames() {
        return names;
    }
    public HashMap<String , Integer> cost = new HashMap<>();
    //TODO call createBuildingCost before usage
    public void createBuildingCost(int wood , int stone , int gold , int iron , int oil ){
        cost.put("wood" , wood);
        cost.put("stone" , stone);
        cost.put("gold" , gold);
        cost.put("iron" , iron);
        cost.put("oil" , oil);
    }
    public void stable(){
        names = Names.STABLE;
        createBuildingCost(20 , 0 , 400 , 0 , 0);
    }
}
