package top.apee.birdFly2;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;

/**
 * 游戏主体
 */
public class GamePane extends JPanel {
    /**
     * 配置类
     */
    class Config {
        /**
         * 每个循环单位的时间间隔
         */
        public static int loopTime = 15;
    }

    /**
     * 游戏开始
     */
    public static final int START = 0;
    /**
     * 游戏进行中
     */
    public static final int RUNNING = 1;
    /**
     * 游戏结束
     */
    public static final int OVER = 2;
    /**
     * 地面
     */
    public Ground ground;

    /**
     * 游戏状态，开始、进行中、结束
     */
    public int status = 0;

    public GamePane() {
        ground = new Ground();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.loadBackgroundImage(g);
        switch (this.status) {
            case GamePane.START:
                this.ground.show();
                this.ground.move();
                break;
            case GamePane.RUNNING:
                this.ground.show();
                this.ground.move();
                break;
            case GamePane.OVER:
                this.ground.remove();
                break;
        }
        g.drawImage(this.ground.image, this.ground.x, this.ground.y, null);
    }

    /**
     * 绘制背景图片
     * 
     * @param g
     */
    public void loadBackgroundImage(Graphics g) {
        BufferedImage image = Tool.loadImage("image/bg.png");
        g.drawImage(image, 0, 0, null);
    }

    /**
     * 启用游戏
     */
    public void startLoop() {
        while (true) {
            try {
                Thread.sleep(GamePane.Config.loopTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }
}