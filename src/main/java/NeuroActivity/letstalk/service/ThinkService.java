package NeuroActivity.letstalk.service;

import NeuroActivity.letstalk.domain.Think;
import NeuroActivity.letstalk.domain.User;
import NeuroActivity.letstalk.domain.UserSubscription;
import NeuroActivity.letstalk.domain.Views;
import NeuroActivity.letstalk.dto.EventType;
import NeuroActivity.letstalk.dto.MetaDto;
import NeuroActivity.letstalk.dto.ObjectType;
import NeuroActivity.letstalk.dto.ThinkPageDTO;
import NeuroActivity.letstalk.repository.ThinkRepo;
import NeuroActivity.letstalk.repository.UserSubscriptionRepo;
import NeuroActivity.letstalk.util.WebSocketSender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ThinkService {

    private static String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMG_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

    private final ThinkRepo thinkRepo;
    private final UserSubscriptionRepo userSubscriptionRepo;
    private final BiConsumer<EventType, Think> webSocketSender;

    @Autowired
    public ThinkService(ThinkRepo thinkRepo,
                        UserSubscriptionRepo userSubscriptionRepo,
                        WebSocketSender webSocketSender) {
        this.thinkRepo = thinkRepo;
        this.userSubscriptionRepo = userSubscriptionRepo;
        this.webSocketSender = webSocketSender.getSender(ObjectType.THINK, Views.IdText.class);
    }

    private void fillMeta(Think think) throws IOException {
        String text = think.getText();

        Matcher matcher = URL_REGEX.matcher(text);

        if(matcher.find()){ //Find url in text
            String url = text.substring(matcher.start(), matcher.end());

            matcher = IMG_REGEX.matcher(url);

            think.setLink(url);

            if(matcher.find()){ //find url in text
                think.setLinkCover(url);
            }else if (!url.contains("youtu")){
                MetaDto meta = getMeta(url);

                think.setLinkCover(meta.getCover());
                think.setLinkTitle(meta.getTitle());
                think.setLinkDescription(meta.getDescription());
            }
        }
    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Elements title = doc.select("meta[name$=title],meta[property$=title]");
        Elements description = doc.select("meta[name$=description],meta[property$=description]");
        Elements cover = doc.select("meta[name$=image],meta[property$=image]");

        return new MetaDto(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first())
        );

    }

    private String getContent(Element element) {
        return element == null ? "": element.attr("content");
    }

    public void delete(Think think) {
        thinkRepo.delete(think);
        webSocketSender.accept(EventType.REMOVE, think);
    }

    public Think update(Think thinkDatabase, Think thinkUser) throws IOException {
        BeanUtils.copyProperties(thinkUser, thinkDatabase, "id");

        fillMeta(thinkDatabase);

        Think updatedThink = thinkRepo.save(thinkDatabase);
        webSocketSender.accept(EventType.UPDATE, updatedThink);
        return updatedThink;
    }

    public Think create(Think think, User user) throws IOException {
        think.setDate(LocalDateTime.now());
        fillMeta(think);
        think.setAuthor(user);
        Think updatedThink = thinkRepo.save(think);

        webSocketSender.accept(EventType.UPDATE, updatedThink);
        return updatedThink;
    }

    public ThinkPageDTO findForUser(Pageable pageable, User user) {
        List<User> channels = userSubscriptionRepo.findBySubscriber(user)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getChannel)
                .collect(Collectors.toList());

        channels.add(user);

        Page<Think> page = thinkRepo.findByAuthorIn(channels, pageable);
        return new ThinkPageDTO(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }
}
