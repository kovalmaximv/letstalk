package NeuroActivity.letstalk.repository;

import NeuroActivity.letstalk.domain.User;
import NeuroActivity.letstalk.domain.UserSubscription;
import NeuroActivity.letstalk.domain.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubscriptionRepo extends JpaRepository<UserSubscription, UserSubscriptionId> {
    List<UserSubscription> findBySubscriber(User user);

    List<UserSubscription> findByChannel(User channel);

    UserSubscription findByChannelAndSubscriber(User channel, User subscriber);
}
