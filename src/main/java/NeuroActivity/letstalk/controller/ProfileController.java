package NeuroActivity.letstalk.controller;

import NeuroActivity.letstalk.domain.User;
import NeuroActivity.letstalk.domain.UserSubscription;
import NeuroActivity.letstalk.domain.Views;
import NeuroActivity.letstalk.service.ProfileService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User get(@PathVariable("id") User user){
        return user;
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public User changeSubscription(
            @AuthenticationPrincipal User subscriber,
            @PathVariable("channelId") User channel
    ){
        if(subscriber.equals(channel)){
            return channel;
        }else{
            return profileService.changeSubscription(channel, subscriber);
        }
    }

    @GetMapping("get-subscribers/{channelId}")
    @JsonView(Views.IdText.class)
    public List<UserSubscription> subscribers(
            @PathVariable("channelId") User channel
    ){
        return profileService.getSubscribers(channel);
    }

    @PostMapping("change-status/{subscriberId}")
    @JsonView(Views.IdText.class)
    public UserSubscription changeSubscriptionsStatus(
            @AuthenticationPrincipal User channel,
            @PathVariable("subscriberId") User subscriber
    ){
        return profileService.changeSubscriptionsStatus(channel, subscriber);
    }

}