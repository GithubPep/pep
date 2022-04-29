package com.example.pep.iot.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pep.iot.auth.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LiuGang
 * @since 2022-03-22 21:19
 */
@Mapper
public interface AdminMapper extends BaseMapper<AdminUser> {

}
