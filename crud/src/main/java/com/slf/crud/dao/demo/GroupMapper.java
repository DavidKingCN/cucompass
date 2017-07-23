package com.slf.crud.dao.demo;

import com.slf.crud.model.demo.Group;
import com.slf.crud.model.demo.GroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupMapper {
    int countByExample(GroupExample example);

    int deleteByExample(GroupExample example);

    int deleteByPrimaryKey(Integer groupId);

    int insert(Group record);

    int insertSelective(Group record);

    int deleteLogicById(@Param("isDelete") int isDelete, @Param("id") Integer id);

    int deleteLogicByIds(@Param("isDelete") int isDelete, @Param("ids") Integer[] ids);

    List<Group> findByRecord(@Param("record") Group record, @Param("start") int start, @Param("end") int end);
    //查找个数
    int findByRecordCount(@Param("record") Group record);

    List<Group> selectByExample(GroupExample example);

    Group selectByPrimaryKey(Integer groupId);

    int updateByExampleSelective(@Param("record") Group record, @Param("example") GroupExample example);

    int updateByExample(@Param("record") Group record, @Param("example") GroupExample example);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);
    
    int saveOrUpdate(Group group);
    //查找存在的组
    Group findOneByRecord(@Param("record") Group record);
    
    Group findOneByRecord2( Group record);
}