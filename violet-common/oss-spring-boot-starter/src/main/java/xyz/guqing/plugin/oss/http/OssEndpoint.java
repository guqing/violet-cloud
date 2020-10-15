package xyz.guqing.plugin.oss.http;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.dreamlu.mica.auto.annotation.AutoIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.guqing.plugin.oss.service.OssTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * aws 对外提供服务端点
 * <p>
 * 如果需要使用该EndPoint提供的接口则需要配置
 * <blockquote><pre>
 * oss.info=true
 * </pre></blockquote>
 * 需要自定义则注入OssTemplate使用</p>
 *
 * @author guqing
 * @author lengleng
 * @author 858695266
 */
@AutoIgnore
@RestController
@AllArgsConstructor
@RequestMapping("/oss")
public class OssEndpoint {

	private final OssTemplate template;

	/**
	 * Bucket Endpoints
	 */
	@SneakyThrows
	@PostMapping("/bucket/{bucketName}")
	public Bucket createBucket(@PathVariable String bucketName) {
		template.createBucket(bucketName);
		Optional<Bucket> bucketOptional = template.getBucket(bucketName);
		return bucketOptional.orElse(null);
	}

	@SneakyThrows
	@GetMapping("/bucket")
	public List<Bucket> getBuckets() {
		return template.getAllBuckets();
	}

	@SneakyThrows
	@GetMapping("/bucket/{bucketName}")
	public Bucket getBucket(@PathVariable String bucketName) {
		return template.getBucket(bucketName).orElseThrow(() -> new IllegalArgumentException("Bucket Name not found!"));
	}

	@SneakyThrows
	@DeleteMapping("/bucket/{bucketName}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void deleteBucket(@PathVariable String bucketName) {
		template.removeBucket(bucketName);
	}

	/**
	 * Object Endpoints
	 */
	@SneakyThrows
	@PostMapping("/object/{bucketName}")
	public S3Object createObject(@RequestBody MultipartFile object, @PathVariable String bucketName) {
		String name = object.getOriginalFilename();
		template.putObject(bucketName, name, object.getInputStream(), object.getSize(), object.getContentType());
		return template.getObjectInfo(bucketName, name);
	}

	@SneakyThrows
	@PostMapping("/object/{bucketName}/{objectName}")
	public S3Object createObject(@RequestBody MultipartFile object, @PathVariable String bucketName,
                                 @PathVariable String objectName) {
		template.putObject(bucketName, objectName, object.getInputStream(), object.getSize(), object.getContentType());
		return template.getObjectInfo(bucketName, objectName);
	}

	@SneakyThrows
	@GetMapping("/object/{bucketName}/{objectName}")
	public List<S3ObjectSummary> filterObject(@PathVariable String bucketName, @PathVariable String objectName) {
		return template.getAllObjectsByPrefix(bucketName, objectName, true);
	}

	@SneakyThrows
	@GetMapping("/object/{bucketName}/{objectName}/{expires}")
	public Map<String, Object> getObject(@PathVariable String bucketName, @PathVariable String objectName,
                                         @PathVariable Integer expires) {
		Map<String, Object> responseBody = new HashMap<>(4, 1);
		// Put Object info
		responseBody.put("bucket", bucketName);
		responseBody.put("object", objectName);
		responseBody.put("url", template.getObjectUrl(bucketName, objectName, expires));
		responseBody.put("expires", expires);
		return responseBody;
	}

	@SneakyThrows
	@ResponseStatus(HttpStatus.ACCEPTED)
	@DeleteMapping("/object/{bucketName}/{objectName}/")
	public void deleteObject(@PathVariable String bucketName, @PathVariable String objectName) {

		template.removeObject(bucketName, objectName);
	}

}
