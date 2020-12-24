package com.medizine.backend.services;

import com.medizine.backend.exchanges.DoctorListResponse;
import org.springframework.http.ResponseEntity;

public interface BaseService {

    DoctorListResponse getAvailableDoctors();

    ResponseEntity<?> findEntityById(String id);

    ResponseEntity<?> deleteEntity(String id);

    ResponseEntity<?> restoreEntity(String id);

    ResponseEntity<?> findEntityByPhone(String countryCode, String phoneNumber);
}
