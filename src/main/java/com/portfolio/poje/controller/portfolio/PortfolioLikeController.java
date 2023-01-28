package com.portfolio.poje.controller.portfolio;

import com.portfolio.poje.common.BasicResponse;
import com.portfolio.poje.controller.portfolio.portfolioLikeDto.PfLikeInfoResp;
import com.portfolio.poje.service.portfolio.PortfolioLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class PortfolioLikeController {

    private final PortfolioLikeService portfolioLikeService;


    /**
     * 포트폴리오 '좋아요' 클릭
     * @param portfolioId
     * @return : PfLikeInfoResp
     */
    @PostMapping("/portfolio/{portfolio_id}/heart")
    public ResponseEntity<BasicResponse> likePortfolio(@PathVariable(value = "portfolio_id") Long portfolioId){
        PfLikeInfoResp pfLikeInfoResp = portfolioLikeService.heartPortfolio(portfolioId);

        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "좋아요를 눌렀습니다", pfLikeInfoResp));
    }

}
