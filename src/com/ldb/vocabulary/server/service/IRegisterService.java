package com.ldb.vocabulary.server.service;

import com.ldb.vocabulary.server.domain.RegisterBean;

public interface IRegisterService {

	String getCheckCode(RegisterBean registerBean);
	String register(RegisterBean registerBean);
}
