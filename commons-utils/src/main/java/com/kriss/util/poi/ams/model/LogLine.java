package com.kriss.util.poi.ams.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class LogLine {

	// The @timestamp in the log
	public Date estTimeStamp;
	
	// The message in the log
	public String rawMessage;
	
	public Date utcTimeInMessage;
	public String threadNum;
	public String coid;
	public String logLevel;
	public String className;
	public String messageAfter;
	public String logMessage;
	
	public String vin;
	public String vehicleId;
	
	public String localRequestId;
	public String httpMethod;
	public String uri;
	public String requestHeaders;
	public String requestBody;
	
	public Map<String, String> dataMap;
	
	// The kubernetes.pod.name in the log
	public String podName;
	
	// The kubernetes.labels.release in the log
	public String serviceName;
	
	public List<String> comments;
	

	public Date getEstTimeStamp() {
		return estTimeStamp;
	}

	public void setEstTimeStamp(Date estTimeStamp) {
		this.estTimeStamp = estTimeStamp;
	}

	public String getRawMessage() {
		return rawMessage;
	}

	public void setRawMessage(String rawMessage) {
		this.rawMessage = rawMessage;
	}

	public Date getUtcTimeInMessage() {
		return utcTimeInMessage;
	}

	public void setUtcTimeInMessage(Date utcTimeInMessage) {
		this.utcTimeInMessage = utcTimeInMessage;
	}

	public String getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(String threadNum) {
		this.threadNum = threadNum;
	}

	public String getCoid() {
		return coid;
	}

	public void setCoid(String coid) {
		this.coid = coid;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMessageAfter() {
		return messageAfter;
	}

	public void setMessageAfter(String messageAfter) {
		this.messageAfter = messageAfter;
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getLocalRequestId() {
		return localRequestId;
	}

	public void setLocalRequestId(String localRequestId) {
		this.localRequestId = localRequestId;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getRequestHeaders() {
		return requestHeaders;
	}

	public void setRequestHeaders(String requestHeaders) {
		this.requestHeaders = requestHeaders;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public Map<String, String> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}

	public String getPodName() {
		return podName;
	}

	public void setPodName(String podName) {
		this.podName = podName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "LogLine [estTimeStamp=" + estTimeStamp + ", utcTimeInMessage="
				+ utcTimeInMessage + ", threadNum=" + threadNum + ", coid=" + coid + ", logLevel=" + logLevel
				+ ", className=" + className + ", logMessage=" + logMessage
				+ ", vin=" + vin + ", vehicleId=" + vehicleId + ", localRequestId=" + localRequestId + ", httpMethod="
				+ httpMethod + ", uri=" + uri + ", requestHeaders=" + requestHeaders + ", requestBody=" + requestBody
				+ ", dataMap=" + dataMap + ", podName=" + podName + ", serviceName=" + serviceName + ", comments="
				+ comments + "]";
	}
}
