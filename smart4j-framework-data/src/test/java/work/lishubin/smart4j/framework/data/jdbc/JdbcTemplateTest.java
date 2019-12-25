package work.lishubin.smart4j.framework.data.jdbc;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import work.lishubin.smart4j.framework.data.constant.SqlOperation;
import work.lishubin.smart4j.framework.data.jdbc.entity.TestDomain;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

/**
 * @author 李树彬
 * @version 1.0.0
 * @date 2019/12/25 12:06
 */
class JdbcTemplateTest {
    private TestDemoRepository testDemoRepository;
    private String[] mainSqls;

    @BeforeEach
    public void setUp() {
        testDemoRepository = new TestDemoRepository();
        mainSqls = new String[]{
                "INSERT INTO `test_domain` VALUES ('useranme','password')",
                "DELETE FROM `test_domain` WHERE 1=1 AND username = 'username' AND password = 'password'",
                "UPDATE `test_domain` SET username = 'update', password = 'password' WHERE username = 'username' AND password = 'password'",
                "SELECT * FROM `test_domain` WHERE 1=1 AND username = 'username' AND password = 'password')"
        };

    }

    @Test
    public void testGetMainSql() {
        TestDomain testDomain = new TestDomain();
        testDomain.setId(UUID.randomUUID().toString());
        testDomain.setName("username");
        testDomain.setPassword("password");
        String mainSql = testDemoRepository.getMainSql(SqlOperation.CREATE);
        System.out.println(testDemoRepository.preparedSql(SqlOperation.CREATE, mainSql, testDomain));

        testDemoRepository.executeSqlByCondition(testDemoRepository.preparedSql(SqlOperation.CREATE, mainSql, testDomain), testDomain);

        mainSql = testDemoRepository.getMainSql(SqlOperation.UPDATE);
        System.out.println(testDemoRepository.preparedSql(SqlOperation.UPDATE, mainSql, testDomain));
        mainSql = testDemoRepository.getMainSql(SqlOperation.SELECT);
        System.out.println(testDemoRepository.preparedSql(SqlOperation.SELECT, mainSql, testDomain));
        mainSql = testDemoRepository.getMainSql(SqlOperation.DELETE);
        System.out.println(testDemoRepository.preparedSql(SqlOperation.DELETE, mainSql, testDomain));
    }


    private class TestDemoRepository extends JdbcTemplate<TestDomain> {

        @Override
        protected String getMainSql(SqlOperation sqlOperation) {
            String sql = "";
            switch (sqlOperation) {
                case CREATE:
                    sql = "INSERT INTO `test_domain` ";
                    break;
                case DELETE:
                    sql = "DELETE `test_domain` WHERE 1=1";
                    break;
                case UPDATE:
                    sql = "UPDATE `test_domain` SET ";
                    break;
                case SELECT:
                    sql = "SELECT * FROM `test_domain` WHERE 1=1 ";
                    break;
            }

            return sql;
        }

        @Override
        protected String preparedSql(SqlOperation sqlOperation, String sql, TestDomain testDomain) {
            StringBuilder stringBuilder = new StringBuilder(sql);
            //得到所有属性
            Field[] fields = testDomain.getClass().getDeclaredFields();
            try {
                switch (sqlOperation) {
                    case CREATE:
                        //insert拼接
                        stringBuilder.append("VALUES (");


                        for (Field field : fields) {
                            field.setAccessible(true);
                            //得到属性Value
                            //todo 完整测试
                            String fieldValue = (String) field.get(testDomain);
                            stringBuilder.append("'").append(fieldValue).append("'").append(",");
                        }

                        //删除多余的,
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        stringBuilder.append(")");

                        break;
                    case DELETE:
                    case SELECT:
                        // DELETE test_demo where 1=1 and a1 = 1 and a2 = 2;
                        for (Field field : fields) {
                            field.setAccessible(true);
                            stringBuilder.append(" AND ")
                                    .append(field.getName())
                                    .append("=").append("'").append(field.get(testDomain)).append("'").append(",");
                        }
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        break;
                    case UPDATE:
                        for (Field field : fields) {
                            field.setAccessible(true);
                            stringBuilder.append(field.getName())
                                    .append("=")
                                    .append(field.get(testDomain))
                                    .append(",");
                        }
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

                        stringBuilder.append(" WHERE 1=1 ");
                        for (Field field : fields) {
                            field.setAccessible(true);
                            stringBuilder.append(" AND ")
                                    .append(field.getName())
                                    .append("=").append("'").append(field.get(testDomain)).append("'").append(",");
                        }
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + sqlOperation);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


            return stringBuilder.toString();
        }

        @Override
        protected Collection<TestDomain> resultSet2Object(ResultSet resultSet) {
            Collection<TestDomain> testDomainCollection = Arrays.asList();
            try {
                while (resultSet.next()) {

                    TestDomain instance = TestDomain.class.getDeclaredConstructor().newInstance();
                    Field[] declaredFields = TestDomain.class.getDeclaredFields();
                    for (Field declaredField : declaredFields) {
                        //打开访问权限
                        declaredField.setAccessible(true);
                        Object colValue = resultSet.getString(declaredField.getName().toLowerCase());
                        declaredField.set(instance, colValue);
                    }
                    testDomainCollection.add(instance);
                }
            } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }

            return testDomainCollection;
        }

        @Override
        protected void setQueryValue(PreparedStatement preparedStatement, TestDomain testDomain) {

            Field[] declaredFields = testDomain.getClass().getDeclaredFields();

            int index = 1;
            try {

                for (Field declaredField : declaredFields) {
                    declaredField.setAccessible(true);
                    Object attributeValue = declaredField.get(testDomain);
                    preparedStatement.setObject(index, attributeValue);
                    index++;
                }
            } catch (SQLException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}