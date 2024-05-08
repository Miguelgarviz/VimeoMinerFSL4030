package aiss.restclient.controller;

import aiss.restclient.model.VideoMiner.Channel;
import aiss.restclient.model.VideoMiner.Comment;
import aiss.restclient.model.VideoMiner.User;
import aiss.restclient.model.channel.VimeoChannel;
import aiss.restclient.service.VimeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


@Controller
@RequestMapping("/apipath")
public class VimeoController {

    @Autowired
    VimeoService vimeoService;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/channels/{id}")
    public Channel post(@PathVariable String id) {
        Channel VideoChannel = new Channel();
        String token = "1a91f47a52a63df97b35f0694c7bf4cb";
        VimeoChannel channel = vimeoService.getChannels(token, id);
        Channel newChannel = new Channel();
        newChannel.setId(channel.getUri().replace("/channels/", ""));
        newChannel.setName(channel.getName());
        newChannel.setDescription(channel.getDescription());
        newChannel.setCreatedTime(channel.getCreatedTime());
        if (channel.getVideos() != null && !channel.getVideos().isEmpty()) {
            channel.getVideos().forEach(video -> {
                aiss.restclient.model.VideoMiner.Video vid = new aiss.restclient.model.VideoMiner.Video();
                vid.setId(video.getUri().replace("/videos/", ""));
                vid.setName(video.getName());
                if (video.getDescription() != null) {
                    vid.setDescription(video.getDescription().toString());
                }
                vid.setReleaseTime(video.getCreatedTime());
                vid.setCaptions(new ArrayList<>());
                if (video.getCaptions() != null && !video.getCaptions().isEmpty()) {
                    video.getCaptions().forEach(caption -> {
                        aiss.restclient.model.VideoMiner.Caption cap = new aiss.restclient.model.VideoMiner.Caption();
                        cap.setId(caption.getUri().replace("/captions/", ""));
                        cap.setLanguage(caption.getLanguage());
                        cap.setName(caption.getName());
                        vid.getCaptions().add(cap);
                    });
                }
                vid.setComments(new ArrayList<>());
                if(video.getComments() != null && !video.getComments().isEmpty()) {
                    video.getComments().forEach(comment -> {
                        Comment com = new Comment();
                        User user = new User();
                        com.setId(comment.getUri().replace("/comments/", ""));
                        com.setText(comment.getText());
                        user.setName(comment.getUser().getName());
                        user.setUser_link(comment.getUser().getLink());
                        user.setPicture_link(comment.getUser().getPictures().getBaseLink());
                        com.setAuthor(user);
                        vid.getComments().add(com);
                    });
                }
                newChannel.getVideos().add(vid);
            });
            VideoChannel = restTemplate.postForObject("http://localhost:8080/videominer/channels", newChannel, Channel.class);
        }
        return VideoChannel;
    }
}
