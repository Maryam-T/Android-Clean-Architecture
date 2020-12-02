package com.pooyesh.pgag.util;


import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class DownloadMediaTask extends AsyncTask<List<String>, String, Void> {
    public static final String TAG = DownloadMediaTask.class.getSimpleName();
    public AsyncDLResponse asyncDLResponse;

    public DownloadMediaTask(AsyncDLResponse asyncDLResponse) {
        this.asyncDLResponse = asyncDLResponse;
    }

    public interface AsyncDLResponse {
        void processFinish();
        void processStart();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        asyncDLResponse.processStart();
    }
    @Override
    protected Void doInBackground(List<String>... lists) {
        int count;
        URL mUrl = null;
        try {
            mUrl = new URL(lists[0].get(0));
            URLConnection conn = mUrl.openConnection();
            int contentLength = conn.getContentLength();
            InputStream inputStream = new BufferedInputStream(mUrl.openStream(), 8192);

            String path = lists[0].get(2) +"/"+ lists[0].get(1) + Utils.getUrlExt(mUrl.toString());
            File file = new File(path);
            file.createNewFile();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(file));
            long total = 0;
            byte data[] = new byte[1024];
            while ((count = inputStream.read(data)) != -1) {
                if (isCancelled())
                    break;
                total += count;
                publishProgress(String.valueOf((int)((total * 100)/contentLength)));
                fos.write(data, 0, count);
            }
            fos.flush();
            fos.close();
            inputStream.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage()+"");
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        asyncDLResponse.processFinish();
    }

}
