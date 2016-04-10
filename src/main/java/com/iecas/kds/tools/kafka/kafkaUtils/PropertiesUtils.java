package com.iecas.kds.tools.kafka.kafkaUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置文件加载工具
 */
public class PropertiesUtils {
	
	//kafka.properties配置文件属性对象
	public static Properties application = null;
	static {
		try {
			application = new Properties();
			File f = new File("kafka.properties");
			if(f.exists()){
				application.load(new FileReader(f));
			}else{
				application.load(PropertiesUtils.class.getResourceAsStream("/kafka.properties"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
