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
package org.jclouds.rackspace.cloudfiles.blobstore.functions;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.blobstore.domain.MutableBlobMetadata;
import org.jclouds.blobstore.domain.ResourceType;
import org.jclouds.blobstore.domain.internal.MutableBlobMetadataImpl;
import org.jclouds.blobstore.strategy.IsDirectoryStrategy;
import org.jclouds.encryption.EncryptionService;
import org.jclouds.rackspace.cloudfiles.domain.MutableObjectInfoWithMetadata;
import org.jclouds.rackspace.cloudfiles.domain.ObjectInfo;

import com.google.common.base.Function;

/**
 * @author Adrian Cole
 */
@Singleton
public class ObjectToBlobMetadata implements Function<ObjectInfo, MutableBlobMetadata> {
   private final IsDirectoryStrategy isDirectoryStrategy;
   private final EncryptionService encryptionService;

   @Inject
   public ObjectToBlobMetadata(IsDirectoryStrategy isDirectoryStrategy,
            EncryptionService encryptionService) {
      this.isDirectoryStrategy = isDirectoryStrategy;
      this.encryptionService = encryptionService;
   }

   public MutableBlobMetadata apply(ObjectInfo from) {
      MutableBlobMetadata to = new MutableBlobMetadataImpl();
      to.setContentMD5(from.getHash());
      if (from.getContentType() != null)
         to.setContentType(from.getContentType());
      if (from.getHash() != null)
         to.setETag(encryptionService.toHexString(from.getHash()));
      to.setName(from.getName());
      if (from.getBytes() != null)
         to.setSize(from.getBytes());
      if (from.getLastModified() != null)
         to.setLastModified(from.getLastModified());
      to.setType(ResourceType.BLOB);
      if (from instanceof MutableObjectInfoWithMetadata)
         to.setUserMetadata(((MutableObjectInfoWithMetadata) from).getMetadata());
      if (isDirectoryStrategy.execute(to)) {
         to.setType(ResourceType.RELATIVE_PATH);
      }
      return to;
   }
}