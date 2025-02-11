package com.portfolio.poje.domain.project.controller;

import com.portfolio.poje.common.BasicResponse;
import com.portfolio.poje.domain.project.dto.PrAwardDto;
import com.portfolio.poje.domain.project.service.ProjectAwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class ProjectAwardController {

    private final ProjectAwardService projectAwardService;


    /**
     * 프로젝트 수상 정보 등록
     * @param projectId
     * @param prAwardCreateReq
     * @return
     */
    @PostMapping("/project/{project_id}/award")
    public ResponseEntity<BasicResponse> createProjectAward(@PathVariable(value = "project_id") Long projectId,
                                                            @RequestBody @Valid PrAwardDto.PrAwardCreateReq prAwardCreateReq){
        projectAwardService.enroll(projectId, prAwardCreateReq);

        return ResponseEntity.ok(new BasicResponse(HttpStatus.CREATED.value(), "등록되었습니다."));
    }


    /**
     * 프로젝트 수상 정보 수정
     * @param projectId
     * @param prAwardUpdateReq
     * @return
     */
    @PutMapping("/project/{project_id}/award")
    public ResponseEntity<BasicResponse> updateProjectAward(@PathVariable(value = "project_id") Long projectId,
                                                            @RequestBody PrAwardDto.PrAwardUpdateReq prAwardUpdateReq){
        projectAwardService.updateAwardInfo(projectId, prAwardUpdateReq);

        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "수정되었습니다."));
    }


    /**
     * 프로젝트 수상 정보 삭제
     * @param projectMap
     * @return
     */
    @DeleteMapping("/project/award")
    public ResponseEntity<BasicResponse> deleteProjectAwardInfo(@RequestBody Map<String, Long> projectMap){
        projectAwardService.deleteAward(projectMap.get("projectId"));

        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "삭제되었습니다."));
    }

}
