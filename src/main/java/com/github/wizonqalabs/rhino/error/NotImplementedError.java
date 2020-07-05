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

/**
 * @author Wasiq Bhamla
 */
public class NotImplementedError extends RhinoError {
    private static final long serialVersionUID = 5669493204461208732L;

    /**
     * @param message
     *     Error message
     */
    public NotImplementedError (final String message) {
        super (message);
    }

    /**
     * @param message
     *     Error message
     * @param cause
     *     Error cause
     */
    public NotImplementedError (final String message, final Throwable cause) {
        super (message, cause);
    }
}