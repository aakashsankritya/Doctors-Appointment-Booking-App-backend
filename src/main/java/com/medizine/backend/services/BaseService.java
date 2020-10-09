package com.medizine.backend.services;

import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.GetUserResponse;

public interface BaseService {

  GetUserResponse getAvailableDoctors();

  BaseResponse<?> findEntityById(String id);

  BaseResponse<?> deleteEntity(String id);

  BaseResponse<?> restoreEntity(String id);

}
