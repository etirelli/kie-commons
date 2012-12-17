/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.commons.java.nio.base.options;

import java.util.Date;
import java.util.TimeZone;

import org.kie.commons.java.nio.file.CopyOption;
import org.kie.commons.java.nio.file.OpenOption;

public class CommentedOption
        implements OpenOption,
                   CopyOption {

    private final String   name;
    private final String   email;
    private final String   message;
    private final Date     when;
    private final TimeZone timeZone;

    public CommentedOption( final String name ) {
        this( name, null, null, null, null );
    }

    public CommentedOption( final String name,
                            final String message ) {
        this( name, null, message, null, null );
    }

    public CommentedOption( final String name,
                            final String email,
                            final String message ) {
        this( name, email, message, null, null );
    }

    public CommentedOption( final String name,
                            final String email,
                            final String message,
                            final Date when ) {
        this( name, email, message, when, null );
    }

    public CommentedOption( final String name,
                            final String email,
                            final String message,
                            final Date when,
                            final TimeZone timeZone ) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.when = when;
        this.timeZone = timeZone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public Date getWhen() {
        return when;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }
}
