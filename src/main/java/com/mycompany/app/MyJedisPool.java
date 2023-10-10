package com.mycompany.app;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Connection;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class MyJedisPool
{
    public static void main( String[] args )
    {
        try {
            String host = "clustercfg.tls.rvgfdn.apse1.cache.amazonaws.com";
            int port = 6379;
            int connectionTimeout = 10000;
            int soTimeout = 10000;//读写超时时间
            int maxAttempts = 20;//最大重试次数
            String password = "0ShjfSP864tRYR242CpU";
            
            Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
            jedisClusterNodes.add(new HostAndPort(host, port));

            GenericObjectPoolConfig<Connection> jedisPoolConfig = new GenericObjectPoolConfig<Connection>();
            jedisPoolConfig.setMaxTotal(100);
            jedisPoolConfig.setMaxIdle(10);
            jedisPoolConfig.setMinIdle(10);
            jedisPoolConfig.setMaxWait(Duration.ofMillis(5000));
            
            JedisCluster jedisCluster = new JedisCluster(
                jedisClusterNodes, 
                connectionTimeout, 
                soTimeout,
                maxAttempts,
                password,
                "mypoc",
                jedisPoolConfig,
                true
            );
            jedisCluster.set("ycj1010", "my-poc-value-03");
            System.out.println(jedisCluster.exists("ycj1010"));
            System.out.println(jedisCluster.get("ycj1010"));
            jedisCluster.close();
            
        }catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
