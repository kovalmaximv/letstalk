package NeuroActivity.letstalk.dto;

import NeuroActivity.letstalk.domain.Think;
import NeuroActivity.letstalk.domain.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonView(Views.FullPost.class)
public class ThinkPageDTO {
    private List<Think> thinks;
    private int currentPage;
    private int totalPage;

}
