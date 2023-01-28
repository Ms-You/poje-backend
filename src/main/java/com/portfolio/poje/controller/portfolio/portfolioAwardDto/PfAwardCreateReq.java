package com.portfolio.poje.controller.portfolio.portfolioAwardDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PfAwardCreateReq {

    private Long portfolioId;

    private String supervision;

    private String grade;

    private String description;


    @Builder
    private PfAwardCreateReq(String supervision, String grade, String description){
        this.supervision = supervision;
        this.grade = grade;
        this.description = description;
    }

}
