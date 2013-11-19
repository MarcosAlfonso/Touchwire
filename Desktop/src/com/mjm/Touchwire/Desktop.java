package com.mjm.Touchwire;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Desktop {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Title";
        cfg.useGL20 = true;
        cfg.width = GameManager.ScreenX;
        cfg.height = GameManager.ScreenY;
        new LwjglApplication(new GameManager(), cfg);
    }
}