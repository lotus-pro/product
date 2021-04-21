package com.platform.common.config;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.platform.common.util.FilePathUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;

@Service
public class ProductMessageSource implements MessageSource {
    private static final Logger log = LoggerFactory.getLogger(ProductMessageSource.class);
    private static final String I18N_FOLDER = "i18n";
    private static final String[] JRAF_I18N_PACKAGES = new String[]{"com.platform.product.i18n"};
    private static final Map<String, Properties> I18N_MAP = Maps.newConcurrentMap();

    public ProductMessageSource() {
    }
    public void loadAllMessages(String i18nPackages) {
        this.loadPackage(i18nPackages);
        File[] folderPropertiesFiles = this.getFolderPropertiesFiles();
        this.readProperties(folderPropertiesFiles);
    }

    private void readProperties(File[] files) {
        if (!ArrayUtils.isEmpty(files)) {
            File[] var2 = files;
            int var3 = files.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                File file = var2[var4];
                String fileLocaleString = this.getFileLocaleName(file.getName());
                if (null != fileLocaleString) {
                    Properties properties = this.propertiesFileToMap(file);
                    this.putPropertiesToI18nMap(properties, fileLocaleString);
                }
            }

        }
    }

    private void loadPackage(String i18nPackages) {
        Set<String> i18nPackageList = this.readI18nPackages(i18nPackages);
        log.info("MessageSource basedir: {}", String.join(",", i18nPackageList));
        Set<Locale> localeSet = LocaleUtils.availableLocaleSet();
        Iterator var4 = i18nPackageList.iterator();

        while(var4.hasNext()) {
            String i18nPackage = (String)var4.next();
            String i18nPath = this.packageToPath(i18nPackage);
            Iterator var7 = localeSet.iterator();

            while(var7.hasNext()) {
                Locale locale = (Locale)var7.next();
                String localeString = locale.toString();
                if (!StringUtils.isBlank(localeString)) {
                    String filePath = i18nPath.concat("/messages_").concat(locale.toString()).concat(".properties");
                    ClassPathResource classPathResource = new ClassPathResource(filePath);
                    if (classPathResource.exists()) {
                        Properties properties = this.propertiesFileToMap(classPathResource);
                        this.putPropertiesToI18nMap(properties, localeString);
                    }
                }
            }
        }

    }


    private void putPropertiesToI18nMap(Properties properties, String localeString) {
        if (null != properties) {
            Properties i18nProperties = (Properties)I18N_MAP.get(localeString);
            if (null == i18nProperties) {
                I18N_MAP.put(localeString, properties);
            } else {
                i18nProperties.putAll(properties);
            }

        }
    }

    private String getFileLocaleName(String filename) {
        String fileBasename = FilenameUtils.getBaseName(filename);
        String extension = FilenameUtils.getExtension(filename);
        String filePre = "messages";
        String fileSuf = "properties";
        if (fileBasename.startsWith(filePre) && fileSuf.equals(extension)) {
            Set<Locale> localeSet = LocaleUtils.availableLocaleSet();
            Iterator var7 = localeSet.iterator();

            Locale locale;
            do {
                if (!var7.hasNext()) {
                    return null;
                }

                locale = (Locale)var7.next();
            } while(StringUtils.isEmpty(locale.toString()) || !fileBasename.endsWith(locale.toString()));

            return locale.toString();
        } else {
            return null;
        }
    }

    private Set<String> readI18nPackages(String i18nPackages) {
        Set<String> i18nPackageSet = Sets.newLinkedHashSet();
        CollectionUtils.addAll(i18nPackageSet, JRAF_I18N_PACKAGES);
        if (StringUtils.isBlank(i18nPackages)) {
            return i18nPackageSet;
        } else {
            String[] i18nPackageArray = i18nPackages.split(",");
            String[] var4 = i18nPackageArray;
            int var5 = i18nPackageArray.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String i18nPackage = var4[var6];
                if (StringUtils.isNotBlank(i18nPackage)) {
                    i18nPackageSet.add(i18nPackage);
                }
            }

            return i18nPackageSet;
        }
    }

    private String packageToPath(String i18nPackage) {
        return i18nPackage.replace('.', '/');
    }

    private Properties propertiesFileToMap(File file) {
        try {
            FileInputStream fileInputStream = FileUtils.openInputStream(file);
            Throwable var3 = null;

            Properties var4;
            try {
                var4 = this.propertiesFileToMap((InputStream)fileInputStream);
            } catch (Throwable var14) {
                var3 = var14;
                throw var14;
            } finally {
                if (fileInputStream != null) {
                    if (var3 != null) {
                        try {
                            fileInputStream.close();
                        } catch (Throwable var13) {
                            var3.addSuppressed(var13);
                        }
                    } else {
                        fileInputStream.close();
                    }
                }

            }

            return var4;
        } catch (IOException var16) {
            log.error("从file中读取国际化资源异常", var16);
            return null;
        }
    }

    private File[] getFolderPropertiesFiles() {
        File i18nFolder = this.getI18nFolder();
        return null != i18nFolder && i18nFolder.exists() && i18nFolder.isDirectory() ? i18nFolder.listFiles() : new File[0];
    }

    private Properties propertiesFileToMap(ClassPathResource classPathResource) {
        try {
            InputStream inputStream = classPathResource.getInputStream();
            Throwable var3 = null;

            Properties var4;
            try {
                var4 = this.propertiesFileToMap(inputStream);
            } catch (Throwable var14) {
                var3 = var14;
                throw var14;
            } finally {
                if (inputStream != null) {
                    if (var3 != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable var13) {
                            var3.addSuppressed(var13);
                        }
                    } else {
                        inputStream.close();
                    }
                }

            }

            return var4;
        } catch (IOException var16) {
            log.error("从classPathResource中读取国际化资源异常", var16);
            return null;
        }
    }

    private Properties propertiesFileToMap(InputStream inputStream) {
        try {
            InputStreamReader bundleReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            Throwable var3 = null;

            Properties var5;
            try {
                Properties properties = new Properties();
                properties.load(bundleReader);
                var5 = properties;
            } catch (Throwable var15) {
                var3 = var15;
                throw var15;
            } finally {
                if (bundleReader != null) {
                    if (var3 != null) {
                        try {
                            bundleReader.close();
                        } catch (Throwable var14) {
                            var3.addSuppressed(var14);
                        }
                    } else {
                        bundleReader.close();
                    }
                }

            }

            return var5;
        } catch (IOException var17) {
            log.error("从文件输入流中读取国际化资源异常", var17);
            return null;
        }
    }

    private File getI18nFolder() {
        File i18nFolder = null;
        String basedir = FilePathUtil.getBasedir();
        if (StringUtils.isEmpty(basedir)) {
            try {
                basedir = "classpath:".concat("i18n");
                i18nFolder = ResourceUtils.getFile(basedir);
            } catch (FileNotFoundException var4) {
                log.info("i18n folder not exists");
                return null;
            }
        } else {
            basedir = basedir.concat("/").concat("i18n");
            i18nFolder = new File(basedir);
        }

        log.info("MessageSource basedir: {}", basedir);
        return i18nFolder;
    }

    @Override
    public String getMessage(@Nonnull String code, @Nonnull Object[] args, @Nonnull String defaultMessage, @Nonnull Locale locale) {
        String messageValue = MapUtils.getString((Map)I18N_MAP.get(locale.toString()), code);
        if (StringUtils.isBlank(messageValue)) {
            return StringUtils.isBlank(defaultMessage) ? code : defaultMessage;
        } else {
            if (ArrayUtils.isNotEmpty(args)) {
                messageValue = MessageFormat.format(messageValue, args);
            }

            return messageValue;
        }
    }

    @Override
    public String  getMessage(@Nonnull String code, @Nonnull Object[] args, @Nonnull Locale locale) throws NoSuchMessageException {
        return this.getMessage(code, args, (String)null, locale);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, @Nonnull Locale locale) throws NoSuchMessageException {
        String[] codes = resolvable.getCodes();
        if (codes != null) {
            String[] var4 = codes;
            int var5 = codes.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String code = var4[var6];
                String message = this.getMessage(code, resolvable.getArguments(), locale);
                if (message != null) {
                    return message;
                }
            }
        }

        String defaultMessage = this.getMessage(resolvable, locale);
        if (defaultMessage != null) {
            return defaultMessage;
        } else {
            throw new NoSuchMessageException(!ObjectUtils.isEmpty(codes) ? codes[codes.length - 1] : "", locale);
        }
    }
}
