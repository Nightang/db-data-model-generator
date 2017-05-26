package org.nightang.db.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class DBModelGenerator {
	
	public void generateDBModel(String configPath, String dbFilePath) throws Exception {
		System.out.println("Start Generate DB Model Code ......");
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		
		// Set File Name
		File configFile = new File(configPath);
		
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		
		// Replace ConnectionURL
		List<Context> cxts = config.getContexts();
		for(Context cxt : cxts) {
			String url = cxt.getJdbcConnectionConfiguration().getConnectionURL();
			cxt.getJdbcConnectionConfiguration().setConnectionURL(url.replace("{0}", dbFilePath));
		}		
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
		System.out.println("Finished");
	}

}
