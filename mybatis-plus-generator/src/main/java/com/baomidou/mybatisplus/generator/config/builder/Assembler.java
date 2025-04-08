package com.baomidou.mybatisplus.generator.config.builder;

import com.baomidou.mybatisplus.generator.ITemplate;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.function.ConverterFileName;
import com.baomidou.mybatisplus.generator.util.ClassUtils;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 毛宇鹏
 * @version 1.0
 * @date 2025/4/7 20:00
 */
public class Assembler implements ITemplate {
    /**
     * 是否覆盖已有文件（默认 false）
     *
     * @since 3.5.2
     */
    @Getter
    private boolean fileOverride;

    /**
      * 自定义继承的Controller类全称，带包名
     */
    private String superClass;

    /**
     * 转换输出控制器文件名称
     *
     * @since 3.5.0
     */
    private ConverterFileName converterFileName = (entityName -> entityName + ConstVal.ASSEMBLER);

    @NotNull
    public ConverterFileName getConverterFileName() {
        return converterFileName;
    }

    /**
     * 是否生成
     *
     * @since 3.5.6
     */
    @Getter
    private boolean generate = true;

    /**
     * 模板路径
     * @since 3.5.6
     */
    @Getter
    private String templatePath = ConstVal.TEMPLATE_ASSEMBLER;



    @Override
    public @NotNull Map<String, Object> renderData(@NotNull TableInfo tableInfo) {
        Map<String, Object> data = new HashMap<>();
        data.put("superControllerClass", ClassUtils.getSimpleName(this.superClass));
        return data;
    }

    public static class Builder extends BaseBuilder {
        private final Assembler assembler = new Assembler();

        public Builder(@NotNull StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        /**
         * 指定模板路径
         *
         * @param template 模板路径
         * @return this
         * @since 3.5.6
         */
        public Builder template(@NotNull String template) {
            this.assembler.templatePath = template;
            return this;
        }

        /**
         * 转换输出文件名称
         *
         * @param converter 　转换处理
         * @return this
         * @since 3.5.0
         */
        public Builder convertFileName(@NotNull ConverterFileName converter) {
            this.assembler.converterFileName = converter;
            return this;
        }

        /**
         * 覆盖已有文件
         *
         * @since 3.5.3
         */
        public Builder enableFileOverride() {
            this.assembler.fileOverride = true;
            return this;
        }

        /**
         * 不覆盖已有文件
         *
         * @since 3.5.3
         */
        public Builder disableFileOverride() {
            this.assembler.fileOverride = false;
            return this;
        }

        /**
         * 禁用生成
         *
         * @return this
         * @since 3.5.6
         */
        public Builder disable() {
            this.assembler.generate = false;
            return this;
        }

        @NotNull
        public Assembler get() {
            return this.assembler;
        }
    }


}
