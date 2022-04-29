package com.example.pep.iot.business.account.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pep.iot.base.page.PageReq;
import com.example.pep.iot.base.page.PageResponse;
import com.example.pep.iot.business.account.dto.AppCountDto;
import com.example.pep.iot.business.account.entity.AppAccount;
import com.example.pep.iot.business.account.mapper.AppAccountMapper;
import com.example.pep.iot.business.account.vo.AccountVO;
import com.example.pep.iot.utils.ResultUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * 账户逻辑
 *
 * @author LiuGang
 * @since 2022-03-04 2:16 PM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService extends ServiceImpl<AppAccountMapper, AppAccount> {

    /**
     * 添加品牌,分配ak.sk
     *
     * @param brandCode 品牌
     */
    @Transactional(rollbackFor = Exception.class)
    public AppAccount addBrand(String brandCode) {
        //valid brand
        Optional<AppAccount> optional = getOneByBrandCode(brandCode);
        ResultUtils.ifThrow(optional.isPresent(), "当前brandCode,已经存在");
        //build ak/sk
        AppAccount appAccount = new AppAccount().setBrandCode(brandCode).setAppKey(IdUtil.fastSimpleUUID()).setAppSecret(IdUtil.fastSimpleUUID());
        this.save(appAccount);
        return appAccount;
    }

    private Optional<AppAccount> getOneByBrandCode(String brandCode) {
        LambdaQueryWrapper<AppAccount> queryWrapper = Wrappers.lambdaQuery(AppAccount.class);
        queryWrapper.eq(AppAccount::getBrandCode, brandCode);
        return Optional.ofNullable(this.getOne(queryWrapper));
    }

    public AppCountDto getAppAccountByDeviceCode(String brandCode) {
        Optional<AppAccount> optional = getOneByBrandCode(brandCode);
        if (optional.isPresent()) {
            AppAccount appAccount = optional.get();
            return BeanUtil.copyProperties(appAccount, AppCountDto.class);
        }
        ResultUtils.ifThrow(true, "当前brandCode不存在");
        return null;
    }


    public PageResponse<AppAccount, AccountVO> queryPage(PageReq pageReq) {
        Page<AppAccount> page = new Page<>(pageReq.getCurrentPage(), pageReq.getPageSize());
        if (StrUtil.isNotBlank(pageReq.getSortFieldName())) {
            page.setOrders(Collections.singletonList(new OrderItem(pageReq.getSortFieldName(), pageReq.isAsc())));
        } else {
            page.setOrders(Collections.singletonList(new OrderItem("createTime", false)));
        }
        QueryWrapper<AppAccount> wrapper = new QueryWrapper<AppAccount>()
                .like(StrUtil.isNotBlank(pageReq.getSearchFieldName()), pageReq.getSearchFieldName(), pageReq.getSearchWord());
        Page<AppAccount> appAccountPage = this.page(page, wrapper);
        return new PageResponse<>(appAccountPage, appAccount -> BeanUtil.copyProperties(appAccount, AccountVO.class));
    }

    public AccountVO getInfo(String id) {
        AppAccount appAccount = this.getById(id);
        return BeanUtil.copyProperties(appAccount, AccountVO.class);
    }
}
