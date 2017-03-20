package com.aspsine.irecyclerview.demo.model;

/**
 * Created by aspsine on 16/4/5.
 */
public class Image {
    public String image;
    public String title;

    public Image(String image, String title) {
        this.image = image;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Image{" +
                "image='" + image + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
