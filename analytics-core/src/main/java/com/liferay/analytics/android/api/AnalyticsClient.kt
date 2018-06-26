/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.analytics.android.api

import com.liferay.analytics.android.model.AnalyticsEvents
import com.liferay.analytics.android.util.HttpClient
import com.liferay.analytics.android.util.JSONParser
import java.io.IOException

/**
 * @author Igor Matos
 * @author Allan Melo
 */
internal class AnalyticsClient {

	val analyticsGatewayHost: String
		get() = ANALYTICS_GATEWAY_HOST

	val analyticsGatewayPath: String
		get() = ANALYTICS_GATEWAY_PATH

	val analyticsGatewayPort: String
		get() = ANALYTICS_GATEWAY_PORT

	val analyticsGatewayProtocol: String
		get() = ANALYTICS_GATEWAY_PROTOCOL

	@Throws(IOException::class)
	fun sendAnalytics(analyticsEvents: AnalyticsEvents): String {
		val json = JSONParser.toJSON(analyticsEvents)

		val analyticsPath = if (analyticsGatewayPort.isEmpty()) "$analyticsGatewayProtocol://" +
			"$analyticsGatewayHost$analyticsGatewayPath" else "$analyticsGatewayProtocol://" +
			"$analyticsGatewayHost:$analyticsGatewayPort$analyticsGatewayPath"

		return HttpClient.post(analyticsPath, json)
	}

	companion object {
		private const val ANALYTICS_GATEWAY_HOST = "analytics-gw.liferay.com"
		private const val ANALYTICS_GATEWAY_PATH = "/api/analyticsgateway/send-analytics-events"
		private const val ANALYTICS_GATEWAY_PORT = ""
		private const val ANALYTICS_GATEWAY_PROTOCOL = "https"
	}
}