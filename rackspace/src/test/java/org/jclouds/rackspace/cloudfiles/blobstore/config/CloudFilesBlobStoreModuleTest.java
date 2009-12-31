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
package org.jclouds.rackspace.cloudfiles.blobstore.config;

import static org.testng.Assert.assertEquals;

import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.internal.BlobStoreContextImpl;
import org.jclouds.concurrent.WithinThreadExecutorService;
import org.jclouds.concurrent.config.ExecutorServiceModule;
import org.jclouds.logging.jdk.config.JDKLoggingModule;
import org.jclouds.rackspace.StubRackspaceAuthenticationModule;
import org.jclouds.rackspace.cloudfiles.CloudFilesAsyncClient;
import org.jclouds.rackspace.cloudfiles.CloudFilesClient;
import org.jclouds.rackspace.cloudfiles.config.CloudFilesStubClientModule;
import org.jclouds.rackspace.reference.RackspaceConstants;
import org.jclouds.util.Jsr330;
import org.testng.annotations.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

/**
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "cloudfiles.CloudFilesBlobStoreModuleTest")
public class CloudFilesBlobStoreModuleTest {

   Injector createInjector() {
      return Guice.createInjector(new ExecutorServiceModule(new WithinThreadExecutorService()),
               new JDKLoggingModule(), new CloudFilesStubClientModule(),
               new StubRackspaceAuthenticationModule(), new CloudFilesBlobStoreContextModule() {
                  @Override
                  protected void configure() {
                     bindConstant().annotatedWith(
                              Jsr330.named(RackspaceConstants.PROPERTY_RACKSPACE_USER)).to("user");
                     bindConstant().annotatedWith(
                              Jsr330.named(RackspaceConstants.PROPERTY_RACKSPACE_KEY)).to("key");
                     bindConstant().annotatedWith(
                              Jsr330.named(RackspaceConstants.PROPERTY_RACKSPACE_ENDPOINT)).to(
                              "http://localhost");
                     super.configure();
                  }
               });
   }

   @Test
   void testContextImpl() {
      BlobStoreContext<CloudFilesAsyncClient, CloudFilesClient> context = createInjector()
               .getInstance(
                        Key
                                 .get(new TypeLiteral<BlobStoreContext<CloudFilesAsyncClient, CloudFilesClient>>() {
                                 }));
      assertEquals(context.getClass(), BlobStoreContextImpl.class);
   }

}