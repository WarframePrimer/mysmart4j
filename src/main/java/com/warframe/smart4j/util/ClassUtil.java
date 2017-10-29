package com.warframe.smart4j.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/9/17 10:25
 * <p>
 * 自定义类加载器
 * 进行加载类 的操作
 */
public final class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader() {

        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     * 提供lazy load
     * lazy load(懒加载)
     * 在加载类的时候可以先不加载静态代码块
     * 直到有该类实例是才进行加载
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure", e);
            throw new RuntimeException(e);
        }
        return cls;
    }


    public static Class<?> loadClass(String className){
        return loadClass(className,true);
    }


    /**
     * 获取指定包名下的所有类
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();

        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {

                        String packagePath = url.getPath().replaceAll("%20", " ");
                        //如果类型为file
                        addClass(classSet, packagePath, packageName);
                    } else if ("jar".equals(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
//                        url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring
                                                (0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classSet,className);
                                    }
                                }
                            }
                        }
                    }

                }
            }
        } catch (IOException e) {
            LOGGER.error("get class set failure", e);
            throw new RuntimeException(e);
        }

        return classSet;

    }


    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(file -> {
            //查找并提取符合条件的File(包括文件对象，路径对象，目录对象)
            //这里具体的判断条件是1.以.class结尾的文件2.目录
            return ((file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());
        });
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }

                doAddClass(classSet,className);
            } else {
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                //递归，对于目录继续进行递归
                addClass(classSet,subPackagePath,subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet,String className){
        Class<?> cls = loadClass(className,false);
        classSet.add(cls);
    }

}
