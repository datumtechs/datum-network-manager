package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.AuthorityBusiness;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author juzix
 * @Date 2022/7/21 12:02
 * @Version
 * @Desc ******************************
 */
public interface AuthorityBusinessMapper {
    int deleteById(Integer id);

    int insert(AuthorityBusiness record);

    int insertSelectiveReturnId(AuthorityBusiness record);

    AuthorityBusiness selectById(Integer id);

    int updateByIdSelective(AuthorityBusiness record);

    int updateById(AuthorityBusiness record);

    /**
     * 查询我待办的事务
     *
     * @return
     */
    List<AuthorityBusiness> selectTodoList(@Param("keyword") String keyword);

    /**
     * 查询待处理的提案
     *
     * @return
     */
    List<AuthorityBusiness> selectProposalTodoList();

    /**
     * 查询我待办的事务总数
     */
    int selectTodoCount();

    /**
     * 查询我已办的事务
     *
     * @return
     */
    List<AuthorityBusiness> selectDoneList(@Param("keyword") String keyword);

    int updateProcessStatusById(@Param("id") int id, @Param("status") int status);

    AuthorityBusiness selectByTypeAndRelationId(@Param("type") int type, @Param("relationId") String relationId);

    int updateStatusByTypeAndRelationId(@Param("type") int type, @Param("relationId") String relationId, @Param("status") int status);

}