package com.alexvit.cats.data.model.api;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by Aleksandrs Vitjukovs on 11/5/2017.
 */

@Root(name = "data")
public class VoteData {

    @ElementList
    public ArrayList<Vote> votes;

}
