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
package com.github.wizonqalabs.rhino.error.enums;

/**
 * @author Wasiq Bhamla
 * @since Jul 8, 2017 8:49:05 PM
 */
public enum Reason {
    /**
     * Application error.
     */
    R1 ("Application"),
    /**
     * Appium Framework error.
     */
    R2 ("Appium"),
    /**
     * Config framework error.
     */
    R3 ("Configurations"),
    /**
     * API Services framework.
     */
    R4 ("Services");

    private final String name;

    Reason (final String name) {
        this.name = name;
    }

    /**
     * @return reason
     *
     * @author Wasiq Bhamla
     * @since Jul 8, 2017 8:51:08 PM
     */
    public String reason () {
        return this.name;
    }
}