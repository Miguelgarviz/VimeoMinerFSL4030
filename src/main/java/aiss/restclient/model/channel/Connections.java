
package aiss.restclient.model.channel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Connections {

    @JsonProperty("videos")
    private VideosUri videos;

    @JsonProperty("videos")
    public VideosUri getVideos() {
        return videos;
    }

    @JsonProperty("videos")
    public void setVideos(VideosUri videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(((this.videos == null)?"<null>":this.videos));
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
