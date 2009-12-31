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
package org.jclouds.rackspace.cloudservers.functions;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.jclouds.http.functions.ParseJson;
import org.jclouds.rackspace.cloudservers.domain.Image;

import com.google.gson.Gson;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.inject.internal.Lists;

/**
 * This parses {@link Image} from a gson string.
 * 
 * @author Adrian Cole
 */
@Singleton
public class ParseImageListFromJsonResponse extends ParseJson<List<Image>> {

   @Inject
   public ParseImageListFromJsonResponse(Gson gson) {
      super(gson);
   }

   private static class ImageListResponse {
      List<Image> images = Lists.newArrayList();
   }

   public List<Image> apply(InputStream stream) {

      try {
         return gson.fromJson(new InputStreamReader(stream, "UTF-8"), ImageListResponse.class).images;
      } catch (UnsupportedEncodingException e) {
         throw new RuntimeException("jclouds requires UTF-8 encoding", e);
      }
   }
}