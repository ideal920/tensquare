package com.tensquare.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}

	@Autowired
	private HttpServletRequest request;

	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		/*//校验权限
		String token = request.getHeader("Authorization");
		//获得请求头 ||是否以Bearer开头
		if(StringUtils.isEmpty(token) || !token.startsWith("Bearer ") ){
			//获得失败=>提示先登录
			throw new RuntimeException("请先登录!");
		}
		//提取token
		token = token.substring(7);
		//jwt工具类解析token
		Claims claims = jwtUtils.parseJwt(token);
		//判断当前用户角色是否为管理员
		String roles = claims.get("roles", String.class);
		if(!"admin".equals(roles)){
			//否=> 提示没有权限
			throw new RuntimeException("您没有权限!");
		}*/

		if(request.getAttribute("admin_claims")==null){//不是管理员
			throw new RuntimeException("没有权限!");
		}

		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	/**
	 * 发送验证码
	 */
	@RequestMapping(value="/sendsms/{mobile}",method= RequestMethod.POST)
	public Result sendsms(@PathVariable String mobile ){

		userService.sendSms(mobile); //

		return new Result(true,StatusCode.OK,"发送成功");
	}/**
	 * 注册用户
	 */
	@RequestMapping(value="/register/{code}",method= RequestMethod.POST)
	public Result register(@PathVariable String code,@RequestBody User user ){

		userService.add(user,code);

		return new Result(true,StatusCode.OK,"注册成功");
	}
	@Autowired
	private JwtUtils jwtUtils;
	/**
	 * 登录用户
	 */
	@RequestMapping(value="/login",method= RequestMethod.POST)
	public Result login(@RequestBody User user ){

		User dbUser  = userService.login(user);

		//签发token
		String token = jwtUtils.generateJwt(dbUser.getId(), dbUser.getNickname(), "user");

		Map tokenMap = new HashMap();
		tokenMap.put("token", token);
		tokenMap.put("name", dbUser.getNickname());
		tokenMap.put("avatar", dbUser.getAvatar());

		return new Result(true,StatusCode.OK,"登录成功",tokenMap);
	}
}
