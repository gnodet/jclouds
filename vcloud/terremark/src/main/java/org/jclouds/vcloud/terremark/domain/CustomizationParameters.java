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
package org.jclouds.vcloud.terremark.domain;

/**
 * The response lists which customization options are supported for this particular vApp. The
 * possible customization options are Network and Password.
 * 
 * @author Adrian Cole
 * @see <a
 *      href="https://community.vcloudexpress.terremark.com/en-us/product_docs/w/wiki/6-using-the-vcloud-express-api.aspx"
 *      >Terremark documentation</a>
 */
public class CustomizationParameters {
   private final boolean customizeNetwork;
   private final boolean customizePassword;

   public CustomizationParameters(boolean customizeNetwork, boolean customizePassword) {
      this.customizeNetwork = customizeNetwork;
      this.customizePassword = customizePassword;
   }

   public boolean canCustomizeNetwork() {
      return customizeNetwork;
   }

   public boolean canCustomizePassword() {
      return customizePassword;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + (customizeNetwork ? 1231 : 1237);
      result = prime * result + (customizePassword ? 1231 : 1237);
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      CustomizationParameters other = (CustomizationParameters) obj;
      if (customizeNetwork != other.customizeNetwork)
         return false;
      if (customizePassword != other.customizePassword)
         return false;
      return true;
   }

   @Override
   public String toString() {
      return "CustomizationParameters [customizeNetwork=" + customizeNetwork
               + ", customizePassword=" + customizePassword + "]";
   }
}