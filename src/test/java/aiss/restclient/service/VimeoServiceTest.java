package aiss.restclient.service;

import aiss.restclient.model.channel.VimeoChannel;
import aiss.restclient.model.videos.VimeoVideos;
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
        String id = "988576";
        VimeoChannel channels = service.getChannels(token, id);
        assertNotNull(channels, "The channels are null");
        System.out.println(channels);
    }

    @Test
    @DisplayName("Get videos")
    void getVideos() {
        String token = "1a91f47a52a63df97b35f0694c7bf4cb";
        String videosUri = "https://api.vimeo.com/videos/136262971";
        VimeoVideos videos = service.getVideos(token, videosUri);
        assertNotNull(videos, "The videos are null");
        System.out.println(videos);
    }
}