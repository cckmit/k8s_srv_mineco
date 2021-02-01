package com.egoveris.plugins.manager.dao;

import java.util.List;

import com.egoveris.plugins.manager.model.Version;

public interface IDao {

	public abstract void init(String directory);

	public abstract <T> void save(T obj);

	/**
	 * Method to get all snapshot versions
	 * @return
	 */
	public abstract List<Version> getAllVersions();

	/**
	 * Method to get the active snapshot version
	 * @return
	 */
	public abstract Version getActiveSnapshot();

}