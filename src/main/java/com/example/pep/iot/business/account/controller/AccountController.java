package com.example.pep.iot.business.account.controller;

import com.example.pep.iot.base.page.PageReq;
import com.example.pep.iot.base.page.PageResponse;
import com.example.pep.iot.business.account.entity.AppAccount;
import com.example.pep.iot.business.account.service.AccountService;
import com.example.pep.iot.business.account.vo.AccountVO;
import com.example.pep.iot.response.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * 品牌,账户控制器
 *
 * @author LiuGang
 * @since 2022-03-04 2:16 PM
 */
@RequestMapping("/account")
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * 为brand分配ak/sk
     */
    @GetMapping("/add/brand/{brandCode}")
    public ResultVO<Object> addBrand(@PathVariable("brandCode") @Validated @NotBlank String brandCode) {
        AppAccount appAccount = accountService.addBrand(brandCode);
        return ResultVO.ok(appAccount, "新增品牌成功");
    }

    /**
     * 分页查询品牌
     */
    @PostMapping("/query/page")
    public ResultVO<PageResponse<AppAccount, AccountVO>> queryPage(@RequestBody PageReq pageReq) {
        PageResponse<AppAccount, AccountVO> response = accountService.queryPage(pageReq);
        return ResultVO.queryOk(response);
    }

    /**
     * 分页查询品牌
     */
    @GetMapping("/get/info")
    public ResultVO<AccountVO> getInfo(@RequestParam String id) {
        AccountVO accountVO = accountService.getInfo(id);
        return ResultVO.queryOk(accountVO);
    }


}
