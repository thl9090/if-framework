package com.yx.generator;

/**
 * 代码生成器
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public class CodeGenerator {

    public static void main(String[] args) {
        UmpGeneratorUtil mybatisPlusGeneratorUtil = new UmpGeneratorUtil();
        String propertiesFilePath = "generator.properties";
        mybatisPlusGeneratorUtil.generator(propertiesFilePath);
    }

}
