package com.university.crm.interceptor.mybatis;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * date : 2018/8/11
 * description :sql工具，获取分页和count的MappedStatement，设置分页参数
 *
 * @author : zhencai.cheng
 */

@SuppressWarnings({"rawtypes", "unchecked"})
public class SqlUtil {
    private static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList<>(0);
    //分页的id后缀
    private static final String SUFFIX_PAGE = "_PageHelper";
    //count查询的id后缀
    private static final String SUFFIX_COUNT = SUFFIX_PAGE + "_Count";
    //第一个分页参数
    private static final String PAGEPARAMETER_FIRST = "First" + SUFFIX_PAGE;
    //第二个分页参数
    private static final String PAGEPARAMETER_SECOND = "Second" + SUFFIX_PAGE;
    //排序后缀
    private static final String SUFFIX_ORDER = "_Order";
    //排序字段
    private static final String SORT_PARAMETER = "Sort" + SUFFIX_ORDER;
    //排序方式
    private static final String ORDER_PARAMETER = "Order" + SUFFIX_ORDER;

    private static final String PROVIDER_OBJECT = "_provider_object";
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

    /**
     * 反射对象，增加对低版本Mybatis的支持
     *
     * @param object 反射对象
     * @return
     */
    private static MetaObject forObject(Object object) {
        return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
    }

    private Parser sqlParser;

    //数据库方言 - 使用枚举限制数据库类型
    public enum Dialect {
        mysql, mariadb, sqlite, oracle, hsqldb, postgresql
    }

    /**
     * 构造方法
     */
    public SqlUtil(String strDialect) {
        if (strDialect == null || "".equals(strDialect)) {
            throw new IllegalArgumentException("Mybatis分页插件无法获取dialect参数!");
        }
        try {
            Dialect dialect = Dialect.valueOf(strDialect);
            String sqlParserClass = this.getClass().getPackage().getName() + ".SqlParser";
            try {
                //使用SqlParser必须引入jsqlparser-x.x.x.jar
                Class.forName("net.sf.jsqlparser.statement.select.Select");
                sqlParser = (Parser) Class.forName(sqlParserClass).getConstructor(Dialect.class).newInstance(dialect);
            } catch (Exception e) {
                //找不到时，不用处理
            }
            if (sqlParser == null) {
                sqlParser = SimpleParser.newParser(dialect);
            }
        } catch (IllegalArgumentException e) {
            String dialects = null;
            for (Dialect d : Dialect.values()) {
                if (dialects == null) {
                    dialects = d.toString();
                } else {
                    dialects += "," + d;
                }
            }
            throw new IllegalArgumentException("Mybatis分页插件dialect参数值错误，可选值为[" + dialects + "]");
        }
    }

    /**
     * 设置分页参数
     *
     * @param parameterObject
     * @param pageList
     * @return
     */
    public Map setPageParameter(MappedStatement ms, Object parameterObject, PageList pageList) {
        BoundSql boundSql = ms.getBoundSql(parameterObject);
        return sqlParser.setPageParameter(ms, parameterObject, boundSql, pageList);
    }


    /**
     * 处理count查询的MappedStatement
     *
     * @param ms
     * @param sqlSource
     * @param args
     */
    public void processCountMappedStatement(MappedStatement ms, SqlSource sqlSource, Object[] args) {
        args[0] = getMappedStatement(ms, sqlSource, args[1], SUFFIX_COUNT, null);
    }

    /**
     * 处理分页查询的MappedStatement
     *
     * @param ms
     * @param sqlSource
     * @param pageList
     * @param args
     */
    public void processPageMappedStatement(MappedStatement ms, SqlSource sqlSource, PageList pageList, Object[] args) {
        args[0] = getMappedStatement(ms, sqlSource, args[1], SUFFIX_PAGE, null);
//处理入参
        args[1] = setPageParameter((MappedStatement) args[0], args[1], pageList);
    }

    public void processOrderPageMappedStatement(MappedStatement ms, SqlSource sqlSource, PageList pageList, Object[] args) {
        args[0] = getMappedStatement(ms, sqlSource, args[1], SUFFIX_ORDER, pageList);

        args[1] = setPageParameter((MappedStatement) args[0], args[1], pageList);
    }

    /**
     * 处理SQL
     */
    public static interface Parser {
        void isSupportedSql(String sql);

        String getCountSql(String sql);

        String getPageSql(String sql);

        String getOrderPagerSql(String sql, PageList pageList);

        Map setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql, PageList pageList);
    }

    public static abstract class SimpleParser implements Parser {
        public static Parser newParser(Dialect dialect) {
            Parser parser = null;
            switch (dialect) {
                case mysql:
                case mariadb:
                case sqlite:
                    parser = new MysqlParser();
                    break;
                case oracle:
                    parser = new OracleParser();
                    break;
                case hsqldb:
                    parser = new HsqldbParser();
                    break;
                case postgresql:
                default:
                    parser = new PostgreSQLParser();
            }
            return parser;
        }

        public void isSupportedSql(String sql) {
            if (sql.trim().toUpperCase().endsWith("FOR UPDATE")) {
                throw new RuntimeException("分页插件不支持包含for update的sql");
            }
        }


        @Override
        public String getOrderPagerSql(String sql, PageList pageList) {
            StringBuilder sqlBuilder = new StringBuilder(sql.length() + 16);
            sqlBuilder.append(sql);
            sqlBuilder.append(" order by ");
            sqlBuilder.append(pageList.getSortField());
            sqlBuilder.append(" ");
            sqlBuilder.append(pageList.getOrderBy());
            return this.getPageSql(sqlBuilder.toString());
        }

        /**
         * 获取总数sql - 如果要支持其他数据库，修改这里就可以
         *
         * @param sql 原查询sql
         * @return 返回count查询sql
         */
        public String getCountSql(final String sql) {
            isSupportedSql(sql);
            StringBuilder stringBuilder = new StringBuilder(sql.length() + 40);
            stringBuilder.append("select count(*) from (");
            stringBuilder.append(sql);
            stringBuilder.append(") tmp_count");
            return stringBuilder.toString();
        }

        /**
         * 获取分页sql - 如果要支持其他数据库，修改这里就可以
         *
         * @param sql 原查询sql
         * @return 返回分页sql
         */
        public abstract String getPageSql(String sql);

        public Map setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql, PageList pageList) {
            Map paramMap = null;
            if (parameterObject == null) {
                paramMap = new HashMap();
            } else if (parameterObject instanceof Map) {
                paramMap = (Map) parameterObject;
            } else {
                paramMap = new HashMap();
//动态sql时的判断条件不会出现在ParameterMapping中，但是必须有，所以这里需要收集所有的getter属性
//TypeHandlerRegistry可以直接处理的会作为一个直接使用的对象进行处理
                boolean hasTypeHandler = ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass());
                MetaObject metaObject = forObject(parameterObject);
//需要针对注解形式的MyProviderSqlSource保存原值
                if (ms.getSqlSource() instanceof MyProviderSqlSource) {
                    paramMap.put(PROVIDER_OBJECT, parameterObject);
                }
                if (!hasTypeHandler) {
                    for (String name : metaObject.getGetterNames()) {
                        paramMap.put(name, metaObject.getValue(name));
                    }
                }
//下面这段方法，主要解决一个常见类型的参数时的问题
                if (boundSql.getParameterMappings() != null && boundSql.getParameterMappings().size() > 0) {
                    for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
                        String name = parameterMapping.getProperty();
                        if (!name.equals(PAGEPARAMETER_FIRST)
                                && !name.equals(PAGEPARAMETER_SECOND)
                                && !name.equals(SORT_PARAMETER)
                                && !name.equals(ORDER_PARAMETER)
                                && paramMap.get(name) == null) {
                            if (hasTypeHandler
                                    || parameterMapping.getJavaType().equals(parameterObject.getClass())) {
                                paramMap.put(name, parameterObject);
                            } else {
                                paramMap.put(name, metaObject.getValue(name));
                            }
                        }
                    }
                }
            }
            return paramMap;
        }
    }

    //Mysql
    private static class MysqlParser extends SimpleParser {
        @Override
        public String getPageSql(String sql) {
            return sql + " limit ?,?";
        }


        @Override
        public Map setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql, PageList pageList) {
            Map paramMap = super.setPageParameter(ms, parameterObject, boundSql, pageList);
            paramMap.put(PAGEPARAMETER_FIRST, pageList.getStartRow());
            paramMap.put(PAGEPARAMETER_SECOND, pageList.getPageSize());
            return paramMap;
        }


    }

    //Oracle
    private static class OracleParser extends SimpleParser {
        @Override
        public String getPageSql(String sql) {
            StringBuilder sqlBuilder = new StringBuilder(sql.length() + 120);
            sqlBuilder.append("select * from ( select tmp_page.*, rownum row_id from ( ");
            sqlBuilder.append(sql);
            sqlBuilder.append(" ) tmp_page where rownum <= ? ) where row_id > ?");
            return sqlBuilder.toString();
        }

        @Override
        public Map setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql, PageList pageList) {
            Map paramMap = super.setPageParameter(ms, parameterObject, boundSql, pageList);
            paramMap.put(PAGEPARAMETER_FIRST, pageList.getEndRow());
            paramMap.put(PAGEPARAMETER_SECOND, pageList.getStartRow());
            return paramMap;
        }


    }

    //Hsqldb
    private static class HsqldbParser extends SimpleParser {
        @Override
        public String getPageSql(String sql) {
            return sql + " limit ? offset ?";
        }

        @Override
        public Map setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql, PageList pageList) {
            Map paramMap = super.setPageParameter(ms, parameterObject, boundSql, pageList);
            paramMap.put(PAGEPARAMETER_FIRST, pageList.getPageSize());
            paramMap.put(PAGEPARAMETER_SECOND, pageList.getStartRow());
            return paramMap;
        }


    }

    //PostgreSQL
    private static class PostgreSQLParser extends SimpleParser {
        @Override
        public String getPageSql(String sql) {
            return sql + " limit ? offset ?";
        }

        @Override
        public Map setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql, PageList pageList) {
            Map paramMap = super.setPageParameter(ms, parameterObject, boundSql, pageList);
            paramMap.put(PAGEPARAMETER_FIRST, pageList.getPageSize());
            paramMap.put(PAGEPARAMETER_SECOND, pageList.getStartRow());
            return paramMap;
        }


    }

    /**
     * 自定义动态SqlSource
     */
    private class MyDynamicSqlSource implements SqlSource {
        private Configuration configuration;
        private SqlNode rootSqlNode;
        /**
         * 用于区分动态的count查询或分页查询
         */
        private Boolean count;

        private Boolean orderBy;

        private PageList pageList;

        public MyDynamicSqlSource(Configuration configuration, SqlNode rootSqlNode, Boolean count, Boolean orderBy, PageList pageList) {
            this.configuration = configuration;
            this.rootSqlNode = rootSqlNode;
            this.count = count;
            this.orderBy = orderBy;
            this.pageList = pageList;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            DynamicContext context = new DynamicContext(configuration, parameterObject);
            rootSqlNode.apply(context);
            SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
            Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
            SqlSource sqlSource = sqlSourceParser.parse(context.getSql(), parameterType, context.getBindings());
            if (count) {
                sqlSource = getCountSqlSource(configuration, sqlSource, parameterObject);
            } else {
                if (orderBy) {
                    sqlSource = getOrderPageSqlSource(configuration, sqlSource, parameterObject, pageList);
                } else {
                    sqlSource = getPageSqlSource(configuration, sqlSource, parameterObject);
                }
            }
            BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
//设置条件参数
            for (Map.Entry<String, Object> entry : context.getBindings().entrySet()) {
                boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
            }
            return boundSql;
        }
    }

    /**
     * 自定义ProviderSqlSource，代理方法
     */
    private class MyProviderSqlSource implements SqlSource {
        private Configuration configuration;
        private ProviderSqlSource providerSqlSource;
        /**
         * 用于区分动态的count查询或分页查询
         */
        private Boolean count;

        private MyProviderSqlSource(Configuration configuration, ProviderSqlSource providerSqlSource, Boolean count) {
            this.configuration = configuration;
            this.providerSqlSource = providerSqlSource;
            this.count = count;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            BoundSql boundSql = null;
            if (parameterObject instanceof Map && ((Map) parameterObject).containsKey(PROVIDER_OBJECT)) {
                boundSql = providerSqlSource.getBoundSql(((Map) parameterObject).get(PROVIDER_OBJECT));
            } else {
                boundSql = providerSqlSource.getBoundSql(parameterObject);
            }
            if (count) {
                return new BoundSql(
                        configuration,
                        sqlParser.getCountSql(boundSql.getSql()),
                        boundSql.getParameterMappings(),
                        parameterObject);
            } else {
                return new BoundSql(
                        configuration,
                        sqlParser.getPageSql(boundSql.getSql()),
                        getPageParameterMapping(configuration, boundSql),
                        parameterObject);
            }
        }
    }

    /**
     * 获取ms - 在这里对新建的ms做了缓存，第一次新增，后面都会使用缓存值
     *
     * @param ms
     * @param sqlSource
     * @param suffix
     * @return
     */
    private MappedStatement getMappedStatement(MappedStatement ms, SqlSource sqlSource, Object parameterObject, String suffix, PageList pageList) {
        MappedStatement qs = null;
        try {
            qs = ms.getConfiguration().getMappedStatement(ms.getId() + suffix);
        } catch (Exception e) {
//ignore
        }
        if (qs == null) {
//创建一个新的MappedStatement
            qs = newMappedStatement(ms, getsqlSource(ms, sqlSource, parameterObject, suffix, pageList), suffix);
            try {
                ms.getConfiguration().addMappedStatement(qs);
            } catch (Exception e) {
//ignore
            }
        }
        return qs;
    }

    /**
     * 新建count查询和分页查询的MappedStatement
     *
     * @param ms
     * @param sqlSource
     * @param suffix
     * @return
     */
    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource sqlSource, String suffix) {
        String id = ms.getId() + suffix;
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), id, sqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        if (suffix == SUFFIX_PAGE || suffix == SUFFIX_ORDER) {
            builder.resultMaps(ms.getResultMaps());
        } else {
//count查询返回值int
            List<ResultMap> resultMaps = new ArrayList<ResultMap>();
            ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), id, int.class, EMPTY_RESULTMAPPING).build();
            resultMaps.add(resultMap);
            builder.resultMaps(resultMaps);
        }
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    /**
     * 判断当前执行的是否为动态sql
     *
     * @param ms
     * @return
     */
    public boolean isDynamic(MappedStatement ms) {
        return ms.getSqlSource() instanceof DynamicSqlSource;
    }

    /**
     * 获取新的sqlSource
     *
     * @param ms
     * @param sqlSource
     * @param parameterObject
     * @param suffix
     * @return
     */
    private SqlSource getsqlSource(MappedStatement ms, SqlSource sqlSource, Object parameterObject, String suffix, PageList pageList) {
//1. 从XMLLanguageDriver.java和XMLScriptBuilder.java可以看出只有两种SqlSource
//2. 增加注解情况的ProviderSqlSource
//3. 对于RawSqlSource需要进一步测试完善
//如果是动态sql
        if (isDynamic(ms)) {
            MetaObject msObject = forObject(ms);
            SqlNode sqlNode = (SqlNode) msObject.getValue("sqlSource.rootSqlNode");
            MixedSqlNode mixedSqlNode = null;
            if (sqlNode instanceof MixedSqlNode) {
                mixedSqlNode = (MixedSqlNode) sqlNode;
            } else {
                List<SqlNode> contents = new ArrayList<SqlNode>(1);
                contents.add(sqlNode);
                mixedSqlNode = new MixedSqlNode(contents);
            }
            return new MyDynamicSqlSource(ms.getConfiguration(), mixedSqlNode, Objects.equals(suffix, SUFFIX_COUNT), Objects.equals(suffix, SUFFIX_ORDER), pageList);
        } else if (sqlSource instanceof ProviderSqlSource) {
            return new MyProviderSqlSource(ms.getConfiguration(), (ProviderSqlSource) sqlSource, Objects.equals(suffix, SUFFIX_COUNT));
        }
//
        else if (Objects.equals(suffix, SUFFIX_PAGE)) {
//改为分页sql
            return getPageSqlSource(ms.getConfiguration(), sqlSource, parameterObject);
        } else if (Objects.equals(suffix, SUFFIX_ORDER)) {
            return getOrderPageSqlSource(ms.getConfiguration(), sqlSource, parameterObject, pageList);
        }

//如果是静态count-sql
        else {
            return getCountSqlSource(ms.getConfiguration(), sqlSource, parameterObject);
        }
    }

    /**
     * 增加分页参数映射
     *
     * @param configuration
     * @param boundSql
     * @return
     */
    private List<ParameterMapping> getPageParameterMapping(Configuration configuration, BoundSql boundSql) {
        List<ParameterMapping> newParameterMappings = new ArrayList<>();
        newParameterMappings.addAll(boundSql.getParameterMappings());
        newParameterMappings.add(new ParameterMapping.Builder(configuration, PAGEPARAMETER_FIRST, Integer.class).build());
        newParameterMappings.add(new ParameterMapping.Builder(configuration, PAGEPARAMETER_SECOND, Integer.class).build());
        return newParameterMappings;
    }


    /**
     * 获取分页的sqlSource
     *
     * @param configuration
     * @param sqlSource
     * @return
     */
    private SqlSource getPageSqlSource(Configuration configuration, SqlSource sqlSource, Object parameterObject) {
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        return new StaticSqlSource(configuration, sqlParser.getPageSql(boundSql.getSql()), getPageParameterMapping(configuration, boundSql));
    }

    /**
     * 获取排序sqlSource
     *
     * @param configuration
     * @param sqlSource
     * @param parameterObject
     * @return
     */
    private SqlSource getOrderPageSqlSource(Configuration configuration, SqlSource sqlSource, Object parameterObject, PageList pageList) {
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        return new StaticSqlSource(configuration, sqlParser.getOrderPagerSql(boundSql.getSql(), pageList), getPageParameterMapping(configuration, boundSql));
    }

    /**
     * 获取count的sqlSource
     *
     * @param sqlSource
     * @return
     */
    private SqlSource getCountSqlSource(Configuration configuration, SqlSource sqlSource, Object parameterObject) {
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        return new StaticSqlSource(configuration, sqlParser.getCountSql(boundSql.getSql()), boundSql.getParameterMappings());
    }

    /**
     * 测试[控制台输出]count和分页sql
     *
     * @param dialet      数据库类型
     * @param originalSql 原sql
     */
    public static void testSql(String dialet, String originalSql) {
        SqlUtil sqlUtil = new SqlUtil(dialet);
        String countSql = sqlUtil.sqlParser.getCountSql(originalSql);
        System.out.println(countSql);
        String pageSql = sqlUtil.sqlParser.getPageSql(originalSql);
        System.out.println(pageSql);
    }
} 
