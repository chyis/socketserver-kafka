package com.jdy.collections;

import java.io.File;  
import java.io.FileInputStream;  
import java.net.URL;  
  
import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;  
import org.apache.logging.log4j.core.config.ConfigurationSource;  
import org.apache.logging.log4j.core.config.Configurator;

public class LogOut{
	public static Logger logs;
	
	public LogOut(Logger log){
		ConfigurationSource source;
        try {
            //方法1  使用  public ConfigurationSource(InputStream stream) throws IOException 构造函数  
            source = new ConfigurationSource(new FileInputStream("..\\etc\\log4j2.xml"));
            
            //方法2 使用 public ConfigurationSource(InputStream stream, File file)构造函数
            //File config=new File("D:\\NPMLServer\\Prometheus\\etc\\log4j2.xml");
            //source = new ConfigurationSource(new FileInputStream(config),config);
            
            //方法3 使用 public ConfigurationSource(InputStream stream, URL url) 构造函数  
            //String path="D:\\log4j2.xml";
            //source = new ConfigurationSource(new FileInputStream(path),new File(path).toURL());
            //source.setFile(new File("D:\\NPMLServer\\Prometheus\\etc\\log4j2.xml"));
            //source.setInputStream(new FileInputStream("D:\log4j2.xml"));
            Configurator.initialize(null, source);
            logs = log;
        } catch (Exception e) {  
            //e.printStackTrace();  
        }
	}
	
	public void log(String type,String msg) {
		if(type.equals("trace")){
			logs.trace(msg);
		}else if(type.equals("debug")){
			logs.debug(msg);
		}else if(type.equals("info")){
			logs.info(msg);
		}else if(type.equals("error")){
			logs.error(msg);
		}else if(type.equals("warn")){
			logs.warn(msg);
		}else if(type.equals("fatal")){
			logs.fatal(msg);
		}else if(type.equals("all")){
			logs.trace(msg);
			logs.debug(msg);
			logs.info(msg);
			logs.warn(msg);
			logs.error(msg);
            logs.fatal(msg);
		}
	}
}
