package com.truemind.selidpic_v20;

/**
 * Created by 현석 on 2017-04-21.
 */
public class Constants {

    /** PHOTO_TYPE은 촬영하고자 하는 규격의 이름을 담당함*/
    public static final String PHOTO_TYPE1 = "증명사진";
    public static final String PHOTO_TYPE2 = "사용자 지정 규격";
    public static final String PHOTO_TYPE3 = "반명함사진";
    public static final String PHOTO_TYPE4 = "여권사진";
    public static final String PHOTO_TYPE5 = "명함사진";

    /** PHOTO_TYPE의 width와 height는 촬영하고자 하는 규격의 치수를 담당함*/
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

    /** PHOTO_CURRENT_TYPE은 현재 선택된 사진 규격의 이름을 담당함
     * 설정할 때는 한꺼번에, 가져올 때는 따로따로
     * */
    public static String PHOTO_CURRENT_TYPE = "0";
    public static int PHOTO_CURRENT_WIDTH = 0;
    public static int PHOTO_CURRENT_HEIGTH = 0;

    public void setCurrentPhotoAll(String type, int width, int height){
        PHOTO_CURRENT_TYPE = type;
        PHOTO_CURRENT_WIDTH = width;
        PHOTO_CURRENT_HEIGTH = height;
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

    /** 사용자 지정 규격을 설정하는 함수*/
    public void setUserTypeSize(int width, int height){
        PHOTO_TYPE2_WIDTH = width;
        PHOTO_TYPE2_HEIGHT = height;
    }

    /** 최종적으로 출력되는 사진의 규격 (저장, 가공 용도)
     * 이 경우에는 표면적인 사진의 규격이 아닌, 비율과 화면 크기를 고려하여 설정된 값
     * 최종 사진의 규격을 설정*/
    public static double photoWidth = 0;
    public static double photoHeight = 0;

    public void setFinalPhotoSize(int width, int height){
        photoWidth = width;
        photoHeight = height;
    }

    /** 카메라 설정에서의 활성/비활성화 값들*/
    public static boolean camGuideValidate = false;
    public static boolean camTitleInvalidate = false;
    public static int camTimerTime = 5;

    /** 촬영된 사진의 byte Stream을 저장*/
    public static byte[] photoByteStream;

}
