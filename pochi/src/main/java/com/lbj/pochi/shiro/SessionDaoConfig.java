package com.lbj.pochi.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 重写读取存取sessionId
 */
@Component
public class SessionDaoConfig extends EnterpriseCacheSessionDAO {
    @Resource
    private RedisTemplate<Serializable, Session> redisTemplate;

    /**
     * 创建sessionId
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        //获取sessionId
        Serializable sessionId=this.generateSessionId(session);
        //session和sessionId绑定
        SimpleSession simpleSession=(SimpleSession) session;
        simpleSession.setId(sessionId);
        return sessionId;
    }

    /**
     * //从redis中获取sessionId
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        return redisTemplate.boundValueOps(sessionId).get();
    }

    /**
     * 更新sessionId
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {
        if (session instanceof ValidatingSession){
            //判断当前session是否是simpleSession
            ValidatingSession validatingSession= (ValidatingSession) session;
            if (validatingSession.isValid()){
                redisTemplate.boundValueOps(session.getId()).set(session);
            }else {
                //校验失败,说明未登录或者登录失效
                redisTemplate.delete(session.getId());
            }
        }else{
            redisTemplate.boundValueOps(session.getId()).set(session);
        }

    }

    /**
     * 从redis删除sessionId
     * @param session
     */
    @Override
    protected void doDelete(Session session) {
        redisTemplate.delete(session.getId());
    }
}
