package com.mjm.Touchwire;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import javax.sound.midi.SysexMessage;

public class Desktop {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Title";
        cfg.useGL20 = true;
        cfg.width = GameManager.ScreenX;
        cfg.height = GameManager.ScreenY;
        new LwjglApplication(new GameManager(new DesktopPlatform()), cfg);
    }

    public static class DesktopPlatform implements GameManager.platformSpecific
    {


        @Override
        public void ExitGame()
        {
            System.exit(0);
        }

        public void setResolutionResolver()
        {
            GameManager.ResolutionResolver = 1;
        }

    }
}