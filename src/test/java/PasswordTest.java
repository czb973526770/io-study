import cn.hutool.crypto.digest.BCrypt;

/**
 * @author ：chenzb
 * @date ：2020/2/27 7:34
 * @description：
 */
public class PasswordTest {
    public static void main (String[] args) {
        String password = BCrypt.hashpw("czb123456", BCrypt.gensalt());
        System.out.println(password);
    }
}
