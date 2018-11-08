package android.oath.com.flicpic.parser;

import android.oath.com.flicpic.client.FlickrAPIClient;
import android.oath.com.flicpic.entity.PhotoSourceURLEntity;
import android.oath.com.flicpic.exception.FlickrException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that fetches the JSON Object and passes the URL parameters to generate URL.
 */
public class UrlGenerator {
    /**
     *
     * @param pagination - This is the page number or page offset value
     * @return - returns list of Photo URLs using Flickr Api
     * @throws FlickrException - Throws Custom exception if API fails
     *
     * This method returns a list of PhotoSourceURLEntity objects that consist of the Source URLs
     * for each of the photos.
     *
     */
    public static List<PhotoSourceURLEntity> urlGenerator(int pagination) throws FlickrException {
        List<PhotoSourceURLEntity> photoSourceURLEntities = new ArrayList<>();
        JsonObject response = FlickrAPIClient.getRecentPhotosService(pagination);
        JsonObject photos = response.get("photos").getAsJsonObject();
        for (JsonElement photoUrl :
                photos.getAsJsonArray("photo")) {
            JsonObject photoObject = photoUrl.getAsJsonObject();
            photoSourceURLEntities.add(new PhotoSourceURLEntity(photoObject));
        }
        return photoSourceURLEntities;
    }


}
