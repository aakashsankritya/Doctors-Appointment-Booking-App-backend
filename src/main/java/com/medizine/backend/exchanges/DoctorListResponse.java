package com.medizine.backend.exchanges;

import com.medizine.backend.dto.Doctor;

import java.util.List;

public class DoctorListResponse extends BaseResponse<List<Doctor>> {

    public DoctorListResponse(List<Doctor> data, String message) {
        super(data, message);
    }
}
