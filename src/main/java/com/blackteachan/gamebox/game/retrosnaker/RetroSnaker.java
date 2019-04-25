package com.blackteachan.gamebox.game.retrosnaker;

import com.blackteachan.gamebox.entity.BlockEntity;
import com.blackteachan.gamebox.utils.LogUtil;

/**
 * 贪吃蛇
 * <br/>运行在一个线程里，间隔UPDATE_TIME更新一次
 * <br/>地图数据存储在mMap里
 * @author blackteachan
 */
public class RetroSnaker extends Thread{

    /**
     * 更新间隔时间
     */
    public static final int UPDATE_TIME = 1000;
    /**
     * 方向-左
     */
    public static final int ORIENTATION_LEFT = 0;
    /**
     * 方向-上
     */
    public static final int ORIENTATION_UP = 1;
    /**
     * 方向-右
     */
    public static final int ORIENTATION_RIGHT = 2;
    /**
     * 方向-下
     */
    public static final int ORIENTATION_DOWN = 3;
    /**
     * X轴块数
     */
    private static final int NUMBER_X = 10;
    /**
     * Y轴块数
     */
    private static final int NUMBER_Y = 20;

    public static final byte STATE_STOP = 0;
    public static final byte STATE_RUN = 1;
    public static final byte STATE_PAUSE = 2;

    public static final byte BLOCK_BLANK = 0;
    public static final byte BLOCK_ENTITY = 1;
    public static final byte BLOCK_REWARD = 2;

    /**
     * 地图
     * <br/>0：空白区域
     * <br/>1：蛇身体部分
     * <br/>2：奖励
     */
    private static byte[][] mMap = new byte[NUMBER_Y][NUMBER_X];
    /**
     * 蛇的全身坐标<br/>从头到尾排列
     */
    private static BlockEntity[] mSnake = new BlockEntity[NUMBER_X*NUMBER_Y];
    /**
     * 蛇头X轴坐标
     */
    private static int mX = 0;
    /**
     * 蛇头Y轴坐标
     */
    private static int mY = 0;
    /**
     * 蛇的长度
     */
    private static int mSize = 0;
    /**
     * 奖励数
     */
    private static int mReward = 0;
    /**
     * 运行状态
     */
    private static byte mState = STATE_STOP;
    private static Callback mCallback = null;

    LogUtil log = new LogUtil(RetroSnaker.class);

    public RetroSnaker(){
        initMap();
    }

    @Override
    public void start() {
        mState = STATE_RUN;
        while(true){
            if(mState == STATE_RUN){
                //运行中
                try {
                    Thread.sleep(UPDATE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                update();
            }else if(mState == STATE_STOP){
                //已停止
                break;
            }
        }
    }

    /**
     * 移动
     * @param orientation 方向
     */
    public void move(int orientation){
        if(mState == STATE_STOP){
            return;
        }
        if(orientation == ORIENTATION_LEFT){
            //防止穿墙
            if(mX != 0){
                mMap[mY][mX] = eat(mMap[mY][mX-1]);
                mMap[mY][--mX] = 1;
            }else{
                die();
            }
        }else if(orientation == ORIENTATION_UP){
            if(mY != 0){
                mMap[mY][mX] = eat(mMap[mY-1][mX]);
                mMap[--mY][mX] = 1;
            }else{
                die();
            }
        }else if(orientation == ORIENTATION_RIGHT){
            if(mX != NUMBER_X-1){
                mMap[mY][mX] = eat(mMap[mY][mX+1]);
                mMap[mY][++mX] = 1;
            }else{
                die();
            }
        }else if(orientation == ORIENTATION_DOWN){
            if(mY != NUMBER_Y-1){
                mMap[mY][mX] = eat(mMap[mY+1][mX]);
                mMap[++mY][mX] = 1;
            }else{
                die();
            }
        }
        update();
    }

    /**
     * 吃
     * @param nextBlock
     * @return
     */
    private byte eat(byte nextBlock){
        byte thisBlock = BLOCK_BLANK;
        if(nextBlock == BLOCK_REWARD){
            thisBlock = BLOCK_ENTITY;
        }
        return thisBlock;
    }

    /**
     * 死亡
     */
    private void die(){
        mCallback.die();
        mState = STATE_STOP;
    }

    /**
     * 更新Map
     */
    private void update(){
        if(mCallback != null && mState == STATE_RUN) {
            mCallback.update(NUMBER_X, NUMBER_Y, mMap);
        }
    }

    /**
     * 设置奖励
     */
    private void setReward(){
        long time = System.currentTimeMillis();
        if(mReward == 0 && mSize < NUMBER_X*NUMBER_Y) {
            int x = (int)(Math.random() * NUMBER_X);
            int y = (int)(Math.random() * NUMBER_Y);
            if (mMap[y][x] == 0){
                mMap[y][x] = 2;
            }else {
                setReward();
            }
            mReward++;
        }
        log.d("RetroSnaker.setReward() - time: " + (System.currentTimeMillis() - time) + "ms");
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
        mSize = 1;
        setReward();
    }

    /**
     * 蛇行走
     * @param x X轴坐标
     * @param y Y轴坐标
     */
    private void remove(int x, int y){
        if(mSize > 1){

        }
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
        public abstract void die();
    }

}
