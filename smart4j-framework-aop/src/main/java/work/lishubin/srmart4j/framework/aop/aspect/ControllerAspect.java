package work.lishubin.srmart4j.framework.aop.aspect;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author lishubin
 */
public class ControllerAspect extends AspectProxy {

    @Override
    protected <T> T begin() {
        return (T) new Date(System.currentTimeMillis());
    }

    @Override
    protected <T> T end() {
        return (T) new Date(System.currentTimeMillis());
    }

    public long getRunTime(){
        long time = 0L;
        if (this.getBeginResult() instanceof Date && this.getEndResult() instanceof Date){

            Date beginResult = (Date) this.getBeginResult();
            Date endResult = (Date) this.getEndResult();
            time = endResult.getTime() - beginResult.getTime();
        }
        return time;

    }

}
