package kr.co.tbell.mm.dto.project;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectSearchCond {
    private String year;
    private String teamName;
    /**
     * ASC or DESC
     * */
    private String orderBy;
}
