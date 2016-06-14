package qyl.com.common.log;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuyunlong on 16/6/13.
 */
public final class LogConfig {

    public Context mContext;

    public int logLevel;  //日志级别

    public boolean needSaveToFile;  //是否保存到文件

    public String dirPath;         //保存文件路径

    public String prefix;          //文件名前缀

    public String suffix;          //文件名后缀

    public String defaultTag;      //默认保存模块

    public List<String> saveToFileTags;   //需要保存到文件的模块


    private LogConfig(Builder builder) {
        mContext = builder.context;
        logLevel = builder.logLevel;
        needSaveToFile = builder.needSaveToFile;
        dirPath = builder.dirPath;
        prefix = builder.prefix;
        suffix = builder.suffix;
        defaultTag = builder.defaultTag;
        saveToFileTags = builder.saveToFileTags;
    }

    public static class Builder {
        private Context context;
        private int logLevel;
        private boolean needSaveToFile;
        private String dirPath;         //保存文件路径
        private String prefix;          //文件名前缀
        private String suffix;          //文件名后缀
        private String defaultTag;      //默认保存模块
        private List<String> saveToFileTags;   //需要保存到文件的模块

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setLogLevel(int logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Builder setNeedSaveToFile(boolean needSaveToFile) {
            this.needSaveToFile = needSaveToFile;
            return this;
        }

        public Builder dirPath(String dirPath){
            this.dirPath = dirPath;
            return this;
        }

        public Builder prefix(String prefix){
            this.prefix = prefix;
            return this;
        }

        public Builder suffix(String suffix){
            this.suffix = suffix;
            return this;
        }

        public Builder defaultTag(String tag){
            this.defaultTag = tag;
            return this;
        }

        public Builder tags(List<String> tags){
            this.saveToFileTags = tags;
            return this;
        }

        public Builder addTag(String tag){
            if (this.saveToFileTags == null){
                this.saveToFileTags = new ArrayList<>();
            }
            this.saveToFileTags.add(tag);
            return this;
        }

        public LogConfig build(){
            initEmptyFieldsWithDefaultValues();
            return new LogConfig(this);
        }

        public void initEmptyFieldsWithDefaultValues() {
            if (logLevel == 0) {
                logLevel = Log.ASSERT;
            }

            if (TextUtils.isEmpty(dirPath)) {
                dirPath = DefaultConfigFactory.createDirPath(context);
            }

            if (TextUtils.isEmpty(prefix)) {
                prefix = DefaultConfigFactory.createPrefix();
            }

            if (TextUtils.isEmpty(suffix)) {
                suffix = DefaultConfigFactory.createSuffix();
            }

            if (TextUtils.isEmpty(defaultTag)) {
                defaultTag = DefaultConfigFactory.createDefaultTag();
            }

            if (saveToFileTags == null){
                saveToFileTags = new ArrayList<>();
            }

        }
    }

    public static class DefaultConfigFactory {

        public static final String DEFAULT_MODEL = "log";

        public static String createDirPath(Context context) {
            PackageManager packageManager = null;
            ApplicationInfo applicationInfo = null;
            try {
                packageManager = context.getApplicationContext().getPackageManager();
                applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                applicationInfo = null;
            }
            String applicationName =
                    (String) packageManager.getApplicationLabel(applicationInfo);
            return applicationName;
        }

        public static String createPrefix() {
            return "-app-";
        }

        public static String createSuffix() {
            return ".log";
        }

        public static String createDefaultTag() {
            return "app";
        }

    }
}
