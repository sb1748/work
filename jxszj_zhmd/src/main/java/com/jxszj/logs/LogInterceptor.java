package com.jxszj.logs;

//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;
 
@Component
public  class LogInterceptor {
    /**
     * 因为我这引入的是slf4j与log4j的桥接包，即可用log4j也可用slf4j输出日志
     * log4j：
     *      import org.apache.logging.log4j.LogManager;
     *      import org.apache.logging.log4j.Logger;
     * slf4j:
     *      import org.slf4j.Logger;
     *      import org.slf4j.LoggerFactory;
     */
//    private static final Logger logger = LogManager.getLogger(LogInterceptor.class);
    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
 
    String logStr=null;
//    //前置通知
    public void before(JoinPoint jp){
        logStr="<<<<<<<<<<<<<<<<<<<<<<<<<<开始执行"+jp.getSignature().getName()+"()方法>>>>>>>>>>>>>>>>>>>>>>>>>>";
        logger.info(logStr);
    }
//      //最终通知
//    public void after(JoinPoint jp){
//        logStr=jp.getTarget().getClass().getName()+"类的"
//                +jp.getSignature().getName()+"方法执行结束******End******";
//        logger.info(logStr);
//    }
//      //异常抛出后通知
//    public void afterThrowing(JoinPoint call){
//        String className = call.getTarget().getClass().getName();
//        String methodName = call.getSignature().getName();
//        System.out.println(className+"."+methodName+"()方法抛出了异常...");
//    }
//     //后置通知
    public void afterReturn(JoinPoint call){
        String methodName = call.getSignature().getName();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<"+methodName+"()方法执行结束>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
 
 
    //用来做环绕通知的方法可以第一个参数定义为org.aspectj.lang.ProceedingJoinPoint类型
//    public Object around(ProceedingJoinPoint call) throws Throwable {
//        Object result = null;
// 
//        //取得类名和方法名
//        String className = call.getTarget().getClass().getName();
//        String methodName = call.getSignature().getName();
// 
//        //相当于前置通知
//        logStr="<<<<<<<<<<<<<<<<<<<<<<<<<<开始执行"+methodName+"()方法>>>>>>>>>>>>>>>>>>>>>>>>>>";
//        logger.info(logStr);
// 
//        try {
//            result = call.proceed();
//            //相当于后置通知
//            logStr="-------------------------"+methodName+"()方法执行成功-------------------------";
//            logger.info(logStr);
// 
//        } catch (Throwable e) {
//            //相当于异常抛出后通知
//            logger.error("-----------------------"+methodName+"()方法执行失败-----------------------");
//            logger.error("-----------------------异常信息为：  "+e.fillInStackTrace().toString()+"-----------------------");
//            throw e;
// 
//        }finally{
//            //相当于最终通知
//            logStr="<<<<<<<<<<<<<<<<<<<<<<<<<<"+methodName+"()方法执行结束>>>>>>>>>>>>>>>>>>>>>>>>>>";
//            logger.info(logStr);
//        }
// 
//        return result;
//    }
 
}
