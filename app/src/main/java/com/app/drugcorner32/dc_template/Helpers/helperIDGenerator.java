package com.app.drugcorner32.dc_template.Helpers;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Tarun on 05-03-2015.
 */
public class helperIDGenerator {
    public static Integer[] randomUniqueInt = new Integer[1000];

    public static int count = 0;

    public static void init(){
        for(int i = 0;i<1000;i++){
            randomUniqueInt[i] = i;
        }
        Collections.shuffle(Arrays.asList(randomUniqueInt));
    }

    public static int getID(){
        return randomUniqueInt[count++];
    }
}
