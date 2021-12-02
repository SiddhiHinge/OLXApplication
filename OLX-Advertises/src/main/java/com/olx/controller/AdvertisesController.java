package com.olx.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.Advertise;
import com.olx.service.AdvertisesService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("olx/advertise")
public class AdvertisesController {

	@Autowired
	private AdvertisesService advertisesService;

	static ArrayList<Advertise> advertises = new ArrayList<Advertise>();

	@PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="This REST endpoint creates new Advertise")
	public ResponseEntity<Advertise> createNewAdvertise(@RequestBody Advertise advertise,
			@RequestHeader("auth-token") String authToken) {
		Advertise responseAdvertise = advertisesService.createNewAdvertise(advertise, authToken);
		return new ResponseEntity<Advertise>(responseAdvertise, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="This REST endpoint updates Advertise")
	public ResponseEntity<Advertise> updateAdvertise(@RequestBody Advertise advertise, @RequestHeader("auth-token") String authToken,
			@PathVariable("id") int advertiseId) {
		Advertise responseAdvertise = advertisesService.updateAdvertise(advertise, authToken , advertiseId);
		return new ResponseEntity<Advertise>(responseAdvertise, HttpStatus.OK);
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="This REST endpoint gets all Advertises")
	public ResponseEntity<List<Advertise>> getAllAdvertises(@RequestHeader("auth-token") String authToken) {
		List<Advertise> listOfAdvertise = advertisesService.getAllAdvertises(authToken);
		return new ResponseEntity<List<Advertise>>(listOfAdvertise, HttpStatus.OK);
	}

	@GetMapping(value = "/user/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="This REST endpoint gets Advertise by user Id")
	public ResponseEntity<Advertise> getAdvertiseByUserPostId(@RequestHeader("auth-token") String authToken,
			@PathVariable("postId") int postId) {
		Advertise advertise = advertisesService.getAdvertiseByUserPostId(authToken, postId);
		return new ResponseEntity<Advertise>(advertise, HttpStatus.OK);

	}

	@DeleteMapping(value = "/{postId}")
	@ApiOperation(value="This REST endpoint deletes Advertise by Id")
	public ResponseEntity<Boolean> deleteAdvertiseByPostId(@RequestHeader("auth-token") String authToken,
			@PathVariable("postId") int postId) {
		Boolean isDeleted = advertisesService.deleteAdvertiseByPostId(authToken, postId);
		return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);
	}

	@GetMapping(value = "/search/filtercriteria", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="This REST endpoint gets list of Advertises according to the filter criteria")
	public List<Advertise> getAdvertiseByCriteria(@RequestParam(value = "searchText", required=false) String searchText,
			@RequestParam(value = "category", required=false) String category, @RequestParam(value ="postedBy" , required=false) String postedBy,
			@RequestParam(value = "dateCondition" , required=false) String dateCondition, @RequestParam(value = "onDate" , required=false) String onDate,
			@RequestParam(value = "fromDate" , required=false) String fromDate, @RequestParam(value = "toDate" , required=false) String toDate,
			@RequestParam(value = "sortBy" , required=false, defaultValue = "Lower") String sortBy, @RequestParam(value = "startIndex" , required=false , defaultValue = "0") int startIndex,
			@RequestParam(value = "records" , required=false , defaultValue = "3") int records) {
		return advertisesService.getAdvertiseByCriteria(searchText, category, postedBy, dateCondition, onDate, fromDate,
				toDate, sortBy, startIndex, records);
	}

	@GetMapping(value = "/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="This REST endpoint gets Advertise by Id")
	public ResponseEntity<Advertise> getAdvertiseByPostId(@RequestHeader("auth-token") String authToken,
			@PathVariable("postId") int postId) {
		Advertise advertise = advertisesService.getAdvertiseByPostId(authToken, postId);
		return new ResponseEntity<Advertise>(advertise, HttpStatus.OK);
	}

	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="This REST endpoint gets list of Advertises according to the search text")
	public List<Advertise> getAdvertiseBySearchText(@RequestParam("searchText") String searchText) {
		return advertisesService.getAdvertiseBySearchText(searchText);
	}
}
