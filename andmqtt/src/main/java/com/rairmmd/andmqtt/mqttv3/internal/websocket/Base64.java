/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *    James Sutton - Bug 459142 - WebSocket support for the Java client.
 */
package com.rairmmd.andmqtt.mqttv3.internal.websocket;

import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;

public class Base64 {
	
	private static final Base64 instance = new Base64();
	private static final Base64Encoder encoder = instance.new Base64Encoder();
	
	public static String encode (String s){
		encoder.putByteArray("akey", s.getBytes());
		String result = encoder.getBase64String();
		return result;
	}
	
	public static String encodeBytes (byte[] b){
		encoder.putByteArray("aKey", b);
		String result = encoder.getBase64String();
		return result;
		
	}
	
	public class Base64Encoder extends AbstractPreferences {

		private String base64String = null;
		
		public Base64Encoder() {
			super(null, "");
		}

		
		@Override
		protected void putSpi(String key, String value) {
			base64String = value;	
		}
		
		public String getBase64String() {
			return base64String;
		}

		
		@Override
		protected String getSpi(String key) {
			return null;
		}

		
		@Override
		protected void removeSpi(String key) {
		}

		
		@Override
		protected void removeNodeSpi() throws BackingStoreException {
			
		}

		
		@Override
		protected String[] keysSpi() throws BackingStoreException {
			return null;
		}

		
		@Override
		protected String[] childrenNamesSpi() throws BackingStoreException {
			return null;
		}

		
		@Override
		protected AbstractPreferences childSpi(String name) {
			return null;
		}

		
		@Override
		protected void syncSpi() throws BackingStoreException {
			
		}

		
		@Override
		protected void flushSpi() throws BackingStoreException {
			
		}
	
	}

}
