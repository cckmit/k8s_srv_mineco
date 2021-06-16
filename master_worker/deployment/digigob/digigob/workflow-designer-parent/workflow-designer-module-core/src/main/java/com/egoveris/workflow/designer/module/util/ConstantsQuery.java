package com.egoveris.workflow.designer.module.util;

public class ConstantsQuery {

	
	public static final  String FIND_BY_STATE_NAME_VERSION = "select s from SubProcess s where s.stateFlow =:stateFlow"
	 		+ " and s.stateName =:stateName and s.version=:version order by s.id asc ";
	
	public static final String UPDATE_SUBPROCESS_VERSION = "update SubProcess set version = :projectVersion"
			+ " where stateFlow = :projectName and version = (:projectVersion - 1)";
	
	public static final String UPDATE_SUBPROCESS_PROJECT = "update SubProcess set stateFlow = :projectName"
			+ " where stateFlow = :projectNameOld and version = :projectVersion ";
	
	public static final String  UPDATE_PROJECT = "update ProjectDesginer set name = :projectName where name = :projectNameOld";
	
	public static final  String UPDATE_SUBPROCESS_PROJECT_PROCEDURE = "update SubProcess set versionProcedure = :versionProcedure"
			+ " where stateFlow = :projectName ";
	
	public static final String FIND_LAST_VERSION_BY_PROJECTNAME = "select versionProcedure from SubProcess where stateFlow = :projectName "
			+ " group by versionProcedure order by versionProcedure desc";
	
	private ConstantsQuery(){}
	
}
