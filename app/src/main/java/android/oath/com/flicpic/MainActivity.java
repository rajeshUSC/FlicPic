package android.oath.com.flicpic;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.oath.com.flicpic.constants.Constants;
import android.oath.com.flicpic.parser.LoadImagesFromFlickrTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * MainActivity is the entry point to the application. This is the only activity in this app
 * and it spawns two threads: (1) UI Thread (2) AsyncTask Thread.
 * The AsyncTask thread retrieves the photos from the Flickr API.
 */
public class MainActivity extends AppCompatActivity {
    public static RecyclerView.Adapter mAdapter = null;
    static public RecyclerView.LayoutManager layoutManager;
    static LoadImagesFromFlickrTask loadImagesFromFlickrTask;
    private RecyclerView recyclerView;

    /**
     *
     * @param savedInstanceState
     * OnCreate Method spwans two threads one for Ui and the other for fetching photos
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //initialize UI componenets
        initializeComponents();

        //Checks if device is connected to internet and calls the asyncTask to fetch photos.
        if (checkConnectivity()) {
            loadImagesFromFlickrTask = new LoadImagesFromFlickrTask(this);
            loadImagesFromFlickrTask.execute();
        } else {
            Log.i("TAG", "NO INTERNET CONNECTIVITY");
            displayDialog(getString(R.string.connectivity_error), getString(R.string.connectivity_error_msg));
        }


    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * Method that initializes UI Components in the UI thread.
     * Number of columns in the grid view are set based on
     * device width.
     */

    public void initializeComponents() {
        recyclerView = (RecyclerView) findViewById(R.id.FlickerView);
        recyclerView.setHasFixedSize(true);
        float scalefactor = getResources().getDisplayMetrics().density * 100;
        int number = getWindowManager().getDefaultDisplay().getWidth();
        final int columns = (int) ((float) number / (float) scalefactor);
        layoutManager = new GridLayoutManager(this, columns);

        ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return Constants.IMAGE_POJO_LIST.get(position).getType() == 1 ? 1 : columns;
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    /**
     * @param title   - The title of the Dialog/Alert
     * @param message - The message of the Dialog/Alert
     *
     * A generic method to display error or exception alerts.
     * The dialogs generated contain information about the error
     * that has occurred and gives user the option of quitting the app
     *  or continuing.
     */

    public void displayDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * @return - Returns true if connected to internet, flase otherwise
     * <p>
     * Method to check if the device the app is running on has
     * an active connection to the internet either through WiFi
     * or Mobile Network
     */

    public boolean checkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }
}
