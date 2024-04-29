package aiss.restclient.service;

import aiss.restclient.model.channel.Channel;
import aiss.restclient.model.comments.Comment;
import aiss.restclient.model.videos.Videos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


@Service
public class VimeoService {
    @Autowired
    RestTemplate restTemplate;

    public HttpEntity<Channel> auth (String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return new HttpEntity<>(null, headers);
    }

    public Channel getChannels(String token, String id, String fields) {
        String uri = String.format("https://api.vimeo.com/channels/%s?fields=%s", id, fields);
        ResponseEntity<Channel> response = restTemplate.exchange(uri, HttpMethod.GET, auth(token), Channel.class);
        response.getBody().setUri(response.getBody().getUri().replace("/channels/",""));
        String videoFields = "uri,name,description,created_time,metadata.connections.comments.uri";
        response.getBody().setVideos(getVideos(token,videoFields,response.getBody().getMetadata().getConnections().getVideos().getUri()).getData());
        if(response.getBody().getVideos() != null){
            response.getBody().setVideos(new ArrayList<>());
        }
        return response.getBody();
    }
    public Videos getVideos(String token, String fields, String videosUri) {
        String uri = String.format("https://api.vimeo.com/%s?fields=%s", videosUri, fields);
        ResponseEntity<Videos> response = restTemplate.exchange(uri, HttpMethod.GET, auth(token), Videos.class);
        if (response.getBody() != null && response.getBody().getData() != null) {
            response.getBody().getData().forEach(video -> video.setUri(video.getUri().replace("/videos/", "")));
            return response.getBody();
        }
        else{
            return new Videos();
        }
    }

    public Comment getComments(String token, String commentUri){

    }
}
