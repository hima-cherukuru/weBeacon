package com.och.bean;

public class MapImageLocation {
    private String name;
    private String description;
    private int buildingNum;
    private String type;
    private int floorNum;
    private int roomNum;
    private float[] latlng;
    private String available;
    

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getavailable() {
        return available;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    public void setBuildingNum(int buildingNum) {
        this.buildingNum = buildingNum;
    }

    public int getBuildingNum() {
        return buildingNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public int getRoomNum() {
        return roomNum;
    }
    
    public MapImageLocation() {
    }
    
    public MapImageLocation (String name, String description, int buildingNum, int floorNum, int roomNum, float[] latlng, String available, String type) {
        this.name = name;
        this.description = description;
        this.buildingNum = buildingNum;
        this.floorNum = floorNum;
        this.roomNum = roomNum;
        this.latlng = latlng;
        this.available = available;
        this.type = type;
    }
    
    public String getName() {
    return name;
    }
    
    public void setName(String name) {
    this.name = name;
    }
    
    public String getDescription() {
    return description;
    }
    
    public void setDescription(String description) {
    this.description = description;
    }
    
    public float[] getLatlng() {
    return latlng;
    }
    
    public void setLatlng(float[] latlng) {
    this.latlng = latlng;
    }
}