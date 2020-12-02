package com.pooyeshgaran.nohe.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import androidx.core.app.NotificationCompat;
import com.pooyeshgaran.nohe.R;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class DownloadDataTask extends AsyncTask<List<String>, String, String> {
    public static final String TAG = DownloadDataTask.class.getSimpleName();
    public AsyncDLResponse asyncDLResponse;
    private Context context;
    private String type;
    private int id;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private static final int PROGRESS_MAX = 100;

    public DownloadDataTask(AsyncDLResponse asyncDLResponse, String type, Context context, int id) {
        this.type = type;
        this.context = context;
        this.asyncDLResponse = asyncDLResponse;
        this.id = id;
    }

    public interface AsyncDLResponse {
        void processFinish();
        void processStart();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        asyncDLResponse.processStart();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(context.getString(R.string.download))
                .setContentText(context.getString(R.string.dl_notification))
                .setProgress(PROGRESS_MAX, 0, false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(Notification.PRIORITY_LOW);
        notificationManager.notify(id, builder.build());
    }
    @Override
    protected String doInBackground(List<String>... lists) {
        URL mUrl = null;
        try {
            if (type.equals(Constants.MP3))
                mUrl = new URL(lists[0].get(0));
            else
                mUrl = new URL(lists[0].get(2));
            URLConnection conn = mUrl.openConnection();
            int contentLength = conn.getContentLength();
            InputStream inputStream = new BufferedInputStream(mUrl.openStream(), 8192);

            String mPath = context.getFilesDir() +"/"+ new Util().generateFileName(type, lists[0].get(1),
                    new Util().getUrlExt(mUrl.toString()));
            File file = new File(mPath);
            file.createNewFile();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(file));
            long total = 0;
            int count, latestPercentDone;
            int percentDone = -1;
            byte data[] = new byte[1024];
            while ((count = inputStream.read(data)) != -1) {
                total += count;
                latestPercentDone = (int)((total * 100)/contentLength);
                if (percentDone != latestPercentDone) {
                    percentDone = latestPercentDone;
                    publishProgress(String.valueOf(percentDone));
                }
                fos.write(data, 0, count);
            }
            fos.flush();
            fos.close();
            inputStream.close();
        } catch (Exception e) {
            return context.getString(R.string.dl_failed);
        }
        return context.getString(R.string.dl_success);
    }
    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        asyncDLResponse.processFinish();
        String title;
        if (string.equals(context.getString(R.string.dl_success))) {
            title = context.getString(R.string.download_success);
        } else {
            title = context.getString(R.string.download_failed);
        }
        builder.setContentText(title)
                .setOngoing(false)
                .setProgress(0, 0, false);
        notificationManager.notify(id, builder.build());
    }
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        builder.setContentInfo(values[0] + "%")
                .setProgress(PROGRESS_MAX, Integer.parseInt(values[0]), false);
        notificationManager.notify(id, builder.build());
    }
}
