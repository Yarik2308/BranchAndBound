package com.project.models;

import java.util.ArrayList;
import java.util.List;

public class Leaf {
    List<Road> usedRoadsList;
    List<Road> blockedRoads;
    int Cost;

    public Leaf(int cost){
        this.Cost = cost;
        this.usedRoadsList = new ArrayList<>();
        this.blockedRoads = new ArrayList<>();
    }

    public List<Road> getUsedRoads() {return this.usedRoadsList;}

    public void addUsedRoad(Road road) {this.usedRoadsList.add(road);}

    public void setUsedRoadsList(List<Road> usedRoadsList) {
        this.usedRoadsList = usedRoadsList;
    }

    public List<Road> getBlockedRoads() {
        return blockedRoads;
    }

    public void addBlockedRoad(Road road){
        this.blockedRoads.add(road);
    }

    public void setBlockedRoads(List<Road> blockedRoads) {
        this.blockedRoads = blockedRoads;
    }

    public int getCost(){
        return this.Cost;
    }

    public void setCost(int cost) {
        Cost = cost;
    }
}
