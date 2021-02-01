package com.egoveris.shared.map;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;

public class ListMapper {

	private ListMapper() {
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List mapList(List sourceList,Mapper mapper,Class targetClass){
		List destinationList = new  ArrayList<>();
		for (Object object : sourceList) {
			destinationList.add(mapper.map(object, targetClass));
		}
		return destinationList;
	}
}
