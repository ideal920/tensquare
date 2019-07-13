package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    /**
     * 最新回复的问题显示在上方，按回复时间降序排序
     * @param labelId
     * @param pageable
     * @return
     */
    @Query(value = "select * from tb_problem where id in (SELECT problemid FROM tb_pl WHERE labelid = ?1 ) order by replytime desc  ",nativeQuery = true,
            countQuery = "select count(*) from tb_problem where id in (SELECT problemid FROM tb_pl WHERE labelid = ?1 )")
    public Page<Problem> findNewListByLabelId(String labelId, Pageable pageable);

    /**
     * 按回复数降序排序
     * @param labelId
     * @param pageable
     * @return
     */
    //按照回复数排序
    @Query(value = "select * from tb_problem where id in (SELECT problemid FROM tb_pl WHERE labelid = ?1 ) order by reply desc  ",nativeQuery = true,
            countQuery = "select count(*) from tb_problem where id in (SELECT problemid FROM tb_pl WHERE labelid = ?1 )")
    Page<Problem> findHotListByLabelId(String labelId, Pageable pageable);


    /**
     * 根据标签ID查询等待回答列表
     * @param labelId
     * @param pageable
     * @return
     */
    @Query(value = "select * from tb_problem where id in( select problemid from tb_pl where labelid=?1 ) and reply=0 order by createtime desc ",nativeQuery = true,
            countQuery = "select * from tb_problem where id in( select problemid from tb_pl where labelid=?1 ) and reply=0")
    public Page<Problem> findWaitListByLabelId(String labelId, Pageable pageable);
}
