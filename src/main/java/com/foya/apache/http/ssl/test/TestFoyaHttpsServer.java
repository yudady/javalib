/* ==================================================================== Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. ====================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the Apache Software Foundation. For more information on the Apache Software Foundation, please see <http://www.apache.org/>. */

package com.foya.apache.http.ssl.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foya.apache.http.ssl.coltroller.IndexController;
import com.foya.apache.http.ssl.coltroller.NoMatchController;
import com.foya.apache.http.ssl.coltroller.TstarController;
import com.foya.apache.http.ssl.server.FoyaHttpsServer;

public class TestFoyaHttpsServer {

	private static final Logger mLogger = LoggerFactory.getLogger(TestFoyaHttpsServer.class);

	public static void main(String[] args) throws Exception {
		FoyaHttpsServer server = new FoyaHttpsServer();
		server.setTimeout(5000);

		// Initialize the server-side request handler
		server.registerHandler("*", new NoMatchController());
		server.registerHandler("/tstar", new TstarController());
		server.registerHandler("/", new IndexController());

		server.start();
		for (int i = 0; i < 15; i++) {
			Thread.sleep(1000);
			System.out.println(i);
			mLogger.debug("" + i);
		}
		server.shutdown();
		mLogger.debug("end");
	}

}
