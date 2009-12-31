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
package org.jclouds.aws.s3.blobstore.functions;

import javax.inject.Singleton;

import org.jclouds.blobstore.domain.MutableResourceMetadata;
import org.jclouds.blobstore.domain.ResourceMetadata;
import org.jclouds.blobstore.domain.ResourceType;
import org.jclouds.blobstore.domain.internal.MutableResourceMetadataImpl;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

/**
 * @author Adrian Cole
 */
@Singleton
public class CommonPrefixesToResourceMetadata implements
         Function<Iterable<String>, Iterable<ResourceMetadata>> {
   public Iterable<ResourceMetadata> apply(

   Iterable<String> prefixes) {
      return Iterables.transform(prefixes, new Function<String, ResourceMetadata>() {
         public ResourceMetadata apply(String from) {
            MutableResourceMetadata returnVal = new MutableResourceMetadataImpl();
            returnVal.setType(ResourceType.RELATIVE_PATH);
            returnVal.setName(from);
            return returnVal;
         }
      });
   }
}