package org.jclouds.aws.ec2.commands.options;

import static org.testng.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jclouds.aws.util.DateService;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

/**
 * Tests possible uses of BaseEC2RequestOptions and BaseEC2RequestOptions.Builder.*
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "ec2.BaseEC2RequestOptionsTest")
public class BaseEC2RequestOptionsTest {

   @Test
   public void testGetAction() {
      EC2RequestOptions options = new MyRequestOptions();
      assertEquals(options.getAction(), "MyRequest");
   }

   public static class MyRequestOptions extends BaseEC2RequestOptions<MyRequestOptions> {
      static {
         realClass = MyRequestOptions.class;
      }

      @Override
      public String getAction() {
         return "MyRequest";
      }

      /**
       * @see MyRequestOptions#withId(String)
       */
      public String getId() {
         return parameters.get("id");
      }

      /**
       * add the 'id' parameter to the query string
       */
      public MyRequestOptions withId(String id) {
         encodeAndReplaceParameter("id", id);
         return this;
      }

      public static class Builder extends BaseEC2RequestOptions.Builder {
         /**
          * @see MyRequestOptions#withId(String)
          */
         public static MyRequestOptions withId(String id) {
            MyRequestOptions options = new MyRequestOptions();
            return options.withId(id);
         }
      }
   }

   @Test
   public void testExpireAt() throws UnsupportedEncodingException {
      DateTime date = new DateTime();
      BaseEC2RequestOptions<EC2RequestOptions> options = MyRequestOptions.Builder.expireAt(date);
      String dateString = URLEncoder.encode(new DateService().iso8601DateFormat(date), "UTF-8");
      makeReady(options);
      assert options.buildQueryString().contains("Expires=" + dateString) : String.format(
               "%1s$ should have contained %2s$", options.buildQueryString(), dateString);
   }

   @Test(expectedExceptions = IllegalStateException.class)
   public void testBuildQueryWithNotEnoughArguments() {
      EC2RequestOptions options = new MyRequestOptions();
      options.buildQueryString();
   }

   @Test
   public void testBuildQueryWithEnoughArguments() {
      EC2RequestOptions options = new MyRequestOptions();
      makeReady(options);
      options.buildQueryString();
   }

   private void makeReady(EC2RequestOptions options) {
      options.signWith("meow", "bark");
      options.usingHost("localhost");
   }

   @Test
   public void testExpireAtStatic() throws UnsupportedEncodingException {
      DateTime date = new DateTime();
      BaseEC2RequestOptions<EC2RequestOptions> options = MyRequestOptions.Builder.expireAt(date);
      String dateString = URLEncoder.encode(new DateService().iso8601DateFormat(date), "UTF-8");
      makeReady(options);
      assert options.buildQueryString().contains("Expires=" + dateString) : String.format(
               "%1s$ should have contained %2s$", options.buildQueryString(), dateString);

   }
   
}