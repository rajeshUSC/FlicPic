package android.oath.com.flicpic.parser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.oath.com.flicpic.ImagePojo;
import android.oath.com.flicpic.MainActivity;
import android.oath.com.flicpic.MyAdapter;
import android.oath.com.flicpic.constants.Constants;
import android.oath.com.flicpic.entity.PhotoSourceURLEntity;
import android.oath.com.flicpic.exception.FlickrException;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.oath.com.flicpic.parser.UrlGenerator.urlGenerator;

/**
 * LoadImagesFromFlickrTask extends AsyncTask and retrieves photos
 * in the doInBackGround method. This class enables the isolation of UI thread from
 * the thread that retrieves photos over the network.
 */
public class LoadImagesFromFlickrTask extends AsyncTask {


    public Context mContext;
    public int currentScrollPostion;
    private ProgressDialog dialog;


    public LoadImagesFromFlickrTask(Context context, int position) {
        this.mContext = context;
        this.currentScrollPostion = position;
        dialog = new ProgressDialog(context);
    }

    public LoadImagesFromFlickrTask(Context context) {
        this.mContext = context;
        dialog = new ProgressDialog(context);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (MainActivity.mAdapter == null) {
            MainActivity activity = (MainActivity) mContext;
            dialog.setMessage("Loading Photos...");
            dialog.show();

            final Timer t = new Timer();
            t.schedule(new TimerTask() {
                public void run() {
                    dialog.dismiss();
                    t.cancel();
                }
            }, 2000);
        }

    }

    /**
     *
     * @param objects
     * @return -
     *
     * Fetches photos in the background and creates ImagePojos which are displayed in GridView.
     */
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            List<ImagePojo> tempList = new ArrayList<>();

            Constants.PAGINATION.incrementAndGet();


            List<PhotoSourceURLEntity> imageList = urlGenerator(Constants.PAGINATION.intValue());

            for (PhotoSourceURLEntity en : imageList) {
                tempList.add(new ImagePojo(en.toString(), 1));
            }

            if (Constants.IMAGE_POJO_LIST.size() == 0) {
                Constants.IMAGE_POJO_LIST.addAll(tempList);
                ImagePojo button = new ImagePojo("", 2);
                Constants.IMAGE_POJO_LIST.add(button);
            } else if (Constants.IMAGE_POJO_LIST.size() != 0 && Constants.PAGINATION.intValue() < 10)
                Constants.IMAGE_POJO_LIST.addAll(Constants.IMAGE_POJO_LIST.size() - 1, tempList);

            else {
                Constants.IMAGE_POJO_LIST.addAll(Constants.IMAGE_POJO_LIST.size() - 1, tempList);
                Constants.IMAGE_POJO_LIST.remove(Constants.IMAGE_POJO_LIST.size() - 1);
            }
        } catch (FlickrException e) {
            MainActivity activity = (MainActivity) mContext;
            activity.displayDialog(e.getMessage(), "Close Application?");
        }

        return null;
    }

    /**
     *
     * @param o
     *
     * This method initializes the adapter on the first run and then
     * notifies the dapater for subsequent addition of photos to the list
     * using notifyDataSetChanged() method.
     */
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        MainActivity activity = (MainActivity) mContext;
        if (MainActivity.mAdapter == null) {
            MainActivity.mAdapter = new MyAdapter(Constants.IMAGE_POJO_LIST, mContext);
            activity.getRecyclerView().setAdapter(MainActivity.mAdapter);
            MainActivity.mAdapter.notifyDataSetChanged();

        } else {
            MainActivity.mAdapter = new MyAdapter(Constants.IMAGE_POJO_LIST, mContext);
            activity.getRecyclerView().setAdapter(MainActivity.mAdapter);
            MainActivity.mAdapter.notifyDataSetChanged();
            activity.getRecyclerView().scrollToPosition(currentScrollPostion - 6);
        }

    }


}
