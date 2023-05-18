package com.gogobuy.houseDA;

import java.awt.*;
import java.awt.event.InputEvent;

public class MouseRobot {

    public static void main(String[] args) {
        try {
            while(true){
                PointerInfo pointerInfo = MouseInfo.getPointerInfo();
                Point point = pointerInfo.getLocation();
                int x = (int) point.getX();
                int y = (int) point.getY();
                Robot robot = new Robot();
                robot.mouseMove(x, y);
                System.out.println("鼠标移动 x:" + x + "y:" + y);
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
