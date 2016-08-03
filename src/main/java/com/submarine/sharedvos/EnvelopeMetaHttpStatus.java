package com.submarine.sharedvos;

public enum EnvelopeMetaHttpStatus {

	// 2xx Success
	HTTP_200(200, "OK", "General status code. Most common code used to indicate success."),

	// 4xx Client Error
	HTTP_400(
			400,
			"Bad Request",
			"General error when fulfilling the request would cause an invalid state. Domain validation errors, missing data, etc. are some examples."), HTTP_401(
			401, "Unauthorized", "Error code response for missing or invalid authentication token."),

	HTTP_403(
			403,
			"Forbidden",
			"Error code for user not authorized to perform the operation or the resource is unavailable for some reason (e.g. time constraints, etc.)."), /**
                                                                                                                                                                   *
                                                                                                                                                                   */
	HTTP_404(
			404,
			"Not Found",
			"Requested resource is not found, whether it doesn't exist or if there was a 401 or 403 that, for security reasons, the service wants to mask."),

	HTTP_405(
			405,
			"Method Not Allowed",
			"A request was made of a resource using a request method not supported by that resource; for example, using GET on a form which requires data to be presented via POST, or using PUT on a read-only resource."),

	HTTP_410(
			410,
			"Gone",
			"Indicates that the resource requested is no longer available and will not be available again. This should be used when a resource has been intentionally removed and the resource should be purged. Upon receiving a 410 status code, the client should not request the resource again in the future. Clients such as search engines should remove the resource from their indices. Most use cases do not require clients and search engines to purge the resource, and a '404 Not Found' may be used instead."),

	HTTP_415(
			415,
			"Unsupported Media Type",
			"The server is refusing to service the request because the entity of the request is in a format not supported by the requested resource for the requested method."),

	HTTP_417(417, "Expectation Failed", "The server cannot meet the requirements of the Expect request-header field."),

	HTTP_429(
			429,
			"Too Many Requests",
			"The user has sent too many requests in a given amount of time. Intended for use with rate limiting schemes. Proposed in an Internet-Draft"),

	// server
	HTTP_500(500, "Internal Server Error", "The general catch-all error when the server-side throws an exception.");

	public Integer CODE; // HTTP STATUS
	public String TYPE; // HTTP MESSAGE
	public String MESSAGE; // HTTP STATUS DESCRIPTION

	EnvelopeMetaHttpStatus(final Integer CODE, final String TYPE, final String MESSAGE) {
		this.CODE = CODE;
		this.TYPE = TYPE;
		this.MESSAGE = MESSAGE;
	}

}
