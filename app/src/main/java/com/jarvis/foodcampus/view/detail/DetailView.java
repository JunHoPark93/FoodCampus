package com.jarvis.foodcampus.view.detail;

import com.jarvis.foodcampus.model.DetailModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by EunBi on 2016-10-23.
 */

public interface DetailView {

    void add(ArrayList<String> arrayGroup, HashMap<String, ArrayList<DetailModel>> arrayChild);
}
