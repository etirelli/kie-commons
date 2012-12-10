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

package org.kie.commons.java.nio.fs.jgit;

import org.kie.commons.java.nio.IOException;
import org.kie.commons.java.nio.base.AbstractBasicFileAttributeView;
import org.kie.commons.java.nio.file.attribute.BasicFileAttributeView;
import org.kie.commons.java.nio.file.attribute.BasicFileAttributes;
import org.kie.commons.java.nio.fs.jgit.util.JGitUtil;

/**
 *
 */
public class JGitBasicFileAttributeView extends AbstractBasicFileAttributeView<JGitPathImpl> {

    private BasicFileAttributes attrs = null;

    public JGitBasicFileAttributeView( final JGitPathImpl path ) {
        super( path );
    }

    @Override
    public <T extends BasicFileAttributes> T readAttributes() throws IOException {
        if ( attrs == null ) {
            attrs = JGitUtil.buildBasicFileAttributes( path.getFileSystem().gitRepo(), path.getRefTree(), path.getPath() );
        }
        return (T) attrs;
    }

    @Override
    public Class<? extends BasicFileAttributeView>[] viewTypes() {
        return new Class[]{ BasicFileAttributeView.class, JGitBasicFileAttributeView.class };
    }
}
