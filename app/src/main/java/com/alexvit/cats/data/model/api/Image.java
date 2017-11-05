package com.alexvit.cats.data.model.api;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@Root
public class Image {

    @Element
    public String id;

    @Element
    public String url;

    @Element(name = "source_url", required = false)
    public String sourceUrl;

    @Element(required = false)
    public int score;

    @Element(required = false)
    String created;

    @Element(name = "sub_id", required = false)
    String subId;

}
