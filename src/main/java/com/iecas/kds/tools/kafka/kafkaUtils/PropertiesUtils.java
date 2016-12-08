package com.iecas.kds.tools.kafka.kafkaUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by james on 2016/6/15.
 */
public class PropertiesUtils {

  private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

  private static Map<String, Properties> map = new HashMap<>();

  synchronized public static Properties get(String filename) {
    if (!filename.endsWith(".properties"))
      filename = filename + ".properties";
    if (!map.containsKey(filename))
      map.put(filename, load(new Properties(), filename));
    return map.get(filename);
  }

  public static Properties load(Properties prop, String filename) {
    try {
      prop.load(findAtClasspath(filename));
      InputStream ise = findAtJarDir(filename);
      if (ise != null)
        prop.load(ise);
    } catch (IOException e) {
      logger.error("load failed, filename={}", filename, e);
      return null;
    }
    return prop;
  }

  public static InputStream search(String filename) {
    InputStream is = findAtJarDir(filename);
    if (is == null)
      is = findAtClasspath(filename);
    if (null == is)
      logger.error("search resource file failed, filename={}", filename);
    return is;
  }

  public static InputStream findAtJarDir(String filename) {
    // jar 包的全路径中不能有中文, 否则后面会因为编码找不到本来存在的文件
    String path = new PropertiesUtils().getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    if (!path.endsWith(".jar"))
      return null;
    path = path.substring(0, path.lastIndexOf("/"));
    path = convertUrlByOS(path) + "/" + filename;
    try {
      InputStream is = new FileInputStream(path);
      logger.info("load resource file at jar dir, path={}", path);
      return is;
    } catch (FileNotFoundException e) {
      return null;
    }
  }

  public static InputStream findAtClasspath(String filename) {
    String path = PropertiesUtils.class.getResource("/" + filename).getFile();
    if (path == null || "".equals(path))
      return null;
    InputStream is = PropertiesUtils.class.getResourceAsStream("/" + filename);
    if (is != null)
      logger.info("load resource file at classpath, path={}", path);
    return is;
  }

  private static String convertUrlByOS(String path) {
    String os = System.getProperty("os.name");
    logger.info("os={}", os);
    if (null != os && os.indexOf("Windows") != -1)
      path = path.substring(1);
    return path;
  }

}
