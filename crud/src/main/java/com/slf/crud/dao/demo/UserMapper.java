package com.slf.crud.dao.demo;

import com.slf.crud.model.demo.Group;
import com.slf.crud.model.demo.User;
import com.slf.crud.model.demo.UserExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    int deleteLogicById(@Param("isDelete") int isDelete, @Param("id") Integer id);

    int deleteLogicByIds(@Param("isDelete") int isDelete, @Param("ids") Integer[] ids);

    List<User> findByRecord(@Param("record") User record, @Param("start") int start, @Param("end") int end);

    int findByRecordCount(@Param("record") User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    int saveOrUpdate(User user);
}