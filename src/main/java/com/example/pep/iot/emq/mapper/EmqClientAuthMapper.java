package com.example.pep.iot.emq.mapper;

import com.example.pep.iot.emq.entity.EmqClientAuth;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

/**
 * @author LiuGang
 * @since 2022-02-17 3:23 PM
 */
@org.apache.ibatis.annotations.Mapper
public interface EmqClientAuthMapper {

    @Select("select * from pep_emq_client_auth where id = #{id}")
    EmqClientAuth selectById(@Param("id") Integer id);

    @Select("select * from pep_emq_client_auth where deviceCode = #{deviceCode}")
    EmqClientAuth selectOneByDeviceCode(@Param("deviceCode") String deviceCode);

    @Insert("insert into pep_emq_client_auth (deviceCode,clientId,serviceAddress,servicePort,username,password)" +
            " values(#{deviceCode},#{clientId},#{serviceAddress},#{servicePort},#{username},#{password})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Integer.class)
    int insert(EmqClientAuth emqClientAuth);
}
