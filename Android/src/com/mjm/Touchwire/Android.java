package com.mjm.Touchwire;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class Android extends AndroidApplication
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        //cfg.useWakelock = true;
        cfg.useGL20 = true;
        initialize(new GameManager(new AndroidPlatform()), cfg);
    }

    public class AndroidPlatform implements GameManager.platformSpecific
    {
        public void ExitGame()
        {
            moveTaskToBack(true);
        }

        public void setResolutionResolver()
        {
            GameManager.ResolutionResolver = 2;
        }

    }
}