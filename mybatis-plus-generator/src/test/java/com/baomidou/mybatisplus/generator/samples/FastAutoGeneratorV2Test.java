package com.baomidou.mybatisplus.generator.samples;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.io.File;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * <p>
 * 快速生成
 * </p>
 *
 * @author lanjerry
 * @since 2021-09-16
 */
public class FastAutoGeneratorV2Test extends BaseGeneratorTest {

    /**
     * 请设置数据库登录名和密码
     */
    private static final String DB_USER = "root";

    /**
     * 数据库登录密码
     */
    private static final String DB_PASSWORD = "maomaomao";

    /**
     * 数据库名称
     */
    private static final String DB_NAME = "cmoa";

    /**
     * 聚合名称（一个聚合对应一个仓储）
     */
    private static final String MODULE_NAME = "stock";

    /**
     * 如果工程在根目录下，可填写为 "/"，如果有父级目录，则需要填写父级目录前缀，如："mao-ddd-service/"
     */
    private static final String PROJECT_DIR = File.separator;

    /**
     * 当前工程目录名称
     */
    private static final String PROJECT_NAME = "infrastructure";

    /**
     * 仓储目录包名
     */
    private static final String PACKAGE_PARENT = "com.coolmild.cmoa.infrastructure";

    /**
     * 数据库JDBC连接URL
     */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/%s?useUnicode=true&useSSL=false&characterEncoding=utf8";

    /**
     * 在类的合适位置定义常量
     */
    private static final String WILDCARD = "*";

    /**
     * 执行 run
     */
    public static void main(String[] args) throws SQLException {
//        String projectPath = System.getProperty("user.dir");
        String projectPath = "/Users/mao/Desktop/mp";

        FastAutoGenerator
            // 创建Datasource
            .create(String.format(DB_URL, DB_NAME), DB_USER, DB_PASSWORD)
            // 全局配置
            .globalConfig(builder -> builder
                .author("毛宇鹏")
                .commentDate("yyyy-MM-dd hh:mm:ss")
                .outputDir(projectPath + File.separator + PROJECT_DIR + PROJECT_NAME + File.separator + "src" + File.separator + "main" + File.separator + "java")
                .disableOpenDir())
            // 字段类型映射配置
            .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                // 类型是Integer时，并且字段名称是created或者updated，则使用Long类型
                String columnName = metaInfo.getColumnName();
                if (typeCode == Types.INTEGER && ("created".equalsIgnoreCase(columnName) || "updated".equalsIgnoreCase(columnName) || columnName.endsWith("date") || columnName.endsWith("time"))) {
                    return DbColumnType.LONG;
                }
                if (typeCode == Types.SMALLINT || typeCode == Types.TINYINT) {
                    // 自定义类型转换
                    return DbColumnType.INTEGER;
                }
                return typeRegistry.getColumnType(metaInfo);
            }))
            // 包路径配置
            .packageConfig(builder -> builder
                .parent(PACKAGE_PARENT)
                .pathInfo(Collections.singletonMap(
                    OutputFile.xml,
                    projectPath + File.separator + PROJECT_DIR + PROJECT_NAME + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + MODULE_NAME))
                .entity("persistence." + MODULE_NAME + ".po")
                .mapper("persistence." + MODULE_NAME + ".mapper")
                .service("persistence." + MODULE_NAME + ".facade")
                .serviceImpl("persistence." + MODULE_NAME + ".facade.impl")
                .assembler("persistence." + MODULE_NAME + ".assembler")
            )
            // 构建策略配置
            .strategyConfig(builder -> {
                // 实体类配置
                List<IFill> tableFills = new ArrayList<>();
                tableFills.add(new Column("created", FieldFill.INSERT));
                tableFills.add(new Column("updated", FieldFill.INSERT_UPDATE));
                builder.entityBuilder()
                    .enableLombok()
                    .enableFileOverride()
                    // 标记成 逻辑删除
//                            .logicDeleteColumnName("deleted")
                    .disableSerialVersionUID()
                    .enableChainModel()
//                            .enableColumnConstant()
                    .enableRemoveIsPrefix()
                    .enableTableFieldAnnotation()
                    .versionColumnName("version")
                    .addTableFills(tableFills)
                    .naming(NamingStrategy.underline_to_camel)
                    .columnNaming(NamingStrategy.underline_to_camel)
                    .convertFileName(c -> c.concat("PO"));

                // Mapper配置
                builder.mapperBuilder()
                    .enableBaseResultMap()
                    .enableFileOverride()
                    // mybatis plus join
                    .convertMapperFileName(c -> c.concat("Mapper"));

                // Service 配置
                builder.serviceBuilder()
                    .enableFileOverride()
                    // mybatis plus join
                    .convertServiceFileName(c -> c.concat("ServiceI"))
                    .convertServiceImplFileName(c -> c.concat("ServiceImpl"));

                // Controller 配置
                builder.controllerBuilder()
                    // 禁止生成controller
                    .disable();

                // Assembler配置
                builder.assemblerBuilder()
                    .enableFileOverride();
//                    .disableFileOverride();

                String scanner = scanner("表名，多个英文逗号分割,全部输入 *");
                if (scanner.isEmpty()) {
                    throw new RuntimeException("没有输入表名");
                }

                if (!WILDCARD.equalsIgnoreCase(scanner)) {
                    builder.addInclude(scanner.split(","));
                }
            })
            .execute();
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("请输入" + tip + "：");
            while (true) {
                if (scanner.hasNextLine()) {
                    String ipt = scanner.nextLine().trim();
                    if (!ipt.isEmpty()) {
                        return ipt;
                    } else {
                        System.out.println("输入不能为空，请重新输入" + tip + "：");
                    }
                } else {
                    throw new RuntimeException("无法读取输入，请确保输入有效内容！");
                }
            }
        }
    }
}
