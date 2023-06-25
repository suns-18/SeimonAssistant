package yu.seimonassistant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yu.seimonassistant.controller.HealthController;
import yu.seimonassistant.entity.Health;
import yu.seimonassistant.util.UUIDUtil;

import java.util.logging.Logger;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public
class HealthTest {
	@Autowired
	HealthController c;
	Logger log = Logger.getLogger(String.valueOf(HealthTest.class));
	@Test
	void query() {

		Assertions.assertNotNull(c.queryList().getData(),
			"返回列表出错");
		log.info("query功能>>测试1：查询所有健康信息，返回所有，通过");
		var h = new Health();
		h.setId(UUIDUtil.getOneUUID());
		Assertions.assertNull(c.queryById(h).getData(),
			"查找一个不存在的健康信息记录");
		log.info("query功能>>测试2：查询不存在的单一健康信息，返回为空，通过");
		h.setId("0127d8a43700dfa784ef5ba41bd35500");
		Assertions.assertNotNull(c.queryById(h).getData(),
			"查找一个存在的健康信息记录");
		log.info("query功能>>测试2：查询存在的单一健康信息，返回正常，通过");
		h.setId(null);
		c.queryById(h);
		Assertions.assertNull(c.queryById(h).getData(),
			"查找健康信息记录参数错误");
		log.info("query功能>>测试3：查询id为null的单一健康信息，返回为空，通过");
		log.info("query功能>>单个请求测试，通过");
	}

	@Test
	void add() {
		Assertions.assertEquals(c.add(null).getCode(), 0);


		var h = new Health();
		h.setTitle("aaa");
		Assertions.assertEquals(c.add(h).getCode(), 1);
	}

	@Test
	void update() {
		Assertions.assertEquals(c.modify(null).getData(), 0);

		var h = new Health();
		h.setId("0127d8a43700dfa784ef5ba41bd35500");
		h.setTitle("bbb");
		Assertions.assertEquals(c.modify(h).getCode(), 1);

		h.setId("114514");
		h.setTitle("bbb");
		Assertions.assertEquals(c.modify(h).getCode(), 0);
	}


}
