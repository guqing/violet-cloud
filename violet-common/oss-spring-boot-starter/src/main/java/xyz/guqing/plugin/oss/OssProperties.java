package xyz.guqing.plugin.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置信息:
 * <p>配置文件添加：
 * <blockquote><pre>
 *  oss:
 *    enable: true
 *    endpoint: xxx #例如:oss-cn-hangzhou.aliyuncs.com
 *    access-key: xxx
 *    secret-key: xxx
 *    bucket-name: xxx
 * </pre></blockquote>
 * </p>
 *
 * oss环境要求:
 * <p>bucket 设置公共读权限</p>
 *
 * @author guqing
 * @author lengleng
 * @author 858695266
 */
@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

	/**
	 * 对象存储服务的URL
	 */
	private String endpoint;

	/**
	 * 自定义域名
	 */
	private String customDomain;

	/**
	 * true path-style nginx 反向代理和S3默认支持 pathStyle {http://endpoint/bucketname} false
	 * supports virtual-hosted-style 阿里云等需要配置为 virtual-hosted-style
	 * 模式{http://bucketname.endpoint}
	 */
	private Boolean pathStyleAccess = true;

	/**
	 * 应用ID
	 */
	private String appId;

	/**
	 * 区域
	 */
	private String region;

	/**
	 * Access key就像用户ID，可以唯一标识你的账户
	 */
	private String accessKey;

	/**
	 * Secret key是你账户的密码
	 */
	private String secretKey;

	/**
	 * 默认的存储桶名称
	 */
	private String bucketName = "bucket-test";
}
