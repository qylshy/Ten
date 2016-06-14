package qyl.com.common.log;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by qiuyunlong on 16/6/13.
 */
public class LogFileWriter {

    public static final String prefix = ".app";
    public static final String suffix = ".log";

    public  String tag = "";
    private OutputStream output;
    private File logFile;
    private String filename;

    public LogFileWriter() {
        open(this.filename);
    }

    public LogFileWriter(String tag){
        this.tag = tag;
        open(this.filename);
    }

    private boolean open(String sFileName) {
        try {
            String path = null;
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                return false;
            } else {
                path = Environment.getExternalStorageDirectory().toString() +"/ten/log/";
            }

            File logPath = new File(path);
            if (!logPath.exists()) {
                logPath.mkdirs();
            }
            if (sFileName == null || sFileName.length() == 0) {
                String sDateStr = getTimeStr("yyyy-MM-dd");
                if (TextUtils.isEmpty(tag)) {
                    return false;
                } else {
                    sFileName = path + sDateStr + prefix + tag + suffix;
                }
            } else {
                sFileName = path + sFileName + ".log";
            }

            logFile = new File(sFileName);
            if (!logFile.exists())
                logFile.createNewFile();

            output = new FileOutputStream(logFile, true);

        } catch (IOException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return output != null;
    }

    public void close() {
        try {
            if (null != output) {
                output.close();
                output = null;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public synchronized void writeLog(String str) {
        try {
            if (output == null) {
                if (!open(this.filename)) {
                    return;
                }
            }
            if (null != output) {
                String content = "[" + getTimeStr("yyyy-MM-dd HH:mm:ss.SSS") + "] " + str + "\r\n";
                output.write(content.getBytes("UTF-8"));
                output.flush();
            }
            close();
        } catch (UnsupportedEncodingException ee) {
        } catch (IOException ioe) {
        }
    }

    private String getTimeStr(String datetype) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(datetype);
        Calendar calendarLocal = Calendar.getInstance();
        return dateFormat.format(calendarLocal.getTime());
    }

    public String getApplicationName(Context context) {
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

}
