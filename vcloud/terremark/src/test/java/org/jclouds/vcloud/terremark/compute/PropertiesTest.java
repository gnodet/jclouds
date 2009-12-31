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
package org.jclouds.vcloud.terremark.compute;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Properties;

import org.jclouds.vcloud.terremark.TerremarkVCloudContextBuilder;
import org.jclouds.vcloud.terremark.TerremarkVCloudPropertiesBuilder;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.io.Resources;

/**
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "compute.PropertiesTest")
public class PropertiesTest {
   private Properties properties;

   @BeforeTest
   public void setUp() throws IOException {
      properties = new Properties();
      properties.load(Resources.newInputStreamSupplier(Resources.getResource("compute.properties"))
               .getInput());
   }

   public void testRimu() {
      assertEquals(properties.getProperty("terremark.contextbuilder"),
               TerremarkVCloudContextBuilder.class.getName());
      assertEquals(properties.getProperty("terremark.propertiesbuilder"),
               TerremarkVCloudPropertiesBuilder.class.getName());
   }

}
