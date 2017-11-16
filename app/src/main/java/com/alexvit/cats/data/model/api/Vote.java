package com.alexvit.cats.data.model.api;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Aleksandrs Vitjukovs on 11/5/2017.
 */

@Root
public class Vote {

    @Element(name = "image_id")
    public String imageId;

    @Element
    public int score;

    @Element
    public String action;

    @Element(name = "sub_id")
    public String subId;

}
