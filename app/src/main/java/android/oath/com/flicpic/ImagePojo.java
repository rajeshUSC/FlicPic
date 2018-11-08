package android.oath.com.flicpic;

public class ImagePojo {

    String url;
    int type;

    public ImagePojo(String url) {
        this.url = url;
    }

    public ImagePojo(String url, int type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
