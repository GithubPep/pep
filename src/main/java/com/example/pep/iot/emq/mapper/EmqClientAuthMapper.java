package com.example.pep.iot.emq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pep.iot.emq.entity.EmqClientAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author LiuGang
 * @since 2022-02-17 3:23 PM
 */
@Mapper
public interface EmqClientAuthMapper extends BaseMapper<EmqClientAuth> {

}
