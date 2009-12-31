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
package org.jclouds.vcloud.domain.internal;

import java.net.URI;

import javax.annotation.Nullable;

import org.jclouds.vcloud.VCloudMediaType;
import org.jclouds.vcloud.domain.VAppStatus;
import org.jclouds.vcloud.domain.VAppTemplate;

/**
 * 
 * @author Adrian Cole
 * 
 */
public class VAppTemplateImpl extends NamedResourceImpl implements VAppTemplate {

   /** The serialVersionUID */
   private static final long serialVersionUID = 8464716396538298809L;
   private final String description;
   private final VAppStatus status;

   public VAppTemplateImpl(String id, String name, URI location, @Nullable String description,
            @Nullable VAppStatus status) {
      super(id, name, VCloudMediaType.VAPPTEMPLATE_XML, location);
      this.description = description;
      this.status = status;
   }

   @Override
   public String getDescription() {
      return description;
   }

   public VAppStatus getStatus() {
      return status;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((description == null) ? 0 : description.hashCode());
      result = prime * result + ((status == null) ? 0 : status.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (!super.equals(obj))
         return false;
      if (getClass() != obj.getClass())
         return false;
      VAppTemplateImpl other = (VAppTemplateImpl) obj;
      if (description == null) {
         if (other.description != null)
            return false;
      } else if (!description.equals(other.description))
         return false;
      if (status == null) {
         if (other.status != null)
            return false;
      } else if (!status.equals(other.status))
         return false;
      return true;
   }

   @Override
   public String toString() {
      return "VAppTemplateImpl [description=" + description + ", status=" + status + "]";
   }

}