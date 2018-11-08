package android.oath.com.flicpic.client;


/**
 * Flickr Client
 *
 * @author rajesh
 * @since 11/05/2018
 */

import android.oath.com.flicpic.exception.FlickrException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

/**
 * Client class for calling Flickr APIs
 */
public class FlickrAPIClient {
    private static final FlickrAPIClient flickrAPIClient = new FlickrAPIClient();
    private static final String FLICKR_URL = "https://api.flickr.com/";
    private static final String FLICKR_METHOD = "flickr.photos.getRecent";
    private static final String FLICKR_API_KEY = "7b85e389607020e3b5a12c5a40e260db";
    private FlickrAPIInterface flickrAPIInterface;

    /**
     * Constructor for {@link FlickrAPIClient}
     */
    private FlickrAPIClient() {
        try {
            this.flickrAPIInterface = new Retrofit.Builder().baseUrl(FLICKR_URL).build().create(FlickrAPIInterface.class);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Public method for making api calls
     *
     * @param pagination pagination
     * @return JsonObject result
     * @throws FlickrException - Custom Exception thrown if the API fails.
     */

    public static JsonObject getRecentPhotosService(int pagination) throws FlickrException {
        JsonObject errorBody = new JsonObject();
        try {
            Call<ResponseBody> responseBodyCall = flickrAPIClient.getRecentPhotos(Integer.toString(pagination));
            Response<ResponseBody> response = responseBodyCall.execute();
            if (response.isSuccessful()) {
                return new JsonParser().parse(new String(response.body().bytes())).getAsJsonObject();
            } else {
                errorBody = new JsonParser().parse(new String(response.errorBody().bytes())).getAsJsonObject();
            }
        } catch (IOException | UnsupportedOperationException e) {
            e.printStackTrace();
        }
        throw new FlickrException(errorBody.getAsString());
    }

    /**
     * public method to get recent photos response
     *
     * @return response type
     */
    public Call<ResponseBody> getRecentPhotos(String pagination) {
        return this.flickrAPIInterface.getRecentPhotos(FLICKR_METHOD, FLICKR_API_KEY, pagination, "json", "1");
    }

    /**
     * private interface for Flickr APIs
     */
    private interface FlickrAPIInterface {

        @HTTP(method = "GET", path = "/services/rest/")
        Call<ResponseBody> getRecentPhotos(@Query("method") String method, @Query("api_key") String apiKey, @Query("page") String page, @Query("format") String format, @Query("nojsoncallback") String noJsonCallback);
    }
}
