import com.university.crm.util.HashKit;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;

/**
 * date: 2019/3/19
 * description :
 *
 * @author : zhencai.cheng
 */
public class Test {


    public static void main(String[] args) {

        String salt =HashKit.getCharAndNum(6).toUpperCase();

        String password = HashKit.md5HashAddSalt("admin","I733R2");

        System.out.println(salt);
        System.out.println(password);
    }
}
