package bilibili.comment;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class URLUtils {


    public static String createBVURL(String BV) {
        return "https://www.bilibili.com/video/" + BV;
    }

    public static String createRepliesURL(String aid, String pn, String sort) {
        return "https://api.bilibili.com/x/v2/reply?pn=" + pn + "&type=1&oid=" + aid + "&sort=0&_=1603500190026";
    }

    public static String createReRepliesURL(String aid, String pn, String root) {
        return "https://api.bilibili.com/x/v2/reply/reply?pn=" + pn + "&type=1&oid=" + aid + "&ps=10&root=" + root + "&_=160350308426";
    }

    public static String addURLByPn(String url) {
        String[] urlNum = url.split("pn=");
        int ans = 0, i = 0;
        for (;i < urlNum[1].length();i ++) {
            char c = urlNum[1].charAt(i);
            if ('0' <= c && c <= '9') {
                ans = ans * 10 + (c - '0');
            } else {
                break;
            }
        }
        ans ++;
        urlNum[1] = urlNum[1].substring(i);
        String ss = urlNum[0] + ("pn=" + ans + "") + urlNum[1];
        //System.out.println(ss);
        return ss;
    }

    @Test
    public void test() {

        String url = "https://api.bilibili.com/x/v2/reply?pn=123&type=1&oid=670053755&sort=0&_=1603500190026";
        String s = addURLByPn(url);
        System.out.println(s);

    }

}
