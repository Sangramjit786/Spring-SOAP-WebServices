package com.soap.webservices.endpoint;

import com.java_soap.courses.CourseDetails;
import com.java_soap.courses.GetCourseDetailsRequest;
import com.java_soap.courses.GetCourseDetailsResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CourseDetailsEndpoint {

    // method
    // input - GetCourseDetailsRequest
    // output - GetCourseDetailsResponse


    //http://java-soap.com/courses
    //GetCourseDetailsRequest
    @PayloadRoot(namespace = "http://java-soap.com/courses",
            localPart="GetCourseDetailsRequest")
    @ResponsePayload
    public GetCourseDetailsResponse
        processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();

        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setId(request.getId());
        courseDetails.setName("Microservices Course");
        courseDetails.setDescription("That would be a wonderful course!");

        response.setCourseDetails(courseDetails);

        return response;
    }
}
