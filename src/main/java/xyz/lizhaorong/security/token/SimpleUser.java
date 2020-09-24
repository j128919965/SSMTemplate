package xyz.lizhaorong.security.token;

/**
 * 解析User之后的内容
 * id 和 role
 * 只有role大于等于Authorization的value时，才能被放行
 * @author lzr
 * @see xyz.lizhaorong.security.webUtil.Authorization
 */
public class SimpleUser {

    private String userId;
    private String addr;
    private int role;

    public SimpleUser(String userId, int role, String addr) {
        this.userId = userId;
        this.addr = addr;
        this.role = role;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "SimpleUser{" +
                "userId='" + userId + '\'' +
                ", addr='" + addr + '\'' +
                ", role=" + role +
                '}';
    }
}
