/*
 * Copyright 2010 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.ning.http.client.multipart;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamPartVisitor implements PartVisitor {

    private final OutputStream out;

    public OutputStreamPartVisitor(OutputStream out) {
        this.out = out;
    }

    @Override
    public void withBytes(byte[] bytes) throws IOException {
        out.write(bytes);
    }

    @Override
    public void withByte(byte b) throws IOException {
        out.write(b);
    }

    public OutputStream getOutputStream() {
        return out;
    }
}
