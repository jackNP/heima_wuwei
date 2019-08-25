package com.itheima.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 *  数据库连接池工具类
 *  目的: 返回数据库连接对象Jedis
 *  读取config配置文件
 */
public class JedisUtils {
    //声明出连接池对象
    private static JedisPool pool ;
    //读取配置文件
    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        //获取服务器IP地址
        String host = resourceBundle.getString("host");
        //获取端口号
        int port = Integer.parseInt(resourceBundle.getString("port"));
        //获取最大连接数
        int maxTotal = Integer.parseInt( resourceBundle.getString("maxTotal"));
        //获取最大空闲连接
        int maxIdle = Integer.parseInt( resourceBundle.getString("maxIdle"));
        //创建连接池的配置信息对象
        JedisPoolConfig config = new JedisPoolConfig();
        //设置相关的信息
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        //创建连接池对象
        pool = new JedisPool(config,host,port);
    }
    /**
     * 创建方法
     * 连接池对象,返回Jedis对象
     */
    public static Jedis getJedis(){
        return pool.getResource();
    }
    //释放资源方法
    public static void close(Jedis jedis){
        if(jedis!=null)
            jedis.close();
    }

    public static void close(JedisPool pool){
        if(pool!=null)
            pool.close();
    }
}
