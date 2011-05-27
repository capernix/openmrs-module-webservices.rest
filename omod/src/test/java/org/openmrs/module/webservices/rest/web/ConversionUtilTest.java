/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.webservices.rest.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

public class ConversionUtilTest extends BaseModuleWebContextSensitiveTest {
	
	/**
	 * @see ConversionUtil#convert(Object,Class<?>)
	 * @verifies String to Date conversion for multiple formatted date/dateTime strings
	 */
	@Test
	public void convert_shouldReturnEqualsDateFromString() throws Exception {
		Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse("2011-05-01T00:00:00.000");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setTimeZone(TimeZone.getDefault());
		Date expected = cal.getTime();
		String[] dateFormats = { "2011-05-01", "2011-05-01 00:00:00", "2011-05-01T00:00:00.000", "2011-05-01T00:00:00.000" };
		for (int i = 0; i < dateFormats.length; i++) {
			Date result = (Date) ConversionUtil.convert(dateFormats[i], Date.class);
			Assert.assertEquals(result, expected);
		}
	}
	
	/**
	 * @see ConversionUtil#convert(Object,Class<?>)
	 * @verifies String to Date conversion by assert false for date mismatches
	 */
	@Test
	public void convert_shouldReturnFalseOnIncorrectDateFromString() throws Exception {
		Date expected = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse("2011-05-01T00:00:00.000+0530");
		String[] dateFormats = { "2011-05-01T00:00:00.000+0200", "2012-05-01T00:00:00.000" };
		for (int i = 0; i < dateFormats.length; i++) {
			Date result = (Date) ConversionUtil.convert(dateFormats[i], Date.class);
			Assert.assertTrue(result != expected);
		}
	}
	
	/**
	 * @see ConversionUtil#convert(Object,Class<?>)
	 * @verifies String format and its representation are equal
	 */
	@Test
	public void convertToRepresentation_shouldReturnSameStringForToday() throws Exception {
		Date today = new Date();
		String expected = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(today);
		String result = (String) ConversionUtil.convertToRepresentation(today, Representation.REF);
		Assert.assertEquals(result, expected);
	}
}
