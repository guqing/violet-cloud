package xyz.guqing.violet.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.guqing.violet.common.core.model.constant.StringConstant;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author guqing
 * @date 2020-05-14
 */
@Slf4j
public class VioletUtil {
    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");
    private static final String UNKNOW = "unknown";
    private static final String CAMEL_TO_UNDERSCORE_REGEX = "[A-Z]";
    /**
     * 驼峰转下划线
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String camelToUnderscore(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }

        Matcher matcher = Pattern.compile(CAMEL_TO_UNDERSCORE_REGEX).matcher(value);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String g = matcher.group();
            matcher.appendReplacement(sb, StringConstant.UNDER_LINE + g.toLowerCase());
        }
        matcher.appendTail(sb);
        if (StringConstant.UNDER_LINE.equals(sb.charAt(0)+"")) {
            sb.delete(0, 1);
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String underscoreToCamel(String value) {
        StringBuilder result = new StringBuilder();
        String[] arr = value.split("_");
        for (String s : arr) {
            result.append((String.valueOf(s.charAt(0))).toUpperCase()).append(s.substring(1));
        }
        return result.toString();
    }

    /**
     * 正则校验
     *
     * @param regex 正则表达式字符串
     * @param value 要匹配的字符串
     * @return 正则校验结果
     */
    public static boolean match(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     * 获取请求IP
     *
     * @param request ServerHttpRequest
     * @return String IP
     */
    public static String getServerHttpRequestIpAddress(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !UNKNOW.equalsIgnoreCase(ip)) {
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取请求IP
     *
     * @return String 返回ip地址
     */
    public static String getRequestIpAddress() {
        HttpServletRequest request = getHttpServletRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 判断是否包含中文
     *
     * @param value 内容
     * @return 结果
     */
    public static boolean containChinese(String value) {
        if (StringUtils.isBlank(value)) {
            return Boolean.FALSE;
        }
        Matcher matcher = CHINESE_PATTERN.matcher(value);
        return matcher.find();
    }

    public static void printSystemUpBanner(Environment environment) {
        String banner = "-----------------------------------------\n" +
                "服务启动成功，时间：" + DateUtil.formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN) + "\n" +
                "服务名称：" + environment.getProperty("spring.application.name") + "\n" +
                "端口号：" + environment.getProperty("server.port") + "\n" +
                "-----------------------------------------";
        System.out.println(banner);
    }

    public static Long getPageTotal(Long pageSize, Long total) {
        return (total  +  pageSize  - 1) / pageSize;
    }

    public static List<String> commaSeparatedToList(String str) {
        Assert.notNull(str, "The parapeter str cannot be null");
        return Arrays.asList(str.split(StringConstant.COMMA));
    }

    public static Map<String, String> getSystemBrowserInfoByUserAgent(String ua) {
        Map<String,String> map = new HashMap<>(2,1);
        try {
            StringBuilder userAgent = new StringBuilder("[");
            userAgent.append(ua);
            userAgent.append("]");
            int indexOfMac = userAgent.indexOf("Mac OS X");
            int indexOfWindows = userAgent.indexOf("Windows NT");
            int indexOfIe = userAgent.indexOf("MSIE");
            int indexOfIe11 = userAgent.indexOf("rv:");
            int indexOfFirefox = userAgent.indexOf("Firefox");
            int indexOfSogou = userAgent.indexOf("MetaSr");
            int indexOfChrome = userAgent.indexOf("Chrome");
            int indexOfSafari = userAgent.indexOf("Safari");
            boolean isMac = indexOfMac > 0;
            boolean isWindows = indexOfWindows > 0;
            boolean isLinux = userAgent.indexOf("Linux") > 0;
            boolean containIe = indexOfIe > 0 || (isWindows && (indexOfIe11 > 0));
            boolean containFireFox = indexOfFirefox > 0;
            boolean containSogou = indexOfSogou > 0;
            boolean containChrome = indexOfChrome > 0;
            boolean containSafari = indexOfSafari > 0;

            String browser = "";
            if (containSogou) {
                if (containIe) {
                    browser = "搜狗" + userAgent.substring(indexOfIe, indexOfIe + "IE x.x".length());
                } else if (containChrome) {
                    browser = "搜狗" + userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
                }
            } else if (containChrome) {
                browser = userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
            } else if (containSafari) {
                int indexOfSafariVersion = userAgent.indexOf("Version");
                browser = "Safari "
                        + userAgent.substring(indexOfSafariVersion, indexOfSafariVersion + "Version/x.x.x.x".length());
            } else if (containFireFox) {
                browser = userAgent.substring(indexOfFirefox, indexOfFirefox + "Firefox/xx".length());
            } else if (containIe) {
                if (indexOfIe11 > 0) {
                    browser = "IE 11";
                } else {
                    browser = userAgent.substring(indexOfIe, indexOfIe + "IE x.x".length());
                }
            }

            String os = "";
            if (isMac) {
                os = userAgent.substring(indexOfMac, indexOfMac + "MacOS X xxxxxxxx".length());
            } else if (isLinux) {
                os = "Linux";
            } else if (isWindows) {
                os = "Windows ";
                String version = userAgent.substring(indexOfWindows + "Windows NT".length(), indexOfWindows
                        + "Windows NTx.x".length());
                switch (version.trim()) {
                    case "5.0":
                        os += "2000";
                        break;
                    case "5.1":
                        os += "XP";
                        break;
                    case "5.2":
                        os += "2003";
                        break;
                    case "6.0":
                        os += "Vista";
                        break;
                    case "6.1":
                        os += "7";
                        break;
                    case "6.2":
                        os += "8";
                        break;
                    case "6.3":
                        os += "8.1";
                        break;
                    case "10":
                        os += "10";
                    default:
                        break;
                }
            }

            map.put("system", os);
            map.put("browser", StringUtils.replace(browser, "/", " "));
        } catch (Exception e) {
            log.error("获取登录信息失败：[{}]", e.getMessage());
            map.put("system", "");
            map.put("browser", "");
        }
        return map;
    }
}
