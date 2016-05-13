# ini4j (操作文本行的配置文件的工具)


>Ini4j 是一款操作文本行的配置文件的工具，網站對如何使用作了介紹 http://ini4j.sourceforge.net/ 。
他實現了可以讀取類似 *.ini 文件格式的配置文件。


```
<dependency>  
  <groupId>org.ini4j</groupId>  
  <artifactId>ini4j</artifactId>  
  <version>0.5.2</version>  
</dependency>  
```

---

配置文件如下：

```
#############################################
# ini4j example #
#############################################

[system]
program_name=ini4jExample
version=1.0

[person_1]
name=kanpiaoxue_1
age=30
sex=1

[person_2]
name=kanpiaoxue_2
age=31
sex=1

[company]
name=company1
address=beijing

[company]
name=company1
address=beijing

```

---
java代碼如下：

```
import org.apache.commons.lang3.StringUtils;  
import org.ini4j.Config;  
import org.ini4j.Ini;  
import org.ini4j.Profile.Section;  
  
import com.google.common.io.Resources;  
  
import java.io.IOException;  
import java.net.URL;  
import java.util.Map.Entry;  
import java.util.Set;  
  
/** 
 * <pre> 
 * Init4jExample.java 
 * @author kanpiaoxue<br> 
 * @version 1.0 
 * Create Time 2014年7月5日 下午12:43:04<br> 
 * Description : init4j 的使用 
 * </pre> 
 */  
public class Init4jExample {  
    private static final String CONFIG_NAME = "hello.conf";  
    private static final String SYSTEM = "system";  
    private static final String COMPANY = "company";  
    private static final String PROGRAM_NAME = "program_name";  
    private static final String VERSION = "version";  
    private static final String NAME = "name";  
    private static final String AGE = "age";  
    private static final String SEX = "sex";  
    private static final String ADDRESS = "address";  
  
    /** 
     * <pre> 
     * @param args 
     * </pre> 
     */  
    public static void main(String[] args) {  
  
        Config cfg = new Config();  
        // 生成配置文件的URL  
        URL url = Resources.getResource(CONFIG_NAME);  
        // 設置Section允許出現重複  
        cfg.setMultiSection(true);  
        Ini ini = new Ini();  
        ini.setConfig(cfg);  
        try {  
            // 加載配置文件  
            ini.load(url);  
            System.out.println(StringUtils.center(SYSTEM, 50, '='));  
            // 讀取 system  
            Section section = ini.get(SYSTEM);  
            System.out.println(PROGRAM_NAME + " : " + section.get(PROGRAM_NAME));  
            System.out.println(VERSION + " : " + section.get(VERSION));  
  
            // 讀取沒有規律的person系列  
            System.out.println(StringUtils.center("person", 50, '='));  
            Set<Entry<String, Section>> set = ini.entrySet();  
            for (Entry<String, Section> entry : set) {  
                String sectionName = entry.getKey();  
                // 跳過 system 和 company  
                if (!SYSTEM.equals(sectionName) && !COMPANY.equals(sectionName)) {  
                    System.out.println(NAME + " : " + entry.getValue().get(NAME));  
                    System.out.println(AGE + " : " + entry.getValue().get(AGE));  
                    System.out.println(SEX + " : " + entry.getValue().get(SEX));  
                }  
            }  
  
            // 讀取具有相同 Section 的 company  
            System.out.println(StringUtils.center(COMPANY, 50, '='));  
            for (Section session : ini.getAll(COMPANY)) {  
                System.out.println(NAME + " : " + session.get(NAME));  
                System.out.println(ADDRESS + " : " + session.get(ADDRESS));  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
} 
```



