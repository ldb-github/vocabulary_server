package com.ldb.vocabulary.server.android.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ldb.vocabulary.server.service.IProjectService;
import com.ldb.vocabulary.server.service.impl.ProjectService;

public class ProjectInitListener implements ServletContextListener {

	IProjectService service = new ProjectService();
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		service.initProject();
	}
	

}
