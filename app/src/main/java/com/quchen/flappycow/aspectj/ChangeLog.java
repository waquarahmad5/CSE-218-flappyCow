package com.quchen.flappycow.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class ChangeLog {
    private static final String POINTCUT_METHOD =
            "execution(@com.quchen.flappycow.aspectj.ChangeTrace * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.quchen.flappycow.aspectj.ChangeTrace *.new(..))";

    static int count = 0;

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithDebugTrace() {}

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedDebugTrace() {}

    @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();



        Object result = joinPoint.proceed();

        count += 1;

        DebugLog.log(className, buildLogMessage(methodName, count));

        return result;
    }
    private static String buildLogMessage(String methodName, int count) {
        StringBuilder message = new StringBuilder();
        message.append("TraceAspect --> PowerUps initiated");
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(count);
        message.append("]");

        return message.toString();
    }
}
