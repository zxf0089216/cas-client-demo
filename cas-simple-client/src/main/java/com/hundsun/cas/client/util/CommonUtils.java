package com.hundsun.cas.client.util;

/**
 * @author zhangxf21203
 * @version 1.0
 */
public class CommonUtils {
    private static final String REGEX_SPLIT_CHAR=";";

    public static String assemblyRegexStr(String str) {
        if (str != null && !"".equals(str.trim())) {
            String[] excludes = str.split(REGEX_SPLIT_CHAR);  //以分号进行分割
            int length = excludes.length;
            for (int i = 0; i < length; i++) {
                String tmpExclude = excludes[i];
                //对点、反斜杠和星号进行转义
                tmpExclude = tmpExclude.replace("\\", "\\\\").replace(".", "\\.").replace("*", ".*");

                tmpExclude = "^" + tmpExclude + "$";
                excludes[i] = tmpExclude;
            }
            return join(excludes, "|");
        }
        return str;
    }

    public static String join(Object[] array, String separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            if (separator == null) {
                separator = "";
            }

            int bufSize = endIndex - startIndex;
            if (bufSize <= 0) {
                return "";
            } else {
                bufSize *= (array[startIndex] == null ? 16 : array[startIndex].toString().length()) + separator.length();
                StringBuffer buf = new StringBuffer(bufSize);

                for (int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    if (array[i] != null) {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

}
