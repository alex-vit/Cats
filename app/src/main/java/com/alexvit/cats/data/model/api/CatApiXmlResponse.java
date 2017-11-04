package com.alexvit.cats.data.model.api;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@Root(name = "response")
public class CatApiXmlResponse {

    @Element
    public Data data;

}
