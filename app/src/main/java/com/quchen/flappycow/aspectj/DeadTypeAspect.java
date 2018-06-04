package com.quchen.flappycow.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class DeadTypeAspect {
    private static final String POINTCUT_METHOD =
            "execution(@com.quchen.flappycow.aspectj.DeadTrace * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.quchen.flappycow.aspectj.DeadTrace *.new(..))";

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


        DebugLog.log(className, buildLogMessage(methodName, 0));

        return result;
    }
    private static String buildLogMessage(String methodName, long methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append("TraceDead --> ");
        message.append(methodName);
        return message.toString();
    }
}
