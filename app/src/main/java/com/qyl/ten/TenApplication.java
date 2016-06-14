package com.qyl.ten;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import qyl.com.common.log.LogConfig;
import qyl.com.common.log.LogUtil;

/**
 * Created by qiuyunlong on 16/4/5.
 */
public class TenApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(getApplicationContext());
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());

        Fresco.initialize(this);

        LogConfig logConfig = new LogConfig.Builder(getApplicationContext())
                .setLogLevel(LogUtil.VERBOSE)
                .dirPath("/ten")
                .setNeedSaveToFile(true)
                .addTag("MainActivity")
                .build();
        LogUtil.init(logConfig);
    }
}
