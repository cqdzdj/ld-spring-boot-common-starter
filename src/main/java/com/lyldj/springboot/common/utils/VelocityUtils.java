package com.lyldj.springboot.common.utils;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Properties;

/**
 * Velocity工具类:根据模板生成目标文件
 */
public class VelocityUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(VelocityUtils.class);
	/**
	 * 根据模板生成文件
	 * @param inputVmFilePath 模板路径
	 * @param outputFilePath 输出文件路径
	 * @param context velocity上下文
	 */
	public static void generate(String inputVmFilePath, String outputFilePath, VelocityContext context) {
		try {
			Properties properties = new Properties();
			properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, FileUtils.getDir(inputVmFilePath));
			Velocity.init(properties);
			Template template = Velocity.getTemplate(FileUtils.getFileName(inputVmFilePath), StringUtils.DEFAULT_CHARSET);
			File outputFile = new File(outputFilePath);
			FileWriterWithEncoding writer = new FileWriterWithEncoding(outputFile, StringUtils.DEFAULT_CHARSET);
			template.merge(context, writer);
			writer.close();
		} catch (Exception ex) {
			LOGGER.error("generate file from velocity templates occurs an error!", ex);
		}
	}


}
