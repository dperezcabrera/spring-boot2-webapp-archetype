#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.logging;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingMethodValidationInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Logging loggingRule = mi.getMethod().getAnnotation(Logging.class);
        Logger logger = LoggerFactory.getLogger(mi.getThis().getClass());
        boolean logErrors = false;
        boolean logParameters = true;
        boolean logReturns = true;
        String methodName = mi.getMethod().getName();
        if (loggingRule != null) {
            logErrors = loggingRule.logErrors();
            logParameters = loggingRule.logParameters();
            logReturns = loggingRule.logReturns();
        }
        try {
            int numParameters = mi.getMethod().getParameterCount();
            logger.debug("{} start", methodName);
            if (logParameters && numParameters > 0) {
                logger.trace(methodName + " start (" + patternParameters(mi.getArguments().length) + ")", mi.getArguments());
            }
            long time = System.currentTimeMillis();
            Object result = mi.proceed();
            time = System.currentTimeMillis() - time;
            if (logReturns && mi.getMethod().getReturnType() != void.class) {
                logger.trace("{} returns: {}", methodName, result);
            }
            logger.debug("{} finish in {} ms", methodName, time);
            return result;
        } catch (Exception e) {
            if (logErrors) {
                logger.error(methodName + " throws exception", e);
            }
            throw e;
        }
    }

    private static String patternParameters(int numParameters) {
        String result;
        if (numParameters > 0) {
            StringBuilder sb = new StringBuilder(numParameters * 4 -2);
            sb.append("{}");
            for (int i = 1; i < numParameters; i++) {
                sb.append(", {}");
            }
            result = sb.toString();
        } else {
            result = "";
        }
        return result;
    }
}
