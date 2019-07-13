package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String> {

    //根据userid&friendid 查询总记录数
    @Query(" select count(f) from Friend f where userid=?1 and friendid=?2 ")
    Integer findByUserIdAndFriendId(String userId, String friendId);

    @Modifying
    @Query("update Friend set islike = ?3  where userid=?1 and friendid=?2  ")
    void updateIslike(String userid, String friendid, String islike);
    @Modifying
    @Query("delete from Friend where userid=?1 and friendid=?2  ")
    void deleteByUseridAndFriendid(String userid, String friendid);
}
