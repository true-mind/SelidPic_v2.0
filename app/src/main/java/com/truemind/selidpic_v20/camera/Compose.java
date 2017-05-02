package com.truemind.selidpic_v20.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.truemind.selidpic_v20.R;

public class Compose {
    private double ppi;
    private double width;
    private double height;

    private int screenWidth;
    private int screenHeight;
    private int counterPara;

    private int widthMid;
    private int heightMid;

    private double picHeight;
    private double picWidth;

    private double cropStartX;
    private double cropStartY;

    private double back_width;
    private double back_height;

    private Context context;

    private Bitmap background_before_crop;
    private Bitmap image;
    private Bitmap background;
    private Bitmap backgroundPara;
    private Bitmap imageCropped;
    private byte[] arr;

    public Bitmap compose(final Context context, final byte[] arr, final double width, final double height, final double ppi, Bitmap backgroundPara) {
        this.ppi = ppi;
        this.width = height;
        this.height = width;
        this.context = context;
        this.arr = arr;
        this.backgroundPara = backgroundPara;

        thread.start();
        try {
            thread.join();
            return image;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            //이미지 해상도 조절
            image = set_resolution();

            screenWidth = image.getWidth();
            screenHeight = image.getHeight();

            counterPara = (int) Math.sqrt(ppi)*240000;

            widthMid = screenWidth/2;
            heightMid = screenHeight/2;

            picHeight = (screenWidth/width*height);
            picWidth = (screenHeight/height*width);

            if(picHeight>screenHeight){
                picHeight = screenHeight;
                picWidth = (picHeight/height*width);
            }else{
                picWidth = screenWidth;
                picHeight = (picWidth/width*height);
            }
            cropStartX = widthMid - (picWidth/2);
            cropStartY = heightMid - (picHeight/2);

            imageCropped = Bitmap.createBitmap(image, (int) cropStartX, (int) cropStartY, (int) picWidth, (int) picHeight);
            image.recycle();

            //배경 이미지 가져오기
                    //background_before_crop = BitmapFactory.decodeResource(context.getResources(), R.drawable.photo_back);
                    background_before_crop = backgroundPara;
            back_width = background_before_crop.getWidth();
            back_height = background_before_crop.getHeight();

            double crop_w = (back_width - picHeight);
            crop_w/=2;

            Log.d("mytag", "width : "+width+", height : "+height+", screenWidth : "+screenWidth+", screenHeight : "+screenHeight+", picHeight : "+picHeight+", picWidth : "+picWidth
                    +", cropStartX : "+cropStartX+", cropStartY : "+cropStartY+", back_width : "+back_width+", back_height : "+back_height+", crop_w : "+crop_w);

            background = Bitmap.createBitmap(background_before_crop, (int) crop_w, 0, (int) picHeight, (int) picWidth);
            background_before_crop.recycle();

            image = getEdge(rotateImage(imageCropped, 90));
            imageCropped.recycle();
            background.recycle();
        }
    });

    private Bitmap rotateImage(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.setScale(1, -1);  // 상하반전
        matrix.setScale(-1, 1);  // 좌우반전

        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }


    private Bitmap set_resolution() { //Get the dimensions of the View

        int targetW, targetH;
        //onCreate 안에서 view가 아직 안 띄워짐 고로 임의 값 설정하겠음 나중에 수정해도 됨

        double tempW = width;
        double tempH = height;
        double multy_value = ppi/30;
        tempW *= multy_value;
        tempH *= multy_value;

        targetW = (int) tempW;
        targetH = (int) tempH;

        //Get the dimensions of the bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        if(arr==null){
            Log.d("mytag", "null");
        }
        //BitmapFactory.decodeByteArray(arr, 0, arr.length, options);
        int photoW = options.outHeight;
        int photoH = options.outWidth;

        //Determine how much to alpha down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        //Decode the image file into a Bitmap sized to fill the View
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        return BitmapFactory.decodeByteArray(arr, 0, arr.length, options);
    }



    private Bitmap getEdge(Bitmap bitmapimage){ // Sobel 윤곽선 검출 알고리즘 사용
        int  Gx[][], Gy[][];
        int width = bitmapimage.getWidth();
        int height = bitmapimage.getHeight();
        int[] pixels = new int[width * height];
        int[][] output = new int[width][height];
        int i, j, counter, k=0;

        int edge_y[] = new int[width];
        int temp_point_right[][] = new int[100][2];
        int temp_point_left[][] = new int[100][2];
        int temp_point_left_length = 0;
        int temp_point_right_length = 0;

        for(i=0;i<width;i++){
            for(j=0;j<height;j++){
                pixels[k]=bitmapimage.getPixel(i, j);
                output[i][j] = pixels[k];
                k++;
            }
        }
        Gx = new int[width][height];
        Gy = new int[width][height];

        counter = 0;
        for (i=0; i<width; i++) {
            for (j=0; j<height; j++) {
                if (i==0 || i==width-1 || j==0 || j==height-1) {
                    Gx[i][j] = Gy[i][j] = 0;// Image boundary cleared
                    pixels[counter] = 0;
                    counter++;
                }
                else{
                    Gx[i][j] = output[i+1][j-1] + 2*output[i+1][j] + output[i+1][j+1] -
                            output[i-1][j-1] - 2*output[i-1][j] - output[i-1][j+1];
                    Gy[i][j] = output[i-1][j+1] + 2*output[i][j+1] + output[i+1][j+1] -
                            output[i-1][j-1] - 2*output[i][j-1] - output[i+1][j-1];
                    pixels[counter] = Math.abs(Gx[i][j]) + Math.abs(Gy[i][j]);
                    counter++;
                }
            }
        }

        boolean founded_edge;
        counter=0;
        founded_edge=false;

        for(i=0;i<width;i++){
            for(j=0;j<height;j++){
                if(pixels[counter]>counterPara){
                    if(!founded_edge){
                        edge_y[i] = j;
                        int c, q;
                        for(q=j;q<j+8;q++){
                            if(q>=height){
                                break;
                            }
                            c=background.getPixel(i, q);
                            bitmapimage.setPixel(i, q, c);
                        }
                        founded_edge = true;
                    }
                }
                if(!founded_edge){
                    int c = background.getPixel(i, j);
                    bitmapimage.setPixel(i, j, c);
                }
                counter++;
            }
            if(!founded_edge){
                edge_y[i] = 0;
            }else{
                founded_edge=false;
            }
        }

        j=0;
        for(i=width/2;i<width-1;i++){
            if(edge_y[i]-edge_y[i-1] > (height/5)){
                temp_point_right[j][0] = edge_y[i-1];
                temp_point_right[j][1] = edge_y[i];
                j++;
                temp_point_right_length++;
            }
        }

        j=0;
        for(i=1;i<width/2;i++){
            if(edge_y[i-1]-edge_y[i] > (height/5)){
                temp_point_left[j][0] = edge_y[i-1];
                temp_point_left[j][1] = edge_y[i];
                j++;
                temp_point_left_length++;
            }
        }

        founded_edge=false;
        for(int q=0;q<temp_point_left_length;q++) {
            for (i = temp_point_left[q][0]; i > temp_point_left[q][1]; i--) {
                counter = i;
                for (j = 0; j < width / 2; j++) {
                    counter += height;
                    if (pixels[counter] > counterPara) {
                        if (!founded_edge) {
                            int c, p;
                            for(p=j;p<j+5;p++){
                                if(p>=width/2){
                                    break;
                                }
                                c=background.getPixel(p, i);
                                bitmapimage.setPixel(p, i, c);
                            }
                            founded_edge = true;
                        }
                    }
                    if (!founded_edge) {
                        int c = background.getPixel(j, i);
                        bitmapimage.setPixel(j, i,  c);
                    }
                }
                founded_edge = false;
            }
        }

        founded_edge=false;
        for(int q=0;q<temp_point_right_length;q++){
            for(i = temp_point_right[q][0]; i < temp_point_right[q][1]; i++){
                counter = (height
                        * (width-1)) + i;
                for(j=width-1; j>width/2; j--){
                    counter -= height;
                    if(pixels[counter] > counterPara) {
                        if(!founded_edge){
                            int c, p;
                            for(p=j;p>j-5;p--){
                                if(p<=width/2){
                                    break;
                                }
                                c=background.getPixel(p, i);
                                bitmapimage.setPixel(p, i, c);
                            }
                            founded_edge = true;
                        }
                    }
                    if(!founded_edge){
                        int c = background.getPixel(j, i);
                        bitmapimage.setPixel(j, i, c);}
                }
                founded_edge=false;
            }
        }
        return bitmapimage;
    }

}