package cn.kanmars.sn.dao.tddldao;



import cn.kanmars.sn.entity.UserInfo;

import java.util.List;

/**
 * Created by zhaojiuyang on 2015/12/8.
 */
@cn.kanmars.sn.MybatisRepository
public interface TblUserInfoSerMapper {
    List<UserInfo> getUserInfoList();
    UserInfo getUserInfo(Long pk);
    void insertUserInfo(UserInfo userInfo);
}
