package xyz.guqing.violet.gateway.enhance.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.util.ResourceUtils;
import xyz.guqing.violet.common.core.exception.FileOperationException;
import xyz.guqing.violet.common.core.model.constant.VioletConstant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 根据 IP获取地址
 *
 * @author guqing
 */
@Slf4j
public class RegionAddressUtils {
    private static final String RESOURCE_PATH = "ip2region/ip2region.db";

    public static String getCityInfo(String ip) {
        DbSearcher searcher = null;
        try {
            String filePath = getRegionFilePath();
            DbConfig config = new DbConfig();
            searcher = new DbSearcher(config, filePath);
            Method method = searcher.getClass().getMethod("btreeSearch", String.class);
            DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
            return dataBlock.getRegion();
        } catch (Exception e) {
            log.error("获取ip地址信息异常,{}, 异常堆栈: {}", e.getMessage(), e);
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

    private static String getRegionFilePath() throws IOException, URISyntaxException {
        URI dbUrl = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX + RESOURCE_PATH).toURI();
        if ("jar".equalsIgnoreCase(dbUrl.getScheme())) {
            return getJarFilePath();
        }

        return dbUrl.getPath();
    }

    private static String getJarFilePath() {
        String tmpDir = System.getProperties().getProperty(VioletConstant.JAVA_TEMP_DIR);
        String tempFilePath = tmpDir + "/ip2region.db";

        try(InputStream resourceAsStream = RegionAddressUtils.class.getClassLoader()
                .getResourceAsStream(RESOURCE_PATH)) {
            File file = new File(tempFilePath);
            if(resourceAsStream == null) {
                throw new FileNotFoundException("ip2region文件不存在，无法解析ip地址");
            }

            // 将文件拷贝到临时目录下并返回路径
            FileUtils.copyInputStreamToFile(resourceAsStream, file);
            return tempFilePath;
        }catch (IOException e) {
            throw new FileOperationException("无法解析文件", e);
        }
    }
}