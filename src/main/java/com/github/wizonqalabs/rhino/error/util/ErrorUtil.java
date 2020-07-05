/*
 * Copyright (c) 2020, WizonQA Labs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.wizonqalabs.rhino.error.util;

import static java.text.MessageFormat.format;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.wizonqalabs.rhino.error.RhinoError;
import com.github.wizonqalabs.rhino.error.enums.Category;
import com.github.wizonqalabs.rhino.error.enums.Reason;
import com.github.wizonqalabs.rhino.error.enums.Severity;

/**
 * @author Wasiq Bhamla
 */
public final class ErrorUtil {
    /**
     * @param cls
     *     Error class
     * @param message
     *     Error message
     */
    public static <T extends RhinoError> void fail (final Class<T> cls, final String message) {
        final Class<?>[] clsArray = new Class[] { String.class };
        final Object[] args = new Object[] { message };
        fail (cls, clsArray, args);
    }

    /**
     * @param cls
     *     Error class
     * @param message
     *     Error message
     * @param reason
     *     Error reason
     * @param category
     *     Error category
     * @param severity
     *     Error severity
     */
    public static <T extends RhinoError> void fail (final Class<T> cls, final String message, final Reason reason,
        final Category category, final Severity severity) {
        final Class<?>[] clsArray = new Class[] { String.class, Reason.class, Category.class, Severity.class };
        final Object[] args = new Object[] { message, reason, category, severity };
        fail (cls, clsArray, args);
    }

    /**
     * @param cls
     *     Error class
     * @param message
     *     Error message
     * @param cause
     *     Error cause
     */
    public static <T extends RhinoError> void fail (final Class<T> cls, final String message, final Throwable cause) {
        final Class<?>[] clsArray = new Class[] { String.class, Throwable.class };
        final Object[] args = new Object[] { message, cause };
        fail (cls, clsArray, args);
    }

    /**
     * @param cls
     *     Error class
     * @param message
     *     Error message
     * @param cause
     *     Error cause
     * @param reason
     *     Error reason
     * @param category
     *     Error category
     * @param severity
     *     Error severity
     */
    public static <T extends RhinoError> void fail (final Class<T> cls, final String message, final Throwable cause,
        final Reason reason, final Category category, final Severity severity) {
        final Class<?>[] clsArray = new Class[] { String.class, Throwable.class, Reason.class, Category.class,
            Severity.class };
        final Object[] args = new Object[] { message, cause, reason, category, severity };
        fail (cls, clsArray, args);
    }

    /**
     * @param rootPackage
     *     Root package to filter stack trace
     * @param cause
     *     Error cause
     *
     * @return filtered stack trace
     */
    public static List<String> handleError (final String rootPackage, final Throwable cause) {
        if (cause == null) {
            return Collections.emptyList ();
        }
        Throwable throwable = cause;
        final List<String> stack = new ArrayList<> ();
        boolean firstEntry = true;
        stack.add (format ("Error occurred: ({0})", throwable.getClass ().getName ()));
        final String stackTrace = "\tat {0}: {1} ({2})";
        do {
            if (!firstEntry) {
                stack.add (format ("Caused by: ({0})", throwable.getClass ()));
            }
            stack.add (format ("Message: {0}", throwable.getMessage ()));
            for (final StackTraceElement trace : cause.getStackTrace ()) {
                if (rootPackage == null || trace.getClassName ().startsWith (rootPackage)) {
                    stack.add (
                        format (stackTrace, trace.getClassName (), trace.getMethodName (), trace.getLineNumber ()));
                }
            }
            firstEntry = false;
            throwable = throwable.getCause ();
        } while (throwable != null);
        return stack;
    }

    /**
     * @param cause
     *     Error cause
     *
     * @return filtered stack trace
     */
    public static List<String> handleError (final Throwable cause) {
        return handleError (null, cause);
    }

    private static <T extends RhinoError> void fail (final Class<T> cls, final Class<?>[] clsArray,
        final Object[] args) {
        try {
            final Constructor<T> constructor = cls.getDeclaredConstructor (clsArray);
            throw constructor.newInstance (args);
        } catch (final NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RhinoError ("Error occurred while throwing custom error.", e.getCause ());
        }
    }

    private ErrorUtil () {
        // Utility class.
    }
}