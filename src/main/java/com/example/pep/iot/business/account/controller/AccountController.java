package com.example.pep.iot.business.account.controller;

import com.example.pep.iot.business.account.entity.AppAccount;
import com.example.pep.iot.business.account.service.AccountService;
import com.example.pep.iot.response.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/add-brand/{brandCode}")
    public ResultVO<Object> addBrand(@PathVariable("brandCode") @Validated @NotBlank String brandCode) {
        AppAccount appAccount = accountService.addBrand(brandCode);
        return ResultVO.ok(appAccount);
    }


}
