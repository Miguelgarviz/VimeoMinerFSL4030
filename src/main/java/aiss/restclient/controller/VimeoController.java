package aiss.restclient.controller;

import aiss.restclient.model.*;
import aiss.restclient.model.channel.VimeoChannel;
import aiss.restclient.model.videos.VimeoVideos;
import aiss.restclient.service.VimeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apipath")
public class VimeoController {

    @Autowired
    VimeoService vimeoService;

    // POST http://localhost:8080/apipath/{id}

    @GetMapping("/{id}")
    public VimeoChannel findOne(@PathVariable String id){
        String token = "1a91f47a52a63df97b35f0694c7bf4cb";
        return vimeoService.getChannels(token,id);
    }

    @PostMapping("/{id}")
    public Channel post(@PathVariable String id){
        String token = "1a91f47a52a63df97b35f0694c7bf4cb";
        VimeoChannel channel = vimeoService.getChannels(token,id);
        Channel resChannel =  new Channel(channel.getName(), channel.getDescription(), channel.getCreatedTime());
        VimeoVideos vimeoVideos = vimeoService.getVideos(token,channel.getUri());
        vimeoVideos.getData().forEach(video -> {
            Video vid = new Video(video.getName(),video.getDescription().toString(),video.getCreatedTime());
            video.getCaptions().forEach(caption -> vid.getCaptionList().add(new Caption(caption.getName(),caption.getLanguage())));
            video.getComments().forEach(comment -> {
                User newUser = new User(comment.getUser().getName(),comment.getUser().getLink(),comment.getUser().getPictures().getBaseLink());
                vid.getComments().add(new Comment(comment.getText(),comment.getCreatedOn(),newUser));
            });
            resChannel.getVideoList().add(vid);
        });
        return resChannel;
    }
}
