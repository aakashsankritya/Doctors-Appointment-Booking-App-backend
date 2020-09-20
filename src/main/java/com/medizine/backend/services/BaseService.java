package com.medizine.backend.services;

import com.medizine.backend.exchanges.GetUserResponse;

public interface BaseService {
  GetUserResponse getAvailableDoctors();
}
