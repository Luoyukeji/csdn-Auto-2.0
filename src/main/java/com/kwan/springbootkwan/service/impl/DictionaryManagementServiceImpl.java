package com.kwan.springbootkwan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kwan.springbootkwan.entity.DictionaryManagement;
import com.kwan.springbootkwan.mapper.DictionaryManagementMapper;
import com.kwan.springbootkwan.service.DictionaryManagementService;
import org.springframework.stereotype.Service;

@Service
public class DictionaryManagementServiceImpl extends ServiceImpl<DictionaryManagementMapper, DictionaryManagement> implements DictionaryManagementService {

}

