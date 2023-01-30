package com.portfolio.poje.controller.portfolio.portfolioSkillDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PfSkillListResp {

    private String type;

    private List<PfSkillInfoResp> skills;

}
