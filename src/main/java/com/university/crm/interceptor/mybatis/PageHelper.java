package com.university.crm.interceptor.mybatis;

import com.university.crm.bean.base.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Properties;

/**
 * date : 2018/8/11
 * description :通用分页拦截器
 *
 * @author : zhencai.cheng
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class PageHelper implements Interceptor {
    private static final ThreadLocal<PageList> LOCAL_PAGE = new ThreadLocal<>();
    //sql工具类
    private SqlUtil SQLUTIL;
    //RowBounds参数offset作为PageNum使用 - 默认不使用
    private boolean offsetAsPageNum = false;
    //RowBounds是否进行count查询 - 默认不查询
    private boolean rowBoundsWithCount = false;
    //当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页
    private boolean pageSizeZero = false;
    //分页合理化
    private boolean reasonable = false;
    //默认不排序
    private boolean orderBy = false;

    /**
     * 开始分页
     *
     * @param page 页码\码数\排序字段\排序方式
     */
    public static void startPage(Page page) {
        if (StringUtils.isBlank(page.getSort())
                || StringUtils.isBlank(page.getSort())) {
            startPage(page, true, false);
        } else {
            startPage(page, true, true);
        }
    }

    /**
     * 开始分页
     *
     * @param page  页码
     * @param count 是否进行count查询
     */
    public static void startPage(Page page, boolean count, boolean orderBy) {
        LOCAL_PAGE.set(new PageList(page, count, orderBy));
    }

    /**
     * 获取分页参数
     *
     * @param rowBounds RowBounds参数
     * @return 返回Page对象
     */
    private PageList getPage(RowBounds rowBounds) {
        PageList pageList = LOCAL_PAGE.get();
        //移除本地变量
        LOCAL_PAGE.remove();
        if (pageList == null) {
            if (offsetAsPageNum) {
                pageList = new PageList(rowBounds.getOffset(), rowBounds.getLimit(), rowBoundsWithCount);
            } else {
                pageList = new PageList(rowBounds, rowBoundsWithCount);
            }
        }
        //分页合理化
        pageList.setReasonable(reasonable);
        return pageList;
    }

    /**
     * Mybatis拦截器方法
     *
     * @param invocation 拦截器入参
     * @return 返回执行结果
     * @throws Throwable 抛出异常
     */
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        RowBounds rowBounds = (RowBounds) args[2];
        if (LOCAL_PAGE.get() == null && rowBounds == RowBounds.DEFAULT) {
            return invocation.proceed();
        } else {
            //获取原始的ms
            MappedStatement ms = (MappedStatement) args[0];
            //忽略RowBounds-否则会进行Mybatis自带的内存分页
            args[2] = RowBounds.DEFAULT;
            //分页信息
            PageList pageList = getPage(rowBounds);
            //pageSizeZero的判断
            if (pageSizeZero && pageList.getPageSize() == 0) {
                //执行正常（不分页）查询
                Object result = invocation.proceed();
                //得到处理结果
                pageList.addAll((List) result);
                //相当于查询第一页
                pageList.setPageNum(1);
                //这种情况相当于pageSize=total
                pageList.setPageSize(pageList.size());
                //仍然要设置total
                pageList.setTotal(pageList.size());
                //返回结果仍然为Page类型 - 便于后面对接收类型的统一处理
                return pageList;
            }
            SqlSource sqlSource = ((MappedStatement) args[0]).getSqlSource();
            //简单的通过total的值来判断是否进行count查询
            if (pageList.isCount()) {
                //将参数中的MappedStatement替换为新的qs
                SQLUTIL.processCountMappedStatement(ms, sqlSource, args);
                //查询总数
                Object result = invocation.proceed();
                //设置总数
                pageList.setTotal((Integer) ((List) result).get(0));
                if (pageList.getTotal() == 0) {
                    return pageList;
                }
            }
            //pageSize>0的时候执行分页查询，pageSize<=0的时候不执行相当于可能只返回了一个count
            if (pageList.getPageSize() > 0 &&
                    ((rowBounds == RowBounds.DEFAULT && pageList.getPageNum() > 0)
                            || rowBounds != RowBounds.DEFAULT)) {
                if (pageList.isSort()) {
                    SQLUTIL.processOrderPageMappedStatement(ms, sqlSource, pageList, args);
                } else {
                    //将参数中的MappedStatement替换为新的qs
                    SQLUTIL.processPageMappedStatement(ms, sqlSource, pageList, args);
                }
                //执行分页查询
                Object result = invocation.proceed();
                //得到处理结果
                pageList.addAll((List) result);
            }
            //返回结果
            return pageList;
        }
    }

    /**
     * 只拦截Executor
     *
     * @param target
     * @return
     */
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    /**
     * 设置属性值
     *
     * @param p 属性值
     */
    public void setProperties(Properties p) {
        //数据库方言
        String dialect = p.getProperty("dialect");
        SQLUTIL = new SqlUtil(dialect);
        //offset作为PageNum使用
        String offsetAsPageNum = p.getProperty("offsetAsPageNum");
        this.offsetAsPageNum = Boolean.parseBoolean(offsetAsPageNum);
        //RowBounds方式是否做count查询
        String rowBoundsWithCount = p.getProperty("rowBoundsWithCount");
        this.rowBoundsWithCount = Boolean.parseBoolean(rowBoundsWithCount);
        //当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页
        String pageSizeZero = p.getProperty("pageSizeZero");
        this.pageSizeZero = Boolean.parseBoolean(pageSizeZero);
        //分页合理化，true开启，如果分页参数不合理会自动修正。默认false不启用
        String reasonable = p.getProperty("reasonable");
        this.reasonable = Boolean.parseBoolean(reasonable);
    }
}

