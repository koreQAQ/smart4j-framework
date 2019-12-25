package work.lishubin.smart4j.framework.data.jdbc;

import work.lishubin.smart4j.framework.data.constant.SqlOperation;
import work.lishubin.smart4j.framework.data.exception.ResultNotMatchedException;
import work.lishubin.smart4j.framework.data.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author 李树彬
 * @version 1.0.0
 * @date 2019/12/25 11:45
 */
public abstract class JdbcTemplate<T> {


    /*----------定义四种数据库操作----------------*/

    /**
     * 新建
     *
     * @return 影响的行数
     */
    public final int create(T t) {
        String createSql = getMainSql(SqlOperation.CREATE);
        String preparedSql = preparedSql(SqlOperation.CREATE, createSql, t);
        return executeSqlByCondition(preparedSql, t);
    }

    /**
     * 删除
     *
     * @return 影响的行数
     */
    public final int delete(T t) {

        String createSql = getMainSql(SqlOperation.DELETE);
        String preparedSql = preparedSql(SqlOperation.DELETE, createSql, t);
        return executeSqlByCondition(preparedSql, t);
    }

    /**
     * 更新
     *
     * @return 影响的行数
     */
    public final int update(T t) {
        String createSql = getMainSql(SqlOperation.UPDATE);
        String preparedSql = preparedSql(SqlOperation.UPDATE, createSql, t);
        return executeSqlByCondition(preparedSql, t);

    }

    /**
     * 查找一个
     *
     * @return 影响的行数
     */
    public final T queryOne(T t) throws ResultNotMatchedException {

        Collection<T> queryList = queryList(t);
        if (queryList.size() > 1) {
            throw new ResultNotMatchedException();
        }
        return queryList.iterator().next();

    }

    /**
     * 查找一组
     *
     * @return 影响的行数
     */
    public Collection<T> queryList(T t) {
        String mainSql = getMainSql(SqlOperation.SELECT);
        String preparedSql = preparedSql(SqlOperation.SELECT, mainSql, t);
        return selectByCondition(preparedSql, t);
    }




    /*-------定义模板方法 钩子函数--------*/

    /**
     * 根据操作语句类型不同，得到主要的sql语句
     *
     * @param sqlOperation 操作语句类型
     * @return MainSql
     */
    protected abstract String getMainSql(SqlOperation sqlOperation);

    /**
     * 将对应的sql语句，根据对应的对象属性来进行动态拼装
     *
     * @param sql 未拼装的sql
     * @param t   操作对象
     * @return preparedSql
     */
    protected abstract String preparedSql(SqlOperation sqlOperation, String sql, T t);

    /**
     * 将pmst得到的Result映射为对象
     *
     * @param resultSet 数据库返回的结果集
     * @return 集合映射
     */
    protected abstract Collection<T> resultSet2Object(ResultSet resultSet);

    /**
     * 将对应的
     *
     * @param preparedStatement 预编译SQL
     * @param t                 将对象属性放入对应的预编译SQL
     */
    protected abstract void setQueryValue(PreparedStatement preparedStatement, T t);


    /**
     * 定义Jdbc执行骨架
     */
    public final Collection<T> selectByCondition(String sql, T t) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Collection<T> result = null;
        try {
            connection = JdbcUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            setQueryValue(preparedStatement, t);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet2Object(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                JdbcUtils.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public final int executeSqlByCondition(String sql, T t) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int effectedRows = 0;
        try {
            connection = JdbcUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            setQueryValue(preparedStatement, t);
            effectedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                JdbcUtils.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return effectedRows;

    }
}
