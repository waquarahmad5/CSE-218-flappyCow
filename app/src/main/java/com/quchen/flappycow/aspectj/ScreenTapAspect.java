package com.quchen.flappycow.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class ScreenTapAspect {
    private static final String POINTCUT_METHOD =
            "execution(@com.quchen.flappycow.aspectj.TapTrace * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.quchen.flappycow.aspectj.TapTrace *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithDebugTrace() {}

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedDebugTrace() {}

    final ScreenTap screenTap = new ScreenTap();

    @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        screenTap.tap();
        Object result = joinPoint.proceed();

        DebugLog.log(className, buildLogMessage(methodName, screenTap.getTapCount()));

        return result;
    }
    private static String buildLogMessage(String methodName, long methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append("ScreenTapAspect --> ");
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(methodDuration);
        message.append(" Taps");
        message.append("]");

        return message.toString();
    }
}
