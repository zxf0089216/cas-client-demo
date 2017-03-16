import com.hundsun.cas.client.util.CommonUtils;
import org.junit.Test;

import java.util.regex.Pattern;

/**
 * https://www.zifangsky.cn/647.html
 */
public class FilterRegexTest {

    @Test
    public void test1() throws Exception {
//        String filterRegex = "/scripts/*.js;/styles/*;/images/*;*.json";
//        String filterRegex = "/api/v1/*;(!(*.json))";
//        String filterRegex = "/api/v1;*.css;*.js";
        String filterRegex = "/api/v1;*.css;?<!*.json";
//        String filterRegex = "/;/scripts/*;/styles/*;/images/*";
        String t2 = "*/js/*;/scripts/*;";
        String t3 = "\\;\\scripts\\*";
        String t4 = "*";
        String t5 = "/pages/*/js/*";
        String t6 = "/page.html/js/*";

//        String test = "/pages/scripts/xx.js";
//        String test = "/scripts/xx.js";
//        String test = "1.json";
        String test = "/api/v2/11.js";
        String regStr = CommonUtils.assemblyRegexStr(filterRegex);
        System.out.println("正则表达式: "+regStr);
        Pattern pattern = Pattern.compile(regStr);
        if (pattern.matcher(test).matches()) {
            System.out.println("该路径匹配: "+test);
//            filterChain.doFilter(request, response);
        } else {
            System.out.println("该路径未匹配: "+test);
//            EscapeScriptwrapper escapeScriptwrapper = new EscapeScriptwrapper(request);
//            filterChain.doFilter(escapeScriptwrapper, response);
        }
    }

}