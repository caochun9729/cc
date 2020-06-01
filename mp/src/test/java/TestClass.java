import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cc.MpApplication;
import com.cc.entity.SysUser;
import com.cc.redis.RedisUtil;
import com.cc.service.SysUserService;
import com.cc.thread.HelloThread;
import com.cc.websocket.MessageResponse;
import com.cc.websocket.SocketHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@SpringBootTest(classes = MpApplication.class)
public class TestClass {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SocketHandler socketHandler;
    @Autowired
    private HelloThread helloThread;
    private final static Executor executor = Executors.newCachedThreadPool();// 启用多线程

    @Test
    public void test1(){
        SysUser sysUser=new SysUser();
        sysUser.setUsername("eeeeeeeeeeeeeee");
        sysUserService.save(sysUser);
    }

    public static void main(String[] args) {
        String s="30<数量指标<50 ";
        System.out.println(getNumber("=100% "));
        System.out.println(s.substring(s.length()-1,s.length()));
        String ss="=";
        switch (ss) {
            case "=":
            case "≤":
            case "<":
                System.out.println("1");
                break;
            case "≥":
            case ">":
                System.out.println("2");
                break;
        }
        List<SysUser> list=new ArrayList<>();
        SysUser sysUser=new SysUser();
        sysUser.setUsername("rrr");
        sysUser.setUserId(2112);
        list.add(sysUser);
        SysUser sysUser1=new SysUser();
        sysUser1.setUsername("rrr1");
        sysUser1.setUserId(2112);
        list.add(sysUser1);
        System.out.println(list.stream().distinct().collect(Collectors.toList()));
    }

    /**
     * 字符串得到数字
     *
     * @param a
     * @return
     */
    public static String getNumber(String a) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
        return m.replaceAll("").trim();
    }

    @Test
    public void testInsertString(){
        List<Map<String,Object>> o = (List<Map<String, Object>>)redisUtil.get("remind:10000086");
        System.out.println(o.size());
    }

    @Test
    public  void test11() throws InterruptedException {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("max(create_time)").like("USERNAME", "t");
        List<SysUser> list = sysUserService.list(queryWrapper);
        System.out.println(list);
    }

}
