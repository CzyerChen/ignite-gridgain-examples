/*
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

/*  _________        _____ __________________        _____
 *  __  ____/___________(_)______  /__  ____/______ ____(_)_______
 *  _  / __  __  ___/__  / _  __  / _  / __  _  __ `/__  / __  __ \
 *  / /_/ /  _  /    _  /  / /_/ /  / /_/ /  / /_/ / _  /  _  / / /
 *  \____/   /_/     /_/   \_,__/   \____/   \__,_/  /_/   /_/ /_/
 */

package org.gridgain.examples.datagrid.model;

import org.gridgain.grid.cache.query.*;

import java.io.*;
import java.util.concurrent.atomic.*;

/**
 * Organization class.
 */
public class Organization implements Serializable {
    /** ID generator. */
    private static final AtomicLong IDGEN = new AtomicLong(System.currentTimeMillis());

    /** Organization ID (indexed). */
    @GridCacheQuerySqlField(index = true)
    private long id;

    /** Organization name (indexed). */
    @GridCacheQuerySqlField(index = true)
    private String name;

    /**
     * Create organization.
     *
     * @param name Organization name.
     */
    public Organization(String name) {
        id = IDGEN.incrementAndGet();

        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return "Organization " + "[id=" + id + ", name=" + name + ']';
    }
}

