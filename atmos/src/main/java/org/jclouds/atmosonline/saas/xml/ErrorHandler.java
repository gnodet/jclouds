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
package org.jclouds.atmosonline.saas.xml;

import org.jclouds.atmosonline.saas.domain.AtmosStorageError;
import org.jclouds.http.functions.ParseSax;

/**
 * Parses the error from the Atmos Online Storage REST API.
 * 
 * @author Adrian Cole
 */
public class ErrorHandler extends ParseSax.HandlerWithResult<AtmosStorageError> {

   private StringBuilder currentText = new StringBuilder();
   private int code;
   private String message;

   public AtmosStorageError getResult() {
      return new AtmosStorageError(code, message);
   }

   public void endElement(String uri, String name, String qName) {

      if (qName.equals("Code")) {
         this.code = Integer.parseInt(currentText.toString().trim());
      } else if (qName.equals("Message")) {
         this.message = currentText.toString().trim();
      }
      currentText = new StringBuilder();
   }

   public void characters(char ch[], int start, int length) {
      currentText.append(ch, start, length);
   }
}
