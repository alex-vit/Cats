package com.alexvit.cats.data.model.api;

import org.simpleframework.xml.Element;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class Image {

    @Element
    public String id;

    @Element
    public String source_url;

    @Element
    public String url;

}
