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
package org.jclouds.aws.ec2.services;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jclouds.aws.ec2.domain.Image;
import org.jclouds.aws.ec2.domain.Permission;
import org.jclouds.aws.ec2.domain.Region;
import org.jclouds.aws.ec2.domain.Image.EbsBlockDevice;
import org.jclouds.aws.ec2.options.CreateImageOptions;
import org.jclouds.aws.ec2.options.DescribeImagesOptions;
import org.jclouds.aws.ec2.options.RegisterImageBackedByEbsOptions;
import org.jclouds.aws.ec2.options.RegisterImageOptions;
import org.jclouds.concurrent.Timeout;

/**
 * Provides access to EC2 via their REST API.
 * <p/>
 * 
 * @author Adrian Cole
 */
@Timeout(duration = 30, timeUnit = TimeUnit.SECONDS)
public interface AMIClient {

   /**
    * Returns information about AMIs, AKIs, and ARIs. This includes image type, product codes,
    * architecture, and kernel and RAM disk IDs. Images available to you include p ublic images,
    * private images that you own, and private images owned by other users for which you have
    * explicit launch permissions.
    * 
    * @param region
    *           AMIs are tied to the Region where its files are located within Amazon S3.
    * @see InstanceClient#describeInstances
    * @see #describeImageAttribute
    * @see <a href="http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/ApiReference-query-DescribeImages.html"
    *      />
    * @see DescribeImagesOptions
    */
   @Timeout(duration = 180, timeUnit = TimeUnit.SECONDS)
   Set<Image> describeImagesInRegion(Region region, DescribeImagesOptions... options);

   /**
    * Returns the Product Codes of an image.
    * 
    * @param region
    *           AMIs are tied to the Region where its files are located within Amazon S3.
    * @param imageId
    *           The ID of the AMI for which an attribute will be described
    * @see #describeImages
    * @see #modifyImageAttribute
    * @see #resetImageAttribute
    * @see <a href="http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/ApiReference-query-DescribeImageAttribute.html"
    *      />
    * @see DescribeImagesOptions
    */
   Set<String> getProductCodesForImageInRegion(Region region, String imageId);

   /**
    * Returns a map of device name to block device for the image.
    * 
    * @param region
    *           AMIs are tied to the Region where its files are located within Amazon S3.
    * @param imageId
    *           The ID of the AMI for which an attribute will be described
    * @see #describeImages
    * @see #modifyImageAttribute
    * @see #resetImageAttribute
    * @see <a href="http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/ApiReference-query-DescribeImageAttribute.html"
    *      />
    * @see DescribeImagesOptions
    */
   Map<String, EbsBlockDevice> getBlockDeviceMappingsForImageInRegion(Region region, String imageId);

   /**
    * Creates an AMI that uses an Amazon EBS root device from a "running" or "stopped" instance.
    * 
    * @param region
    *           AMIs are tied to the Region where its files are located within Amazon S3.
    * @param name
    *           The name of the AMI that was provided during image creation. 3-128 alphanumeric
    *           characters, parenthesis (()), commas (,), slashes (/), dashes (-), or underscores(_)
    * @param instanceId
    *           The ID of the instance.
    * @return imageId
    * 
    * @see <a href="http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/ApiReference-query-DescribeImages.html"
    *      />
    * @see CreateImageOptions
    * @see InstanceClient#runInstances
    * @see InstanceClient#describeInstances
    * @see InstanceClient#terminateInstances
    * @see <a href=
    *      "http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/ApiReference-query-CreateImage.html"
    *      />
    */
   String createImageInRegion(Region region, String name, String instanceId,
            CreateImageOptions... options);

   /**
    * 
    * Deregisters the specified AMI. Once deregistered, the AMI cannot be used to launch new
    * instances.
    * 
    * @param region
    *           AMIs are tied to the Region where its files are located within Amazon S3.
    * @param imageId
    *           Unique ID of the AMI which was assigned during registration. To register an AMI, use
    *           RegisterImage. To view the AMI IDs of AMIs that belong to your account. use
    *           DescribeImages.
    * 
    * @see #describeImages
    * @see #registerImage
    * @see <a href="http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/ApiReference-query-DeregisterImage.html"
    *      />
    */
   void deregisterImageInRegion(Region region, String imageId);

   /**
    * Registers an AMI with Amazon EC2. Images must be registered before they can be launched. To
    * launch instances, use the {@link InstanceClient#runInstances} operation.
    * <p/>
    * Each AMI is associated with an unique ID which is provided by the Amazon EC2 service through
    * this operation. If needed, you can deregister an AMI at any time.
    * <p/>
    * <h3>Note</h3> Any modifications to an AMI backed by Amazon S3 invalidates this registration.
    * If you make changes to an image, deregister the previous image and register the new image.
    * 
    * @param region
    *           AMIs are tied to the Region where its files are located within Amazon S3.
    * @param name
    *           The name of the AMI that was provided during image creation. 3-128 alphanumeric
    *           characters, parenthesis (()), commas (,), slashes (/), dashes (-), or underscores(_)
    * @param pathToManifest
    *           Full path to your AMI manifest in Amazon S3 storage.
    * @param options
    *           Options to specify metadata such as architecture or secondary volumes to be
    *           associated with this image.
    * @return imageId
    * 
    * @see #describeImages
    * @see #deregisterImage
    * @see <a href=
    *      "http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/ApiReference-query-RegisterImage.html"
    *      />
    */
   String registerImageFromManifestInRegion(Region region, String name, String pathToManifest,
            RegisterImageOptions... options);

   /**
    * Registers an AMI with Amazon EC2. Images must be registered before they can be launched. To
    * launch instances, use the {@link InstanceClient#runInstances} operation. The root device name
    * is /dev/sda1
    * <p/>
    * Each AMI is associated with an unique ID which is provided by the Amazon EC2 service through
    * this operation. If needed, you can deregister an AMI at any time.
    * <p/>
    * <h3>Note</h3> AMIs backed by Amazon EBS are automatically registered when you create the
    * image. However, you can use this to register a snapshot of an instance backed by Amazon EBS.
    * <p/>
    * Amazon EBS snapshots are not guaranteed to be bootable.
    * 
    * @param region
    *           AMIs are tied to the Region where its files are located within Amazon S3.
    * @param name
    *           The name of the AMI that was provided during image creation. 3-128 alphanumeric
    *           characters, parenthesis (()), commas (,), slashes (/), dashes (-), or underscores(_)
    * 
    * @param ebsSnapshotId
    *           The id of the root snapshot (e.g., snap-6eba6e06).
    * @param options
    *           Options to specify metadata such as architecture or secondary volumes to be
    *           associated with this image.
    * @return imageId
    * 
    * @see #describeImages
    * @see #deregisterImage
    * @see <a href=
    *      "http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/ApiReference-query-RegisterImage.html"
    *      />
    */
   String registerUnixImageBackedByEbsInRegion(Region region, String name, String ebsSnapshotId,
            RegisterImageBackedByEbsOptions... options);
   
   /**
    * Returns the {@link Permission}s of an image.
    * 
    * @param region
    *           AMIs are tied to the Region where its files are located within Amazon S3.
    * @param imageId
    *           The ID of the AMI for which an attribute will be described
    * @see #describeImages
    * @see #modifyImageAttribute
    * @see #resetImageAttribute
    * @see <a href="http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/ApiReference-query-DescribeImageAttribute.html"
    *      />
    * @see DescribeImagesOptions
    */
   Permission getLaunchPermissionForImageInRegion(Region region, String imageId);

   /**
    * Adds {@code launchPermission}s to an AMI.
    * 
    * @param region
    *           AMIs are tied to the Region where its files are located within Amazon S3.
    * @param userIds
    *           AWS Access Key ID.
    * @param userGroups
    *           Name of the groups. Currently supports \"all.\""
    * @param imageId
    *           The AMI ID.
    * 
    * @see #removeLaunchPermissionsFromImage
    * @see #describeImageAttribute
    * @see #resetImageAttribute
    * @see <a href="http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/ApiReference-query-ModifyImageAttribute.html"
    *      />
    */
   void addLaunchPermissionsToImageInRegion(Region region, Iterable<String> userIds,
            Iterable<String> userGroups, String imageId);

   /**
    * Resets the {@code launchPermission}s on an AMI.
    * 
    * @param region
    *           AMIs are tied to the Region where its files are located within Amazon S3.
    * @param imageId
    *           ID of the AMI on which the attribute will be reset.
    * 
    * @see #addLaunchPermissionsToImage
    * @see #describeImageAttribute
    * @see #removeProductCodesFromImage
    * @see <a href="http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/ApiReference-query-ResetImageAttribute.html"
    *      />
    */
   void resetLaunchPermissionsOnImageInRegion(Region region, String imageId);

   /**
    * Removes {@code launchPermission}s from an AMI.
    * 
    * @param region
    *           AMIs are tied to the Region where its files are located within Amazon S3.
    * @param userIds
    *           AWS Access Key ID.
    * @param userGroups
    *           Name of the groups. Currently supports \"all.\""
    * @param imageId
    *           The AMI ID.
    * 
    * @see #addLaunchPermissionsToImage
    * @see #describeImageAttribute
    * @see #resetImageAttribute
    * @see <a href="http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/ApiReference-query-ModifyImageAttribute.html"
    *      />
    */
   void removeLaunchPermissionsFromImageInRegion(Region region, Iterable<String> userIds,
            Iterable<String> userGroups, String imageId);

   /**
    * Adds {@code productCode}s to an AMI.
    * 
    * @param region
    *           AMIs are tied to the Region where its files are located within Amazon S3.
    * @param productCodes
    *           Product Codes
    * @param imageId
    *           The AMI ID.
    * 
    * @see #removeProductCodesFromImage
    * @see #describeImageAttribute
    * @see #resetImageAttribute
    * @see <a href="http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/ApiReference-query-ModifyImageAttribute.html"
    *      />
    */
   void addProductCodesToImageInRegion(Region region, Iterable<String> productCodes, String imageId);

   /**
    * Removes {@code productCode}s from an AMI.
    * 
    * @param region
    *           AMIs are tied to the Region where its files are located within Amazon S3.
    * @param productCodes
    *           Product Codes
    * @param imageId
    *           The AMI ID.
    * 
    * @see #addProductCodesToImage
    * @see #describeImageAttribute
    * @see #resetImageAttribute
    * @see <a href="http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/ApiReference-query-ModifyImageAttribute.html"
    *      />
    */
   void removeProductCodesFromImageInRegion(Region region, Iterable<String> productCodes,
            String imageId);
}
