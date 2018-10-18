package com.alexvit.cats.data.model.api;

import com.alexvit.cats.data.model.Image;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@Root(name = "data")
public class ImageData {

    @ElementList(name = "images")
    public ArrayList<Image> images;

}
