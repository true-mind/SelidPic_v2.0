package com.truemind.selidpic_v20;

/**
 * Created by 현석 on 2017-04-21.
 */
public class Constants {

    public static final String PHOTO_TYPE1 = "증명사진";
    public static final String PHOTO_TYPE2 = "사용자 지정 규격";
    public static final String PHOTO_TYPE3 = "반명함사진";
    public static final String PHOTO_TYPE4 = "여권사진";
    public static final String PHOTO_TYPE5 = "명함사진";

    public static final int PHOTO_TYPE1_WIDTH = 25;
    public static final int PHOTO_TYPE1_HEIGHT = 30;

    public static int PHOTO_TYPE2_WIDTH = 0;
    public static int PHOTO_TYPE2_HEIGHT = 0;

    public static final int PHOTO_TYPE3_WIDTH = 30;
    public static final int PHOTO_TYPE3_HEIGHT = 40;

    public static final int PHOTO_TYPE4_WIDTH = 35;
    public static final int PHOTO_TYPE4_HEIGHT = 45;

    public static final int PHOTO_TYPE5_WIDTH = 50;
    public static final int PHOTO_TYPE5_HEIGHT = 70;

    public static String PHOTO_CURRENT_TYPE = "0";
    public static int PHOTO_CURRENT_WIDTH = 0;
    public static int PHOTO_CURRENT_HEIGTH = 0;

    public void setUserTypeSize(int width, int height){
        PHOTO_TYPE2_WIDTH = width;
        PHOTO_TYPE2_HEIGHT = height;
    }

    public String getCurrentPhotoType(){
        return PHOTO_CURRENT_TYPE;
    }

    public int getCurrentPhotoWidth(){
        return PHOTO_CURRENT_WIDTH;
    }

    public int getCurrentPhotoHeight() {
        return PHOTO_CURRENT_HEIGTH;
    }

    public void setCurrentPhotoAll(String type, int width, int height){
        PHOTO_CURRENT_TYPE = type;
        PHOTO_CURRENT_WIDTH = width;
        PHOTO_CURRENT_HEIGTH = height;
    }

    public static double photoWidth = 0;
    public static double photoHeight = 0;

    public void setFinalPhotoSize(int width, int height){
        photoWidth = width;
        photoHeight = height;
    }

    public static boolean camGuideValidate = false;
    public static boolean camTitleInvalidate = false;
    public static int camTimerTime = 5;
    public static boolean camManualGuide = false;

    public static byte[] photoByteStream;

}
