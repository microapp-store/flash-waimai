package cn.enilu.flash.security;

import cn.enilu.flash.utils.Constants;

/**
 * descript
 *
 *@Author enilu
 * @Date 2021/4/21 22:02
 * @Version 1.0
 */
public class AccountInfo {
    private String username;
    private Long userId;
    private String userType;
    private String password;

    public boolean isMgr(){
        return Constants.USER_TYPE_MGR.equals(userType);
    }
    public AccountInfo(){

    }
    public AccountInfo(String username, Long userId, String userType, String password) {
        this.username = username;
        this.userId = userId;
        this.userType = userType;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
