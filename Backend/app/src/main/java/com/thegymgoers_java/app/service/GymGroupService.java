package com.thegymgoers_java.app.service;

import com.thegymgoers_java.app.repository.GymGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class GymGroupService {

    @Autowired
    GymGroupRepository gymGroupRepository;
}
