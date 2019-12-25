package work.lishubin.smart4j.framework.data.exception;

/**
 * @author 李树彬
 * @version 1.0.0
 * @date 2019/12/25 11:47
 */
public class ResultNotMatchedException extends Exception {

    @Override
    public String getMessage() {
        return "ResultSet不止一个结果，请使用Query";
    }
}
