package com.truemind.selidpic_v20;

/**
 * Created by 현석 on 2017-04-21.
 */
public class Constants {

    public static final String PHOTO_TYPE1 = "1";
    public static final String PHOTO_TYPE2 = "2";
    public static final String PHOTO_TYPE3 = "3";
    public static final String PHOTO_TYPE4 = "4";
    public static final String PHOTO_TYPE5 = "5";

    public static final int PHOTO_TYPE1_WIDTH = 25;
    public static final int PHOTO_TYPE1_HEIGHT = 30;

    public static int PHOTO_TYPE2_WIDTH;
    public static int PHOTO_TYPE2_HEIGHT;

    public static final int PHOTO_TYPE3_WIDTH = 30;
    public static final int PHOTO_TYPE3_HEIGHT = 40;

    public static final int PHOTO_TYPE4_WIDTH = 35;
    public static final int PHOTO_TYPE4_HEIGHT = 40;

    public static final int PHOTO_TYPE5_WIDTH = 50;
    public static final int PHOTO_TYPE5_HEIGHT = 60;

    public void writeUserTypeSize(int width, int height){

        this.PHOTO_TYPE2_WIDTH = width;
        this.PHOTO_TYPE2_HEIGHT = height;

    }

}
