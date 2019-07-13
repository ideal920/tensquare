package com.tensquare.friend.service;

import com.netflix.discovery.converters.Auto;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.feign.UserClient;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;


    //添加好友
    public void addFriend(String userId,String friendid){
        //判断是否已经添加过好友
       if( friendDao.findByUserIdAndFriendId(userId, friendid)>0){
           throw new RuntimeException("请勿重复关注");
       }
        //创建Friend对象
        Friend friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendid);

        //查询反向是否已经关注
        Integer count = friendDao.findByUserIdAndFriendId(friendid, userId);
        if(count>0){//对方已经关注我
        //关注 => 修改双方的关注的islike字段为1
            friend.setIslike("1");//互相关注
            friendDao.updateIslike(friendid, userId, "1");
        }else{
            friend.setIslike("0");//单向关注
        }
        //远程调用,维护用户的关注数&粉丝数
        userClient.followcount(userId, 1);
        userClient.fanscount(friendid, 1);

        //保存实体
        friendDao.save(friend);
    }

    //添加非好友
    public void addNoFriend(String userId,String friendid){
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendid);

        noFriendDao.save(noFriend);

    }


    //删除好友
    public void delFriend(String userId,String friendid){
        //1 判断是否双向关注
        Integer count = friendDao.findByUserIdAndFriendId(friendid, userId);
        if(count>0){
        //2 双向关注 => 修改对方的islike属性为0
            friendDao.updateIslike(friendid, userId, "0");
        }
        //3 删除Friend表对应记录
        friendDao.deleteByUseridAndFriendid(userId, friendid);

        //远程调用,维护用户的关注数&粉丝数
        userClient.followcount(userId, -1);
        userClient.fanscount(friendid, -1);

        //4 向NoFriend表添加记录
        addNoFriend(userId,friendid);


    }

}
