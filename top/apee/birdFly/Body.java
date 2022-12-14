package top.apee.birdFly;

import java.awt.Graphics;
import javax.swing.*;
import java.awt.image.*;
import java.awt.Font;

/**
 * 游戏界面主体
 * 
 * @author 欧阳鹏
 * @version 1.0
 */
public class Body extends JPanel {
    /**
     * 地板
     */
    public Ground ground;
    /**
     * 小鸟
     */
    public Bird bird;
    /**
     * 柱子
     */
    public Column column1, column2, column3;
    /**
     * 游戏状态
     */
    public int status;
    /**
     * 准备开始
     */
    public final int START = 0;
    /**
     * 游戏进行中
     */
    public final int RUNNING = 1;
    /**
     * 游戏结束
     */
    public final int OVER = 2;

    /**
     * 得分
     */
    public int score;

    public Body() {
        // 创建地板
        this.ground = new Ground();
        // 创建小鸟
        this.bird = new Bird();
        // 创建3根柱子
        this.column1 = new Column(this);
        this.column2 = new Column(this);
        this.column3 = new Column(this);
        // 增加鼠标事件, 用于鼠标操作游戏
        this.addMouseListener(new BodyClick(this));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 载入背景图片
        g.drawImage(Main.loadImage("image/bg.png"), 0, 0, null);
        // 载入开始准备页面图片
        BufferedImage startImage = Main.loadImage("image/start.png");
        // 载入游戏结束页面图片
        BufferedImage overImage = Main.loadImage("image/gameover.png");
        // 游戏得分文本
        String scoreText = "得分: " + this.score;
        // 判断游戏状态
        switch (this.status) {
            case START:
                // 游戏准备页面, 地板移动
                this.ground.move();
                // 修改小鸟形态
                this.bird.changeIndex(this);
                // 游戏结束页面隐藏
                overImage = null;
                // 小鸟y轴位置还原
                this.bird.y = 220;
                // 运动方向还原为向下
                this.bird.dir = 1;
                // 得分清零
                this.score = 0;
                scoreText = "";
                // 隐藏柱子
                this.column1.hideImage();
                this.column2.hideImage();
                this.column3.hideImage();
                // 初始化柱子位置
                this.column1.x = 300;
                this.column2.x = 550;
                this.column3.x = 800;
                break;
            case RUNNING:
                // 游戏进行中, 地板移动
                this.ground.move();
                // 开始准备页面隐藏
                startImage = null;
                // 修改小鸟形态
                this.bird.changeIndex(this);
                // 小鸟摆动翅膀
                this.bird.move(this);
                // 游戏结束页面隐藏
                overImage = null;
                // 如果小鸟正在向上, 并且从点击屏幕以来, 向上已经移动了20个单位, 此时开始回落
                if (this.bird.dir == -1 && this.bird.topNum++ == this.bird.topAll) {
                    // 方向变为向下
                    this.bird.dir = 1;
                    // 已经上移单位数归0
                    this.bird.topNum = 0;
                }
                // 显示柱子
                this.column1.showImage();
                this.column2.showImage();
                this.column3.showImage();
                // 柱子开始移动
                this.column1.move(this.bird);
                this.column2.move(this.bird);
                this.column3.move(this.bird);
                break;
            case OVER:
                // 开始准备页面隐藏
                startImage = null;
                // 小鸟隐藏
                this.bird.image = null;
                // 游戏结束, 隐藏柱子
                // this.column1.hideImage();
                // this.column2.hideImage();
                // this.column3.hideImage();
                break;
        }
        // 绘制柱子1
        g.drawImage(this.column1.image, this.column1.x, this.column1.y, null);
        // 绘制柱子2
        g.drawImage(this.column2.image, this.column2.x, this.column2.y, null);
        // 绘制柱子3
        g.drawImage(this.column3.image, this.column3.x, this.column3.y, null);
        // 绘制开始准备页面
        g.drawImage(startImage, 0, 0, null);
        // 绘制游戏结束页面
        g.drawImage(overImage, 0, 0, null);
        // 绘制小鸟
        g.drawImage(this.bird.image, this.bird.x, this.bird.y, null);
        // 载入地板图片
        g.drawImage(this.ground.image, this.ground.x, this.ground.y, null);
        // 设置字体
        g.setFont(new Font("微软雅黑", Font.BOLD, 30));
        // 绘制分数文本
        g.drawString(scoreText, 10, 40);
    }

    /**
     * 开始游戏
     */
    public void start() {
        while (true) {
            // 重新绘制页面
            repaint();
            // 游戏整体速度, 暂停15毫秒, 该值越小, 难度越高
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}