package com.portfolio.poje.controller.portfolio.portfolioDto;

import com.portfolio.poje.controller.ability.licenseDto.LicenseInfoResp;
import com.portfolio.poje.controller.member.memberDto.MemberInfoResp;
import com.portfolio.poje.controller.portfolio.portfolioAwardDto.PfAwardInfoResp;
import com.portfolio.poje.controller.portfolio.portfolioSkillDto.PfSkillListResp;
import com.portfolio.poje.controller.project.projectDto.PrAllInfoResp;
import com.portfolio.poje.domain.portfolio.Portfolio;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PfAllInfoResp {

    // Portfolio
    private PfInfoResp pfInfo;

    private List<PfAwardInfoResp> pfAwardList;

    private List<PfSkillListResp> pfSkillList;

    // Member
    private MemberInfoResp memberInfo;

    // License
    private List<LicenseInfoResp> licenseList;

    // Project
    private List<PrAllInfoResp> prList;


    @Builder
    private PfAllInfoResp(Portfolio portfolio){
        this.pfInfo = toPortfolioInfo(portfolio);
        this.pfAwardList = toPortfolioAwardDto(portfolio);
        this.pfSkillList = toPortfolioSkillDto(portfolio);
        this.memberInfo = toMemberInfoDto(portfolio);
        this.licenseList = toLicenseDto(portfolio);
        this.prList = toProjectDto(portfolio);
    }

    private PfInfoResp toPortfolioInfo(Portfolio portfolio){
        return PfInfoResp.builder()
                .portfolio(portfolio)
                .build();
    }

    private List<PfAwardInfoResp> toPortfolioAwardDto(Portfolio portfolio){
        return portfolio.getPortfolioAwards().stream()
                .map(award -> PfAwardInfoResp.builder()
                        .supervision(award.getSupervision())
                        .grade(award.getGrade())
                        .description(award.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    private List<PfSkillListResp> toPortfolioSkillDto(Portfolio portfolio){
        return portfolio.getPortfolioSkills().stream()
                .map(skill -> PfSkillListResp.builder()
                        .skill(skill)
                        .build())
                .collect(Collectors.toList());
    }

    private MemberInfoResp toMemberInfoDto(Portfolio portfolio){
        return MemberInfoResp.builder()
                .member(portfolio.getWriter())
                .build();
    }

    private List<LicenseInfoResp> toLicenseDto(Portfolio portfolio){
        return portfolio.getWriter().getLicenseList().stream()
                .map(license -> new LicenseInfoResp(license.getName()))
                .collect(Collectors.toList());
    }

    private List<PrAllInfoResp> toProjectDto(Portfolio portfolio){
        return portfolio.getProjects().stream()
                .map(project -> PrAllInfoResp.builder()
                        .project(project)
                        .build())
                .collect(Collectors.toList());
    }

}
