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
package org.jclouds.azure.storage.blob.options;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

/**
 * Tests behavior of {@code ListBlobsOptions}
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "azurestorage.ListBlobsOptionsTest")
public class ListBlobsOptionsTest {

   public void testDelimiter() {
      ListBlobsOptions options = new ListBlobsOptions().delimiter("/");
      assertEquals(ImmutableList.of("/"), options.buildQueryParameters().get("delimiter"));
   }

   public void testDelimiterStatic() {
      ListBlobsOptions options = ListBlobsOptions.Builder.delimiter("/");
      assertEquals(ImmutableList.of("/"), options.buildQueryParameters().get("delimiter"));
   }

}
