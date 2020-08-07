package com.example.demo.controller;


import com.example.demo.entity.SysUser;
import com.example.demo.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import java.util.List;

/**
* @author cc
* @since 2020-08-07
*/
@Api(tags = {"用户管理"})
@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "获取全部用户")
    @GetMapping("/getAllUsers")
    @ApiResponses(
            @ApiResponse(code = 200, message = "",response = SysUser.class)
    )
    public List<SysUser> getAllUsers(){
        List<SysUser> list = sysUserService.list();
        return list;
    }


    @ApiOperation(value = "获取全部用户")
    @PostMapping("/addAllUsers")
    public Object addAllUsers(@RequestBody SysUser sysUser){

        sysUserService.save(sysUser);
        return null;
    }
}

