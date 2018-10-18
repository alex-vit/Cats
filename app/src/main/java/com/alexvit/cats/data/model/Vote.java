package com.alexvit.cats.data.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Aleksandrs Vitjukovs on 11/5/2017.
 */

@Root
public class Vote {

    @Element(name = "image_id")
    public String imageId;

    @Element(name = "score")
    public int score;

    @Element(name = "action")
    public String action;

    @Element(name = "sub_id")
    public String subId;

}
