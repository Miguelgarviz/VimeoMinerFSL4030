package aiss.restclient.service;

import aiss.restclient.model.channel.Channel;
import aiss.restclient.model.videos.Videos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VimeoServiceTest {

    @Autowired
    VimeoService service;

    @Test
    @DisplayName("Get channels")
    void getChannels() {
        String token = "1a91f47a52a63df97b35f0694c7bf4cb";
        String id = "1902576";
        String fields = "uri,name,description,created_time,metadata.connections.videos.uri";
        Channel channels = service.getChannels(token, id, fields);
        assertNotNull(channels, "The channels are null");
        System.out.println(channels);
    }

    @Test
    @DisplayName("Get videos")
    void getVideos() {
        String token = "1a91f47a52a63df97b35f0694c7bf4cb";
        String videosUri = "";
        String videoFields = "uri,name,description,created_time,metadata.connections.comments.uri";
        Videos videos = service.getVideos(token, videoFields, videosUri);
        assertNotNull(videos, "The videos are null");
        System.out.println(videos);
    }
}