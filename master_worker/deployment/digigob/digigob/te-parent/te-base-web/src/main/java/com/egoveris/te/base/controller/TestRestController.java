package com.egoveris.te.base.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that exposes the application as a REST API
 * 
 * @author everis
 */
@RestController
@RequestMapping(value = TestRestController.MAPPING)
public class TestRestController {

	/** Root context. */
	public static final String MAPPING = "/test";

	/** The success message */
	private static final String SUCCESS_MESSAGE = "Successful operation";

	/**
	 * Lists the TO-DOs
	 * 
	 * @param completed
	 *            flag to filter the completed TO-DOs
	 * @return list of TO-DOs
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public String getList() {
		return SUCCESS_MESSAGE;
	}

}
