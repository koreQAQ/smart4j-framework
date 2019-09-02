package work.lishubin.smart4j.framework.webmvc.entity;

/**
 * @author 李树彬
 * @date 2019/8/31  23:24
 */
public class SmartRequest {

    private String requestMethod;
    private String requestUrl;

    /**
     * 不允许使用new
     */
    private SmartRequest(){}

    public static SmartRequest getNewInstance(){
        return new SmartRequest();
    }


    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public int hashCode() {
        return String.format(
                "%s%s",
                String.valueOf(requestMethod.hashCode()),
                String.valueOf(requestUrl.hashCode())).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean flag = false;

        if (obj instanceof SmartRequest){
            SmartRequest compared = (SmartRequest) obj;
            if (
                    (this.getRequestUrl().equals(compared.getRequestUrl()))
                    &&
                    (this.getRequestMethod().equals(compared.getRequestMethod()))
            ){
                flag = true;
            }

        }

        return flag;



    }
}
