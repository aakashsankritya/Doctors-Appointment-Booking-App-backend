package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.Doctor;
import com.medizine.backend.models.DoctorEntity;
import com.medizine.backend.repositories.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorRepositoryServiceImpl implements DoctorRepositoryService {

  @Autowired
  private DoctorRepository doctorRepository;

  @Autowired
  private Provider<ModelMapper> modelMapperProvider;

  @Override
  public List<Doctor> getAllDoctorsCloseBy() {
    List<Doctor> doctorList = new ArrayList<>();
    ModelMapper modelMapper = modelMapperProvider.get();

    List<DoctorEntity> doctorEntityList = doctorRepository.findAll();
    for (DoctorEntity doctorEntity : doctorEntityList) {
      doctorList.add(modelMapper.map(doctorEntity, Doctor.class));
    }
    return doctorList;
  }
}
