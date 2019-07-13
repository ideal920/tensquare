package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import util.SnowflakeIdWorker;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private SnowflakeIdWorker idWorker;

    //根据id查
    public Label findById(String id){
        return labelDao.findById(id).get();
    }
    //查询所有
    public List<Label> findAll(){
        return  labelDao.findAll();
    }

    //增
    public void save(Label label){
            //使用id生成器生成id值
        label.setId(idWorker.nextId()+"");
            //调用Dao保存
        labelDao.save(label);
    }
    //删
    public void deleteById(String labelId){
        labelDao.deleteById(labelId);
    }

    //改
    public void update(String labelId,Label label){
        //指定id
        label.setId(labelId);
        //执行修改
        labelDao.save(label);
    }


    /**
     * 分页条件查询
     * @param label
     * @param page
     * @param size
     * @return
     */
    public Page<Label> findSearch(Label label, int page, int size){
        return labelDao.findAll((root, criteriaQuery, builder) -> {
            List<Predicate> conditionList = new ArrayList<>();
            //labelName
            if(!StringUtils.isEmpty(label.getLabelname())){ //判断条件不为空=>拼装条件
                conditionList.add(builder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%"));
            }
            //state
            if(!StringUtils.isEmpty(label.getState())){ //判断条件不为空=>拼装条件
                conditionList.add(builder.like(root.get("state").as(String.class), label.getLabelname()));
            }
            //recommend
            if(!StringUtils.isEmpty(label.getRecommend())){ //判断条件不为空=>拼装条件
                conditionList.add(builder.like(root.get("recommend").as(String.class), label.getRecommend()));
            }
        if (conditionList.size()==0){//没有条件
            return null;//查询所有
        }
            //有条件,使用and连接
            return builder.and(conditionList.toArray(new Predicate[conditionList.size()]));
        },PageRequest.of(page-1, size));
    }
}
