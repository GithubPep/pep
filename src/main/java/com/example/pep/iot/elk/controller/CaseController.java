package com.example.pep.iot.elk.controller;

import com.example.pep.iot.elk.service.CaseService;
import com.example.pep.iot.response.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiuGang
 * @since 2022-04-22 15:43
 */
@RestController
@RequestMapping("/case")
public class CaseController {

    private CaseService caseService;

    @Autowired
    private void setCaseService(CaseService caseService) {
        this.caseService = caseService;
    }

    @GetMapping("/save")
    public ResultVO<String> saveCase() {
        String caseId = caseService.saveCase();
        return ResultVO.ok(caseId);
    }



}
