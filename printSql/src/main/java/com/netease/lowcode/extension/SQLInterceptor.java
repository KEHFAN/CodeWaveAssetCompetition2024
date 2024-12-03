package com.netease.lowcode.extension;

import com.netease.lowcode.core.annotation.NaslLogic;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

@Intercepts({
        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})
})
public class SQLInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object arg0 = invocation.getArgs()[0];
        if(arg0 instanceof MappedStatement) {

            MappedStatement ms = (MappedStatement) arg0;
            Object params = null;
            if(invocation.getArgs().length > 1){
                params = invocation.getArgs()[1];
            }

            BoundSql boundSql = ms.getBoundSql(params);
            System.out.println(boundSql.getSql());

            System.out.println("helllfdlsfsdfsfffffffffffffffffffffffff");
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    @NaslLogic
    public static String test() {
        return "hello";
    }
}