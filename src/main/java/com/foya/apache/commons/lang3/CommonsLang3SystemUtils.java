package com.foya.apache.commons.lang3;

import org.apache.commons.lang3.SystemUtils;

public class CommonsLang3SystemUtils {
	  /**
     * @param args
     */
    public static void main(String[] args) {

        /*
         * <code>awt.toolkit</code> 系統屬性
         * 如果為null,說明運行時未安全訪問或屬性不存在.
         */
        System.out.println("AWT_TOOLKIT:"+SystemUtils.AWT_TOOLKIT);

        /*
         * 文件編碼.
         * 如果為null,說明運行時未安全訪問或屬性不存在.
         * eg. UTF-8
         */
        System.out.println("FILE_ENCODING:"+SystemUtils.FILE_ENCODING);

        /*
         * 文件分隔符 "\".
         * 如果為null,說明運行時未安全訪問或屬性不存在.
         */
        System.out.println("FILE_SEPARATOR:"+SystemUtils.FILE_SEPARATOR);

        /*
         *  <code>java.awt.fonts</code> 系統屬性
         *  如果為null,說明運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_AWT_FONTS:"+SystemUtils.JAVA_AWT_FONTS);

        /*
         *   <code>java.awt.graphicsenv</code> 系統屬性.
         *   如果為null,說明運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_AWT_GRAPHICSENV:"+SystemUtils.JAVA_AWT_GRAPHICSENV);

        /*
         *   <code>java.awt.headless</code> 系統屬性.
         *   如果為null,說明運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_AWT_HEADLESS:"+SystemUtils.JAVA_AWT_HEADLESS);


        /*
         *   <code>java.awt.printerjob</code> 系統屬性.
         *   如果為null,說明運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_AWT_PRINTERJOB:"+SystemUtils.JAVA_AWT_PRINTERJOB);

        /*
         *   <code>java.class.path</code> 系統屬性.
         *   Java class path.
         *   如果為null,說明運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_CLASS_PATH:"+SystemUtils.JAVA_CLASS_PATH);


        /*
         *   <code>java.class.version</code> 系統屬性.
         *   java class format version number.
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_CLASS_VERSION:"+SystemUtils.JAVA_CLASS_VERSION);

        /*
         *   <code>java.compiler</code> 系統屬性.
         *   一個叫JIT的編輯器,用於JDK version1.2,不適用於sun的JDK
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_COMPILER:"+SystemUtils.JAVA_COMPILER);

        /*
         *   <code>java.endorsed.dirs</code> 系統屬性.
         *   endorsed路徑
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_ENDORSED_DIRS:"+SystemUtils.JAVA_ENDORSED_DIRS);

        /*
         *   <code>java.ext.dirs</code> 系統屬性.
         *    extension路徑
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_EXT_DIRS:"+SystemUtils.JAVA_EXT_DIRS);

        /*
         *   <code>java.home</code> 系統屬性.
         *   java安裝目錄
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_HOME:"+SystemUtils.JAVA_HOME);

        /*
         *   <code>java.io.tmpdir</code> 系統屬性.
         *   默認的臨時文件路徑
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_IO_TMPDIR:"+SystemUtils.JAVA_IO_TMPDIR);

        /*
         *   <code>java.library.path</code> 系統屬性.
         *   環境變量裏的path屬性列表
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_LIBRARY_PATH:"+SystemUtils.JAVA_LIBRARY_PATH);

        /*
         *   <code>java.runtime.name</code> 系統屬性.
         *   java運行環境名稱(jre)
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_RUNTIME_NAME:"+SystemUtils.JAVA_RUNTIME_NAME);

        /*
         *   <code>java.runtime.version</code> 系統屬性.
         *   java運行環境版本(java -version)
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_RUNTIME_VERSION:"+SystemUtils.JAVA_RUNTIME_VERSION);

        /*
         *   <code>java.specification.name</code> 系統屬性.
         *   Java運行時環境規範名稱.
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_SPECIFICATION_NAME:"+SystemUtils.JAVA_SPECIFICATION_NAME);

        /*
         *   <code>java.specification.vendor</code> 系統屬性.
         *   Java運行時環境規範供應商
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_SPECIFICATION_VENDOR:"+SystemUtils.JAVA_SPECIFICATION_VENDOR);

        /*
         *   <code>java.specification.version</code> 系統屬性.
         *   Java運行時環境規範版本.
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_SPECIFICATION_VERSION:"+SystemUtils.JAVA_SPECIFICATION_VERSION);

        /*
         *   <code>java.util.prefs.PreferencesFactory</code> 系統屬性.
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_UTIL_PREFS_PREFERENCES_FACTORY:"+SystemUtils.JAVA_UTIL_PREFS_PREFERENCES_FACTORY);

        /*
         *   <code>java.vendor</code> 系統屬性.
         *   Java供應商特定的字符串。
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_VENDOR:"+SystemUtils.JAVA_VENDOR);

        /*
         *   <code>java.version</code> 系統屬性.
         *   Java版本號。
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_VERSION:"+SystemUtils.JAVA_VERSION);

        /*
         *   Java版本號(int)。
         * <ul>
         * <li><code>120</code> for Java 1.2
         * <li><code>131</code> for Java 1.3.1
         * </ul>
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        //System.out.println("JAVA_VERSION_INT:"+SystemUtils.JAVA_VERSION_INT);

        /*
         *   Java版本號(float)。
         * <ul>
         * <li><code>1.2f</code> for Java 1.2
         * <li><code>1.31f</code> for Java 1.3.1
         * </ul>
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        //System.out.println("JAVA_VERSION_FLOAT:"+SystemUtils.JAVA_VERSION_FLOAT);

        /*
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        //System.out.println("JAVA_VERSION_TRIMMED:"+SystemUtils.JAVA_VERSION_TRIMMED);

        /*
         *   java虛擬機相關信息.
         *   默認為null,如果運行時未安全訪問或屬性不存在.
         */
        System.out.println("JAVA_VM_INFO:"+SystemUtils.JAVA_VM_INFO);
        System.out.println("JAVA_VM_NAME:"+SystemUtils.JAVA_VM_NAME);
        System.out.println("JAVA_VM_SPECIFICATION_NAME:"+SystemUtils.JAVA_VM_SPECIFICATION_NAME);
        System.out.println("JAVA_VM_SPECIFICATION_VENDOR:"+SystemUtils.JAVA_VM_SPECIFICATION_VENDOR);
        System.out.println("JAVA_VM_SPECIFICATION_VERSION:"+SystemUtils.JAVA_VM_SPECIFICATION_VERSION);
        System.out.println("JAVA_VM_VENDOR:"+SystemUtils.JAVA_VM_VENDOR);
        System.out.println("JAVA_VM_VERSION:"+SystemUtils.JAVA_VM_VERSION);



        /*
         *   <code>line.separator</code>系統屬性
         *   (<code>&quot;\n&quot;</code> on UNIX).
         *   unix下的行分隔符 "\"
         */
        System.out.println("LINE_SEPARATOR:"+SystemUtils.LINE_SEPARATOR);
        /*
         *  <code>path.separator</code> 系統屬性.
         *  <code>&quot;:&quot;</code> on UNIX
         *  unix下的代碼 ";"
         */
        System.out.println("PATH_SEPARATOR:"+SystemUtils.PATH_SEPARATOR);

         /*
          * 操作系統相關.
          * 系統架構.
          * 系統名稱.
          * 系統版本.
          */
        System.out.println("OS_ARCH:"+SystemUtils.OS_ARCH);
        System.out.println("OS_NAME:"+SystemUtils.OS_NAME);
        System.out.println("OS_VERSION:"+SystemUtils.OS_VERSION);

        /*
         * 用戶相關.
         * 用戶所在國家.
         * 用戶項目名稱.
         * 用戶主目錄.
         * 用戶語言.
         * 用戶名稱.
         * 用戶時區.
         */
        System.out.println("USER_COUNTRY:"+SystemUtils.USER_COUNTRY);
        System.out.println("USER_DIR:"+SystemUtils.USER_DIR);
        System.out.println("USER_HOME:"+SystemUtils.USER_HOME);
        System.out.println("USER_LANGUAGE:"+SystemUtils.USER_LANGUAGE);
        System.out.println("USER_NAME:"+SystemUtils.USER_NAME);
        System.out.println("USER_TIMEZONE:"+SystemUtils.USER_TIMEZONE);

        /*
         * 判斷java版本.
         * true為當前版本.
         * false不為當前版本.
         */
        System.out.println("IS_JAVA_1_1:"+SystemUtils.IS_JAVA_1_1);
        System.out.println("IS_JAVA_1_2:"+SystemUtils.IS_JAVA_1_2);
        System.out.println("IS_JAVA_1_3:"+SystemUtils.IS_JAVA_1_3);
        System.out.println("IS_JAVA_1_4:"+SystemUtils.IS_JAVA_1_4);
        System.out.println("IS_JAVA_1_5:"+SystemUtils.IS_JAVA_1_5);
        System.out.println("IS_JAVA_1_6:"+SystemUtils.IS_JAVA_1_6);
        System.out.println("IS_JAVA_1_7:"+SystemUtils.IS_JAVA_1_7);

        /*
         * 判斷操作系統.
         *
         */
        System.out.println("IS_OS_AIX:"+SystemUtils.IS_OS_AIX);
        System.out.println("IS_OS_HP_UX:"+SystemUtils.IS_OS_HP_UX);
        System.out.println("IS_OS_IRIX:"+SystemUtils.IS_OS_IRIX);
        System.out.println("IS_OS_LINUX:"+SystemUtils.IS_OS_LINUX);
        System.out.println("IS_OS_MAC:"+SystemUtils.IS_OS_MAC);
        System.out.println("IS_OS_MAC_OSX:"+SystemUtils.IS_OS_MAC_OSX);
        System.out.println("IS_OS_OS2:"+SystemUtils.IS_OS_OS2);
        System.out.println("IS_OS_SOLARIS:"+SystemUtils.IS_OS_SOLARIS);
        System.out.println("IS_OS_UNIX:"+SystemUtils.IS_OS_UNIX);
        System.out.println("IS_OS_WINDOWS:"+SystemUtils.IS_OS_WINDOWS);
        System.out.println("IS_OS_WINDOWS_2000:"+SystemUtils.IS_OS_WINDOWS_2000);
        System.out.println("IS_OS_WINDOWS_7:"+SystemUtils.IS_OS_WINDOWS_7);
        System.out.println("IS_OS_WINDOWS_95:"+SystemUtils.IS_OS_WINDOWS_95);
        System.out.println("IS_OS_WINDOWS_98:"+SystemUtils.IS_OS_WINDOWS_98);
        System.out.println("IS_OS_WINDOWS_ME:"+SystemUtils.IS_OS_WINDOWS_ME);
        System.out.println("IS_OS_WINDOWS_NT:"+SystemUtils.IS_OS_WINDOWS_NT);
        System.out.println("IS_OS_WINDOWS_VISTA:"+SystemUtils.IS_OS_WINDOWS_VISTA);
        System.out.println("IS_OS_WINDOWS_XP:"+SystemUtils.IS_OS_WINDOWS_XP);


        System.out.println("getJavaHome():"+SystemUtils.getJavaHome());
        System.out.println("getJavaIoTmpDir():"+SystemUtils.getJavaIoTmpDir());
        System.out.println("getUserHome():"+SystemUtils.getUserHome());

    }
}
