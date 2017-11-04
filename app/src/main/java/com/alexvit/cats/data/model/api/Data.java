package com.alexvit.cats.data.model.api;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@Root
public class Data {

    @ElementList
    public ArrayList<Image> images;

}
