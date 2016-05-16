# commons.lang3


---


[ System properties](../../src/main/java/com/foya/apache/commons/lang3/CommonsLang3SystemUtils.java)

```
public class CommonsLang3SystemUtils {

    /**
     * @param args
     */
    public static void main(String[] args) {

        /*
         * <code>awt.toolkit</code> 系統屬性
         * 如果為null,說明運行時未安全訪問或屬性不存在.
         */
        System.out.println("AWT_TOOLKIT:" +SystemUtils.AWT_TOOLKIT);

        /*
         * 文件編碼.
         * 如果為null,說明運行時未安全訪問或屬性不存在.
         * eg. UTF-8
         */
        System.out.println("FILE_ENCODING:" +SystemUtils.FILE_ENCODING);

        ....
    }

}



```

