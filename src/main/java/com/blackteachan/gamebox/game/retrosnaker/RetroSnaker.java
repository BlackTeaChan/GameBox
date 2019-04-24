package com.blackteachan.gamebox.game.retrosnaker;

/**
 * @author blackteachan
 */
public class RetroSnaker extends Thread{

    public static final int ORIENTATION_LEFT = 0;
    public static final int ORIENTATION_UP = 1;
    public static final int ORIENTATION_RIGHT = 2;
    public static final int ORIENTATION_DOWN = 3;

    private static final int NUMBER_X = 10;
    private static final int NUMBER_Y = 20;

    public static final byte STATE_STOP = 0;
    public static final byte STATE_RUN = 1;
    public static final byte STATE_PAUSE = 2;
    private byte mState = STATE_STOP;

    private static byte[][] mMap = new byte[NUMBER_Y][NUMBER_X];
    private static int mX = 0;
    private static int mY = 0;
    private static Callback mCallback = null;

    public RetroSnaker(){
        initMap();
    }

    @Override
    public void start() {
        while(true){
            if(mState == STATE_RUN){
                //运行
                //■□
//                System.out.println();
//                for (int i = 0; i < NUMBER_Y; i++) {
//                    for (int j = 0; j < NUMBER_X; j++) {
//                        if(mMap[i][j] == 0){
//                            System.out.printf("□");
//                        }else {
//                            System.out.printf("■");
//                        }
//                    }
//                    System.out.println();
//                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(mCallback != null) {
                    mCallback.update(NUMBER_X, NUMBER_Y, mMap);
                }
            }else if(mState == STATE_STOP){
                //停止
                break;
            }
        }
    }

    public void remove(int orientation){
        if(orientation == ORIENTATION_LEFT){
            //防止穿墙
            if(mX != 0){
                mMap[mY][mX--] = 0;
                mMap[mY][mX] = 1;
            }
        }else if(orientation == ORIENTATION_UP){
            if(mY != 0){
                mMap[mY--][mX] = 0;
                mMap[mY][mX] = 1;
            }
        }else if(orientation == ORIENTATION_RIGHT){
            if(mX != NUMBER_X){
                mMap[mY][mX++] = 0;
                mMap[mY][mX] = 1;
            }
        }else if(orientation == ORIENTATION_DOWN){
            if(mY != NUMBER_Y){
                mMap[mY++][mX] = 0;
                mMap[mY][mX] = 1;
            }
        }
        if(mCallback != null) {
            mCallback.update(NUMBER_X, NUMBER_Y, mMap);
        }
    }

    /**
     * 设置运行状态
     * @param state
     */
    public void setState(byte state) {
        this.mState = state;
    }

    /**
     * 初始化Map
     */
    private void initMap(){
        //每行
        for (int i = 0; i < NUMBER_Y; i++) {
            //每个
            for (int j = 0; j < NUMBER_X; j++) {
                mMap[i][j] = 0;
            }
        }
        mMap[0][0] = 1;
    }

    /**
     * 设置回调
     * @param callback
     */
    public void setCallback(Callback callback){
        mCallback = callback;
    }
    public abstract static class Callback{
        public abstract void update(int x, int y, byte[][] map);
    }

}
