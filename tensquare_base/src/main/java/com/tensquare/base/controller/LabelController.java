package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 标签控制层
 */
// 指定当前类中的配置也纳入消息总线刷新范围
@RefreshScope
@CrossOrigin
@RestController
@RequestMapping("/label")
public class LabelController {

    @Value("${haha.hehe}")
    private String haha;

    @Autowired
    private LabelService labelService;

    /**
     * 查询全部列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",labelService.findAll());
    }

    /**
     * 根据ID查询标签
     * @param labelId
     * @return
     */
    @RequestMapping(value="/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String labelId){
        System.out.println(haha);
        return new Result(true,StatusCode.OK,"查询成功",labelService.findById(labelId));
    }

    /**
     * 添加数据
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label) {
        labelService.save(label);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     * 修改信息
     * @param labelId
     * @param label
     * @return
     */
    @RequestMapping(value="/{labelId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String labelId,@RequestBody Label label){
        labelService.update(labelId,label);
        return new Result(true, StatusCode.OK,"修改成功");
    }

    /**
     * 根据id删除数据
     * @param labelId
     * @return
     */
    @RequestMapping(value="/{labelId}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String labelId){
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK,"删除成功");
    }


    /**
     * 条件+分页查询
     * @param label
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value="/search/{page}/{size}",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label
            , @PathVariable int page, @PathVariable int size){
        Page pageList= labelService.findSearch(label,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>
                (pageList.getTotalElements(),pageList.getContent() ));
    }

}
