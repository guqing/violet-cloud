package xyz.guqing.violet.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import xyz.guqing.violet.common.core.exception.VioletInternalException;
import xyz.guqing.violet.common.core.model.constant.VioletConstant;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * 根据ip地址获取地理位置
 * @author guqing
 * @date 2020-06-01
 */
@Slf4j
public class RegionAddressUtils {
    public static String getCityInfo(String ip) {
        DbSearcher searcher = null;
        try {
            DbConfig config = new DbConfig();
            Resource resource = getIp2RegionPath();
            searcher = new DbSearcher(config, resource.getURI().getPath());
            Method method = searcher.getClass().getMethod("btreeSearch", String.class);
            DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
            return dataBlock.getRegion();
        } catch (Exception e) {
            log.warn("获取地址信息异常,{}", e.getMessage());
            return StringUtils.EMPTY;
        } finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Resource getIp2RegionPath() {
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource resource = resourcePatternResolver.getResource("classpath:ip2region/ip2region.db");
        if (!resource.exists()) {
            String tmpDir = System.getProperties().getProperty(VioletConstant.JAVA_TEMP_DIR);
            String dbPath = tmpDir + "ip.db";
            File file = new File(dbPath);
            try(InputStream resourceAsStream = resource.getInputStream()) {
                FileUtils.copyInputStreamToFile(resourceAsStream, file);
            } catch (IOException e) {
                throw new VioletInternalException(e.getMessage(), e);
            }
        }
        return resource;
    }
}
