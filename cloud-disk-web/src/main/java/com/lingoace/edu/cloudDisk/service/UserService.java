package com.lingoace.edu.cloudDisk.service;


import com.lingoace.edu.cloudDisk.entity.LingoAceUserBriefInfo;

public interface UserService {
    LingoAceUserBriefInfo getUserByName(String username);
}
