package NeuroActivity.letstalk.controller;


import NeuroActivity.letstalk.domain.Think;
import NeuroActivity.letstalk.domain.Views;
import NeuroActivity.letstalk.dto.EventType;
import NeuroActivity.letstalk.dto.MetaDto;
import NeuroActivity.letstalk.dto.ObjectType;
import NeuroActivity.letstalk.repository.ThinkRepo;
import NeuroActivity.letstalk.util.WebSocketSender;
import com.fasterxml.jackson.annotation.JsonView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/thinks")
public class ThinkController {

    private static String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMG_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

    private final ThinkRepo thinkRepo;
    private final BiConsumer<EventType, Think> webSocketSender;

    @Autowired
    public ThinkController(ThinkRepo thinkRepo, WebSocketSender webSocketSender) {
        this.thinkRepo = thinkRepo;
        this.webSocketSender = webSocketSender.getSender(ObjectType.THINK, Views.IdText.class);
    }

    @GetMapping
    @JsonView(Views.IdText.class)
    public List<Think> getListOfThinks(){
        return thinkRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullPost.class)
    public Think getOneThink(@PathVariable("id") Think think){
        return think;
    }

    @PostMapping
    public Think createThink(@RequestBody Think think) throws IOException {
        think.setDate(LocalDateTime.now());
        fillMeta(think);
        Think updatedThink = thinkRepo.save(think);

        webSocketSender.accept(EventType.UPDATE, updatedThink);
        return updatedThink;
    }

    @PutMapping("{id}")
    public Think changeThink(@PathVariable("id") Think thinkDatabase, @RequestBody Think thinkUser) throws IOException {
        BeanUtils.copyProperties(thinkUser, thinkDatabase, "id");

        fillMeta(thinkDatabase);

        Think updatedThink = thinkRepo.save(thinkDatabase);
        webSocketSender.accept(EventType.UPDATE, updatedThink);
        return updatedThink;
    }

    @DeleteMapping("{id}")
    public void deleteThink(@PathVariable("id") Think think){
        thinkRepo.delete(think);
        webSocketSender.accept(EventType.REMOVE, think);
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
}
