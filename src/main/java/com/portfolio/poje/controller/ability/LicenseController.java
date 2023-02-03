package com.portfolio.poje.controller.ability;

import com.portfolio.poje.common.BasicResponse;
import com.portfolio.poje.controller.ability.licenseDto.LicenseCreateReq;
import com.portfolio.poje.controller.ability.licenseDto.LicenseInfoResp;
import com.portfolio.poje.controller.ability.licenseDto.LicenseUpdateReq;
import com.portfolio.poje.service.ability.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LicenseController {

    private final LicenseService licenseService;

    /**
     * 자격증 등록
     * @param licenseCreateReq
     * @return
     */
    @PostMapping("/member/license")
    ResponseEntity<BasicResponse> createLicense(@RequestBody LicenseCreateReq licenseCreateReq){
        licenseService.enroll(licenseCreateReq);

        return ResponseEntity.ok(new BasicResponse(HttpStatus.CREATED.value(), "등록되었습니다."));
    }


    /**
     * 자격증 수정
     * @param licenseId
     * @param licenseUpdateReq
     * @return : List<LicenseInfoResp>
     */
    @PutMapping("/member/license/{license_id}")
    ResponseEntity<BasicResponse> updateLicenseInfo(@PathVariable(name = "license_id") Long licenseId,
                                                    @RequestBody LicenseUpdateReq licenseUpdateReq){
        List<LicenseInfoResp> licenseInfoRespList = licenseService.updateLicenseInfo(licenseId, licenseUpdateReq);

        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "수정되었습니다.", licenseInfoRespList));
    }


    /**
     * 자격증 목록 반환
     * @return : List<LicenseInfoResp>
     */
    @GetMapping("/member/license")
    ResponseEntity<BasicResponse> licenseInfo(){
        List<LicenseInfoResp> licenseInfoRespList = licenseService.getLicenseList();

        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "자격증 목록 조회", licenseInfoRespList));
    }


    /**
     * 자격증 삭제
     * @param licenseId
     * @return
     */
    @DeleteMapping("/member/license/{license_id}")
    ResponseEntity<BasicResponse> deleteLicenseInfo(@PathVariable(value = "license_id") Long licenseId){
        licenseService.deleteLicense(licenseId);

        return ResponseEntity.ok(new BasicResponse(HttpStatus.OK.value(), "삭제되었습니다."));
    }

}
