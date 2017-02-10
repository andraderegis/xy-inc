package com.zup.xyapi.controller.impl;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zup.xyapi.controller.BaseController;
import com.zup.xyapi.dao.impl.InterestPointDAO;
import com.zup.xyapi.model.InterestPoint;
import com.zup.xyapi.response.ApiResponse;

@Controller
@Scope("request")
@RequestMapping("/points")
@EnableAsync
public class InterestPointController extends BaseController<InterestPoint> {

	@Autowired
	private InterestPointDAO dao;

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public Callable<ResponseEntity<ApiResponse<InterestPoint>>> save(@Validated @RequestBody InterestPoint point) {
		return () -> {
			dao.save(point);
			return SuccessResponse(null);
		};
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public Callable<ResponseEntity<ApiResponse<InterestPoint>>> list() {
		return () -> {
			return SuccessResponse(dao.list());
		};
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, params = { "lat", "lng", "distance" })
	public Callable<ResponseEntity<ApiResponse<InterestPoint>>> list(
			@RequestParam(value = "lat", required = true) int lat,
			@RequestParam(value = "lng", required = true) int lng,
			@RequestParam(value = "distance", required = true) int distance) {
		return () -> {
			return SuccessResponse(dao.listByProximity(lat, lng, distance));
		};
	}
}
