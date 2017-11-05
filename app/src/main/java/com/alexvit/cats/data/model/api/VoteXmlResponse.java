package com.alexvit.cats.data.model.api;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Aleksandrs Vitjukovs on 11/5/2017.
 */

@Root(name = "response")
public class VoteXmlResponse {

    @Element(name = "data")
    public VoteData data;
}
