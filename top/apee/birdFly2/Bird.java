package top.apee.birdFly2;

import java.awt.image.BufferedImage;

/**
 * 小鸟类
 */
public class Bird {
    /**
     * 配置类
     */
    public class Config {
        /**
         * 小鸟尺寸
         */
        public static final int WIDTH = 51, HEIGHT = 48;
        /**
         * 每个循环单位移动的像素
         */
        public static final int MOVEPX = 3;
        /**
         * 从每次点击屏幕开始，小鸟允许向上移动的循环单位数，超过后小鸟回落
         */
        public static final int TOPALL = 20;
        /**
         * 小鸟掉地时的y值
         */
        public static final int BOTTOMY = 465;
        /**
         * 小鸟初始位置
         */
        public static final int X = 120, Y = 220;
    }

    /**
     * 点击屏幕后，小鸟已经向上移动的距离
     */
    public int topNum = 0;
    /**
     * 小鸟位置
     */
    public int x = Bird.Config.X, y = Bird.Config.Y;
    /**
     * 当前小鸟图片文件下标
     */
    public int index = 0;
    /**
     * 用于计数
     */
    public int number = 0;
    /**
     * 移动方向，上-1，下1
     */
    public int dir = 1;
    /**
     * 小鸟图片
     */
    public BufferedImage image;
    /**
     * 临时图片，该图片用于赋值给image
     */
    public BufferedImage[] imageTemp = new BufferedImage[8];
    GamePane pane;

    public Bird(GamePane pane) {
        this.loadImages();
        this.pane = pane;
    }

    /**
     * 将图片列表加载到imageTemp
     */
    public void loadImages() {
        for (int index = 0; index < 8; index++) {
            BufferedImage image = Tool.loadImage("image/" + index + ".png");
            imageTemp[index] = image;
        }
    }

    /**
     * 显示小鸟
     */
    public void show() {
        this.number++;
        if (this.number % 10 == 0) {
            this.index++;
            if (this.index == 8) {
                this.index = 0;
            }
            this.image = imageTemp[this.index];
        }
    }

    /**
     * 移除小鸟
     */
    public void remove() {
        this.image = null;
    }

    /**
     * 移动小鸟
     */
    public void move() {
        this.y += Bird.Config.MOVEPX * this.dir;
        if (this.dir == -1) {
            this.topNum++;
        }
        // 判断落地
        if (this.y >= Bird.Config.BOTTOMY) {
            this.pane.status = GamePane.Config.OVER;
        }
    }
}
