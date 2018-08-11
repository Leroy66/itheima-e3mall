package e3.mall.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	/**
	 * 浪费资源
	 *//*
	@Test
	public void testJedis() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.set("set1", "set1set1set1");
		String s = jedis.get("set1");
		System.out.println(s);
		jedis.close();
	}*/

	/*@Test
	public void testJedisPool() {
		JedisPool pool = new JedisPool("127.0.0.1", 6379);
		Jedis jedis = pool.getResource();
		String s = jedis.get("set1");
		System.out.println(s + "-----------");
		jedis.close();
		pool.close();
	}*/

	/**
	 * 集群测试---需要先配置好集群才行，下面端口未开放，不能运行
	 */
/*	@Test
	public void testJedisCluster() {
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("127.0.0.1", 7001));
		nodes.add(new HostAndPort("127.0.0.1", 7002));
		nodes.add(new HostAndPort("127.0.0.1", 7003));
		nodes.add(new HostAndPort("127.0.0.1", 7004));
		JedisCluster jc = new JedisCluster(nodes);
		jc.set("jdc1", "???????");
		String s = jc.get("jdc1");
		System.out.println(s);
		jc.close();
	}
*/
}
