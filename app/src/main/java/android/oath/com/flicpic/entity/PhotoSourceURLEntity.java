package android.oath.com.flicpic.entity;

import com.google.gson.JsonObject;

/**
 * This class builds the URL for each photo by combining various
 * parameters.
 */
public class PhotoSourceURLEntity {

    private String farmId;
    private String serverId;
    private String secretId;
    private String photoId;
    private String size;

    private PhotoSourceURLEntity() {

    }

    /**
     * @param farmId
     * @param serverId
     * @param secretId
     * @param photoId
     * @param size
     */
    public PhotoSourceURLEntity(String farmId, String serverId, String secretId, String photoId, String size) {
        this.farmId = farmId;
        this.serverId = serverId;
        this.secretId = secretId;
        this.photoId = photoId;
        this.size = size;
    }

    /**
     * @param jsonObject
     */
    public PhotoSourceURLEntity(JsonObject jsonObject) {
        this.farmId = jsonObject.get("farm").getAsString();
        this.serverId = jsonObject.get("server").getAsString();
        this.secretId = jsonObject.get("secret").getAsString();
        this.photoId = jsonObject.get("id").getAsString();
        this.size = "t";
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        serverId = serverId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "https://farm" + farmId + ".staticflickr.com/" + serverId + "/" + photoId + "_" + secretId + "_" + size + ".jpg";
    }
}
