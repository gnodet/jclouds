/**
 *
 * Copyright (C) 2009 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */
package org.jclouds.http.payloads;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.jclouds.http.Payload;
import org.jclouds.util.Utils;

import com.google.common.io.ByteStreams;

/**
 * @author Adrian Cole
 */
public class ByteArrayPayload implements Payload {

   private final byte[] content;

   public ByteArrayPayload(byte[] content) {
      this.content = checkNotNull(content, "content");
   }

   public byte[] getRawContent() {
      return content;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public InputStream getContent() {
      return new ByteArrayInputStream(content);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isRepeatable() {
      return true;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeTo(OutputStream outstream) throws IOException {
      ByteStreams.write(content, Utils.newOutputStreamSupplier(outstream));
   }

   @Override
   public Long calculateSize() {
      return new Long(content.length);
   }
}