package com.epam.mjc;

import com.sun.jdi.connect.Connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */

    public MethodSignature parseFunction(String signatureString) {
        int index = signatureString.indexOf("(");
        String[] half = new String[2];
        half[0] = signatureString.substring(0, index);
        half[1] = signatureString.substring(index + 1);

        String[] left = half[0].split(" ");

        String methodName = left[left.length - 1];
        String methodReturnType = left[left.length - 2];
        String methodModifier = "";
        if (left.length == 3)
            methodModifier = left[0];

        half[1] = half[1].substring(0, half[1].length() - 1);

        List<MethodSignature.Argument> argumentsList = new ArrayList<>();


        if (half[1].length() != 0) {
            String[] args = half[1].split(", ");
            for (String arg : args) {
                String[] strings = arg.split(" ");
                argumentsList.add(new MethodSignature.Argument(strings[0], strings[1]));
            }
        }

        MethodSignature methodSignature = new MethodSignature(methodName, argumentsList);
        methodSignature.setReturnType(methodReturnType);
        if (!methodModifier.equals(""))
            methodSignature.setAccessModifier(methodModifier);

        return methodSignature;
    }
}
