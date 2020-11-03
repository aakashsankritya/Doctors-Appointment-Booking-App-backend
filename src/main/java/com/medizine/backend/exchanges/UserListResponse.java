package com.medizine.backend.exchanges;

import com.medizine.backend.dto.User;
import lombok.Builder;

import java.util.List;

public class UserListResponse extends Response<List<User>> {

    @Builder
    public UserListResponse(List<User> users) {
        super(users);
    }
}
