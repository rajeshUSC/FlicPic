package android.oath.com.flicpic;


import android.content.Context;
import android.oath.com.flicpic.parser.LoadImagesFromFlickrTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for the recyclerView we're using. Picasso is the libraray we're using to
 * get pictures from URL's.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final Integer TYPE_ONE = 1;
    private static final Integer TYPE_TWO = 2;
    private Context aContext;

    private List<ImagePojo> imageList;

    public MyAdapter(List<ImagePojo> imageList, Context aContext) {
        this.imageList = imageList;
        this.aContext = aContext;
    }

    public List<ImagePojo> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImagePojo> imageList) {
        this.imageList = imageList;
    }

    @Override
    public int getItemViewType(int position) {
        return imageList.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ONE) {
            View v = inflater.inflate(R.layout.thumbnailview, parent, false);
            RecyclerView.ViewHolder viewHolder = new ViewHolder1(v);
            return viewHolder;
        } else {
            View v = inflater.inflate(R.layout.loadmorebutton, parent, false);
            RecyclerView.ViewHolder viewHolder = new ViewHolder2(v);
            return viewHolder;
        }
    }

    /**
     *
     * @param holder
     * @param position
     * This method is used to fill the holders with images loaded from picasso library.
     * It also has the onclick listener for the load More button which spawns threads to fetch more photos in the background.
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int type = imageList.get(position).getType();
        if (type == 1)
        {
            final String imageUrl = imageList.get(position).getUrl();
            Picasso.get().load(imageUrl).into(((ViewHolder1) holder).flickrImage);
        } else {
            ViewHolder2 holder2 = (ViewHolder2) holder;
            holder2.loadMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity mainActivity = (MainActivity) aContext;
                    if (mainActivity.checkConnectivity()) {
                        int currentPos = holder.getAdapterPosition();
                        new LoadImagesFromFlickrTask(aContext, currentPos).execute();

                    } else {
                        mainActivity.displayDialog("No Internet Connection", "Close Application?");
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        public ImageView flickrImage;

        public ViewHolder1(View v) {
            super(v);
            flickrImage = (ImageView) v.findViewById(R.id.flickrSingleImage);
        }

    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        public Button loadMoreButton;

        public ViewHolder2(View v) {
            super(v);
            loadMoreButton = v.findViewById(R.id.loadButton);
        }

    }
}
