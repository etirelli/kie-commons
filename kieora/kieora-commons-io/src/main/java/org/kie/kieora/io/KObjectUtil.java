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

package org.kie.kieora.io;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.kie.commons.java.nio.base.FileSystemId;
import org.kie.commons.java.nio.base.SegmentedPath;
import org.kie.commons.java.nio.file.Path;
import org.kie.commons.java.nio.file.attribute.FileAttribute;
import org.kie.kieora.model.KObject;
import org.kie.kieora.model.KObjectKey;
import org.kie.kieora.model.KProperty;
import org.kie.kieora.model.schema.MetaType;

import static org.apache.commons.io.FilenameUtils.*;

/**
 *
 */
public final class KObjectUtil {

    private static final MessageDigest DIGEST;

    static {
        try {
            DIGEST = MessageDigest.getInstance( "SHA1" );
        } catch ( final NoSuchAlgorithmException e ) {
            throw new RuntimeException( e );
        }
    }

    private static final MetaType META_TYPE = new MetaType() {
        @Override
        public String getName() {
            return Path.class.getName();
        }
    };

    private KObjectUtil() {

    }

    public static KObjectKey toKObjectKey( final Path path ) {
        return new KObjectKey() {
            @Override
            public String getId() {
                return sha1( getType().getName() + "|" + getKey() );
            }

            @Override
            public MetaType getType() {
                return META_TYPE;
            }

            @Override
            public String getClusterId() {
                return ( (FileSystemId) path.getFileSystem() ).id();
            }

            @Override
            public String getSegmentId() {
                return ( (SegmentedPath) path ).getSegmentId();
            }

            @Override
            public String getKey() {
                return path.toString();
            }
        };
    }

    public static KObject toKObject( final Path path,
                                     final FileAttribute<?>... attrs ) {
        return new KObject() {
            @Override
            public String getId() {
                return sha1( getType().getName() + "|" + getKey() );
            }

            @Override
            public MetaType getType() {
                return META_TYPE;
            }

            @Override
            public String getClusterId() {
                return ( (FileSystemId) path.getFileSystem() ).id();
            }

            @Override
            public String getSegmentId() {
                return ( (SegmentedPath) path ).getSegmentId();
            }

            @Override
            public String getKey() {
                return path.toUri().toString();
            }

            @Override
            public Iterable<KProperty<?>> getProperties() {
                return new ArrayList<KProperty<?>>( attrs.length ) {{
                    for ( final FileAttribute<?> attr : attrs ) {
                        add( new KProperty<Object>() {
                            @Override
                            public String getName() {
                                return attr.name();
                            }

                            @Override
                            public Object getValue() {
                                return attr.value();
                            }

                            @Override
                            public boolean isSearchable() {
                                return true;
                            }
                        } );
                    }
                    add( new KProperty<String>() {
                        @Override
                        public String getName() {
                            return "filename";
                        }

                        @Override
                        public String getValue() {
                            if ( path.getFileName() == null ) {
                                return "/";
                            }
                            return path.getFileName().toString();
                        }

                        @Override
                        public boolean isSearchable() {
                            return true;
                        }
                    } );
                    add( new KProperty<String>() {
                        @Override
                        public String getName() {
                            return "extension";
                        }

                        @Override
                        public String getValue() {
                            if ( path.getFileName() == null ) {
                                return "";
                            }
                            return getExtension( path.getFileName().toString() );
                        }

                        @Override
                        public boolean isSearchable() {
                            return true;
                        }
                    } );
                    add( new KProperty<String>() {
                        @Override
                        public String getName() {
                            return "basename";
                        }

                        @Override
                        public String getValue() {
                            if ( path.getFileName() == null ) {
                                return "";
                            }
                            return getBaseName( path.getFileName().toString() );
                        }

                        @Override
                        public boolean isSearchable() {
                            return true;
                        }
                    } );

                }};
            }
        };
    }

    private static String sha1( final String input ) {
        byte[] result = DIGEST.digest( input.getBytes() );
        final StringBuffer sb = new StringBuffer();
        for ( int i = 0; i < result.length; i++ ) {
            sb.append( Integer.toString( ( result[ i ] & 0xff ) + 0x100, 16 ).substring( 1 ) );
        }

        return sb.toString();
    }
}
