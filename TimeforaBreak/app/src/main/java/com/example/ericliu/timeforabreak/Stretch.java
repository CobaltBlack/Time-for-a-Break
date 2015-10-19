package com.example.ericliu.timeforabreak;

import java.util.List;

/**
 * Created by Eric Liu on 9/22/2015.
 * <p/>
 * Stretch object
 * <p/>
 * attributes:
 * id
 * path to image
 * name
 * description
 * array of tags
 */
public class Stretch {

    //Attributes
    private int id;
    private String image_path;
    private String name;
    private String description;
    private List tags;

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
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

    public List getTags() {
        return tags;
    }

    public void addTag(int tag) {
        //tags.add(tag);
    }

    Stretch() {

    }

    /**
     * Constructor for Stretch
     *
     * @param id:the                  ID number for the stretch
     * @param name:name               of stretch
     * @param description:description value to find in XML file
     * @param image_path:path         to image for stretch
     */
    Stretch(int id, String name, String description, String image_path) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image_path = image_path;
    }
}
