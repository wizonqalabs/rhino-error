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
package com.github.wizonqalabs.rhino.error;

import static com.github.wizonqalabs.rhino.error.util.ErrorUtil.fail;
import static com.github.wizonqalabs.rhino.error.util.ErrorUtil.handleError;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import com.github.wizonqalabs.rhino.error.enums.Category;
import com.github.wizonqalabs.rhino.error.enums.Reason;
import com.github.wizonqalabs.rhino.error.enums.Severity;
import com.github.wizonqalabs.rhino.error.util.ErrorUtil;
import org.testng.annotations.Test;

/**
 * @author Wasiq Bhamla
 */
public class ErrorUtilTest {
    /**
     * @author Wasiq Bhamla
     */
    @Test
    public void testInitializationFailure ()
        throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException,
        IllegalArgumentException, InvocationTargetException {
        final Class<ErrorUtil> clazz = ErrorUtil.class;
        final Constructor<ErrorUtil> constructor = clazz.getDeclaredConstructor ();
        assertWithMessage ("ctor modifier").that (Modifier.isPrivate (constructor.getModifiers ())).isTrue ();
        try {
            constructor.setAccessible (true);
            constructor.newInstance ();
        } finally {
            constructor.setAccessible (false);
        }
    }

    /**
     * @author Wasiq Bhamla
     */
    @Test (expectedExceptions = NotImplementedError.class, expectedExceptionsMessageRegExp = "Test Error!")
    public void testNotImplementedErrorWithOneArg () {
        fail (NotImplementedError.class, "Test Error!");
    }

    /**
     * @author Wasiq Bhamla
     */
    @Test (expectedExceptions = NotImplementedError.class, expectedExceptionsMessageRegExp = "Test Error!")
    public void testNotImplementedErrorWithTwoArg () {
        fail (NotImplementedError.class, "Test Error!", new Throwable ());
    }

    /**
     * @author Wasiq Bhamla
     */
    @Test
    public void testOperationNotSupportedErrorTypes () {
        try {
            fail (OperationNotSupportedError.class, "Error");
        } catch (final OperationNotSupportedError e) {
            handleError (e).forEach (System.err::println);
            assertThat (e.getReason ().reason ()).isEqualTo (Reason.R2.reason ());
            assertThat (e.getCategory ().category ()).isEqualTo (Category.C1.category ());
            assertThat (e.getSeverity ()).isEqualTo (Severity.CRITICAL);
        }
    }

    /**
     * @author Wasiq Bhamla
     */
    @Test (expectedExceptions = OperationNotSupportedError.class, expectedExceptionsMessageRegExp = "Test Error!")
    public void testOperationNotSupportedErrorWithOneArg () {
        fail (OperationNotSupportedError.class, "Test Error!");
    }

    /**
     * @author Wasiq Bhamla
     */
    @Test (expectedExceptions = OperationNotSupportedError.class, expectedExceptionsMessageRegExp = "Test Error!")
    public void testOperationNotSupportedErrorWithTwoArg () {
        fail (OperationNotSupportedError.class, "Test Error!", new FileNotFoundException ());
    }

    /**
     * @author Wasiq Bhamla
     */
    @Test (expectedExceptions = RhinoError.class, expectedExceptionsMessageRegExp = "Test Error!")
    public void testRhinoErrorWithFiveArg () {
        try {
            fail (RhinoError.class, "Test Error!", new FileNotFoundException (), Reason.R2, Category.C1,
                Severity.CRITICAL);
        } catch (final RhinoError e) {
            handleError ("com.github.wasiqb", e).forEach (System.err::println);
            throw e;
        }
    }

    /**
     * @author Wasiq Bhamla
     */
    @Test (expectedExceptions = RhinoError.class, expectedExceptionsMessageRegExp = "Test Error!")
    public void testRhinoErrorWithFourArg () {
        fail (RhinoError.class, "Test Error!", Reason.R2, Category.C1, Severity.CRITICAL);
    }
}