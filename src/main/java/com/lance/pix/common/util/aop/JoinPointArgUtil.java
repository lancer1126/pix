package com.lance.pix.common.util.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lancer1126
 * @Date 2020-12-4
 * @Description 切面工具类
 */
@Component
public class JoinPointArgUtil {
    public String getFirstMethodArgByAnnotationValueMethodValue(JoinPoint joinPoint, Class argAnnotationClass, String annotationMethodValue)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return (String) getJointArgsByAnnotation(joinPoint, argAnnotationClass, "value",annotationMethodValue).get(0).getValue();
    }

    public List<JoinPointArg> getJointArgsByAnnotation(JoinPoint joinPoint, Class argAnnotationClass, String annotationMethodName, String annotationMethodValue)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<JoinPointArg> argList = new ArrayList<>();
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        assert args.length == parameterAnnotations.length;
        for (int argIndex = 0; argIndex < args.length; argIndex++) {
            for (Annotation annotation : parameterAnnotations[argIndex]) {
                if (!annotation.annotationType().equals(argAnnotationClass)) {
                    continue;
                }
                if (annotationMethodName != null && annotationMethodValue != null) {
                    Method m = argAnnotationClass.getMethod(annotationMethodName);
                    if (!annotationMethodValue.equals(m.invoke(annotation))) {
                        continue;
                    }
                }
                argList.add(new JoinPointArg(argIndex, args[argIndex]));
            }
        }
        return argList;
    }
}
















