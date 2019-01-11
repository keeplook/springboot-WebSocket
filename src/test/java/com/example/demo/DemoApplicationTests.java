package com.example.demo;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
//import java.lang.reflect.Method;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() throws NoSuchMethodException, NoSuchFieldException, IOException, IllegalAccessException {
        /**
         * MyTest
         * */
//        Connection connection = Jsoup.connect("http://www.hunteron.com/elite/position/detail/1.htm");
//        connection.timeout(20000);
//        Document document = connection.execute().parse();
//        MyTest myTest = new MyTest();
//        Field[] fields = myTest.getClass().getDeclaredFields();
//        for (Field f : fields) {
//            FiledAnnotation filedAnnotation = f.getAnnotation(FiledAnnotation.class);
//            String re = document.select(filedAnnotation.selector()).text();
//            f.setAccessible(true);//因为属性为private所以必须执行这个操作
//            f.set(myTest, re);
//
//        }
//        System.out.println(myTest);

        /**
         * CsdnTest
         * */
//        Connection connection = Jsoup.connect("https://blog.csdn.net/championhengyi/article/details/68491306");
//        connection.timeout(20000);
//        Document document = connection.execute().parse();
//        CsdnTest myTest = new CsdnTest();
//        Field[] fields = myTest.getClass().getDeclaredFields();
//        for (Field f : fields) {
//            FiledAnnotation filedAnnotation = f.getAnnotation(FiledAnnotation.class);
//            String re = document.select(filedAnnotation.selector()).text();
//            f.setAccessible(true);//因为属性为private所以必须执行这个操作
//            f.set(myTest, re);
//
//        }
//        System.out.println(myTest);

        Connection con = Jsoup
                .connect("http://www.renren.com");// 获取连接
        con.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
//        Connection.Response connection = Jsoup.connect("http://www.renren.com").data("email", "177817615741", "password", "lihaoll123").method(Connection.Method.POST).timeout(10000).execute();

        Response rs = con.execute();// 获取响应
        Document d1 = Jsoup.parse(rs.body());// 转换为Dom树
        Elements et = d1.select("#loginForm");// 获取form表单，可以通过查看页面源码代码得知
//        Elements et1 = et.select("img");

        // 获取，cooking和表单属性，下面map存放post时的数据
        Map<String, String> datas = new HashMap<>();
        for (Element e : et.get(0).getAllElements()) {
            if (e.attr("name").equals("email")) {
                e.attr("value", "17781761574");// 设置用户名
            }
            if (e.attr("name").equals("password")) {
                e.attr("value", "lihaoll123"); // 设置用户密码
            }
            if (e.attr("name").equals("origURL")) {
                e.attr("value", "http%3A%2F%2Fwww.renren.com%2Fhome");
            }
            if (e.attr("name").equals("domain")) {
                e.attr("value", "renren.com");
            }
            if (e.attr("name").equals("key_id")) {
                e.attr("value", "1");
            }
            if (e.attr("name").equals("captcha_type")) {
                e.attr("value", "web_login");
            }
            if (e.attr("name").length() > 0) {// 排除空值表单属性
                datas.put(e.attr("name"), e.attr("value"));
            }

        }




        Connection con2 = Jsoup
                .connect("http://www.renren.com/ajaxLogin/login?1=1&uniqueTimestamp=2019032028425");
        con2.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        // 设置cookie和post上面的map数据
        System.out.println(rs.cookies());
        Document login = con2.ignoreContentType(true).method(Method.POST)
                .data(datas).cookies(rs.cookies()).execute().parse();
        // 打印，登陆成功后的信息
        System.out.println(login.html());
        Connection con3 = Jsoup
                .connect("http://www.renren.com/home");
        con2.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        Document r = con3.ignoreContentType(true).method(Method.GET).cookies(rs.cookies()).execute().parse();
        System.out.println(r.html());
        // 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
//        Map<String, String> map = login.;
//        for (String s : map.keySet()) {
//            System.out.println(s + "      " + map.get(s));
//        }
    }
}

